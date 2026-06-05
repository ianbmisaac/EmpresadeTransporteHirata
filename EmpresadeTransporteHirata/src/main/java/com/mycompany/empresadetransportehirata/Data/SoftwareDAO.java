package com.mycompany.empresadetransportehirata.Data;

import com.mycompany.empresadetransportehirata.Logica.Software;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la entidad {@link Software}.
 *
 * <p>
 * Encapsula operaciones de persistencia, consulta, asignación y eliminación de
 * registros de software en la base de datos.</p>
 */
public class SoftwareDAO {

    /**
     * Lista todos los registros de software.
     *
     * @return lista completa de software; vacía si no hay registros o falla la
     * consulta
     */
    public List<Software> listar() {
        List<Software> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM software";
        try (Connection conn = conexion.get(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar software: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Persiste un nuevo registro de software en la base de datos.
     *
     * @param s entidad {@link Software} a guardar
     * @return {@code true} si se insertó correctamente; {@code false} en caso
     * contrario
     */
    public boolean guardar(Software s) {
        String sql = "INSERT INTO software (nombre) VALUES (?)";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar software: " + e.getMessage());
            return false;
        }
    }

    /**
     * Asigna un software a un equipo actualizando {@code id_equipo} en la tabla
     * software.
     *
     * @param idSoftware id del software a asignar
     * @param idEquipo id del equipo destino
     * @return {@code true} si se actualizó correctamente; {@code false} en caso
     * contrario
     */
    public boolean asignarAEquipo(int idSoftware, int idEquipo) {
        String sql = "UPDATE equipos_oficina SET id_software=? WHERE id=?";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSoftware);
            ps.setInt(2, idEquipo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al asignar software: " + e.getMessage());
            return false;
        }
    }

    /**
     * Quita la asignación de equipo de un software dejando {@code id_equipo} en
     * null.
     *
     * @param idSoftware id del software a desasignar
     * @return {@code true} si se actualizó correctamente; {@code false} en caso
     * contrario
     */
    public boolean quitarAsignacion(int idEquipo) {
        String sql = "UPDATE equipos_oficina SET id_software=NULL WHERE id=?";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al quitar asignación: " + e.getMessage());
            return false;
        }
    }
    /**
     * Verifica si un software está asignado a algún equipo.
     * @param idSoftware identificador del software
     * @return {@code true} si al menos un equipo lo tiene asignado
     */
    public boolean estaAsignado(int idSoftware) {
    String sql = "SELECT COUNT(*) FROM equipos_oficina WHERE id_software = ?";
    try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idSoftware);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar asignación: " + e.getMessage());
    }
    return false;
}

    /**
     * Actualiza el nombre de un software existente.
     * @param s entidad con id y nombre actualizados
     * @return {@code true} si se actualizó al menos una fila
     */
    public boolean actualizar(Software s) {
        String sql = "UPDATE software SET nombre = ? WHERE id = ?";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setInt(2, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar software: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un registro de software por su identificador.
     *
     * @param id identificador del software a eliminar
     * @return {@code true} si se eliminó al menos una fila; {@code false} en
     * caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM software WHERE id=?";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar software: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una fila del resultado SQL en una entidad {@link Software}.
     *
     * @param rs fila actual del {@link ResultSet}
     * @return entidad {@link Software} mapeada
     * @throws SQLException si falla la lectura de alguna columna del resultado
     */
    private Software mapear(ResultSet rs) throws SQLException {
        return new Software(rs.getInt("id"), rs.getString("nombre"));
    }
}
