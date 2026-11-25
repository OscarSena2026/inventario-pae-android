package co.edu.sena.inventariopae.viewmodel;

/** Estado de una operación que la pantalla observa: cargando, éxito (con datos) o error. */
public final class Estado<T> {
    public enum Tipo { CARGANDO, EXITO, ERROR }

    private final Tipo tipo;
    private final T datos;
    private final String mensaje;

    private Estado(Tipo tipo, T datos, String mensaje) {
        this.tipo = tipo;
        this.datos = datos;
        this.mensaje = mensaje;
    }

    public static <T> Estado<T> cargando() { return new Estado<>(Tipo.CARGANDO, null, null); }

    public static <T> Estado<T> exito(T datos) { return new Estado<>(Tipo.EXITO, datos, null); }

    public static <T> Estado<T> error(String mensaje) { return new Estado<>(Tipo.ERROR, null, mensaje); }

    public Tipo getTipo() { return tipo; }

    public T getDatos() { return datos; }

    public String getMensaje() { return mensaje; }
}
