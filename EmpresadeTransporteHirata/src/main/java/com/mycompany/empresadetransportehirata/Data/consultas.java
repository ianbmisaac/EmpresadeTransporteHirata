/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mycompany.empresadetransportehirata.Logica.ValidarRut;

/**
 * Consultas de autenticación y resolución de roles.
 */
public class consultas {

    /**
     * Valida acceso de usuario por rut, contraseña y rol.
     *
     * @param user rut del usuario
     * @param pass contraseña ingresada
     * @param rol rol esperado
     * @return true si las credenciales son correctas
     */
    public boolean accesosUsuarios(String user, String pass, String rol) {
        System.out.println("RUT: " + user + " | Contraseña: " + pass); 
        conexionpermisos db = new conexionpermisos();
        try (
            Connection cn = db.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "SELECT pass, nombre FROM users WHERE rut = ? AND rol = ?"
            )
        ) {
            pst.setString(1, user);
            pst.setString(2, rol);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String passCorrecta = rs.getString("pass");
                    String nombre = rs.getString("nombre");
                    if (pass.equals(passCorrecta)) {
                        JOptionPane.showMessageDialog(null, "Bienvenido " + nombre);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecto.");
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Rut o rol incorrecto");
                    return false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el rol del usuario autenticando por rut y contraseña.
     *
     * @param user rut del usuario
     * @param pass contraseña ingresada
     * @return rol del usuario o cadena vacía si falla la autenticación
     */
    public String obtenerRolUsuario(String user, String pass) {
        if (!ValidarRut.validar(user)) {
            JOptionPane.showMessageDialog(null, "RUT inválido. Use formato 12345678-9.");
            return "";
        }

        conexionpermisos db = new conexionpermisos();
        Connection cn = db.conectar();
        if (cn == null) {
            return "";
        }

        try (
            cn;
            PreparedStatement pst = cn.prepareStatement(
                "SELECT rol, nombre FROM users WHERE rut = ? AND pass = ?"
            )
        ) {
            pst.setString(1, user);
            pst.setString(2, pass);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String rol = rs.getString("rol");

                    if (rol == null || rol.isBlank()) {
                        JOptionPane.showMessageDialog(null, "El usuario no tiene un rol asignado.");
                        return "";
                    }

                    JOptionPane.showMessageDialog(null, "Bienvenido " + nombre);
                    return rol.trim().toLowerCase(java.util.Locale.ROOT);
                }

                JOptionPane.showMessageDialog(null, "RUT o contraseña incorrectos.");
                return "";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return "";
        }
    }

    /**
     * Crea el usuario de acceso para un conductor recién registrado.
     *
     * @param rut rut del conductor
     * @param nombre nombre a mostrar en la tabla de usuarios
     * @param passInicial contraseña inicial asignada
     * @return true si el usuario fue creado correctamente
     */
    public boolean crearUsuarioConductor(String rut, String nombre, String passInicial) {
    conexionpermisos db = new conexionpermisos();
    String sql = "INSERT INTO users (rut, nombre, pass, rol) VALUES (?, ?, ?, 'conductor')";
    try (Connection cn = db.conectar();
         PreparedStatement pst = cn.prepareStatement(sql)) {
        pst.setString(1, rut);
        pst.setString(2, nombre);
        pst.setString(3, passInicial);
        return pst.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al crear usuario conductor: " + e.getMessage());
        return false;
    }
}

    /**
     * Elimina el usuario de acceso asociado a un conductor.
     *
     * @param rut rut del conductor a eliminar en la tabla de usuarios
     * @return true si se eliminó al menos un registro
     */
    public boolean eliminarUsuario(String rut) {
    conexionpermisos db = new conexionpermisos();
    String sql = "DELETE FROM users WHERE rut = ? AND rol = 'conductor'";
    try (Connection cn = db.conectar();
         PreparedStatement pst = cn.prepareStatement(sql)) {
        pst.setString(1, rut);
        return pst.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al eliminar usuario: " + e.getMessage());
        return false;
    }
}
}
