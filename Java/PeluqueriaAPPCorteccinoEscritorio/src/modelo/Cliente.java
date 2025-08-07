/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que representa a un Cliente
 *
 * @author Mario
 */
public class Cliente extends Usuario {

    private String descripcion;
    private Connection conexion;

    public Cliente() {
    }

    public Cliente(int id, Connection conexion) {
        super.setId(id);
        this.conexion = conexion;
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
        return super.toString() + ", descripcion: " + descripcion;
    }

    private boolean inicializarDesdeBD() {
        boolean devo = false;
        super.inicializarDesdeBD(getId(), conexion);
        try {
            String sql = "SELECT * FROM cliente WHERE ID = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setDescripcion(rs.getString("descripcion"));
            }
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    public static ArrayList<Cliente> obtenerTodos(Connection conexionBD) {

        ArrayList<Cliente> devo = null;
        try {
            devo = obtenerClienteFiltrando(false, 0, false, null, conexionBD);
        } catch (Exception e) {
            // Manejo de excepciones
            devo = null;
        }
        return devo;
    }

    public static ArrayList<Cliente> obtenerClienteFiltrando(
            boolean confirmarId,
            int id,
            boolean confirmarDescripcion,
            String descripcion,
            Connection conexionBD) {
        ArrayList<Cliente> cliente = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM cliente WHERE ID IS NOT NULL");
            if (confirmarId) {
                query.append(" AND ID = ?");
            }
            if (confirmarDescripcion) {
                query.append(" AND descripcion = ?");
            }
            stmt = conexionBD.prepareStatement(query.toString());
            int paramIndex = 1;
            if (confirmarId) {
                stmt.setInt(paramIndex++, id);
            }
            if (confirmarDescripcion) {
                stmt.setString(paramIndex++, descripcion);

            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente newCliente = new Cliente();
                newCliente.setId(rs.getInt("ID"));
                newCliente.setDescripcion(rs.getString("descripcion"));
                newCliente.inicializarDesdeBD();
            }
        } catch (Exception e) {
            cliente = null;
            System.err.println("Error al obtener el cliente: " + e.getMessage());
        }
        return cliente;
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
        try {
            cliente = new Cliente(idCliente, conexionBD);
            cliente.inicializarDesdeBD();
            // Si necesitas inicializar más campos heredados, hazlo aquí
        } catch (Exception e) {
            cliente = null;
            System.err.println("Error al obtener el cliente: " + e.getMessage());
        }
        return cliente;
    }

}
