package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Date;

/**
 * Representa un registro de mantenimiento realizado a un equipo de oficina.
 */
public class MantenimientoEquipo {
    private int id;
    private int idEquipo;
    private String nombreEquipo;
    private String tipoMantenimiento;
    private Date fecha;
    private String tecnico;
    private String descripcion;
    private String estado;
    
    // ── NUEVOS ATRIBUTOS PARA INVENTARIO Y PIEZAS ──
    private int idPieza;
    private int cantidadPieza;
    /** Crea una instancia vacía de MantenimientoEquipo. */
    public MantenimientoEquipo() {}

    /**
     * Crea un mantenimiento con todos sus datos.
     * @param id                identificador único
     * @param idEquipo          ID del equipo asociado
     * @param nombreEquipo      nombre del equipo (denormalizado)
     * @param tipoMantenimiento tipo (Preventivo, Correctivo, Instalación)
     * @param fecha             fecha del mantenimiento
     * @param tecnico           nombre del técnico responsable
     * @param descripcion       descripción del trabajo realizado
     * @param estado            estado (Pendiente, En Proceso, Completado)
     * @param idPieza           ID de la pieza o insumo utilizado
     * @param cantidadPieza     Cantidad de piezas utilizadas
     */
    public MantenimientoEquipo(int id, int idEquipo, String nombreEquipo, String tipoMantenimiento, Date fecha, String tecnico, String descripcion, String estado) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.tipoMantenimiento = tipoMantenimiento;
        this.fecha = fecha;
        this.tecnico = tecnico;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idPieza = idPieza;
        this.cantidadPieza = cantidadPieza;
    }

    /** @return identificador único del mantenimiento */
    public int getId() { return id; }
    /** @param id identificador a asignar */
    public void setId(int id) { this.id = id; }

    /** @return ID del equipo asociado */
    public int getIdEquipo() { return idEquipo; }
    /** @param idEquipo ID del equipo a asociar */
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    /** @return nombre del equipo (denormalizado) */
    public String getNombreEquipo() { return nombreEquipo; }
    /** @param nombreEquipo nombre del equipo a asignar */
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    /** @return tipo de mantenimiento */
    public String getTipoMantenimiento() { return tipoMantenimiento; }
    /** @param tipoMantenimiento tipo a asignar */
    public void setTipoMantenimiento(String tipoMantenimiento) { this.tipoMantenimiento = tipoMantenimiento; }

    /** @return fecha del mantenimiento */
    public Date getFecha() { return fecha; }
    /** @param fecha fecha a asignar */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /** @return nombre del técnico responsable */
    public String getTecnico() { return tecnico; }
    /** @param tecnico nombre del técnico a asignar */
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    /** @return descripción del trabajo realizado */
    public String getDescripcion() { return descripcion; }
    /** @param descripcion descripción a asignar */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return estado actual (Pendiente, En Proceso, Completado) */
    public String getEstado() { return estado; }
    /** @param estado estado a asignar */
    public void setEstado(String estado) { this.estado = estado; }

    // ── NUEVOS GETTERS Y SETTERS ──
    
    /** @return ID de la pieza o insumo asignado */
    public int getIdPieza() { return idPieza; }
    /** @param idPieza ID de la pieza a asignar */
    public void setIdPieza(int idPieza) { this.idPieza = idPieza; }

    /** @return cantidad utilizada de la pieza */
    public int getCantidadPieza() { return cantidadPieza; }
    /** @param cantidadPieza cantidad a asignar */
    public void setCantidadPieza(int cantidadPieza) { this.cantidadPieza = cantidadPieza; }

    /**
     * Representación textual del mantenimiento.
     * @return cadena con id, equipo, tipo, fecha, pieza y cantidad
     */
    @Override
    public String toString() {
        return "MantenimientoEquipo{" +
                "id=" + id +
                ", idEquipo=" + idEquipo +
                ", equipo='" + nombreEquipo + '\'' +
                ", tipo='" + tipoMantenimiento + '\'' +
                ", fecha=" + fecha +
                ", idPieza=" + idPieza +
                ", cantidadPieza=" + cantidadPieza +
                '}';
    }
}
