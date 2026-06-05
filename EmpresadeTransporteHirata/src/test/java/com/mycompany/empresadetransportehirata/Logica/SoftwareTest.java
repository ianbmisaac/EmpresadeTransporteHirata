package com.mycompany.empresadetransportehirata.Logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link Software}.
 */
public class SoftwareTest {

    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        Software software = new Software();

        assertEquals(0, software.getId());
        assertNull(software.getNombre());
    }

    @Test
    public void constructorConParametros_asignaValores() {
        Software software = new Software(5, "Antivirus");

        assertEquals(5, software.getId());
        assertEquals("Antivirus", software.getNombre());
    }

    @Test
    public void settersYGetters_actualizanEstado() {
        Software software = new Software();

        software.setId(8);
        software.setNombre("Office");

        assertEquals(8, software.getId());
        assertEquals("Office", software.getNombre());
    }
}
