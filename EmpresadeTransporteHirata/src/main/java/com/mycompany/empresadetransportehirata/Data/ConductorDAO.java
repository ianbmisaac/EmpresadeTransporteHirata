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
import java.util.ArrayList;
import java.util.Locale;

import com.mycompany.empresadetransportehirata.Logica.Conductor;

public class ConductorDAO {

    /**
     * Guarda un conductor usando un identificador previamente definido.
     *
     * @param c entidad del conductor a guardar
     * @param idAsignado id a utilizar en la inserción
     * @return el id asignado si el guardado fue exitoso, o -1 si falló
     */
    public int guardarConductor(Conductor c, int idAsignado) {
        String sql = "INSERT INTO conductores (id, rut, nombre, apellido, licencia) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAsignado);
            ps.setString(2, c.getRut());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellido());
            ps.setString(5, c.getLicencia());

            int filas = ps.executeUpdate();
            return filas > 0 ? idAsignado : -1;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Duplicate entry")) {
                if (msg.contains("rut")) {
                    throw new RuntimeException("El RUT ya existe en la base de datos.", e);
                }
                throw new RuntimeException("Ya existe un dato único duplicado en conductores.", e);
            }
            throw new RuntimeException("Error al guardar conductor: " + msg, e);
        }
    }

    /**
     * Obtiene el próximo identificador disponible para conductores.
     *
     * @return siguiente id sugerido o 1 como valor de respaldo
     */
    public int obtenerProximoIdDisponible() {
        String sql = "SELECT IFNULL(MAX(id), 0) + 1 AS proximoId FROM conductores";
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
     * Lista todos los conductores registrados.
     *
     * @return lista completa de conductores
     */
    public ArrayList<Conductor> listarConductores() {
        ArrayList<Conductor> lista = new ArrayList<>();
        String sql = "SELECT id, rut, nombre, apellido, licencia FROM conductores";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Conductor c = new Conductor();
                c.setId(rs.getInt("id"));
                c.setRut(rs.getString("rut"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setLicencia(rs.getString("licencia"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar conductores: " + e.getMessage());
            throw new RuntimeException("No se pudo listar conductores: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un conductor por su rut normalizado.
     *
     * @param rut rut del conductor a buscar
     * @return conductor encontrado o null si no existe coincidencia
     */
    public Conductor buscarConductorPorRut(String rut) {
        if (rut == null || rut.isBlank()) {
            return null;
        }

        String sql = "SELECT id, rut, nombre, apellido, licencia FROM conductores "
                + "WHERE UPPER(REPLACE(REPLACE(REPLACE(rut, '.', ''), '-', ''), ' ', '')) = ?";

        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarRut(rut));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Conductor conductor = new Conductor();
                    conductor.setId(rs.getInt("id"));
                    conductor.setRut(rs.getString("rut"));
                    conductor.setNombre(rs.getString("nombre"));
                    conductor.setApellido(rs.getString("apellido"));
                    conductor.setLicencia(rs.getString("licencia"));
                    return conductor;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener el conductor por RUT: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Normaliza un rut eliminando puntos, guiones y espacios.
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

    /**
     * Actualiza los datos de un conductor existente.
     *
     * @param c conductor con id y datos a persistir
     * @return true si la actualización afectó al menos una fila
     */
    public boolean editarConductor(Conductor c) {
        String sql = "UPDATE conductores SET rut=?, nombre=?, apellido=?, licencia=? WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getRut());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setString(4, c.getLicencia());
            ps.setInt(5, c.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("Duplicate entry")) {
                if (msg.contains("rut")) {
                    throw new RuntimeException("El RUT ya existe en otro conductor.", e);
                }
                throw new RuntimeException("Ya existe un dato único duplicado en conductores.", e);
            }
            throw new RuntimeException("Error al editar conductor: " + msg, e);
        }
    }

    /**
     * Elimina un conductor por su identificador.
     *
     * @param id id del conductor a eliminar
     * @return true si se eliminó al menos una fila
     */
    public boolean eliminarConductor(int id) {
        String sql = "DELETE FROM conductores WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("foreign key constraint fails")) {
                throw new RuntimeException("No se puede eliminar el conductor porque está asignado a un camión.", e);
            }
            throw new RuntimeException("Error al eliminar conductor: " + msg, e);
        }
    }

    /**
     * Cuenta cuántos camiones están asignados a un conductor.
     *
     * @param idConductor id del conductor
     * @return cantidad de camiones asociados
     */
    public int contarCamionesAsignados(int idConductor) {
        String sql = "SELECT COUNT(*) AS total FROM camiones WHERE id_conductor=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConductor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo verificar camiones asignados: " + e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Elimina un conductor y, opcionalmente, desasigna sus camiones en una sola
     * transacción.
     *
     * @param idConductor id del conductor a eliminar
     * @param confirmarDesasignacion true si se autoriza desasignar camiones
     * @return true si el conductor fue eliminado correctamente
     */
    public boolean eliminarConductor(int idConductor, boolean confirmarDesasignacion) {
        String countSql = "SELECT COUNT(*) AS total FROM camiones WHERE id_conductor=?";
        String desasignarSql = "UPDATE camiones SET id_conductor=NULL WHERE id_conductor=?";
        String eliminarSql = "DELETE FROM conductores WHERE id=?";

        try (Connection conn = conexion.get()) {
            conn.setAutoCommit(false);
            try {
                int ligados = 0;
                try (PreparedStatement psCount = conn.prepareStatement(countSql)) {
                    psCount.setInt(1, idConductor);
                    try (ResultSet rs = psCount.executeQuery()) {
                        if (rs.next()) {
                            ligados = rs.getInt("total");
                        }
                    }
                }

                if (ligados > 0 && !confirmarDesasignacion) {
                    conn.rollback();
                    throw new RuntimeException("Este conductor está ligado a " + ligados
                            + " camión(es). Debe confirmar la desasignación para eliminarlo.");
                }

                if (ligados > 0) {
                    try (PreparedStatement psDesasignar = conn.prepareStatement(desasignarSql)) {
                        psDesasignar.setInt(1, idConductor);
                        psDesasignar.executeUpdate();
                    }
                }

                int filas;
                try (PreparedStatement psDelete = conn.prepareStatement(eliminarSql)) {
                    psDelete.setInt(1, idConductor);
                    filas = psDelete.executeUpdate();
                }

                conn.commit();
                return filas > 0;
            } catch (Exception ex) {
                conn.rollback();
                if (ex instanceof RuntimeException runtimeEx) {
                    throw runtimeEx;
                }
                throw new RuntimeException("Error al eliminar conductor: " + ex.getMessage(), ex);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error de base de datos al eliminar conductor: " + e.getMessage(), e);
        }
    }
}
