package co.edu.sena.inventariopae.viewmodel;

import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.ui.ItemTarjeta;
import co.edu.sena.inventariopae.util.Mapeadores;

import java.util.List;

/** Módulo Productos: listado (HU-02). */
public class ProductosViewModel extends ListaViewModel<Producto> {
    private final InventarioRepositorio repositorio;

    public ProductosViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    protected void consultar(RespuestaCallback<List<Producto>> cb) {
        repositorio.listarProductos(cb);
    }

    @Override
    protected List<ItemTarjeta> aTarjetas(List<Producto> datos, String filtro) {
        return Mapeadores.deProductos(datos, filtro);
    }
}
