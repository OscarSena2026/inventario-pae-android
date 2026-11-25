package co.edu.sena.inventariopae.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.math.BigDecimal;

public class ValidadorTest {

    @Test
    public void login_conDatosCompletos_noHayError() {
        assertNull(Validador.validarLogin("oscar_demo", "clave1234"));
    }

    @Test
    public void login_sinUsuario_pideUsuario() {
        assertEquals("Escribe tu usuario.", Validador.validarLogin("   ", "clave1234"));
    }

    @Test
    public void login_sinContrasena_pideContrasena() {
        assertEquals("Escribe tu contraseña.", Validador.validarLogin("oscar", ""));
    }

    @Test
    public void registro_valido_noHayError() {
        assertNull(Validador.validarRegistro("nuevo", "12345678", "12345678"));
    }

    @Test
    public void registro_contrasenaCorta_rechaza() {
        assertNotNull(Validador.validarRegistro("nuevo", "1234567", "1234567"));
    }

    @Test
    public void registro_contrasenaMuyLarga_rechaza() {
        String larga = new String(new char[51]).replace((char) 0, (char) 97);
        assertNotNull(Validador.validarRegistro("nuevo", larga, larga));
    }

    @Test
    public void registro_confirmacionDistinta_rechaza() {
        assertEquals("Las contraseñas no coinciden.",
                Validador.validarRegistro("nuevo", "12345678", "87654321"));
    }

    @Test
    public void registro_usuarioMuyLargo_rechaza() {
        String largo = new String(new char[61]).replace((char) 0, (char) 97);
        assertNotNull(Validador.validarRegistro(largo, "12345678", "12345678"));
    }

    @Test
    public void producto_valido_noHayError() {
        assertNull(Validador.validarProducto("Arroz", "Granos", "kg", "50"));
    }

    @Test
    public void producto_sinNombre_rechaza() {
        assertEquals("El nombre del producto es obligatorio.",
                Validador.validarProducto("", "Granos", "kg", "50"));
    }

    @Test
    public void producto_stockNegativo_rechaza() {
        assertEquals("El stock mínimo no puede ser negativo.",
                Validador.validarProducto("Arroz", "Granos", "kg", "-5"));
    }

    @Test
    public void producto_stockNoNumerico_rechaza() {
        assertEquals("El stock mínimo debe ser un número.",
                Validador.validarProducto("Arroz", "Granos", "kg", "abc"));
    }

    @Test
    public void parsearNumero_aceptaComaDecimal() {
        assertEquals(new BigDecimal("12.5"), Validador.parsearNumero("12,5"));
    }

    @Test
    public void parsearNumero_textoVacio_devuelveNull() {
        assertNull(Validador.parsearNumero(" "));
    }
}
