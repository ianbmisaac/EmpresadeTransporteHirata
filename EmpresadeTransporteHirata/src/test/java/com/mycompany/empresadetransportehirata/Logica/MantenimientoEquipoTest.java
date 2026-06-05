package com.mycompany.empresadetransportehirata.Logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import java.sql.Date;

/**
 * Pruebas unitarias para {@link MantenimientoEquipo}.
 */
public class MantenimientoEquipoTest {

    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        MantenimientoEquipo registro = new MantenimientoEquipo();

        assertEquals(0, registro.getId());
        assertEquals(0, registro.getIdEquipo());
        assertNull(registro.getNombreEquipo());
        assertNull(registro.getTipoMantenimiento());
        assertNull(registro.getFecha());
        assertNull(registro.getTecnico());
        assertNull(registro.getDescripcion());
        assertNull(registro.getEstado());
        assertEquals(0, registro.getIdPieza());
        assertEquals(0, registro.getCantidadPieza());
    }

    @Test
    public void settersYGetters_actualizanValores() {
        MantenimientoEquipo registro = new MantenimientoEquipo();
        Date fecha = Date.valueOf("2024-10-07");

        registro.setId(1);
        registro.setIdEquipo(20);
        registro.setNombreEquipo("PC Oficina");
        registro.setTipoMantenimiento("Preventivo");
        registro.setFecha(fecha);
        registro.setTecnico("Ana");
        registro.setDescripcion("Cambio de toner");
        registro.setEstado("Completado");
        registro.setIdPieza(5);
        registro.setCantidadPieza(2);

        assertEquals(1, registro.getId());
        assertEquals(20, registro.getIdEquipo());
        assertEquals("PC Oficina", registro.getNombreEquipo());
        assertEquals("Preventivo", registro.getTipoMantenimiento());
        assertEquals(fecha, registro.getFecha());
        assertEquals("Ana", registro.getTecnico());
        assertEquals("Cambio de toner", registro.getDescripcion());
        assertEquals("Completado", registro.getEstado());
        assertEquals(5, registro.getIdPieza());
        assertEquals(2, registro.getCantidadPieza());
    }

    @Test
    public void toString_incluyeInformacionBasica() {
        MantenimientoEquipo registro = new MantenimientoEquipo();
        registro.setId(3);
        registro.setIdEquipo(10);
        registro.setNombreEquipo("Impresora");
        registro.setTipoMantenimiento("Correctivo");
        registro.setFecha(Date.valueOf("2024-10-07"));
        registro.setIdPieza(4);
        registro.setCantidadPieza(1);

        String texto = registro.toString();

        assertEquals(true, texto.contains("id=3"));
        assertEquals(true, texto.contains("idEquipo=10"));
        assertEquals(true, texto.contains("equipo='Impresora'"));
        assertEquals(true, texto.contains("tipo='Correctivo'"));
        assertEquals(true, texto.contains("idPieza=4"));
        assertEquals(true, texto.contains("cantidadPieza=1"));
    }
}
