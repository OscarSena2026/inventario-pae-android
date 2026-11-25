package co.edu.sena.inventariopae.modelo;

import java.math.BigDecimal;

/** Producto del catálogo (respuesta de GET /api/productos). */
public class Producto {
    private Integer idProducto;
    private String nombreProducto;
    private String categoria;
    private String unidadMedida;
    private String perecedero;
    private BigDecimal stockMinimo;

    public Producto(Integer idProducto, String nombreProducto, String categoria,
                    String unidadMedida, String perecedero, BigDecimal stockMinimo) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.perecedero = perecedero;
        this.stockMinimo = stockMinimo;
    }

    public Integer getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public String getCategoria() { return categoria; }
    public String getUnidadMedida() { return unidadMedida; }
    public String getPerecedero() { return perecedero; }
    public BigDecimal getStockMinimo() { return stockMinimo; }
}
