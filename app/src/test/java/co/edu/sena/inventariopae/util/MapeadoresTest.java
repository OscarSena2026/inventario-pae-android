package co.edu.sena.inventariopae.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import co.edu.sena.inventariopae.modelo.AlertaStock;
import co.edu.sena.inventariopae.modelo.AlertaVencimiento;
import co.edu.sena.inventariopae.modelo.Existencia;
import co.edu.sena.inventariopae.modelo.Producto;
import co.edu.sena.inventariopae.ui.ItemTarjeta;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapeadoresTest {

    private List<Existencia> existencias() {
        return Arrays.asList(
                new Existencia("Arroz", "Bodega Central", "2027-01-15", new BigDecimal("250.00")),
                new Existencia("Lenteja", "Bodega Norte", null, new BigDecimal("500.00")));
    }

    @Test
    public void existencias_seConvierteEnTarjeta() {
        List<ItemTarjeta> r = Mapeadores.deExistencias(existencias(), "");
        assertEquals(2, r.size());
        assertEquals("Arroz", r.get(0).getTitulo());
        assertEquals("Bodega: Bodega Central · Vence: 15/01/2027", r.get(0).getSubtitulo());
        assertEquals("250", r.get(0).getDetalle());
    }

    @Test
    public void existencias_filtroIgnoraMayusculas() {
        List<ItemTarjeta> r = Mapeadores.deExistencias(existencias(), "LENT");
        assertEquals(1, r.size());
        assertEquals("Lenteja", r.get(0).getTitulo());
    }

    @Test
    public void existencias_listaNulaDaListaVacia() {
        assertTrue(Mapeadores.deExistencias(null, "").isEmpty());
    }

    @Test
    public void productos_perecederoSeIndicaEnElSubtitulo() {
        List<Producto> datos = Arrays.asList(
                new Producto(1, "Leche en polvo", "Lácteos", "kg", "SI", new BigDecimal("20.00")),
                new Producto(2, "Sal", "Condimentos", "kg", "NO", new BigDecimal("5.00")));
        List<ItemTarjeta> r = Mapeadores.deProductos(datos, null);
        assertEquals("Lácteos · kg · Perecedero", r.get(0).getSubtitulo());
        assertEquals("Condimentos · kg", r.get(1).getSubtitulo());
        assertEquals("Mín: 20", r.get(0).getDetalle());
    }

    @Test
    public void alertaStock_agotadoEsCritico() {
        List<AlertaStock> datos = Arrays.asList(
                new AlertaStock("Leche", new BigDecimal("0"), new BigDecimal("20"), "x"),
                new AlertaStock("Arroz", new BigDecimal("8"), new BigDecimal("10"), "x"));
        List<ItemTarjeta> r = Mapeadores.deAlertasStock(datos);
        assertEquals(ItemTarjeta.Nivel.CRITICO, r.get(0).getNivel());
        assertEquals(ItemTarjeta.Nivel.ADVERTENCIA, r.get(1).getNivel());
        assertEquals("Stock: 8", r.get(1).getDetalle());
    }

    @Test
    public void alertaVencimiento_nivelDependeDeLosDias() {
        List<AlertaVencimiento> datos = new ArrayList<>();
        datos.add(new AlertaVencimiento("Leche", "Central", new BigDecimal("10"), "2026-11-26", 2L));
        datos.add(new AlertaVencimiento("Arroz", "Central", new BigDecimal("10"), "2026-12-20", 26L));
        List<ItemTarjeta> r = Mapeadores.deAlertasVencimiento(datos);
        assertEquals(ItemTarjeta.Nivel.CRITICO, r.get(0).getNivel());
        assertEquals("2 días", r.get(0).getDetalle());
        assertEquals(ItemTarjeta.Nivel.ADVERTENCIA, r.get(1).getNivel());
    }
}
