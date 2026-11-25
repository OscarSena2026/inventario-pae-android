package co.edu.sena.inventariopae.ui;

/** Modelo de interfaz: lo mínimo que necesita una tarjeta de la lista. */
public class ItemTarjeta {
    public enum Nivel { NORMAL, ADVERTENCIA, CRITICO }

    private final String titulo;
    private final String subtitulo;
    private final String detalle;
    private final Nivel nivel;

    public ItemTarjeta(String titulo, String subtitulo, String detalle, Nivel nivel) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.detalle = detalle;
        this.nivel = nivel;
    }

    public String getTitulo() { return titulo; }
    public String getSubtitulo() { return subtitulo; }
    public String getDetalle() { return detalle; }
    public Nivel getNivel() { return nivel; }
}
