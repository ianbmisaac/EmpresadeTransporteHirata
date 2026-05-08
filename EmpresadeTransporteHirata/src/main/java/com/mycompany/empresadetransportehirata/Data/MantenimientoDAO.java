/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.empresadetransportehirata.Logica.Mantenimiento;
/**
 * Acceso a datos para la entidad de mantenimiento.
 *
 * Encapsula operaciones CRUD y consultas por camión.
 */
public class MantenimientoDAO {
    
    
    /**
     * Obtiene el primer ID disponible reutilizando huecos dejados por eliminaciones.
     *
     * @return el próximo ID utilizable o -1 si ocurre un error de base de datos
     */
    public int obtenerProximoIdDisponible() {
        String sql = "SELECT id FROM mantenimientos ORDER BY id ASC";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int esperado = 1;
            while (rs.next()) {
                int actual = rs.getInt("id");
                if (actual > esperado) {
                    return esperado;
                }
                if (actual == esperado) {
                    esperado++;
                }
            }
            return esperado;
        } catch (SQLException e) {
            System.out.println("Error al obtener próximo ID de mantenimiento: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Inserta un mantenimiento nuevo en base de datos.
     *
     * @param m entidad con datos de mantenimiento
     * @return true si la inserción fue exitosa
     */
    public boolean agregar(Mantenimiento m) {
        int nuevoId = obtenerProximoIdDisponible();
        if (nuevoId == -1) {
            return false;
        }

        String sql = "INSERT INTO mantenimientos (id, camion, tipoMantenimiento, fecha, kmActual, costo, estado, observacion, taller) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoId);
            ps.setString(2, m.getCamion());
            ps.setString(3, m.getTipoMantenimiento());
            ps.setDate(4, new java.sql.Date(m.getFecha().getTime()));
            ps.setInt(5, m.getKmActual());
            ps.setInt(6, m.getCosto());
            ps.setInt(7, m.getEstado());
            ps.setString(8, m.getObservacion());
            ps.setString(9, m.getTaller());

            m.setId(nuevoId);
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al agregar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un mantenimiento existente por ID.
     *
     * @param m entidad con datos y ID existente
     * @return true si se actualizó al menos un registro
     */
    public boolean actualizar(Mantenimiento m) {
        String sql = "UPDATE mantenimientos SET camion=?, tipoMantenimiento=?, fecha=?, kmActual=?, "
                   + "costo=?, estado=?, observacion=?, taller=? WHERE id=?";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, m.getCamion());
            ps.setString(2, m.getTipoMantenimiento());
            ps.setDate(3, new java.sql.Date(m.getFecha().getTime()));
            ps.setInt(4, m.getKmActual());
            ps.setInt(5, m.getCosto());
            ps.setInt(6, m.getEstado());
            ps.setString(7, m.getObservacion());
            ps.setString(8, m.getTaller());
            ps.setInt(9, m.getId());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al actualizar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un mantenimiento por ID.
     *
     * @param id identificador de mantenimiento
     * @return true si se eliminó al menos un registro
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mantenimientos WHERE id=?";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al eliminar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los mantenimientos registrados.
     *
     * @return lista completa de mantenimientos
     */
    public List<Mantenimiento> listar() {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                lista.add(mapearMantenimiento(rs));
            }
 
        } catch (SQLException e) {
            System.out.println("Error al listar mantenimientos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca un mantenimiento por su ID.
     *
     * @param id identificador del mantenimiento
     * @return mantenimiento encontrado o null
     */
    public Mantenimiento buscarPorId(int id) {
        String sql = "SELECT * FROM mantenimientos WHERE id=?";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMantenimiento(rs);
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al buscar mantenimiento: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista mantenimientos asociados a una patente de camión.
     *
     * @param patente patente del camión
     * @return lista de mantenimientos del camión
     */
    public List<Mantenimiento> listarPorCamion(String patente) {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos WHERE camion = ?";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMantenimiento(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar por camion: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Verifica si existe un mantenimiento del mismo tipo para una patente.
     *
     * @param patente patente del camión
     * @param tipoMantenimiento tipo de mantenimiento
     * @return true si existe al menos un registro
     */
    public boolean existeMantenimiento(String patente, String tipoMantenimiento) {
        String sql = "SELECT * FROM mantenimientos WHERE camion = ? AND tipoMantenimiento = ?";
        try (Connection con = conexion.get();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patente);
            ps.setString(2, tipoMantenimiento);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea una fila de ResultSet a un objeto Mantenimiento.
     *
     * @param rs fila actual del result set
     * @return entidad Mantenimiento mapeada
     * @throws SQLException si falla la lectura de columnas
     */
    private Mantenimiento mapearMantenimiento(ResultSet rs) throws SQLException {
        Mantenimiento m = new Mantenimiento();
        m.setId(rs.getInt("id"));
        m.setCamion(rs.getString("camion"));
        m.setTipoMantenimiento(rs.getString("tipoMantenimiento"));
        m.setFecha(rs.getDate("fecha"));
        m.setKmActual(rs.getInt("kmActual"));
        m.setCosto(rs.getInt("costo"));
        m.setEstado(rs.getInt("estado"));
        m.setObservacion(rs.getString("observacion"));
        m.setTaller(rs.getString("taller"));
        return m;
    }

    /**
     * Verifica si un camión ya tiene una cita pendiente o en proceso.
     *
     * @param patente patente del camión a consultar
     * @return true si existe un mantenimiento con estado activo
     */
    public boolean tieneCitaActiva(String patente) {
    String sql = "SELECT id FROM mantenimientos WHERE camion = ? AND estado IN (0, 1)";
    try (Connection con = conexion.get();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, patente);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar cita activa: " + e.getMessage());
        return false;
    }
}
}
