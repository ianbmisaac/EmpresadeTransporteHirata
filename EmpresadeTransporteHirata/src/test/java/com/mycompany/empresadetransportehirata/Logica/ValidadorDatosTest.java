package com.mycompany.empresadetransportehirata.Logica;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link ValidadorDatos}.
 *
 * <p>Cubre reglas de validación para textos, rangos numéricos, costos,
 * estados, licencias, kilometraje y fechas.</p>
 */
public class ValidadorDatosTest {

    /**
     * Verifica la validación básica de cadenas nulas, vacías y con contenido.
     */
    @Test
    public void esValido_validaNullVacioYTextoConContenido() {
        assertFalse(ValidadorDatos.esValido(null));
        assertFalse(ValidadorDatos.esValido("   "));
        assertTrue(ValidadorDatos.esValido("Hirata"));
    }

    /**
     * Verifica reglas de positividad y pertenencia a rango inclusive.
     */
    @Test
    public void esPositivoYEsEnRango_respetanLimitesEsperados() {
        assertTrue(ValidadorDatos.esPositivo(1));
        assertFalse(ValidadorDatos.esPositivo(0));
        assertFalse(ValidadorDatos.esPositivo(-2));

        assertTrue(ValidadorDatos.esEnRango(5, 1, 10));
        assertTrue(ValidadorDatos.esEnRango(1, 1, 10));
        assertTrue(ValidadorDatos.esEnRango(10, 1, 10));
        assertFalse(ValidadorDatos.esEnRango(0, 1, 10));
        assertFalse(ValidadorDatos.esEnRango(11, 1, 10));
    }

    /**
     * Verifica validaciones específicas de costo, estado, licencia y
     * kilometraje.
     */
    @Test
    public void validacionesDeCostoEstadoLicenciaYKm_funcionanSegunReglas() {
        assertTrue(ValidadorDatos.esConstoValido(1));
        assertTrue(ValidadorDatos.esConstoValido(999999));
        assertFalse(ValidadorDatos.esConstoValido(0));
        assertFalse(ValidadorDatos.esConstoValido(1000000));

        assertTrue(ValidadorDatos.esEstadoValido(0));
        assertTrue(ValidadorDatos.esEstadoValido(2));
        assertFalse(ValidadorDatos.esEstadoValido(-1));
        assertFalse(ValidadorDatos.esEstadoValido(3));

        assertTrue(ValidadorDatos.esLicenciaValida("A"));
        assertTrue(ValidadorDatos.esLicenciaValida("A4"));
        assertTrue(ValidadorDatos.esLicenciaValida("B"));
        assertTrue(ValidadorDatos.esLicenciaValida("C9"));
        assertFalse(ValidadorDatos.esLicenciaValida(null));
        assertFalse(ValidadorDatos.esLicenciaValida("D1"));
        assertFalse(ValidadorDatos.esLicenciaValida("AA"));

        assertTrue(ValidadorDatos.esKmValido(0));
        assertTrue(ValidadorDatos.esKmValido(1500));
        assertFalse(ValidadorDatos.esKmValido(-1));
    }

    /**
     * Verifica que la fecha sea no nula, no futura y posterior al año mínimo.
     */
    @Test
    public void esFechaRealista_rechazaNullFuturasYAnioMenorAlMinimo() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, -2);
        calendario.set(Calendar.MILLISECOND, 0);
        Date fechaValida = calendario.getTime();

        calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 2);
        calendario.set(Calendar.MILLISECOND, 0);
        Date fechaFutura = calendario.getTime();

        calendario.set(1990, Calendar.JANUARY, 1, 10, 0, 0);
        calendario.set(Calendar.MILLISECOND, 0);
        Date fechaAntigua = calendario.getTime();

        assertFalse(ValidadorDatos.esFechaRealista(null, 2000));
        assertTrue(ValidadorDatos.esFechaRealista(fechaValida, 2000));
        assertFalse(ValidadorDatos.esFechaRealista(fechaFutura, 2000));
        assertFalse(ValidadorDatos.esFechaRealista(fechaAntigua, 2000));
    }
}