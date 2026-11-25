package co.edu.sena.inventariopae.red;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

/** Convierte una respuesta de error de la API en un mensaje claro para el usuario. */
public final class InterpreteErrores {
    private InterpreteErrores() { }

    public static String mensaje(int codigo, String cuerpo) {
        if (cuerpo != null) {
            String texto = cuerpo.trim();
            if (texto.startsWith("{")) {
                String deJson = leerJson(texto);
                if (deJson != null) return deJson;
            } else if (!texto.isEmpty() && !texto.startsWith("<")) {
                return texto; // texto plano, como "Error en la autenticación"
            }
        }
        return porCodigo(codigo);
    }

    public static String sinConexion() {
        return "No se pudo conectar con el servidor. Revisa tu conexión a Internet e intenta de nuevo.";
    }

    private static String leerJson(String texto) {
        try {
            JsonElement raiz = JsonParser.parseString(texto);
            if (!raiz.isJsonObject()) return null;
            JsonObject obj = raiz.getAsJsonObject();
            StringBuilder sb = new StringBuilder();
            if (obj.has("mensaje") && obj.get("mensaje").isJsonPrimitive()) {
                sb.append(obj.get("mensaje").getAsString());
            }
            if (obj.has("errores") && obj.get("errores").isJsonObject()) {
                for (Map.Entry<String, JsonElement> e : obj.getAsJsonObject("errores").entrySet()) {
                    if (e.getValue().isJsonPrimitive()) {
                        sb.append("\n• ").append(e.getValue().getAsString());
                    }
                }
            }
            return sb.length() > 0 ? sb.toString() : null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    private static String porCodigo(int codigo) {
        if (codigo == 400) return "Los datos enviados no son válidos.";
        if (codigo == 401) return "Usuario o contraseña incorrectos.";
        if (codigo == 404) return "No se encontró lo que buscabas.";
        if (codigo == 409) return "Ya existe un registro con esos datos.";
        if (codigo >= 500) return "El servidor no está disponible en este momento. Intenta de nuevo en un rato.";
        return "Ocurrió un error inesperado (código " + codigo + ").";
    }
}
