package com.mycompany.empresadetransportehirata.Data;

import com.mycompany.empresadetransportehirata.Logica.EquipoOficina;
import com.mycompany.empresadetransportehirata.Logica.Software;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la entidad {@link EquipoOficina}.
 * Encapsula operaciones CRUD sobre la tabla equipos_oficina.
 */
public class EquipoOficinaDAO {

    /**
     * Lista todos los equipos de oficina registrados, incluyendo su software asociado.
     * @return lista completa de equipos; vacía si no hay registros o falla la consulta
     */
    public List<EquipoOficina> listar() {
        List<EquipoOficina> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.nombre, e.tipo, e.marca, e.modelo, e.serie, e.estado, "
                + "s.id AS idSoftware, s.nombre AS nombreSoftware "
                + "FROM equipos_oficina e "
                + "LEFT JOIN software s ON e.id_software = s.id";  // cambio aquí
        try (Connection conn = conexion.get(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Persiste un nuevo equipo de oficina.
     * @param e entidad {@link EquipoOficina} a guardar
     * @return {@code true} si se insertó correctamente
     */
    public boolean guardar(EquipoOficina e) {
        String sql = "INSERT INTO equipos_oficina (nombre, tipo, marca, modelo, serie, estado) VALUES (?,?,?,?,?,?)";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getTipo());
            ps.setString(3, e.getMarca());
            ps.setString(4, e.getModelo());
            ps.setString(5, e.getSerie());
            ps.setString(6, e.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al guardar equipo: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un equipo existente.
     * @param e entidad con id y datos actualizados
     * @return {@code true} si se actualizó al menos una fila
     */
    public boolean actualizar(EquipoOficina e) {
        String sql = "UPDATE equipos_oficina SET nombre=?, tipo=?, marca=?, modelo=?, serie=?, estado=? WHERE id=?";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getTipo());
            ps.setString(3, e.getMarca());
            ps.setString(4, e.getModelo());
            ps.setString(5, e.getSerie());
            ps.setString(6, e.getEstado());
            ps.setInt(7, e.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar equipo: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Elimina un equipo por su identificador.
     * @param id identificador del equipo a eliminar
     * @return {@code true} si se eliminó al menos una fila
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM equipos_oficina WHERE id=?";
        try (Connection conn = conexion.get(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una fila del ResultSet en una entidad {@link EquipoOficina}.
     * @param rs fila actual del resultado SQL
     * @return entidad mapeada
     * @throws SQLException si falla la lectura de alguna columna
     */
    private EquipoOficina mapear(ResultSet rs) throws SQLException {
        Software software = null;
        int idSoftware = rs.getInt("idSoftware");
        if (idSoftware > 0) {
            software = new Software(idSoftware, rs.getString("nombreSoftware"));
        }

        return new EquipoOficina(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("tipo"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("serie"),
                rs.getString("estado"),
                software
        );
    }
}
