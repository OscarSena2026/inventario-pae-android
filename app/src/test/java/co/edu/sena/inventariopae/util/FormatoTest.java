package co.edu.sena.inventariopae.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.math.BigDecimal;

public class FormatoTest {

    @Test
    public void cantidad_quitaCerosDecimales() {
        assertEquals("250", Formato.cantidad(new BigDecimal("250.00")));
    }

    @Test
    public void cantidad_conservaDecimalesReales() {
        assertEquals("12.5", Formato.cantidad(new BigDecimal("12.50")));
    }

    @Test
    public void cantidad_cienSeEscribeSinNotacionCientifica() {
        assertEquals("100", Formato.cantidad(new BigDecimal("100.00")));
    }

    @Test
    public void cantidad_nulaOCero_esCero() {
        assertEquals("0", Formato.cantidad(null));
        assertEquals("0", Formato.cantidad(new BigDecimal("0.00")));
    }

    @Test
    public void fecha_cambiaAFormatoDiaMesAnio() {
        assertEquals("01/12/2026", Formato.fecha("2026-12-01"));
    }

    @Test
    public void fecha_vaciaDiceSinFecha() {
        assertEquals("Sin fecha", Formato.fecha(null));
        assertEquals("Sin fecha", Formato.fecha(""));
    }

    @Test
    public void fecha_conFormatoRaro_sedevuelveTalCual() {
        assertEquals("diciembre", Formato.fecha("diciembre"));
    }

    @Test
    public void dias_describeCadaCaso() {
        assertEquals("Vencido", Formato.dias(-3L));
        assertEquals("Vence hoy", Formato.dias(0L));
        assertEquals("1 día", Formato.dias(1L));
        assertEquals("15 días", Formato.dias(15L));
        assertEquals("Sin dato", Formato.dias(null));
    }
}
