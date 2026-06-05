/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Date;

/**
 * Representa un registro de mantenimiento realizado a un equipo de oficina.
 *
 * <p>Contiene los datos históricos de reparaciones o mantenciones preventivas
 * aplicadas a los computadores, impresoras, etc.</p>
 */
public class MantenimientoEquipo {
    private int id;
    private String equipo;
    private String tipoMantenimiento;
    private Date fecha;
    private String tecnico;
    private String descripcion;
    private String estado;

    public MantenimientoEquipo() {}

    public MantenimientoEquipo(int id, String equipo, String tipoMantenimiento, Date fecha, String tecnico, String descripcion, String estado) {
        this.id = id;
        this.equipo = equipo;
        this.tipoMantenimiento = tipoMantenimiento;
        this.fecha = fecha;
        this.tecnico = tecnico;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEquipo() { return equipo; }
    public void setEquipo(String equipo) { this.equipo = equipo; }

    public String getTipoMantenimiento() { return tipoMantenimiento; }
    public void setTipoMantenimiento(String tipoMantenimiento) { this.tipoMantenimiento = tipoMantenimiento; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "MantenimientoEquipo{" +
                "id=" + id +
                ", equipo='" + equipo + '\'' +
                ", tipo='" + tipoMantenimiento + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}