package co.edu.sena.inventariopae.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;
import co.edu.sena.inventariopae.util.Validador;

/** Módulo Acceso: inicio de sesión (HU-01). En éxito, el dato es el nombre de usuario. */
public class LoginViewModel extends ViewModel {
    private final InventarioRepositorio repositorio;
    private final MutableLiveData<Estado<String>> estado = new MutableLiveData<>();

    public LoginViewModel(InventarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public LiveData<Estado<String>> getEstado() {
        return estado;
    }

    public void iniciarSesion(String usuario, String contrasena) {
        if (enCurso()) return;
        String problema = Validador.validarLogin(usuario, contrasena);
        if (problema != null) {
            estado.setValue(Estado.<String>error(problema));
            return;
        }
        final String nombre = usuario.trim();
        estado.setValue(Estado.<String>cargando());
        repositorio.iniciarSesion(nombre, contrasena, new RespuestaCallback<String>() {
            @Override
            public void onExito(String dato) {
                estado.setValue(Estado.exito(nombre));
            }

            @Override
            public void onError(String mensaje) {
                estado.setValue(Estado.<String>error(mensaje));
            }
        });
    }

    /** La pantalla lo llama después de navegar, para no repetir el éxito al girar el teléfono. */
    public void consumirEstado() {
        estado.setValue(null);
    }

    private boolean enCurso() {
        Estado<String> actual = estado.getValue();
        return actual != null && actual.getTipo() == Estado.Tipo.CARGANDO;
    }
}
