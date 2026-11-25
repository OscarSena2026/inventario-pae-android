package co.edu.sena.inventariopae.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.modelo.ProductoRequest;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.util.Validador;

/** Módulo Productos: registrar un producto nuevo (HU-02). */
public class ProductoFormViewModel extends ViewModel {
    private final InventarioRepositorio repositorio;
    private final MutableLiveData<Estado<Producto>> estado = new MutableLiveData<>();

    public ProductoFormViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public LiveData<Estado<Producto>> getEstado() {
        return estado;
    }

    public void guardar(String nombre, String categoria, String unidad,
                        boolean perecedero, String stockTexto) {
        Estado<Producto> actual = estado.getValue();
        if (actual != null && actual.getTipo() == Estado.Tipo.CARGANDO) return;
        String problema = Validador.validarProducto(nombre, categoria, unidad, stockTexto);
        if (problema != null) {
            estado.setValue(Estado.<Producto>error(problema));
            return;
        }
        ProductoRequest pedido = new ProductoRequest(
                nombre.trim(), categoria.trim(), unidad.trim(),
                perecedero ? "SI" : "NO",
                Validador.parsearNumero(stockTexto));
        estado.setValue(Estado.<Producto>cargando());
        repositorio.crearProducto(pedido, new RespuestaCallback<Producto>() {
            @Override
            public void onExito(Producto dato) {
                estado.setValue(Estado.exito(dato));
            }

            @Override
            public void onError(String mensaje) {
                estado.setValue(Estado.<Producto>error(mensaje));
            }
        });
    }

    public void consumirEstado() {
        estado.setValue(null);
    }
}
