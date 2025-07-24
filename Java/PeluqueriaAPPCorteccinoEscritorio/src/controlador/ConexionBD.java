/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.*;
/**
 *  Clase para conectarse a la base de datos 
 * @author Mario
 * @version 1.0.1
 */
public class ConexionBD implements Configuracion{
    /*
     * Atributos de la clase ConexionBD:
     */
    private static String usuario;
    private static String password;
    private static Connection conexion;
    private static String URL;

    /*
     * Constructor parametrizado la clase ConexionBD:
     */
    public ConexionBD(String usuario, String password) {
        conectar(usuario, password);
    }

    /*
     * Método para conectar a la base de datos
     */
    public static boolean conectar(String usu, String pass) {
        usuario = usu;
        password = pass;
        URL=Configuracion.URL; // Establecemos la URL de la base de datos a la predeterminada en la interfaz Configuracion.
        try {
            conexion=getConnection(usuario, password);
            return true;
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return false;
        }
    }
    /*
     * Método para obtener la conexión a la base de datos
     */
    public static Connection getConnection(String usuario, String password) {
        try {
            conexion=DriverManager.getConnection(URL,usuario,password);
        } catch (Exception e) {
            // TODO: handle exception
            conexion= null;
        }
        return conexion;
    }
    /* 
     * Método para cerrar la conexión a la base de datos
     */
    @Override
    public void cerrarConnection() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }

    public static Connection conectarSinLogin() {
        // TODO Auto-generated method stub
        Connection conn = null;
        try {
            conectar("admin", "123pelu");
            conn=conexion;
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }
    
    
}
