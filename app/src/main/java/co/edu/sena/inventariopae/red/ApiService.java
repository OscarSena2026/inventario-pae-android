package co.edu.sena.inventariopae.red;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.modelo.CredencialesRequest;
import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.modelo.ProductoRequest;
import co.edu.sena.inventariopae.modelo.RegistroRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/** Contrato de la API REST (Retrofit genera la implementación). */
public interface ApiService {

    @POST("api/auth/login")
    Call<String> login(@Body CredencialesRequest cuerpo);

    @POST("api/auth/registro")
    Call<String> registro(@Body RegistroRequest cuerpo);

    @GET("api/productos")
    Call<List<Producto>> productos();

    @POST("api/productos")
    Call<Producto> crearProducto(@Body ProductoRequest cuerpo);

    @GET("api/existencias")
    Call<List<Existencia>> existencias();

    @GET("api/alertas/stock-minimo")
    Call<List<AlertaStock>> alertasStock();

    @GET("api/alertas/vencimiento")
    Call<List<AlertaVencimiento>> alertasVencimiento(@Query("dias") int dias);
}
