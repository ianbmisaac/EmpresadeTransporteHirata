package com.mycompany.empresadetransportehirata.Logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link EquipoOficina}.
 */
public class EquipoOficinaTest {

    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        EquipoOficina equipo = new EquipoOficina();

        assertEquals(0, equipo.getId());
        assertNull(equipo.getNombre());
        assertNull(equipo.getTipo());
        assertNull(equipo.getMarca());
        assertNull(equipo.getModelo());
        assertNull(equipo.getSerie());
        assertNull(equipo.getEstado());
        assertNull(equipo.getSoftware());
    }

    @Test
    public void constructorConParametros_asignaTodosLosCampos() {
        Software software = new Software(2, "Office");
        EquipoOficina equipo = new EquipoOficina(3, "PC1", "PC", "Dell", "OptiPlex", "1234", "Activo", software);

        assertEquals(3, equipo.getId());
        assertEquals("PC1", equipo.getNombre());
        assertEquals("PC", equipo.getTipo());
        assertEquals("Dell", equipo.getMarca());
        assertEquals("OptiPlex", equipo.getModelo());
        assertEquals("1234", equipo.getSerie());
        assertEquals("Activo", equipo.getEstado());
        assertEquals(software, equipo.getSoftware());
    }

    @Test
    public void settersYGetters_actualizanValores() {
        EquipoOficina equipo = new EquipoOficina();
        Software software = new Software(7, "Zoom");

        equipo.setId(12);
        equipo.setNombre("Impresora");
        equipo.setTipo("Impresora");
        equipo.setMarca("HP");
        equipo.setModelo("LaserJet");
        equipo.setSerie("A1B2C3");
        equipo.setEstado("En reparación");
        equipo.setSoftware(software);

        assertEquals(12, equipo.getId());
        assertEquals("Impresora", equipo.getNombre());
        assertEquals("Impresora", equipo.getTipo());
        assertEquals("HP", equipo.getMarca());
        assertEquals("LaserJet", equipo.getModelo());
        assertEquals("A1B2C3", equipo.getSerie());
        assertEquals("En reparación", equipo.getEstado());
        assertEquals(software, equipo.getSoftware());
    }
}
