package com.mycompany.empresadetransportehirata.Logica;

/**
 * Representa un equipo de oficina registrado en el sistema.
 */
public class EquipoOficina {

    private int id;
    private String nombre;
    private String tipo;
    private String marca;
    private String modelo;
    private String serie;
    private String estado;
    private Software software;

    /** Crea una instancia vacía de EquipoOficina. */
    public EquipoOficina() {
    }

    /**
     * Crea un equipo de oficina con todos sus datos.
     * @param id       identificador único
     * @param nombre   nombre del equipo
     * @param tipo     tipo (PC, Impresora, etc.)
     * @param marca    marca del equipo
     * @param modelo   modelo del equipo
     * @param serie    número de serie
     * @param estado   estado actual (Activo, En reparación, Dado de baja)
     * @param software software asignado (puede ser null)
     */
    public EquipoOficina(int id, String nombre, String tipo, String marca, String modelo, String serie, String estado, Software software) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.estado = estado;
        this.software = software;
    }

    /** @return identificador único del equipo */
    public int getId() {
        return id;
    }

    /** @param id identificador único a asignar */
    public void setId(int id) {
        this.id = id;
    }

    /** @return nombre del equipo */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nombre a asignar */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return tipo de equipo (PC, Impresora, etc.) */
    public String getTipo() {
        return tipo;
    }

    /** @param tipo tipo a asignar */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /** @return marca del equipo */
    public String getMarca() {
        return marca;
    }

    /** @param marca marca a asignar */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /** @return modelo del equipo */
    public String getModelo() {
        return modelo;
    }

    /** @param modelo modelo a asignar */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /** @return número de serie */
    public String getSerie() {
        return serie;
    }

    /** @param serie número de serie a asignar */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /** @return estado actual (Activo, En reparación, Dado de baja) */
    public String getEstado() {
        return estado;
    }

    /** @param estado estado a asignar */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /** @return software asignado o null si no tiene */
    public Software getSoftware() {
        return software;
    }

    /** @param software software a asignar (puede ser null) */
    public void setSoftware(Software software) {
        this.software = software;
    }

}
