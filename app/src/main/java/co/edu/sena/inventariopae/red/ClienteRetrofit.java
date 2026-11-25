package co.edu.sena.inventariopae.red;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/** Crea una sola vez el cliente HTTP y la implementación de ApiService (Singleton). */
public final class ClienteRetrofit {
    /** Servidor en Railway (https: Android bloquea http sin cifrar). */
    public static final String URL_BASE = "https://inventario-pae-api-production.up.railway.app/";

    private static ApiService api;

    private ClienteRetrofit() { }

    public static synchronized ApiService api() {
        if (api == null) {
            // BASIC solo registra método, URL y código; nunca el cuerpo (no expone contraseñas)
            HttpLoggingInterceptor registro = new HttpLoggingInterceptor();
            registro.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient cliente = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(registro)
                    .build();

            api = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(cliente)
                    // El orden importa: Scalars lee los textos planos del login; Gson lee el resto
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return api;
    }
}
