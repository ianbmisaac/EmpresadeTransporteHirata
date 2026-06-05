/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Logica;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Representa una pieza registrada dentro del inventario de mantenimiento.
 */
public class PiezaInventario {
    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private String unidad;
    private int stockMinimo;
    private String ubicacion;
    private Date fechaRegistro;
    private Timestamp ultimaActualizacion;

    /** Crea una instancia vacía de PiezaInventario. */
    public PiezaInventario() {
    }

    /**
     * Crea una pieza de inventario con todos sus datos.
     * @param id          identificador único
     * @param nombre      nombre de la pieza
     * @param descripcion descripción detallada
     * @param cantidad    cantidad disponible en stock
     * @param unidad      unidad de medida (Unidad, Caja, Kit, Pack)
     * @param stockMinimo cantidad mínima antes de alerta
     * @param ubicacion   ubicación física en bodega
     * @param fechaRegistro fecha de ingreso al inventario
     */
    public PiezaInventario(int id, String nombre, String descripcion, int cantidad,
            String unidad, int stockMinimo, String ubicacion, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.stockMinimo = stockMinimo;
        this.ubicacion = ubicacion;
        this.fechaRegistro = fechaRegistro;
    }

    /** @return identificador único de la pieza */
    public int getId() {
        return id;
    }

    /** @param id identificador a asignar */
    public void setId(int id) {
        this.id = id;
    }

    /** @return nombre de la pieza */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nombre a asignar */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return descripción detallada de la pieza */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion descripción a asignar */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return cantidad disponible en stock */
    public int getCantidad() {
        return cantidad;
    }

    /** @param cantidad cantidad a asignar */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /** @return unidad de medida (Unidad, Caja, Kit, Pack) */
    public String getUnidad() {
        return unidad;
    }

    /** @param unidad unidad a asignar */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /** @return stock mínimo antes de alerta */
    public int getStockMinimo() {
        return stockMinimo;
    }

    /** @param stockMinimo stock mínimo a asignar */
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    /** @return ubicación física en bodega */
    public String getUbicacion() {
        return ubicacion;
    }

    /** @param ubicacion ubicación a asignar */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /** @return fecha de ingreso al inventario */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /** @param fechaRegistro fecha de ingreso a asignar */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /** @return timestamp de la última actualización */
    public Timestamp getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    /** @param ultimaActualizacion timestamp a asignar */
    public void setUltimaActualizacion(Timestamp ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public boolean isStockBajo() {
        return cantidad <= stockMinimo;
    }

    @Override
    public String toString() {
        return nombre + " - " + cantidad + " " + unidad;
    }
}