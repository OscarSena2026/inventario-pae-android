package co.edu.sena.inventariopae.repositorio;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.modelo.CredencialesRequest;
import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.modelo.ProductoRequest;
import co.edu.sena.inventariopae.modelo.RegistroRequest;
import co.edu.sena.inventariopae.red.ApiService;
import co.edu.sena.inventariopae.red.InterpreteErrores;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Implementación del repositorio que consume la API REST con Retrofit. */
public class InventarioRepositorioRemoto implements InventarioRepositorio {
    private final ApiService api;

    public InventarioRepositorioRemoto(ApiService api) {
        this.api = api;
    }

    @Override
    public void iniciarSesion(String usuario, String contrasena, RespuestaCallback<String> cb) {
        ejecutar(api.login(new CredencialesRequest(usuario, contrasena)), cb);
    }

    @Override
    public void registrar(String usuario, String contrasena, RespuestaCallback<String> cb) {
        ejecutar(api.registro(new RegistroRequest(usuario, contrasena)), cb);
    }

    @Override
    public void listarProductos(RespuestaCallback<List<Producto>> cb) {
        ejecutar(api.productos(), cb);
    }

    @Override
    public void crearProducto(ProductoRequest producto, RespuestaCallback<Producto> cb) {
        ejecutar(api.crearProducto(producto), cb);
    }

    @Override
    public void listarExistencias(RespuestaCallback<List<Existencia>> cb) {
        ejecutar(api.existencias(), cb);
    }

    @Override
    public void alertasStock(RespuestaCallback<List<AlertaStock>> cb) {
        ejecutar(api.alertasStock(), cb);
    }

    @Override
    public void alertasVencimiento(int dias, RespuestaCallback<List<AlertaVencimiento>> cb) {
        ejecutar(api.alertasVencimiento(dias), cb);
    }

    /** Lanza la llamada en segundo plano; Retrofit entrega el resultado en el hilo principal. */
    private <T> void ejecutar(Call<T> llamada, final RespuestaCallback<T> cb) {
        llamada.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> respuesta) {
                if (respuesta.isSuccessful()) {
                    cb.onExito(respuesta.body());
                } else {
                    cb.onError(InterpreteErrores.mensaje(respuesta.code(), leerError(respuesta)));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable error) {
                cb.onError(InterpreteErrores.sinConexion());
            }
        });
    }

    private static String leerError(Response<?> respuesta) {
        try {
            return respuesta.errorBody() == null ? null : respuesta.errorBody().string();
        } catch (IOException e) {
            return null;
        }
    }
}
