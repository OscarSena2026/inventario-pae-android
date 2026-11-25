package co.edu.sena.inventariopae.repositorio;

/** Resultado asíncrono de una operación del repositorio. */
public interface RespuestaCallback<T> {
    void onExito(T dato);

    void onError(String mensaje);
}
