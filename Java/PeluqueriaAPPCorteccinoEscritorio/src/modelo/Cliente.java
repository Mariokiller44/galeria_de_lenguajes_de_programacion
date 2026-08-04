/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Clase que representa a un Cliente
 * @author Mario
 */
public class Cliente extends Usuario{
    private String descripcion;

    public Cliente() {
    }

    public Cliente(String descripcion, int id, int telefono, String nombre, String apellidos, String email, String cuenta, String contrasenia, String tipo_de_usuario) {
        super(id, telefono, nombre, apellidos, email, cuenta, contrasenia, tipo_de_usuario);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return super.toString()+", descripcion: "+descripcion;
    }
    
    /*
     * Método para obtener un cliente por su ID.
     *
     * @param idCliente ID del cliente a buscar
     * @param conexionBD Conexión a la base de datos
     * @return Cliente encontrado o null si no se encuentra
     */
    public static Cliente obtenerClientePorId(int idCliente, Connection conexionBD) {
        Cliente cliente = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT ID, DESCRIPCION FROM cliente WHERE ID = ?";
            stmt = conexionBD.prepareStatement(query);
            stmt.setInt(1, idCliente);
            rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("ID"));
                cliente.setDescripcion(rs.getString("DESCRIPCION"));
                // Si necesitas inicializar más campos heredados, hazlo aquí
            }
        } catch (Exception e) {
            cliente = null;
            System.err.println("Error al obtener el cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
        }
        return cliente;
    }

    
    
    
}
