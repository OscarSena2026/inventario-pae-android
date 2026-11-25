package co.edu.sena.inventariopae.viewmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginViewModelTest {
    @Rule
    public InstantTaskExecutorRule regla = new InstantTaskExecutorRule();

    private RepositorioFalso repo;
    private LoginViewModel vm;

    @Before
    public void preparar() {
        repo = new RepositorioFalso();
        vm = new LoginViewModel(repo);
    }

    @Test
    public void camposVacios_dadoError_ySinLlamarALaApi() {
        vm.iniciarSesion("", "");
        assertEquals(Estado.Tipo.ERROR, vm.getEstado().getValue().getTipo());
        assertEquals(0, repo.llamadas);
    }

    @Test
    public void credencialesCorrectas_dadoExito_conElUsuarioSinEspacios() {
        vm.iniciarSesion("  oscar_demo ", "clave1234");
        Estado<String> e = vm.getEstado().getValue();
        assertEquals(Estado.Tipo.EXITO, e.getTipo());
        assertEquals("oscar_demo", e.getDatos());
        assertEquals("oscar_demo", repo.ultimoUsuario);
    }

    @Test
    public void credencialesIncorrectas_muestraElErrorDelServidor() {
        repo.fallar = true;
        repo.mensajeError = "Error en la autenticación";
        vm.iniciarSesion("oscar", "mala");
        Estado<String> e = vm.getEstado().getValue();
        assertEquals(Estado.Tipo.ERROR, e.getTipo());
        assertEquals("Error en la autenticación", e.getMensaje());
    }

    @Test
    public void consumirEstado_limpiaElResultado() {
        vm.iniciarSesion("oscar", "clave1234");
        vm.consumirEstado();
        assertNull(vm.getEstado().getValue());
    }
}
