package co.edu.sena.inventariopae.viewmodel;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import co.edu.sena.inventariopae.modelo.Producto;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductoFormViewModelTest {
    @Rule
    public InstantTaskExecutorRule regla = new InstantTaskExecutorRule();

    private RepositorioFalso repo;
    private ProductoFormViewModel vm;

    @Before
    public void preparar() {
        repo = new RepositorioFalso();
        vm = new ProductoFormViewModel(repo);
    }

    @Test
    public void datosInvalidos_noSeEnvianALaApi() {
        vm.guardar("", "Granos", "kg", false, "10");
        assertEquals(Estado.Tipo.ERROR, vm.getEstado().getValue().getTipo());
        assertEquals(0, repo.llamadas);
    }

    @Test
    public void guardar_armaElPedidoConPerecederoSI() {
        vm.guardar(" Leche en polvo ", "Lácteos", "kg", true, "20,5");
        Estado<Producto> e = vm.getEstado().getValue();
        assertEquals(Estado.Tipo.EXITO, e.getTipo());
        assertEquals("Leche en polvo", repo.ultimoProducto.getNombreProducto());
        assertEquals("SI", repo.ultimoProducto.getPerecedero());
        assertEquals(new BigDecimal("20.5"), repo.ultimoProducto.getStockMinimo());
    }

    @Test
    public void noPerecedero_seEnviaComoNO() {
        vm.guardar("Sal", "Condimentos", "kg", false, "5");
        assertEquals("NO", repo.ultimoProducto.getPerecedero());
    }

    @Test
    public void errorDelServidor_seMuestraAlUsuario() {
        repo.fallar = true;
        repo.mensajeError = "Datos inválidos";
        vm.guardar("Sal", "Condimentos", "kg", false, "5");
        assertEquals("Datos inválidos", vm.getEstado().getValue().getMensaje());
    }
}
