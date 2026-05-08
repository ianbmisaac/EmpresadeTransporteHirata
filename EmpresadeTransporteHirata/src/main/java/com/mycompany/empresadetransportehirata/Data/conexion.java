/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresadetransportehirata.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Provee conexiones JDBC a la base de datos principal de la aplicación.
 */
public class conexion {
    
     private static final String URL = "jdbc:mysql://localhost:3306/empresa_de_transportes";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "troler200";

    /**
     * Abre una conexión JDBC a la base de datos configurada.
     *
     * @return conexión activa a MySQL
     * @throws SQLException si ocurre un error al abrir la conexión
     */
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
    
     
}
