package co.edu.sena.inventariopae.repositorio;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.modelo.ProductoRequest;

import java.util.List;

/**
 * Patrón Repository: los ViewModels piden datos aquí sin saber si vienen de la red,
 * de una base local o de un objeto falso (como en las pruebas unitarias).
 */
public interface InventarioRepositorio {
    void iniciarSesion(String usuario, String contrasena, RespuestaCallback<String> cb);

    void registrar(String usuario, String contrasena, RespuestaCallback<String> cb);

    void listarProductos(RespuestaCallback<List<Producto>> cb);

    void crearProducto(ProductoRequest producto, RespuestaCallback<Producto> cb);

    void listarExistencias(RespuestaCallback<List<Existencia>> cb);

    void alertasStock(RespuestaCallback<List<AlertaStock>> cb);

    void alertasVencimiento(int dias, RespuestaCallback<List<AlertaVencimiento>> cb);
}
