package co.edu.sena.inventariopae.viewmodel;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

public class AlertasViewModelTest {
    @Rule
    public InstantTaskExecutorRule regla = new InstantTaskExecutorRule();

    private RepositorioFalso repo;
    private AlertasViewModel vm;

    @Before
    public void preparar() {
        repo = new RepositorioFalso();
        repo.alertasStock.add(new AlertaStock("Leche", new BigDecimal("5"), new BigDecimal("20"), "bajo"));
        repo.alertasVenc.add(new AlertaVencimiento("Arroz", "Central", new BigDecimal("10"), "2026-12-01", 7L));
        vm = new AlertasViewModel(repo);
    }

    @Test
    public void cargar_llenaLasDosListas() {
        vm.cargar();
        assertEquals(1, vm.getStock().getValue().getDatos().size());
        assertEquals(1, vm.getVencimiento().getValue().getDatos().size());
    }

    @Test
    public void cargar_pideLosLotesDeLosProximos30Dias() {
        vm.cargar();
        assertEquals(30, repo.ultimosDias);
    }

    @Test
    public void conFalloDeRed_ambasListasQuedanEnError() {
        repo.fallar = true;
        vm.cargar();
        assertEquals(Estado.Tipo.ERROR, vm.getStock().getValue().getTipo());
        assertEquals(Estado.Tipo.ERROR, vm.getVencimiento().getValue().getTipo());
    }
}
