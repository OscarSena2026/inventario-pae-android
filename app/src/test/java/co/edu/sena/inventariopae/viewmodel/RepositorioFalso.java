package co.edu.sena.inventariopae.viewmodel;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.modelo.ProductoRequest;
import co.edu.sena.inventariopae.repositorio.InventarioRepositorio;
import co.edu.sena.inventariopae.repositorio.RespuestaCallback;

import java.util.ArrayList;
import java.util.List;

/** Repositorio de prueba: responde al instante, sin red, con lo que cada prueba configure. */
class RepositorioFalso implements InventarioRepositorio {
    boolean fallar = false;
    String mensajeError = "Error de prueba";
    int llamadas = 0;

    String ultimoUsuario;
    ProductoRequest ultimoProducto;
    int ultimosDias;

    List<Producto> productos = new ArrayList<>();
    List<Existencia> existencias = new ArrayList<>();
    List<AlertaStock> alertasStock = new ArrayList<>();
    List<AlertaVencimiento> alertasVenc = new ArrayList<>();

    private <T> void responder(T dato, RespuestaCallback<T> cb) {
        llamadas++;
        if (fallar) cb.onError(mensajeError);
        else cb.onExito(dato);
    }

    @Override
    public void iniciarSesion(String usuario, String contrasena, RespuestaCallback<String> cb) {
        ultimoUsuario = usuario;
        responder("Autenticación satisfactoria", cb);
    }

    @Override
    public void registrar(String usuario, String contrasena, RespuestaCallback<String> cb) {
        ultimoUsuario = usuario;
        responder("Usuario registrado correctamente", cb);
    }

    @Override
    public void listarProductos(RespuestaCallback<List<Producto>> cb) {
        responder(productos, cb);
    }

    @Override
    public void crearProducto(ProductoRequest producto, RespuestaCallback<Producto> cb) {
        ultimoProducto = producto;
        responder(new Producto(1, producto.getNombreProducto(), producto.getCategoria(),
                producto.getUnidadMedida(), producto.getPerecedero(), producto.getStockMinimo()), cb);
    }

    @Override
    public void listarExistencias(RespuestaCallback<List<Existencia>> cb) {
        responder(existencias, cb);
    }

    @Override
    public void alertasStock(RespuestaCallback<List<AlertaStock>> cb) {
        responder(alertasStock, cb);
    }

    @Override
    public void alertasVencimiento(int dias, RespuestaCallback<List<AlertaVencimiento>> cb) {
        ultimosDias = dias;
        responder(alertasVenc, cb);
    }
}
