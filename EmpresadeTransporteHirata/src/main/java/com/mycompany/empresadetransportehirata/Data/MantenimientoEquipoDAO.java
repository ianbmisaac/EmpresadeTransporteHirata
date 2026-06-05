/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Data;

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
 *
 * <p>Se encarga de recuperar el historial de mantenimientos de los equipos de oficina.</p>
 */
public class MantenimientoEquipoDAO {

    /**
     * Lista todo el historial de mantenimientos ordenado por fecha descendente.
     *
     * @return lista completa de mantenimientos
     */
    public List<MantenimientoEquipo> listarHistorial() {
        List<MantenimientoEquipo> lista = new ArrayList<>();
        String sql = "SELECT id, equipo, tipo_mantenimiento, fecha, tecnico, descripcion, estado " +
                     "FROM mantenimiento_equipos ORDER BY fecha DESC";
        
        try (Connection conn = conexion.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                MantenimientoEquipo m = new MantenimientoEquipo();
                m.setId(rs.getInt("id"));
                m.setEquipo(rs.getString("equipo"));
                m.setTipoMantenimiento(rs.getString("tipo_mantenimiento"));
                m.setFecha(rs.getDate("fecha"));
                m.setTecnico(rs.getString("tecnico"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setEstado(rs.getString("estado"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar historial de mantenimiento: " + e.getMessage());
            throw new RuntimeException("Error al cargar la base de datos.", e);
        }
        return lista;
    }
}