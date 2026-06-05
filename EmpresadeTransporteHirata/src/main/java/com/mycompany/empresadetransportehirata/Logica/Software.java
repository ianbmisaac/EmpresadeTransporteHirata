/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

/**
 * Representa un software registrado en el sistema, que puede asignarse a equipos de oficina.
 */
public class Software {

    private int id;
    private String nombre;

    /** Crea una instancia vacía de Software. */
    public Software() {

    }

    /**
     * Crea un software con su identificador y nombre.
     * @param id     identificador único
     * @param nombre nombre del software
     */
    public Software(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /** @return identificador único del software */
    public int getId() {
        return id;
    }

    /** @param id identificador a asignar */
    public void setId(int id) {
        this.id = id;
    }

    /** @return nombre del software */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nombre a asignar */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
