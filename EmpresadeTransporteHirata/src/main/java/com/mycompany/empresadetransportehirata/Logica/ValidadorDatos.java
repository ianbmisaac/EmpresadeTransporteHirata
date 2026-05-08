package com.mycompany.empresadetransportehirata.Logica;

import java.util.Calendar;
import java.util.Date;

/**
 * Utilidad estática para validaciones simples de entrada.
 *
 * <p>Centraliza reglas usadas por la capa de presentación y la lógica del
 * dominio para validar textos, rangos, costos, estados, licencias,
 * kilometraje y fechas.</p>
 */
public class ValidadorDatos {
    
    /**
     * Valida que un string no esté vacío
     * @param valor el valor a validar
     * @return true si no está vacío, false si está vacío o es null
     */
    public static boolean esValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
    
    /**
     * Valida que un número sea positivo
     * @param numero el número a validar
     * @return true si es mayor a 0, false en caso contrario
     */
    public static boolean esPositivo(int numero) {
        return numero > 0;
    }
    
    /**
     * Valida que un número esté dentro de un rango
     * @param numero el número a validar
     * @param minimo el valor mínimo (inclusive)
     * @param maximo el valor máximo (inclusive)
     * @return true si está en el rango, false en caso contrario
     */
    public static boolean esEnRango(int numero, int minimo, int maximo) {
        return numero >= minimo && numero <= maximo;
    }
    
    /**
     * Valida que un costo sea válido (positivo y razonable)
     * @param costo el costo a validar
     * @return true si el costo es válido (entre 0 y 999999)
     */
    public static boolean esConstoValido(int costo) {
        return esEnRango(costo, 1, 999999);
    }
    
    /**
     * Valida que un RUT tenga el formato correcto XXX-XX
     * @param rut el RUT a validar
     * @return true si tiene formato válido, false en caso contrario
     */
   // public static boolean esRutValido(String rut) {
     //   if (!esValido(rut)) return false;
       // return rut.matches("\\d{1,8}-[\\dkK]");
    //}
    
    /**
     * Valida que un estado sea válido (0=Pendiente, 1=En Proceso, 2=Completado)
     * @param estado el estado a validar
     * @return true si es válido
     */
    public static boolean esEstadoValido(int estado) {
        return esEnRango(estado, 0, 2);
    }
    
    /**
     * Valida que un tipo de licencia sea válido
     * @param licencia el tipo de licencia a validar
     * @return true si es válido
     */
    public static boolean esLicenciaValida(String licencia) {
        if (!esValido(licencia)) return false;
        return licencia.matches("[ABC][0-9]?");
    }
    
    /**
     * Valida que un km sea positivo
     * @param km el km a validar
     * @return true si es positivo
     */
    public static boolean esKmValido(int km) {
        return km >= 0;
    }

    /**
     * Valida que una fecha sea realista.
     * Reglas: no null, año >= anioMinimo y no futura.
     * @param fecha la fecha a validar
     * @param anioMinimo año mínimo permitido
     * @return true si la fecha es válida
     */
    public static boolean esFechaRealista(Date fecha, int anioMinimo) {
        if (fecha == null) {
            return false;
        }

        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);
        int anioFecha = calFecha.get(Calendar.YEAR);

        Calendar hoy = Calendar.getInstance();

        return anioFecha >= anioMinimo && !fecha.after(hoy.getTime());
    }
}
