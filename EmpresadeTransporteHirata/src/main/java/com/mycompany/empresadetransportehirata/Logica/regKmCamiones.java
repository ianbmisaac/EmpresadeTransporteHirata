/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Timestamp;

/**
 * Representa un registro histórico de kilometraje de un camión.
 *
 * <p>Incluye el vehículo, el conductor que realizó el registro, la fecha y la
 * observación asociada.</p>
 */
public class regKmCamiones {
    private int id;
    private int kilometraje;
    private String observacion;
    private Timestamp fecha;
    private Conductor camionero;
    private Camion camion;

    /**
     * Obtiene el identificador del registro.
     *
     * @return id del registro de kilometraje
     */
    public int getId() {
        return id;
    }

    /**
     * Define el identificador del registro.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Obtiene el kilometraje registrado.
     *
     * @return kilometraje informado
     */
    public int getKilometraje() {
        return kilometraje;
    }

    /**
     * Define el kilometraje registrado.
     *
     * @param kilometraje kilometraje a guardar
     */
    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    /**
     * Obtiene la observación del registro.
     *
     * @return observación asociada
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Define la observación del registro.
     *
     * @param observacion texto descriptivo del registro
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene la fecha y hora del registro.
     *
     * @return marca temporal del registro
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * Define la fecha y hora del registro.
     *
     * @param fecha marca temporal a asignar
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Obtiene el conductor asociado al registro.
     *
     * @return conductor que registró el kilometraje
     */
    public Conductor getCamionero() {
        return camionero;
    }

    /**
     * Define el conductor asociado al registro.
     *
     * @param camionero conductor a asociar
     */
    public void setCamionero(Conductor camionero) {
        this.camionero = camionero;
    }

    /**
     * Obtiene el camión asociado al registro.
     *
     * @return camión relacionado
     */
    public Camion getCamion() {
        return camion;
    }

    /**
     * Define el camión asociado al registro.
     *
     * @param camion camión a asociar
     */
    public void setCamion(Camion camion) {
        this.camion = camion;
    }
}
