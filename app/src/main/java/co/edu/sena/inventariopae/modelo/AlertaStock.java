package co.edu.sena.inventariopae.modelo;

import java.math.BigDecimal;

/** Producto en o bajo su stock mínimo (GET /api/alertas/stock-minimo). */
public class AlertaStock {
    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal stockActual;
    private BigDecimal stockMinimo;
    private String mensaje;

    public AlertaStock(String nombreProducto, BigDecimal stockActual,
                       BigDecimal stockMinimo, String mensaje) {
        this.nombreProducto = nombreProducto;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.mensaje = mensaje;
    }

    public Integer getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public BigDecimal getStockActual() { return stockActual; }
    public BigDecimal getStockMinimo() { return stockMinimo; }
    public String getMensaje() { return mensaje; }
}
