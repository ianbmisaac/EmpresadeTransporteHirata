/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mycompany.empresadetransportehirata.Logica.Camion;
import com.mycompany.empresadetransportehirata.Logica.Conductor;

/**
 * Acceso a datos para la entidad {@link Camion}.
 *
 * <p>Encapsula operaciones de persistencia, consulta y filtrado de camiones.
 * </p>
 */

public class CamionDAO {

    /**
     * Guarda un camión usando un identificador previamente calculado.
     *
     * @param c entidad del camión a persistir
     * @param idAsignado identificador que se desea utilizar
     * @return el id asignado si la operación fue exitosa, o -1 si no se guardó
     */
    public int guardarCamion(Camion c, int idAsignado) {
        String sql = "INSERT INTO camiones (id, patente, marca, modelo, ano, kmInicial, id_conductor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAsignado);
            ps.setString(2, c.getPatente());
            ps.setString(3, c.getMarca());
            ps.setString(4, c.getModelo());
            ps.setDate(5, c.getAno());
            ps.setInt(6, c.getKmInicial());

            if (c.getConductor() != null) {
                ps.setInt(7, c.getConductor().getId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            int filas = ps.executeUpdate();
            return filas > 0 ? idAsignado : -1;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Duplicate entry")) {
                throw new RuntimeException("La patente ya existe en la base de datos.", e);
            }
            throw new RuntimeException("Error al guardar camión: " + msg, e);
        }
    }

    /**
     * Obtiene el próximo identificador disponible en la tabla de camiones.
     *
     * @return siguiente id sugerido o 1 si no se pudo determinar
     */
    public int obtenerProximoIdDisponible() {
        String sql = "SELECT IFNULL(MAX(id), 0) + 1 AS proximoId FROM camiones";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("proximoId");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener próximo ID: " + e.getMessage());
        }
        return 1; // fallback
    }

    /**
     * Lista todos los camiones y, si aplica, su conductor asociado.
     *
     * @return lista completa de camiones
     */
    public List<Camion> listarCamiones() {
        List<Camion> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.patente, c.marca, c.modelo, c.ano, c.kmInicial, " +
                     "co.id AS idConductor, co.nombre, co.apellido " +
                     "FROM camiones c " +
                     "LEFT JOIN conductores co ON c.id_conductor = co.id";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearCamion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar camiones: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Lista los camiones asociados a un conductor identificado por rut.
     *
     * @param rutConductor rut del conductor autenticado
     * @return lista de camiones asociados a ese conductor
     */
    public List<Camion> listarCamionesPorRutConductor(String rutConductor) {
        List<Camion> lista = new ArrayList<>();
        if (rutConductor == null || rutConductor.isBlank()) {
            return lista;
        }

        String sql = "SELECT c.id, c.patente, c.marca, c.modelo, c.ano, c.kmInicial, "
                + "co.id AS idConductor, co.nombre, co.apellido "
                + "FROM camiones c "
                + "INNER JOIN conductores co ON c.id_conductor = co.id "
                + "WHERE UPPER(REPLACE(REPLACE(REPLACE(co.rut, '.', ''), '-', ''), ' ', '')) = ?";

        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarRut(rutConductor));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCamion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los camiones del conductor: " + e.getMessage(), e);
        }

        return lista;
    }

    /**
     * Actualiza los datos persistidos de un camión existente.
     *
     * @param c entidad con el id y datos a actualizar
     * @return true si se actualizó al menos una fila
     */
    public boolean actualizarCamion(Camion c) {
        String sql = "UPDATE camiones SET patente=?, marca=?, modelo=?, ano=?, kmInicial=?, id_conductor=? WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getPatente());
            ps.setString(2, c.getMarca());
            ps.setString(3, c.getModelo());
            ps.setDate(4, c.getAno());
            ps.setInt(5, c.getKmInicial());

            if (c.getConductor() != null) {
                ps.setInt(6, c.getConductor().getId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setInt(7, c.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar camión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un camión por su identificador.
     *
     * @param id id del camión a eliminar
     * @return true si se eliminó al menos una fila
     */
    public boolean eliminarCamion(int id) {
        String sql = "DELETE FROM camiones WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar camión: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene el kilometraje actual de un camión identificado por patente.
     *
     * @param patente patente del camión
     * @return kilometraje almacenado, o 0 si no existe o falla la consulta
     */
    public int obtenerKmPorPatente(String patente) {
    String sql = "SELECT kmInicial FROM camiones WHERE patente = ?";
    try (Connection conn = conexion.get();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, patente);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("kmInicial");
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener km: " + e.getMessage());
    }
    return 0;
}

    /**
     * Convierte una fila del resultado SQL en una entidad {@link Camion}.
     *
     * @param rs fila actual del result set
     * @return camión mapeado
     * @throws SQLException si falla la lectura del resultado
     */
    private Camion mapearCamion(ResultSet rs) throws SQLException {
        Camion camion = new Camion();
        camion.setId(rs.getInt("id"));
        camion.setPatente(rs.getString("patente"));
        camion.setMarca(rs.getString("marca"));
        camion.setModelo(rs.getString("modelo"));
        camion.setAno(rs.getDate("ano"));
        camion.setKmInicial(rs.getInt("kmInicial"));

        int idConductor = rs.getInt("idConductor");
        if (idConductor > 0) {
            Conductor conductor = new Conductor();
            conductor.setId(idConductor);
            conductor.setNombre(rs.getString("nombre"));
            conductor.setApellido(rs.getString("apellido"));
            camion.setConductor(conductor);
        } else {
            camion.setConductor(null);
        }

        return camion;
    }

    /**
     * Normaliza un rut eliminando separadores y espacios.
     *
     * @param rut rut original
     * @return rut normalizado en mayúsculas
     */
    private String normalizarRut(String rut) {
        return rut
                .trim()
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase(Locale.ROOT);
    }
    
}



