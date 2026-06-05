package com.mycompany.empresadetransportehirata.Logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Pruebas unitarias para {@link PiezaInventario}.
 */
public class PiezaInventarioTest {

    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        PiezaInventario pieza = new PiezaInventario();

        assertEquals(0, pieza.getId());
        assertNull(pieza.getNombre());
        assertNull(pieza.getDescripcion());
        assertEquals(0, pieza.getCantidad());
        assertNull(pieza.getUnidad());
        assertEquals(0, pieza.getStockMinimo());
        assertNull(pieza.getUbicacion());
        assertNull(pieza.getFechaRegistro());
        assertNull(pieza.getUltimaActualizacion());
    }

    @Test
    public void constructorConParametros_asignaTodosLosCampos() {
        Date fechaRegistro = Date.valueOf("2024-10-07");
        PiezaInventario pieza = new PiezaInventario(7, "Toner", "Cartucho de tinta", 10, "Unidad", 2, "Bodega A", fechaRegistro);

        assertEquals(7, pieza.getId());
        assertEquals("Toner", pieza.getNombre());
        assertEquals("Cartucho de tinta", pieza.getDescripcion());
        assertEquals(10, pieza.getCantidad());
        assertEquals("Unidad", pieza.getUnidad());
        assertEquals(2, pieza.getStockMinimo());
        assertEquals("Bodega A", pieza.getUbicacion());
        assertEquals(fechaRegistro, pieza.getFechaRegistro());
    }

    @Test
    public void settersYGetters_actualizanValores() {
        PiezaInventario pieza = new PiezaInventario();
        Date fechaRegistro = Date.valueOf("2024-10-07");
        Timestamp ultimaActualizacion = Timestamp.valueOf("2024-10-07 12:00:00");

        pieza.setId(9);
        pieza.setNombre("Fuente");
        pieza.setDescripcion("Fuente de poder");
        pieza.setCantidad(5);
        pieza.setUnidad("Unidad");
        pieza.setStockMinimo(3);
        pieza.setUbicacion("Estante 2");
        pieza.setFechaRegistro(fechaRegistro);
        pieza.setUltimaActualizacion(ultimaActualizacion);

        assertEquals(9, pieza.getId());
        assertEquals("Fuente", pieza.getNombre());
        assertEquals("Fuente de poder", pieza.getDescripcion());
        assertEquals(5, pieza.getCantidad());
        assertEquals("Unidad", pieza.getUnidad());
        assertEquals(3, pieza.getStockMinimo());
        assertEquals("Estante 2", pieza.getUbicacion());
        assertEquals(fechaRegistro, pieza.getFechaRegistro());
        assertEquals(ultimaActualizacion, pieza.getUltimaActualizacion());
    }

    @Test
    public void isStockBajo_devuelveTrueCuandoCantidadEsMenorOIgualAlMinimo() {
        PiezaInventario pieza = new PiezaInventario();
        pieza.setCantidad(2);
        pieza.setStockMinimo(3);

        assertTrue(pieza.isStockBajo());

        pieza.setCantidad(3);
        assertTrue(pieza.isStockBajo());
    }

    @Test
    public void isStockBajo_devuelveFalseCuandoCantidadEsMayorAlMinimo() {
        PiezaInventario pieza = new PiezaInventario();
        pieza.setCantidad(5);
        pieza.setStockMinimo(3);

        assertFalse(pieza.isStockBajo());
    }

    @Test
    public void toString_incluyeNombreCantidadYUnidad() {
        PiezaInventario pieza = new PiezaInventario(5, "Tornillo", "Tornillo M4", 50, "Unidad", 10, "Bodega B", Date.valueOf("2024-10-07"));

        assertEquals("Tornillo - 50 Unidad", pieza.toString());
    }
}
