/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

/**
 * Representa a un conductor de la flota.
 *
 * <p>Contiene los datos básicos necesarios para asignarlo a camiones y
 * mostrarlo en las pantallas de gestión.</p>
 */
public class Conductor {
    private int id;
    private String rut;
    private String nombre;
    private String apellido;
    private String licencia;

    /**
     * Crea un conductor vacío.
     */
    public Conductor() {
    }

    /**
     * Crea un conductor con todos sus datos principales.
     *
     * @param id identificador interno del conductor
     * @param rut rut del conductor
     * @param nombre nombre del conductor
     * @param apellido apellido del conductor
     * @param licencia tipo de licencia vigente
     */
    public Conductor(int id, String rut, String nombre, String apellido, String licencia) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.licencia = licencia;
    }

    /**
     * Obtiene el identificador del conductor.
     *
     * @return id del conductor
     */
    public int getId() { return id; }

    /**
     * Define el identificador del conductor.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtiene el rut del conductor.
     *
     * @return rut registrado
     */
    public String getRut() { return rut; }

    /**
     * Define el rut del conductor.
     *
     * @param rut rut a asignar
     */
    public void setRut(String rut) { this.rut = rut; }

    /**
     * Obtiene el nombre del conductor.
     *
     * @return nombre registrado
     */
    public String getNombre() { return nombre; }

    /**
     * Define el nombre del conductor.
     *
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el apellido del conductor.
     *
     * @return apellido registrado
     */
    public String getApellido() { return apellido; }

    /**
     * Define el apellido del conductor.
     *
     * @param apellido apellido a asignar
     */
    public void setApellido(String apellido) { this.apellido = apellido; }

    /**
     * Obtiene la licencia del conductor.
     *
     * @return tipo de licencia
     */
    public String getLicencia() { return licencia; }

    /**
     * Define la licencia del conductor.
     *
     * @param licencia licencia a asignar
     */
    public void setLicencia(String licencia) { this.licencia = licencia; }

    /**
     * Devuelve una representación legible del conductor.
     *
     * @return nombre completo junto al rut
     */
    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + rut + ")";
    }
}
