package com.mycompany.empresadetransportehirata.Logica;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link Mantenimiento}.
 *
 * <p>Comprueba valores por defecto y persistencia del estado mediante
 * getters y setters.</p>
 */
public class MantenimientoTest {

    /**
     * Verifica los valores iniciales del constructor vacío.
     */
    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        Mantenimiento mantenimiento = new Mantenimiento();

        assertEquals(0, mantenimiento.getId());
        assertNull(mantenimiento.getCamion());
        assertNull(mantenimiento.getTipoMantenimiento());
        assertNull(mantenimiento.getFecha());
        assertEquals(0, mantenimiento.getKmActual());
        assertEquals(0, mantenimiento.getCosto());
        assertEquals(0, mantenimiento.getEstado());
        assertNull(mantenimiento.getObservacion());
        assertNull(mantenimiento.getTaller());
    }

    /**
     * Verifica que los mutadores actualicen todos los atributos del modelo.
     */
    @Test
    public void settersYGetters_actualizanTodosLosCampos() {
        Mantenimiento mantenimiento = new Mantenimiento();
        Date fecha = new Date();

        mantenimiento.setId(14);
        mantenimiento.setCamion("ABCD-12");
        mantenimiento.setTipoMantenimiento("Cambio de Aceite");
        mantenimiento.setFecha(fecha);
        mantenimiento.setKmActual(150000);
        mantenimiento.setCosto(45000);
        mantenimiento.setEstado(1);
        mantenimiento.setObservacion("Cambio preventivo");
        mantenimiento.setTaller("Taller Norte");

        assertEquals(14, mantenimiento.getId());
        assertEquals("ABCD-12", mantenimiento.getCamion());
        assertEquals("Cambio de Aceite", mantenimiento.getTipoMantenimiento());
        assertEquals(fecha, mantenimiento.getFecha());
        assertEquals(150000, mantenimiento.getKmActual());
        assertEquals(45000, mantenimiento.getCosto());
        assertEquals(1, mantenimiento.getEstado());
        assertEquals("Cambio preventivo", mantenimiento.getObservacion());
        assertEquals("Taller Norte", mantenimiento.getTaller());
    }
}