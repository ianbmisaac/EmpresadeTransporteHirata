/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Data;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class conexionpermisos {

    /**
     * Crea una conexión a la base de datos usada durante la autenticación.
     *
     * @return conexión abierta o null si la conexión falla
     */
    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/empresa_de_transportes",
                "root",  
                "3143"       
            );
            return cn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error conexión: " + e.getMessage());
            return null;
        }
    }
}
