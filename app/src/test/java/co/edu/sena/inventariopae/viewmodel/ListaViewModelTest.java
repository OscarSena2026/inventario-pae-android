package co.edu.sena.inventariopae.viewmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.ui.ItemTarjeta;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ListaViewModelTest {
    @Rule
    public InstantTaskExecutorRule regla = new InstantTaskExecutorRule();

    private RepositorioFalso repo;

    @Before
    public void preparar() {
        repo = new RepositorioFalso();
        repo.productos.add(new Producto(1, "Arroz", "Granos", "kg", "NO", new BigDecimal("50")));
        repo.productos.add(new Producto(2, "Lenteja", "Granos", "kg", "NO", new BigDecimal("30")));
        repo.productos.add(new Producto(3, "Leche en polvo", "Lácteos", "kg", "SI", new BigDecimal("20")));
        repo.existencias.add(new Existencia("Arroz", "Central", "2027-01-15", new BigDecimal("250")));
    }

    @Test
    public void cargar_publicaTodasLasTarjetas() {
        ProductosViewModel vm = new ProductosViewModel(repo);
        vm.cargar();
        Estado<List<ItemTarjeta>> e = vm.getEstado().getValue();
        assertEquals(Estado.Tipo.EXITO, e.getTipo());
        assertEquals(3, e.getDatos().size());
    }

    @Test
    public void filtrar_reduceLaListaSinVolverALaApi() {
        ProductosViewModel vm = new ProductosViewModel(repo);
        vm.cargar();
        vm.filtrar("le");
        assertEquals(2, vm.getEstado().getValue().getDatos().size());
        assertEquals(1, repo.llamadas);
    }

    @Test
    public void filtrar_conTextoVacio_devuelveTodo() {
        ProductosViewModel vm = new ProductosViewModel(repo);
        vm.cargar();
        vm.filtrar("lenteja");
        vm.filtrar("");
        assertEquals(3, vm.getEstado().getValue().getDatos().size());
    }

    @Test
    public void cargar_conFalloDeRed_dejaEstadoDeError() {
        repo.fallar = true;
        repo.mensajeError = "Sin conexión";
        ProductosViewModel vm = new ProductosViewModel(repo);
        vm.cargar();
        Estado<List<ItemTarjeta>> e = vm.getEstado().getValue();
        assertEquals(Estado.Tipo.ERROR, e.getTipo());
        assertEquals("Sin conexión", e.getMensaje());
    }

    @Test
    public void filtrar_antesDeCargar_noPublicaNada() {
        ProductosViewModel vm = new ProductosViewModel(repo);
        vm.filtrar("arroz");
        assertTrue(vm.getEstado().getValue() == null);
    }

    @Test
    public void existencias_seMuestranComoTarjetas() {
        ExistenciasViewModel vm = new ExistenciasViewModel(repo);
        vm.cargar();
        List<ItemTarjeta> items = vm.getEstado().getValue().getDatos();
        assertEquals(1, items.size());
        assertEquals("250", items.get(0).getDetalle());
    }
}
