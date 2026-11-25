package co.edu.sena.inventariopae.red;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InterpreteErroresTest {

    @Test
    public void textoPlano_seMuestraTalCual() {
        assertEquals("Error en la autenticación",
                InterpreteErrores.mensaje(401, "Error en la autenticación"));
    }

    @Test
    public void jsonConMensaje_usaElMensaje() {
        String json = "{\"estado\":409,\"error\":\"Conflict\",\"mensaje\":\"El usuario ya existe\"}";
        assertEquals("El usuario ya existe", InterpreteErrores.mensaje(409, json));
    }

    @Test
    public void jsonConErrores_agregaElDetalleDeCadaCampo() {
        String json = "{\"estado\":400,\"mensaje\":\"Datos inválidos\","
                + "\"errores\":{\"contrasena\":\"La contraseña debe tener entre 8 y 50 caracteres\"}}";
        String r = InterpreteErrores.mensaje(400, json);
        assertTrue(r.startsWith("Datos inválidos"));
        assertTrue(r.contains("La contraseña debe tener entre 8 y 50 caracteres"));
    }

    @Test
    public void sinCuerpo_usaMensajePorCodigo() {
        assertEquals("Usuario o contraseña incorrectos.", InterpreteErrores.mensaje(401, null));
        assertEquals("No se encontró lo que buscabas.", InterpreteErrores.mensaje(404, ""));
    }

    @Test
    public void errorDelServidor_avisaQueNoEstaDisponible() {
        String r = InterpreteErrores.mensaje(502, "{\"status\":\"error\"}");
        assertTrue(r.contains("servidor no está disponible"));
    }

    @Test
    public void paginaHtml_seIgnora() {
        assertEquals("Ocurrió un error inesperado (código 418).",
                InterpreteErrores.mensaje(418, "<html>teapot</html>"));
    }
}
