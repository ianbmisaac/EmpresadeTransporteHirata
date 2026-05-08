/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

/**
 * Utilidad estática para validar RUT chilenos mediante el algoritmo módulo 11.
 *
 * <p>El formato esperado es sin puntos y con guion, por ejemplo
 * {@code 12345678-5} o {@code 6-K}.</p>
 */
public class ValidarRut {

    /**
     * Valida un RUT verificando su estructura y su dígito verificador.
     *
     * @param rut RUT sin puntos y con guion, por ejemplo {@code 12345678-9}
     *            o {@code 12345678-K}
     * @return {@code true} si el RUT tiene un formato válido y su dígito
     *         verificador coincide; en caso contrario, {@code false}
     */
    public static boolean validar(String rut) {
        if (rut == null || !rut.contains("-")) {
            return false;
        }

        // Separar cuerpo y dígito verificador
        String[] partes = rut.split("-");
        if (partes.length != 2) {
            return false;
        }

        String cuerpo = partes[0];
        String dvIngresado = partes[1].toUpperCase();

        // Verificar que el cuerpo sea numérico
        if (!cuerpo.matches("\\d+")) {
            return false;
        }

        // Calcular dígito verificador
        String dvCalculado = calcularDV(cuerpo);

        // Comparar
        return dvIngresado.equals(dvCalculado);
    }

    /**
     * Calcula el dígito verificador de un RUT usando módulo 11.
     *
     * @param cuerpo parte numérica del RUT, sin guion ni dígito verificador
     * @return dígito verificador calculado como {@code String}, entre
     *         {@code "0"} y {@code "9"}, o {@code "K"}
     */
    private static String calcularDV(String cuerpo) {
        int suma = 0;
        int multiplicador = 2;

        // Recorrer de derecha a izquierda
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplicador;
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }

        int resto = 11 - (suma % 11);
        if (resto == 11) {
            return "0";
        } else if (resto == 10) {
            return "K";
        } else {
            return String.valueOf(resto);
        }
    }
}
