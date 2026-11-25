package co.edu.sena.inventariopae.viewmodel;

import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.ui.ItemTarjeta;
import co.edu.sena.inventariopae.util.Mapeadores;

import java.util.List;

/** Módulo Inventario: consulta de existencias (HU-05). */
public class ExistenciasViewModel extends ListaViewModel<Existencia> {
    private final InventarioRepositorio repositorio;

    public ExistenciasViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    protected void consultar(RespuestaCallback<List<Existencia>> cb) {
        repositorio.listarExistencias(cb);
    }

    @Override
    protected List<ItemTarjeta> aTarjetas(List<Existencia> datos, String filtro) {
        return Mapeadores.deExistencias(datos, filtro);
    }
}
