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

    public PiezaInventario() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Timestamp getUltimaActualizacion() {
        return ultimaActualizacion;
    }

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