package com.mycompany.empresadetransportehirata.Data;

import com.mycompany.empresadetransportehirata.Logica.EquipoOficina;
import com.mycompany.empresadetransportehirata.Logica.MantenimientoEquipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la entidad {@link MantenimientoEquipo}.
 * Encapsula operaciones CRUD sobre la tabla historial_mantenimiento_equipos.
 */
public class MantenimientoEquipoDAO {

    /**
     * Lista todos los mantenimientos registrados, ordenados por fecha descendente.
     * @return lista completa de mantenimientos; vacía si no hay registros
     */
    public List<MantenimientoEquipo> listarHistorial() {
        List<MantenimientoEquipo> lista = new ArrayList<>();
    
    String sql = "SELECT h.id, h.id_equipo, e.nombre AS nombre_equipo, h.tipo_mantenimiento, "
               + "h.fecha, h.tecnico, h.descripcion, h.estado, mp.id_pieza, mp.cantidad_usada "
               + "FROM historial_mantenimiento_equipos h "
               + "LEFT JOIN equipos_oficina e ON h.id_equipo = e.id "
               + "LEFT JOIN mantenimiento_piezas mp ON h.id = mp.id_mantenimiento "
               + "ORDER BY h.fecha DESC";

    try (Connection conn = conexion.get();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            lista.add(mapear(rs));
        }
    } catch (SQLException e) {
        System.err.println("Error al listar historial de mantenimiento: " + e.getMessage());
        throw new RuntimeException("Error al cargar la base de datos.", e);
    }
    return lista;
    }

    /**
     * Persiste un nuevo mantenimiento en la base de datos.
     * @param m entidad {@link MantenimientoEquipo} a guardar
     * @return {@code true} si se insertó correctamente
     */
    public boolean guardar(MantenimientoEquipo m) {
        String sql = "INSERT INTO historial_mantenimiento_equipos (id_equipo, tipo_mantenimiento, fecha, tecnico, descripcion, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getIdEquipo());
            ps.setString(2, m.getTipoMantenimiento());
            ps.setDate(3, m.getFecha());
            ps.setString(4, m.getTecnico());
            ps.setString(5, m.getDescripcion());
            ps.setString(6, m.getEstado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar mantenimiento de equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un mantenimiento existente.
     * @param m entidad con id y datos actualizados
     * @return {@code true} si se actualizó al menos una fila
     */
    public boolean actualizar(MantenimientoEquipo m) {
        String sql = "UPDATE historial_mantenimiento_equipos SET id_equipo=?, tipo_mantenimiento=?, fecha=?, "
                   + "tecnico=?, descripcion=?, estado=? WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getIdEquipo());
            ps.setString(2, m.getTipoMantenimiento());
            ps.setDate(3, m.getFecha());
            ps.setString(4, m.getTecnico());
            ps.setString(5, m.getDescripcion());
            ps.setString(6, m.getEstado());
            ps.setInt(7, m.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar mantenimiento de equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un mantenimiento por su identificador.
     * @param id identificador del mantenimiento a eliminar
     * @return {@code true} si se eliminó al menos una fila
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM historial_mantenimiento_equipos WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar mantenimiento de equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un mantenimiento por su identificador.
     * @param id identificador del mantenimiento
     * @return entidad encontrada o {@code null} si no existe
     */
    public MantenimientoEquipo buscarPorId(int id) {
        String sql = "SELECT h.id, h.id_equipo, e.nombre AS nombre_equipo, h.tipo_mantenimiento, "
               + "h.fecha, h.tecnico, h.descripcion, h.estado, mp.id_pieza, mp.cantidad_usada "
               + "FROM historial_mantenimiento_equipos h "
               + "LEFT JOIN equipos_oficina e ON h.id_equipo = e.id "
               + "LEFT JOIN mantenimiento_piezas mp ON h.id = mp.id_mantenimiento "
               + "WHERE h.id=?";
    try (Connection conn = conexion.get();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapear(rs);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar mantenimiento por ID: " + e.getMessage());
    }
    return null;
    }

    /**
     * Verifica si un equipo existe en la base de datos.
     * @param idEquipo identificador del equipo
     * @return {@code true} si el equipo está registrado
     */
    public boolean existeEquipo(int idEquipo) {
        String sql = "SELECT id FROM equipos_oficina WHERE id=?";
        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los equipos de oficina ordenados por nombre.
     * @return lista de equipos disponibles
     */
    public List<EquipoOficina> listarEquipos() {
        List<EquipoOficina> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, tipo, marca, modelo, serie, estado FROM equipos_oficina ORDER BY nombre";
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                EquipoOficina e = new EquipoOficina();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setTipo(rs.getString("tipo"));
                e.setMarca(rs.getString("marca"));
                e.setModelo(rs.getString("modelo"));
                e.setSerie(rs.getString("serie"));
                e.setEstado(rs.getString("estado"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Convierte una fila del ResultSet en una entidad {@link MantenimientoEquipo}.
     * @param rs fila actual del resultado SQL
     * @return entidad mapeada
     * @throws SQLException si falla la lectura de alguna columna
     */
    private MantenimientoEquipo mapear(ResultSet rs) throws SQLException {
        MantenimientoEquipo m = new MantenimientoEquipo();
    m.setId(rs.getInt("id"));
    m.setIdEquipo(rs.getInt("id_equipo"));
    m.setNombreEquipo(rs.getString("nombre_equipo"));
    m.setTipoMantenimiento(rs.getString("tipo_mantenimiento"));
    m.setFecha(rs.getDate("fecha"));
    m.setTecnico(rs.getString("tecnico"));
    m.setDescripcion(rs.getString("descripcion"));
    m.setEstado(rs.getString("estado"));
    
    int idPieza = rs.getInt("id_pieza");
    if (rs.wasNull()) { 
        m.setIdPieza(0); // 0 significa "Sin pieza" o "Ninguno"
    } else {
        m.setIdPieza(idPieza);
    }
    
    int cantidad = rs.getInt("cantidad_usada");
    if (rs.wasNull()) {
        m.setCantidadPieza(0);
    } else {
        m.setCantidadPieza(cantidad);
    }
    
    return m;
    }
    /**
 * Guarda un mantenimiento en TI y retorna el ID generado por MySQL.
     * @param m
     * @return 
 */
public int guardarYObtenerId(com.mycompany.empresadetransportehirata.Logica.MantenimientoEquipo m) {
    String sql = "INSERT INTO historial_mantenimiento_equipos (id_equipo, tipo_mantenimiento, fecha, tecnico, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = conexion.get();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        ps.setInt(1, m.getIdEquipo());
        ps.setString(2, m.getTipoMantenimiento());
        ps.setDate(3, m.getFecha());
        ps.setString(4, m.getTecnico());
        ps.setString(5, m.getDescripcion());
        ps.setString(6, m.getEstado());
        
        int filas = ps.executeUpdate();
        if (filas > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al guardar mantenimiento y obtener ID: " + e.getMessage());
    }
    return -1;
}

/**
 * Registra el consumo del repuesto y descuenta de inventario_piezas.
     * @param idMantenimiento
     * @param idPieza
     * @param cantidadUsada
     * @return 
 */
public boolean registrarUsoPieza(int idMantenimiento, int idPieza, int cantidadUsada) {
    String sqlRelacion = "INSERT INTO mantenimiento_piezas (id_mantenimiento, id_pieza, cantidad_usada) VALUES (?, ?, ?)";
    String sqlStock = "UPDATE inventario_piezas SET cantidad = cantidad - ? WHERE id = ?";
    
    Connection conn = null;
    try {
        conn = conexion.get();
        conn.setAutoCommit(false); 

        try (PreparedStatement psRel = conn.prepareStatement(sqlRelacion)) {
            psRel.setInt(1, idMantenimiento);
            psRel.setInt(2, idPieza);
            psRel.setInt(3, cantidadUsada);
            psRel.executeUpdate();
        }

        try (PreparedStatement psStk = conn.prepareStatement(sqlStock)) {
            psStk.setInt(1, cantidadUsada);
            psStk.setInt(2, idPieza);
            psStk.executeUpdate();
        }

        conn.commit();
        return true;
    } catch (SQLException e) {
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        System.err.println("Error en la transacción de insumos: " + e.getMessage());
        return false;
    } finally {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
}
