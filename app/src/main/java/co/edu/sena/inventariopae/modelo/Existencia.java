package co.edu.sena.inventariopae.modelo;

import java.math.BigDecimal;

/** Stock de un lote en una bodega (respuesta de GET /api/existencias). */
public class Existencia {
    private Integer idExistencia;
    private Integer idBodega;
    private String nombreBodega;
    private Integer idLote;
    private Integer idProducto;
    private String nombreProducto;
    private String fechaVencimiento; // llega como texto AAAA-MM-DD
    private BigDecimal cantidadDisponible;

    public Existencia(String nombreProducto, String nombreBodega,
                      String fechaVencimiento, BigDecimal cantidadDisponible) {
        this.nombreProducto = nombreProducto;
        this.nombreBodega = nombreBodega;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Integer getIdExistencia() { return idExistencia; }
    public Integer getIdBodega() { return idBodega; }
    public String getNombreBodega() { return nombreBodega; }
    public Integer getIdLote() { return idLote; }
    public Integer getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
}
