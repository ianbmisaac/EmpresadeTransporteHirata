package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link regKmCamiones}.
 *
 * <p>Valida el estado inicial del registro y la correcta persistencia de sus
 * propiedades básicas.</p>
 */
public class regKmCamionesTest {

    /**
     * Verifica los valores por defecto del constructor vacío.
     */
    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        regKmCamiones registro = new regKmCamiones();

        assertEquals(0, registro.getId());
        assertEquals(0, registro.getKilometraje());
        assertNull(registro.getObservacion());
        assertNull(registro.getFecha());
        assertNull(registro.getCamionero());
        assertNull(registro.getCamion());
    }

    /**
     * Verifica que getters y setters reflejen correctamente el estado del
     * registro.
     */
    @Test
    public void settersYGetters_actualizanCorrectamenteElEstado() {
        regKmCamiones registro = new regKmCamiones();
        Conductor conductor = new Conductor(6, "12345678-5", "Mario", "Diaz", "A5");
        Camion camion = new Camion();
        camion.setPatente("ASDF-34");
        Timestamp fecha = Timestamp.valueOf("2026-04-17 10:15:00");

        registro.setId(9);
        registro.setKilometraje(125000);
        registro.setObservacion("Ruta interurbana");
        registro.setFecha(fecha);
        registro.setCamionero(conductor);
        registro.setCamion(camion);

        assertEquals(9, registro.getId());
        assertEquals(125000, registro.getKilometraje());
        assertEquals("Ruta interurbana", registro.getObservacion());
        assertEquals(fecha, registro.getFecha());
        assertSame(conductor, registro.getCamionero());
        assertSame(camion, registro.getCamion());
    }
}