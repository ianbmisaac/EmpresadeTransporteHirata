package com.mycompany.empresadetransportehirata.Logica;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Pruebas unitarias para {@link ValidarRut}.
 *
 * <p>Verifica casos válidos, entradas nulas o incompletas, formatos inválidos
 * y dígitos verificadores incorrectos.</p>
 */
public class ValidarRutTest {

    /**
     * Debe aceptar RUT cuyos dígitos verificadores son correctos.
     */
    @Test
    public void validar_retornaTrueParaRutsValidos() {
        assertTrue(ValidarRut.validar("12345678-5"));
        assertTrue(ValidarRut.validar("11111111-1"));
        assertTrue(ValidarRut.validar("6-k"));
    }

    /**
     * Debe rechazar entradas nulas, vacías o sin guion separador.
     */
    @Test
    public void validar_retornaFalseParaRutsNulosOVacios() {
        assertFalse(ValidarRut.validar(null));
        assertFalse(ValidarRut.validar(""));
        assertFalse(ValidarRut.validar("12345678"));
    }

    /**
     * Debe rechazar cadenas con caracteres o estructura no admitidos.
     */
    @Test
    public void validar_retornaFalseParaFormatoInvalido() {
        assertFalse(ValidarRut.validar("12.345.678-5"));
        assertFalse(ValidarRut.validar("ABCDEF12-5"));
        assertFalse(ValidarRut.validar("12345678-5-1"));
    }

    /**
     * Debe rechazar RUT cuyo dígito verificador no coincide con el calculado.
     */
    @Test
    public void validar_retornaFalseParaDigitoVerificadorIncorrecto() {
        assertFalse(ValidarRut.validar("12345678-9"));
        assertFalse(ValidarRut.validar("11111111-K"));
    }
}