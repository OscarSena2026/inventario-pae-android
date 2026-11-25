package co.edu.sena.inventariopae.viewmodel;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegistroViewModelTest {
    @Rule
    public InstantTaskExecutorRule regla = new InstantTaskExecutorRule();

    private RepositorioFalso repo;
    private RegistroViewModel vm;

    @Before
    public void preparar() {
        repo = new RepositorioFalso();
        vm = new RegistroViewModel(repo);
    }

    @Test
    public void contrasenasDistintas_noLlamanALaApi() {
        vm.registrar("nuevo", "12345678", "otra-clave");
        assertEquals(Estado.Tipo.ERROR, vm.getEstado().getValue().getTipo());
        assertEquals(0, repo.llamadas);
    }

    @Test
    public void datosValidos_registranElUsuario() {
        vm.registrar("nuevo", "12345678", "12345678");
        assertEquals(Estado.Tipo.EXITO, vm.getEstado().getValue().getTipo());
        assertEquals("nuevo", repo.ultimoUsuario);
    }

    @Test
    public void usuarioRepetido_muestraElConflicto() {
        repo.fallar = true;
        repo.mensajeError = "El usuario ya existe";
        vm.registrar("nuevo", "12345678", "12345678");
        assertEquals("El usuario ya existe", vm.getEstado().getValue().getMensaje());
    }
}
