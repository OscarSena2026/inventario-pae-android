package co.edu.sena.inventariopae.modelo;

import java.math.BigDecimal;

/** Cuerpo JSON de POST /api/productos. */
public class ProductoRequest {
    private final String nombreProducto;
    private final String categoria;
    private final String unidadMedida;
    private final String perecedero; // "SI" o "NO"
    private final BigDecimal stockMinimo;

    public ProductoRequest(String nombreProducto, String categoria, String unidadMedida,
                           String perecedero, BigDecimal stockMinimo) {
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.perecedero = perecedero;
        this.stockMinimo = stockMinimo;
    }

    public String getNombreProducto() { return nombreProducto; }
    public String getCategoria() { return categoria; }
    public String getUnidadMedida() { return unidadMedida; }
    public String getPerecedero() { return perecedero; }
    public BigDecimal getStockMinimo() { return stockMinimo; }
}
