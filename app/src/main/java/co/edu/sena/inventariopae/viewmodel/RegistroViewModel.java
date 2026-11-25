package co.edu.sena.inventariopae.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.util.Validador;

/** Módulo Acceso: registro de usuario (HU-01). */
public class RegistroViewModel extends ViewModel {
    private final InventarioRepositorio repositorio;
    private final MutableLiveData<Estado<String>> estado = new MutableLiveData<>();

    public RegistroViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public LiveData<Estado<String>> getEstado() {
        return estado;
    }

    public void registrar(String usuario, String contrasena, String confirmacion) {
        Estado<String> actual = estado.getValue();
        if (actual != null && actual.getTipo() == Estado.Tipo.CARGANDO) return;
        String problema = Validador.validarRegistro(usuario, contrasena, confirmacion);
        if (problema != null) {
            estado.setValue(Estado.<String>error(problema));
            return;
        }
        estado.setValue(Estado.<String>cargando());
        repositorio.registrar(usuario.trim(), contrasena, new RespuestaCallback<String>() {
            @Override
            public void onExito(String dato) {
                estado.setValue(Estado.exito(dato));
            }

            @Override
            public void onError(String mensaje) {
                estado.setValue(Estado.<String>error(mensaje));
            }
        });
    }

    public void consumirEstado() {
        estado.setValue(null);
    }
}
