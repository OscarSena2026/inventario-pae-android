package co.edu.sena.inventariopae.util;

import android.content.Context;
import android.content.SharedPreferences;

/** Guarda en el teléfono solo el nombre del usuario que inició sesión (la API no entrega token). */
public final class SesionLocal {
    private static final String ARCHIVO = "sesion";
    private static final String CLAVE_USUARIO = "usuario";

    private SesionLocal() { }

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE);
    }

    public static void guardar(Context c, String usuario) {
        prefs(c).edit().putString(CLAVE_USUARIO, usuario).apply();
    }

    public static String usuario(Context c) {
        return prefs(c).getString(CLAVE_USUARIO, null);
    }

    public static boolean haySesion(Context c) {
        return usuario(c) != null;
    }

    public static void cerrar(Context c) {
        prefs(c).edit().remove(CLAVE_USUARIO).apply();
    }
}
