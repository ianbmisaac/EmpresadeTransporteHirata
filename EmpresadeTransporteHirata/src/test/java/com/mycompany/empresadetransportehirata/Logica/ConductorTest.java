package com.mycompany.empresadetransportehirata.Logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link Conductor}.
 *
 * <p>Comprueba valores por defecto, constructor con parámetros, mutadores y la
 * representación textual del modelo.</p>
 */
public class ConductorTest {

    /**
     * Verifica que el constructor vacío inicializa el objeto sin datos.
     */
    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        Conductor conductor = new Conductor();

        assertEquals(0, conductor.getId());
        assertNull(conductor.getRut());
        assertNull(conductor.getNombre());
        assertNull(conductor.getApellido());
        assertNull(conductor.getLicencia());
    }

    /**
     * Verifica que el constructor con parámetros conserva los valores
     * entregados.
     */
    @Test
    public void constructorConParametros_asignaTodosLosCampos() {
        Conductor conductor = new Conductor(11, "12345678-9", "Carla", "Muñoz", "A3");

        assertEquals(11, conductor.getId());
        assertEquals("12345678-9", conductor.getRut());
        assertEquals("Carla", conductor.getNombre());
        assertEquals("Muñoz", conductor.getApellido());
        assertEquals("A3", conductor.getLicencia());
    }

    /**
     * Verifica que setters y getters actualizan correctamente el estado.
     */
    @Test
    public void settersYGetters_actualizanCorrectamenteElEstado() {
        Conductor conductor = new Conductor();

        conductor.setId(4);
        conductor.setRut("98765432-1");
        conductor.setNombre("Diego");
        conductor.setApellido("Rojas");
        conductor.setLicencia("A5");

        assertEquals(4, conductor.getId());
        assertEquals("98765432-1", conductor.getRut());
        assertEquals("Diego", conductor.getNombre());
        assertEquals("Rojas", conductor.getApellido());
        assertEquals("A5", conductor.getLicencia());
    }

    /**
     * Verifica el formato esperado de {@code toString()}.
     */
    @Test
    public void toString_formateaNombreApellidoYRut() {
        Conductor conductor = new Conductor(9, "11111111-1", "Laura", "Soto", "A2");

        assertEquals("Laura Soto (11111111-1)", conductor.toString());
    }
}
