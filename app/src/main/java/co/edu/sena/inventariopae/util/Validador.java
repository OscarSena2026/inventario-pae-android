package co.edu.sena.inventariopae.util;

import java.math.BigDecimal;

/** Validaciones de formularios. Replican las reglas de la API (DTO) para avisar antes de enviar. */
public final class Validador {
    private Validador() { }

    /** Devuelve el mensaje de error o null si todo está bien. */
    public static String validarLogin(String usuario, String contrasena) {
        if (esVacio(usuario)) return "Escribe tu usuario.";
        if (esVacio(contrasena)) return "Escribe tu contraseña.";
        return null;
    }

    public static String validarRegistro(String usuario, String contrasena, String confirmacion) {
        if (esVacio(usuario)) return "El usuario es obligatorio.";
        if (usuario.trim().length() > 60) return "El usuario no puede superar 60 caracteres.";
        if (esVacio(contrasena)) return "La contraseña es obligatoria.";
        if (contrasena.length() < 8 || contrasena.length() > 50) {
            return "La contraseña debe tener entre 8 y 50 caracteres.";
        }
        if (!contrasena.equals(confirmacion)) return "Las contraseñas no coinciden.";
        return null;
    }

    public static String validarProducto(String nombre, String categoria,
                                         String unidad, String stockTexto) {
        if (esVacio(nombre)) return "El nombre del producto es obligatorio.";
        if (nombre.trim().length() > 80) return "El nombre no puede superar 80 caracteres.";
        if (esVacio(categoria)) return "La categoría es obligatoria.";
        if (categoria.trim().length() > 40) return "La categoría no puede superar 40 caracteres.";
        if (esVacio(unidad)) return "La unidad de medida es obligatoria.";
        if (unidad.trim().length() > 15) return "La unidad no puede superar 15 caracteres.";
        BigDecimal stock = parsearNumero(stockTexto);
        if (stock == null) return "El stock mínimo debe ser un número.";
        if (stock.signum() < 0) return "El stock mínimo no puede ser negativo.";
        return null;
    }

    /** Convierte texto a número (acepta coma o punto decimal). Devuelve null si no es válido. */
    public static BigDecimal parsearNumero(String texto) {
        if (esVacio(texto)) return null;
        try {
            return new BigDecimal(texto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
