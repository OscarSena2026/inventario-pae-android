package co.edu.sena.inventariopae.modelo;

import java.math.BigDecimal;

/** Lote por vencer o vencido (GET /api/alertas/vencimiento?dias=N). */
public class AlertaVencimiento {
    private Integer idLote;
    private Integer idProducto;
    private String nombreProducto;
    private Integer idBodega;
    private String nombreBodega;
    private BigDecimal cantidadDisponible;
    private String fechaVencimiento; // texto AAAA-MM-DD
    private Long diasRestantes;
    private String mensaje;

    public AlertaVencimiento(String nombreProducto, String nombreBodega,
                             BigDecimal cantidadDisponible, String fechaVencimiento,
                             Long diasRestantes) {
        this.nombreProducto = nombreProducto;
        this.nombreBodega = nombreBodega;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaVencimiento = fechaVencimiento;
        this.diasRestantes = diasRestantes;
    }

    public Integer getIdLote() { return idLote; }
    public Integer getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public Integer getIdBodega() { return idBodega; }
    public String getNombreBodega() { return nombreBodega; }
    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public Long getDiasRestantes() { return diasRestantes; }
    public String getMensaje() { return mensaje; }
}
