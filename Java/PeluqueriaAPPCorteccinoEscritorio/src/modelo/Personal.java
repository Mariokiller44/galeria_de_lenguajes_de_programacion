/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que representa al Personal
 *
 * @author Mario
 * @version 1.0.2
 */
public class Personal extends Usuario {

    private double salario;
    private String tipo;
    private Connection conexion; // Conexion a la base de datos

    public Personal() {
        /**
         * Método para obtener un objeto Personal filtrando por los parámetros
         * dados. Este es un método de ejemplo, debes implementar la lógica real
         * según tu base de datos.
         */

    }

    public Personal(double salario, String tipo, int id, int telefono, String nombre, String apellidos, String email,
            String cuenta, String contrasenia, String tipo_de_usuario) {
        super(id, telefono, nombre, apellidos, email, cuenta, contrasenia, tipo_de_usuario);
        this.salario = salario;
        this.tipo = tipo;
    }

    public Personal(int id, Connection connect) {
        setId(id);
        this.conexion = connect;

    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /*
     * Método para obtener la conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }

    @Override
    protected boolean inicializarDesdeBD() {
        boolean devo = false;
        super.inicializarDesdeBD();
        try {
            String sql = "SELECT * FROM personal WHERE ID = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setTipo(rs.getString("tipo"));
                setSalario(rs.getDouble("salario"));
            }
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    public static Personal obtenerPersonalPorId(int idPersonal, Connection conexionBD) {
        Personal devo = null;
        try {
            devo = new Personal(idPersonal, conexionBD);
            devo.inicializarDesdeBD();
        } catch (Exception e) {
            // Manejo de excepciones
            devo = null;
        }
        return devo;
    }

    public static ArrayList<Personal> obtenerTodos(Connection conexionBD) {
        ArrayList<Personal> devo = null;
        try {
            devo = obtenerPersonalFiltrando(false, 0, false, 0, false, null, conexionBD);
        } catch (Exception e) {
            // Manejo de excepciones
            devo = null;
        }
        return devo;
    }

    public static ArrayList<Personal> obtenerPersonalFiltrando(
            boolean confirmarId,
            int id,
            boolean confirmarSalario,
            double salario,
            boolean confirmarTipo,
            String tipo,
            Connection conexionBD) {
        ArrayList<Personal> personal = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM personal WHERE ID IS NOT NULL");
            if (confirmarId) {
                query.append(" AND ID = ?");
            }
            if (confirmarSalario) {
                query.append(" AND SALARIO = ?");
            }
            if (confirmarTipo) {
                query.append(" AND TIPO = ?");
            }
            stmt = conexionBD.prepareStatement(query.toString());
            int paramIndex = 1;
            if (confirmarId) {
                stmt.setInt(paramIndex++, id);
            }
            if (confirmarSalario) {
                stmt.setDouble(paramIndex++, salario);
            }
            if (confirmarTipo) {
                stmt.setString(paramIndex++, tipo);
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                Personal newPersonal = new Personal();
                newPersonal.setId(rs.getInt("ID"));
                newPersonal.setTipo(rs.getString("TIPO"));
                newPersonal.setSalario(rs.getDouble("SALARIO"));
                newPersonal.inicializarDesdeBD();
            }
        } catch (Exception e) {
            personal = null;
            System.err.println("Error al obtener el personal: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
        }
        return personal;
    }

    @Override
    public String toString() {
        return super.toString() + ", tipo: " + tipo + ", salario: " + salario;
    }

}
