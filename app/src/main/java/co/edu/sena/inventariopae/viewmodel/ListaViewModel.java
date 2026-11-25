package co.edu.sena.inventariopae.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.ui.ItemTarjeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Template Method: esta clase fija los pasos (cargar, filtrar, publicar);
 * cada subclase solo dice qué consultar y cómo convertirlo en tarjetas.
 */
public abstract class ListaViewModel<T> extends ViewModel {
    private final MutableLiveData<Estado<List<ItemTarjeta>>> estado = new MutableLiveData<>();
    private List<T> todos = new ArrayList<>();
    private String filtro = "";

    protected abstract void consultar(RespuestaCallback<List<T>> cb);

    protected abstract List<ItemTarjeta> aTarjetas(List<T> datos, String filtro);

    public LiveData<Estado<List<ItemTarjeta>>> getEstado() {
        return estado;
    }

    public void cargar() {
        estado.setValue(Estado.<List<ItemTarjeta>>cargando());
        consultar(new RespuestaCallback<List<T>>() {
            @Override
            public void onExito(List<T> dato) {
                todos = (dato == null) ? new ArrayList<T>() : dato;
                publicar();
            }

            @Override
            public void onError(String mensaje) {
                estado.setValue(Estado.<List<ItemTarjeta>>error(mensaje));
            }
        });
    }

    /** Filtra por texto sin volver a llamar a la API. */
    public void filtrar(String texto) {
        filtro = (texto == null) ? "" : texto;
        Estado<List<ItemTarjeta>> actual = estado.getValue();
        if (actual != null && actual.getTipo() == Estado.Tipo.EXITO) {
            publicar();
        }
    }

    private void publicar() {
        estado.setValue(Estado.exito(aTarjetas(todos, filtro)));
    }
}
