/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link Camion}.
 *
 * <p>Valida constructores, acceso a propiedades y el contenido textual de la
 * representación del vehículo.</p>
 */
public class CamionTest {

    /**
     * Verifica los valores por defecto del constructor vacío.
     */
    @Test
    public void constructorVacio_dejaValoresPorDefecto() {
        Camion camion = new Camion();

        assertEquals(0, camion.getId());
        assertNull(camion.getPatente());
        assertNull(camion.getMarca());
        assertNull(camion.getModelo());
        assertNull(camion.getAno());
        assertEquals(0, camion.getKmInicial());
        assertNull(camion.getConductor());
    }

    /**
     * Verifica que el constructor completo asigna todos los atributos.
     */
    @Test
    public void constructorConParametros_asignaTodosLosCampos() {
        Date ano = Date.valueOf("2024-03-10");
        Conductor conductor = new Conductor(7, "12345678-5", "Pedro", "Gonzalez", "A5");

        Camion camion = new Camion(12, "ABCD-12", "Volvo", "FH16", ano, 150000, conductor);

        assertEquals(12, camion.getId());
        assertEquals("ABCD-12", camion.getPatente());
        assertEquals("Volvo", camion.getMarca());
        assertEquals("FH16", camion.getModelo());
        assertEquals(ano, camion.getAno());
        assertEquals(150000, camion.getKmInicial());
        assertSame(conductor, camion.getConductor());
    }

    /**
     * Verifica que setters y getters reflejan el estado actualizado del modelo.
     */
    @Test
    public void settersYGetters_actualizanCorrectamenteElEstado() {
        Camion camion = new Camion();
        Date ano = Date.valueOf("2022-08-01");
        Conductor conductor = new Conductor();
        conductor.setId(3);
        conductor.setRut("98765432-1");
        conductor.setNombre("Ana");
        conductor.setApellido("Perez");
        conductor.setLicencia("A4");

        camion.setId(5);
        camion.setPatente("ZXCV-99");
        camion.setMarca("Scania");
        camion.setModelo("R500");
        camion.setAno(ano);
        camion.setKmInicial(82000);
        camion.setConductor(conductor);

        assertEquals(5, camion.getId());
        assertEquals("ZXCV-99", camion.getPatente());
        assertEquals("Scania", camion.getMarca());
        assertEquals("R500", camion.getModelo());
        assertEquals(ano, camion.getAno());
        assertEquals(82000, camion.getKmInicial());
        assertSame(conductor, camion.getConductor());
    }

    /**
     * Verifica que {@code toString()} incluya el nombre del conductor cuando
     * existe una asignación.
     */
    @Test
    public void toString_incluyeNombreDelConductorCuandoExiste() {
        Conductor conductor = new Conductor(2, "11111111-1", "Luis", "Soto", "A2");
        Camion camion = new Camion(8, "QWER-45", "Mercedes", "Actros", Date.valueOf("2021-01-15"), 64000, conductor);

        String resultado = camion.toString();

        assertTrue(resultado.contains("id=8"));
        assertTrue(resultado.contains("patente='QWER-45'"));
        assertTrue(resultado.contains("marca='Mercedes'"));
        assertTrue(resultado.contains("modelo='Actros'"));
        assertTrue(resultado.contains("kmInicial=64000"));
        assertTrue(resultado.contains("conductor=Luis Soto"));
    }

    /**
     * Verifica que {@code toString()} indique ausencia de conductor cuando no
     * existe asignación.
     */
    @Test
    public void toString_muestraNingunoCuandoNoHayConductor() {
        Camion camion = new Camion();
        camion.setPatente("LMNO-77");

        String resultado = camion.toString();

        assertTrue(resultado.contains("patente='LMNO-77'"));
        assertTrue(resultado.contains("conductor=Ninguno"));
    }
}
