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
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.mycompany.empresadetransportehirata.Logica.PiezaInventario;

/**
 * Acceso a datos para el inventario de piezas.
 */
public class InventarioPiezasDAO {

    /**
     * Obtiene el próximo identificador disponible para inventario.
     *
     * @return siguiente id sugerido o 1 si ocurre un error
     */
    public int obtenerProximoIdDisponible() {
        String sql = "SELECT IFNULL(MAX(id), 0) + 1 AS proximoId FROM inventario_piezas";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("proximoId");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener próximo ID de inventario: " + e.getMessage());
        }
        return 1;
    }

    /**
     * Guarda una pieza nueva en inventario.
     *
     * @param pieza entidad a guardar
     * @param idAsignado id a utilizar en la inserción
     * @return id asignado si el guardado fue exitoso, o -1 si falló
     */
    public int guardarPieza(PiezaInventario pieza, int idAsignado) {
        String sql = "INSERT INTO inventario_piezas (id, nombre, descripcion, cantidad, unidad, stock_minimo, ubicacion, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAsignado);
            ps.setString(2, pieza.getNombre());
            ps.setString(3, pieza.getDescripcion());
            ps.setInt(4, pieza.getCantidad());
            ps.setString(5, pieza.getUnidad());
            ps.setInt(6, pieza.getStockMinimo());
            ps.setString(7, pieza.getUbicacion());
            ps.setDate(8, pieza.getFechaRegistro());

            int filas = ps.executeUpdate();
            return filas > 0 ? idAsignado : -1;
        } catch (SQLException e) {
            throw construirExcepcionInventario("guardar", e);
        }
    }

    /**
     * Actualiza una pieza existente.
     *
     * @param pieza entidad con id y datos a persistir
     * @return true si la actualización afectó al menos una fila
     */
    public boolean actualizarPieza(PiezaInventario pieza) {
        String sql = "UPDATE inventario_piezas SET nombre=?, descripcion=?, cantidad=?, unidad=?, stock_minimo=?, ubicacion=?, fecha_registro=? WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pieza.getNombre());
            ps.setString(2, pieza.getDescripcion());
            ps.setInt(3, pieza.getCantidad());
            ps.setString(4, pieza.getUnidad());
            ps.setInt(5, pieza.getStockMinimo());
            ps.setString(6, pieza.getUbicacion());
            ps.setDate(7, pieza.getFechaRegistro());
            ps.setInt(8, pieza.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw construirExcepcionInventario("actualizar", e);
        }
    }

    /**
     * Elimina una pieza del inventario.
     *
     * @param id id de la pieza a eliminar
     * @return true si se eliminó un registro
     */
    public boolean eliminarPieza(int id) {
        String sql = "DELETE FROM inventario_piezas WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar pieza: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todas las piezas registradas.
     *
     * @return listado ordenado por nombre
     */
    public ArrayList<PiezaInventario> listarPiezas() {
        ArrayList<PiezaInventario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, cantidad, unidad, stock_minimo, ubicacion, fecha_registro, ultima_actualizacion FROM inventario_piezas ORDER BY nombre ASC";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearPieza(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar el inventario: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca una pieza por id.
     *
     * @param id identificador interno
     * @return pieza encontrada o null
     */
    public PiezaInventario buscarPorId(int id) {
        String sql = "SELECT id, nombre, descripcion, cantidad, unidad, stock_minimo, ubicacion, fecha_registro, ultima_actualizacion FROM inventario_piezas WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPieza(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la pieza: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Verifica si ya existe una pieza con el mismo nombre.
     *
     * @param nombre nombre a validar
     * @return true si ya existe una coincidencia
     */
    public boolean existePiezaPorNombre(String nombre) {
        String sql = "SELECT 1 FROM inventario_piezas WHERE UPPER(TRIM(nombre)) = UPPER(TRIM(?))";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo validar la pieza existente: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si ya existe una pieza con el mismo nombre excluyendo un id.
     *
     * @param nombre nombre a validar
     * @param id id a excluir de la comparación
     * @return true si existe otra pieza con el mismo nombre
     */
    public boolean existePiezaPorNombreExcluyendoId(String nombre, int id) {
        String sql = "SELECT 1 FROM inventario_piezas WHERE UPPER(TRIM(nombre)) = UPPER(TRIM(?)) AND id <> ?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo validar la pieza existente: " + e.getMessage(), e);
        }
    }

    /**
     * Cuenta cuántas piezas distintas existen en el inventario.
     *
     * @return cantidad de registros
     */
    public int contarPiezasRegistradas() {
        String sql = "SELECT COUNT(*) AS total FROM inventario_piezas";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo contar el inventario: " + e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Cuenta cuántas piezas están con stock bajo o crítico.
     *
     * @return cantidad de registros bajo el mínimo
     */
    public int contarPiezasConStockBajo() {
        String sql = "SELECT COUNT(*) AS total FROM inventario_piezas WHERE cantidad <= stock_minimo";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo calcular el stock bajo: " + e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Obtiene la última fecha de modificación del inventario.
     *
     * @return fecha formateada o texto por defecto si no existen movimientos
     */
    public String obtenerUltimaActualizacionVisible() {
        String sql = "SELECT MAX(ultima_actualizacion) AS ultima FROM inventario_piezas";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                Timestamp ultima = rs.getTimestamp("ultima");
                if (ultima != null) {
                    return ultima.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo consultar la última actualización: " + e.getMessage(), e);
        }
        return "Sin movimientos";
    }

    private PiezaInventario mapearPieza(ResultSet rs) throws SQLException {
        PiezaInventario pieza = new PiezaInventario();
        pieza.setId(rs.getInt("id"));
        pieza.setNombre(rs.getString("nombre"));
        pieza.setDescripcion(rs.getString("descripcion"));
        pieza.setCantidad(rs.getInt("cantidad"));
        pieza.setUnidad(rs.getString("unidad"));
        pieza.setStockMinimo(rs.getInt("stock_minimo"));
        pieza.setUbicacion(rs.getString("ubicacion"));
        pieza.setFechaRegistro(rs.getDate("fecha_registro"));
        pieza.setUltimaActualizacion(rs.getTimestamp("ultima_actualizacion"));
        return pieza;
    }

    private RuntimeException construirExcepcionInventario(String accion, SQLException e) {
        String mensaje = e.getMessage();
        if (mensaje != null && mensaje.contains("Duplicate entry")) {
            return new RuntimeException("Ya existe una pieza registrada con ese nombre.", e);
        }
        return new RuntimeException("Error al " + accion + " la pieza: " + mensaje, e);
    }
}