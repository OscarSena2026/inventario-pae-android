package co.edu.sena.inventariopae.util;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.ui.ItemTarjeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Convierte los modelos de la API en ItemTarjeta (lo que dibuja el adaptador). */
public final class Mapeadores {
    private Mapeadores() { }

    public static List<ItemTarjeta> deExistencias(List<Existencia> datos, String filtro) {
        List<ItemTarjeta> salida = new ArrayList<>();
        if (datos == null) return salida;
        for (Existencia e : datos) {
            if (!coincide(filtro, e.getNombreProducto())) continue;
            salida.add(new ItemTarjeta(
                    e.getNombreProducto(),
                    "Bodega: " + e.getNombreBodega() + " · Vence: " + Formato.fecha(e.getFechaVencimiento()),
                    Formato.cantidad(e.getCantidadDisponible()),
                    ItemTarjeta.Nivel.NORMAL));
        }
        return salida;
    }

    public static List<ItemTarjeta> deProductos(List<Producto> datos, String filtro) {
        List<ItemTarjeta> salida = new ArrayList<>();
        if (datos == null) return salida;
        for (Producto p : datos) {
            if (!coincide(filtro, p.getNombreProducto())) continue;
            String sub = p.getCategoria() + " · " + p.getUnidadMedida();
            if ("SI".equals(p.getPerecedero())) sub += " · Perecedero";
            salida.add(new ItemTarjeta(
                    p.getNombreProducto(),
                    sub,
                    "Mín: " + Formato.cantidad(p.getStockMinimo()),
                    ItemTarjeta.Nivel.NORMAL));
        }
        return salida;
    }

    public static List<ItemTarjeta> deAlertasStock(List<AlertaStock> datos) {
        List<ItemTarjeta> salida = new ArrayList<>();
        if (datos == null) return salida;
        for (AlertaStock a : datos) {
            boolean agotado = a.getStockActual() == null || a.getStockActual().signum() <= 0;
            salida.add(new ItemTarjeta(
                    a.getNombreProducto(),
                    "Mínimo: " + Formato.cantidad(a.getStockMinimo()),
                    "Stock: " + Formato.cantidad(a.getStockActual()),
                    agotado ? ItemTarjeta.Nivel.CRITICO : ItemTarjeta.Nivel.ADVERTENCIA));
        }
        return salida;
    }

    public static List<ItemTarjeta> deAlertasVencimiento(List<AlertaVencimiento> datos) {
        List<ItemTarjeta> salida = new ArrayList<>();
        if (datos == null) return salida;
        for (AlertaVencimiento a : datos) {
            Long dias = a.getDiasRestantes();
            ItemTarjeta.Nivel nivel = (dias != null && dias <= 7)
                    ? ItemTarjeta.Nivel.CRITICO : ItemTarjeta.Nivel.ADVERTENCIA;
            salida.add(new ItemTarjeta(
                    a.getNombreProducto(),
                    "Bodega: " + a.getNombreBodega() + " · Cant: " + Formato.cantidad(a.getCantidadDisponible())
                            + " · Vence: " + Formato.fecha(a.getFechaVencimiento()),
                    Formato.dias(dias),
                    nivel));
        }
        return salida;
    }

    /** Sin filtro (null o vacío) todo coincide; si no, busca el texto ignorando mayúsculas. */
    static boolean coincide(String filtro, String nombre) {
        if (filtro == null || filtro.trim().isEmpty()) return true;
        if (nombre == null) return false;
        return nombre.toLowerCase(Locale.ROOT).contains(filtro.trim().toLowerCase(Locale.ROOT));
    }
}
