package co.edu.sena.inventariopae.util;

import java.math.BigDecimal;

/** Convierte datos de la API en texto legible. No usa java.time (la app soporta API 24). */
public final class Formato {
    private Formato() { }

    public static String cantidad(BigDecimal valor) {
        if (valor == null || valor.signum() == 0) return "0";
        return valor.stripTrailingZeros().toPlainString();
    }

    /** "2026-12-01" pasa a "01/12/2026". */
    public static String fecha(String iso) {
        if (iso == null || iso.trim().isEmpty()) return "Sin fecha";
        String[] p = iso.trim().split("-");
        if (p.length == 3 && p[0].length() == 4 && p[1].length() == 2 && p[2].length() == 2) {
            return p[2] + "/" + p[1] + "/" + p[0];
        }
        return iso;
    }

    public static String dias(Long dias) {
        if (dias == null) return "Sin dato";
        if (dias < 0) return "Vencido";
        if (dias == 0) return "Vence hoy";
        if (dias == 1) return "1 día";
        return dias + " días";
    }
}
