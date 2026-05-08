package com.mycompany.empresadetransportehirata.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mycompany.empresadetransportehirata.Logica.Camion;
import com.mycompany.empresadetransportehirata.Logica.Conductor;
import com.mycompany.empresadetransportehirata.Logica.regKmCamiones;

/**
 * Acceso a datos para el historial de kilometraje de camiones.
 */
public class RegKmCamionesDAO {

    /**
     * Registra un nuevo kilometraje y actualiza el valor actual del camión en
     * una misma transacción.
     *
     * @param camion camión cuyo kilometraje se actualiza
     * @param conductor conductor que realiza el registro
     * @param kilometraje nuevo kilometraje informado
     * @param observacion observación opcional del recorrido
     * @return true si la actualización y la inserción se completaron
     */
    public boolean registrarKilometraje(Camion camion, Conductor conductor, int kilometraje, String observacion) {
        if (camion == null || conductor == null) {
            throw new IllegalArgumentException("El camión y el conductor son obligatorios para registrar kilometraje.");
        }

        String actualizarCamionSql = "UPDATE camiones SET kmInicial=? WHERE id=?";
        String insertarRegistroSql = "INSERT INTO regKmCamiones (id_camion, id_conductor, kilometraje, observacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.get()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psActualizar = conn.prepareStatement(actualizarCamionSql);
                 PreparedStatement psInsertar = conn.prepareStatement(insertarRegistroSql)) {

                psActualizar.setInt(1, kilometraje);
                psActualizar.setInt(2, camion.getId());

                if (psActualizar.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                psInsertar.setInt(1, camion.getId());
                psInsertar.setInt(2, conductor.getId());
                psInsertar.setInt(3, kilometraje);

                if (observacion == null || observacion.isBlank()) {
                    psInsertar.setNull(4, Types.VARCHAR);
                } else {
                    psInsertar.setString(4, observacion.trim());
                }

                if (psInsertar.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("No se pudo registrar el kilometraje: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar kilometraje: " + e.getMessage(), e);
        }
    }

    /**
     * Lista el último registro de kilometraje disponible para cada camión de un
     * conductor.
     *
     * @param rutConductor rut del conductor autenticado
     * @return lista de registros más recientes por camión
     */
    public List<regKmCamiones> listarUltimosRegistrosPorRutConductor(String rutConductor) {
        List<regKmCamiones> lista = new ArrayList<>();
        if (rutConductor == null || rutConductor.isBlank()) {
            return lista;
        }

        String sql = "SELECT c.id AS idCamion, c.patente, c.marca, c.modelo, c.ano, c.kmInicial, "
                + "co.id AS idConductor, co.rut, co.nombre, co.apellido, co.licencia, "
                + "r.id AS idRegistro, r.kilometraje, r.observacion, r.fecha "
                + "FROM camiones c "
                + "INNER JOIN conductores co ON c.id_conductor = co.id "
                + "LEFT JOIN regKmCamiones r ON r.id = ("
                + "    SELECT r2.id FROM regKmCamiones r2 "
                + "    WHERE r2.id_camion = c.id "
                + "    ORDER BY r2.fecha DESC, r2.id DESC "
                + "    LIMIT 1"
                + ") "
                + "WHERE UPPER(REPLACE(REPLACE(REPLACE(co.rut, '.', ''), '-', ''), ' ', '')) = ? "
                + "ORDER BY c.patente";

        try (Connection conn = conexion.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarRut(rutConductor));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearRegistro(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los registros de kilometraje: " + e.getMessage(), e);
        }

        return lista;
    }

    /**
     * Mapea una fila del resultado SQL a un registro de kilometraje.
     *
     * @param rs fila actual del resultado
     * @return entidad de registro de kilometraje mapeada
     * @throws SQLException si falla la lectura del resultado
     */
    private regKmCamiones mapearRegistro(ResultSet rs) throws SQLException {
        Conductor conductor = new Conductor();
        conductor.setId(rs.getInt("idConductor"));
        conductor.setRut(rs.getString("rut"));
        conductor.setNombre(rs.getString("nombre"));
        conductor.setApellido(rs.getString("apellido"));
        conductor.setLicencia(rs.getString("licencia"));

        Camion camion = new Camion();
        camion.setId(rs.getInt("idCamion"));
        camion.setPatente(rs.getString("patente"));
        camion.setMarca(rs.getString("marca"));
        camion.setModelo(rs.getString("modelo"));
        camion.setAno(rs.getDate("ano"));
        camion.setKmInicial(rs.getInt("kmInicial"));
        camion.setConductor(conductor);

        regKmCamiones registro = new regKmCamiones();
        int idRegistro = rs.getInt("idRegistro");
        if (!rs.wasNull()) {
            registro.setId(idRegistro);
        }
        registro.setCamion(camion);
        registro.setCamionero(conductor);

        Object kilometraje = rs.getObject("kilometraje");
        registro.setKilometraje(kilometraje != null ? rs.getInt("kilometraje") : camion.getKmInicial());
        registro.setObservacion(rs.getString("observacion"));
        registro.setFecha(rs.getTimestamp("fecha"));
        return registro;
    }

    /**
     * Normaliza un rut para comparaciones en consultas SQL.
     *
     * @param rut rut original
     * @return rut sin separadores y en mayúsculas
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