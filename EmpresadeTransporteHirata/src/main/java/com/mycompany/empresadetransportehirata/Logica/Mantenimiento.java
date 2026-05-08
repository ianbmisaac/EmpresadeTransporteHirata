/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;
import java.util.Date;
/**
 * Representa un registro de mantenimiento asociado a un camión.
 *
 * <p>Almacena la información operativa necesaria para registrar, consultar y
 * actualizar trabajos de mantenimiento.</p>
 */
public class Mantenimiento {

    public int id;
    public String camion;
    public String tipoMantenimiento;
    public Date fecha;
    public int kmActual;
    public int costo;
    public int estado;
    public String observacion;
    public String taller;
    
    /**
     * Obtiene el identificador del mantenimiento.
     *
     * @return id del mantenimiento
     */
    public int getId() {
        return id;
    }

    /**
     * Define el identificador del mantenimiento.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la patente del camión asociado.
     *
     * @return patente del camión
     */
    public String getCamion() {
        return camion;
    }

    /**
     * Define la patente del camión asociado.
     *
     * @param camion patente a asignar
     */
    public void setCamion(String camion) {
        this.camion = camion;
    }

    /**
     * Obtiene el tipo de mantenimiento registrado.
     *
     * @return tipo de mantenimiento
     */
    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    /**
     * Define el tipo de mantenimiento.
     *
     * @param tipoMantenimiento descripción del mantenimiento
     */
    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    /**
     * Obtiene la fecha del mantenimiento.
     *
     * @return fecha registrada
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Define la fecha del mantenimiento.
     *
     * @param fecha fecha a asignar
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el kilometraje reportado al momento del mantenimiento.
     *
     * @return kilometraje actual
     */
    public int getKmActual() {
        return kmActual;
    }

    /**
     * Define el kilometraje reportado al momento del mantenimiento.
     *
     * @param kmActual kilometraje a asignar
     */
    public void setKmActual(int kmActual) {
        this.kmActual = kmActual;
    }

    /**
     * Obtiene el costo del mantenimiento.
     *
     * @return costo registrado
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Define el costo del mantenimiento.
     *
     * @param costo costo a asignar
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Obtiene el estado del mantenimiento.
     *
     * @return código de estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Define el estado del mantenimiento.
     *
     * @param estado código de estado a asignar
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la observación asociada al mantenimiento.
     *
     * @return observación registrada
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Define la observación asociada al mantenimiento.
     *
     * @param observacion texto descriptivo del trabajo realizado
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene el taller responsable.
     *
     * @return nombre del taller
     */
    public String getTaller() {
        return taller;
    }

    /**
     * Define el taller responsable.
     *
     * @param taller taller a asignar
     */
    public void setTaller(String taller) {
        this.taller = taller;
    }
    
}
