/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


/**
 * Interfaz para la conexion con la base de datos
 * @author Mario
 */
public interface Configurable {
    String DB_NAME="bd_alcorteccino";
    String DB_USER="admin";
    String DB_PASSWORD="123pelu";
    String DB_FOLDER="src\\bd";
    String DB_SERVER="vps-1266bd01.vps.ovh.net";
    String SERVER_PORT="3306";
    String URL="jdbc:mysql://vps-1266bd01.vps.ovh.net:3306/" + DB_NAME;
    
    //LA URL DE LA BASE DE DATOS ESTA ENMASCARADA, PARA USAR LA BASE DE DATOS IMPORTADA EN TU SERVIDOR, POR FAVOR CAMBIE LA URL.
    String DB_DRIVER="com.mysql.cj.jdbc.Driver";
    public void cerrarConnection();
    
}
