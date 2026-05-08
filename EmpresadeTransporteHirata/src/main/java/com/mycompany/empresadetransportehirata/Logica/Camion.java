/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Date;

/**
 * Representa un camión administrado por la empresa.
 *
 * <p>Modela la identidad del vehículo, sus datos técnicos básicos y el
 * conductor actualmente asignado.</p>
 */
public class Camion {
    private int id;
    private String patente;
    private String marca;
    private String modelo;
    private Date ano;
    private int kmInicial;
    private Conductor conductor;

    /**
     * Crea un camión sin datos iniciales.
     */
    public Camion() {}

    /**
     * Crea un camión con todos sus atributos principales.
     *
     * @param id identificador interno del camión
     * @param patente patente del vehículo
     * @param marca marca del camión
     * @param modelo modelo del camión
     * @param ano año de fabricación registrado como fecha
     * @param kmInicial kilometraje actual almacenado
     * @param conductor conductor asignado, o null si no existe asignación
     */
    public Camion(int id, String patente, String marca, String modelo, Date ano, int kmInicial, Conductor conductor) {
        this.id = id;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.kmInicial = kmInicial;
        this.conductor = conductor;
    }

    /**
     * Obtiene el identificador del camión.
     *
     * @return id del camión
     */
    public int getId() {
        return id;
    }

    /**
     * Define el identificador del camión.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la patente del camión.
     *
     * @return patente registrada
     */
    public String getPatente() {
        return patente;
    }

    /**
     * Define la patente del camión.
     *
     * @param patente patente a asignar
     */
    public void setPatente(String patente) {
        this.patente = patente;
    }

    /**
     * Obtiene la marca del camión.
     *
     * @return marca registrada
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define la marca del camión.
     *
     * @param marca marca a asignar
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del camión.
     *
     * @return modelo registrado
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define el modelo del camión.
     *
     * @param modelo modelo a asignar
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el año del camión.
     *
     * @return fecha representativa del año de fabricación
     */
    public Date getAno() {
        return ano;
    }

    /**
     * Define el año del camión.
     *
     * @param ano fecha que representa el año de fabricación
     */
    public void setAno(Date ano) {
        this.ano = ano;
    }

    /**
     * Obtiene el kilometraje actual del camión.
     *
     * @return kilometraje registrado
     */
    public int getKmInicial() {
        return kmInicial;
    }

    /**
     * Define el kilometraje actual del camión.
     *
     * @param kmInicial kilometraje a asignar
     */
    public void setKmInicial(int kmInicial) {
        this.kmInicial = kmInicial;
    }

    /**
     * Obtiene el conductor asignado al camión.
     *
     * @return conductor asociado o null si no existe asignación
     */
    public Conductor getConductor() {
        return conductor;
    }

    /**
     * Define el conductor asignado al camión.
     *
     * @param conductor conductor a asociar, o null para quitar asignación
     */
    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    /**
     * Devuelve una representación legible del camión y su asignación.
     *
     * @return texto descriptivo del objeto
     */
    @Override
    public String toString() {
        return "Camion{" +
                "id=" + id +
                ", patente='" + patente + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", kmInicial=" + kmInicial +
                ", conductor=" + (conductor != null ? conductor.getNombre() + " " + conductor.getApellido() : "Ninguno") +
                '}';
    }
}

