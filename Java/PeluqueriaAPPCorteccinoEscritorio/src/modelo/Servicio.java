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
 *
 * Clase Servicio: representa un servicio con sus atributos.
 *
 * @author Mario
 */
public class Servicio {

    // Atributos
    private int id; // ID del servicio
    private String descripcion; // Descripción del servicio
    private double precio; // Precio del servicio
    private int idProducto; // ID del producto asociado al servicio
    private Connection conexionBD; // Conexión a la base de datos
    private Producto producto; // Producto asociado al servicio

    // Constructor
    public Servicio(int id, Connection conexion) {
        setId(id);
        conexionBD = conexion;
    }

    // Métodos getter y setter
    public int getId() {
        return id;
    }

    public boolean setId(int id) {
        boolean exito = false;
        try {
            this.id = id;
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean setDescripcion(String descripcion) {
        boolean exito = false;
        try {
            this.descripcion = descripcion;
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public double getPrecio() {
        double valor = 0.0;
        try {
            valor = this.precio;
        } catch (Exception e) {
            valor = 0.0;
        }
        return valor;
    }

    public boolean setPrecio(double precio) {
        boolean exito = false;
        try {
            this.precio = precio;
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public int getIdProducto() {
        int valor = 0;
        try {
            valor = this.idProducto;
        } catch (Exception e) {
            valor = 0;
        }
        return valor;
    }

    public boolean setIdProducto(int idProducto) {
        boolean exito = false;
        try {
            this.idProducto = idProducto;
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public Connection getConexionBD() {
        Connection conn = null;
        try {
            conn = this.conexionBD;
        } catch (Exception e) {
            conn = null;
        }
        return conn;
    }

    public boolean setConexionBD(Connection conexionBD) {
        boolean exito = false;
        try {
            this.conexionBD = conexionBD;
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public Producto getProducto() {
        Producto prod = null;
        try {
            if (this.idProducto != 0) {
                prod = Producto.buscarPorId(this.idProducto, this.conexionBD);
            } else {
                prod = this.producto;
            }
        } catch (Exception e) {
            prod = null;
        }
        return prod;
    }

    public boolean setProducto(Producto producto) {
        boolean exito = false;
        try {
            this.producto = producto;
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    // Inicializa los datos del servicio desde la base de datos usando el id
    public boolean inicializarDesdeBD() {
        boolean exito = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM servicios WHERE ID = ?";
            stmt = conexionBD.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            // Si se encuentra el servicio, inicializa los atributos
            if (rs.next()) {
                setId(rs.getInt("ID"));
                setDescripcion(rs.getString("DESCRIPCION"));
                setPrecio(rs.getDouble("PRECIO"));
                setIdProducto(rs.getInt("PRODUCTO_ID"));
                getProducto();
            }
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        // Retorna true si se inicializó correctamente, false en caso contrario
        return exito;
    }

    public boolean crearServicio() {
        boolean exito = false;
        try {
            String sql = "INSERT INTO servicios (descripcion, precio, producto_id) VALUES (?, ?, ?)";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, descripcion);
            ps.setDouble(2, precio);
            ps.setInt(3, idProducto);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean actualizarServicio() {
        boolean exito = false;
        try {
            StringBuilder sql = new StringBuilder("UPDATE servicios SET ");
            if (descripcion != null) {
                sql.append(" descripcion= ?, ");
            }
            if (precio != 0) {
                sql.append(" precio = ?, ");
            }
            if (idProducto != 0) {
                sql.append(" producto_id = ? ");
            }
            sql.append(" WHERE id=? ");
            PreparedStatement ps = conexionBD.prepareStatement(sql.toString());
            int idx = 1;
            if (descripcion != null) {
                ps.setString(idx++, descripcion);
            }
            if (precio != 0) {
                ps.setDouble(idx++, precio);
            }
            if (idProducto != 0) {
                ps.setInt(idx++, idProducto);
            }
            ps.setInt(idx + 1, id);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean eliminarServicio() {
        boolean exito = false;
        try {
            String sql = "DELETE FROM servicios WHERE id = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    // Obtener un servicio por ID
    public static Servicio obtenerServicioPorId(int id, Connection conexionBD) {
        Servicio servicio = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            servicio = new Servicio(id, conexionBD);
            if (servicio.inicializarDesdeBD()) {
                return servicio;
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el servicio: " + e.getMessage());
        }
        return servicio;
    }

    // Obtener todos los servicios
    public static ArrayList<Servicio> obtenerTodos(Connection conexionBD) {
        ArrayList<Servicio> servicio = new ArrayList<>();
        try {
            servicio = Servicio.buscarServiciosFiltrado(false, null, false, 0, false, 0, conexionBD);
        } catch (Exception e) {
            System.err.println("Error al obtener el servicio: " + e.getMessage());
            servicio = null;
        }
        return servicio;
    }

    public static ArrayList<Servicio> buscarServiciosFiltrado(
            boolean usarDescripcion, String filtroDescripcion,
            boolean usarPrecio, double precio,
            boolean usarIdProducto, int idProducto,
            Connection conexion) {
        ArrayList<Servicio> servicios = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT ID, DESCRIPCION, PRECIO, producto_id FROM servicios WHERE 1=1");

            if (usarDescripcion && filtroDescripcion != null && !filtroDescripcion.isEmpty()) {
                sql.append(" AND DESCRIPCION LIKE ?");
            }
            if (usarPrecio) {
                sql.append(" AND PRECIO = ?");
            }
            if (usarIdProducto) {
                sql.append(" AND producto_id = ?");
            }

            stmt = conexion.prepareStatement(sql.toString());
            int paramIndex = 1;
            if (usarDescripcion && filtroDescripcion != null && !filtroDescripcion.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtroDescripcion + "%");
            }
            if (usarPrecio) {
                stmt.setDouble(paramIndex++, precio);
            }
            if (usarIdProducto) {
                stmt.setInt(paramIndex++, idProducto);
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                Servicio s = new Servicio(rs.getInt("ID"), conexion);
                s.setDescripcion(rs.getString("DESCRIPCION"));
                s.setPrecio(rs.getDouble("PRECIO"));
                s.setIdProducto(rs.getInt("producto_id"));
                servicios.add(s);
            }
        } catch (Exception e) {
            servicios = null;
        }
        return servicios;
    }

    // toString
    @Override
    public String toString() {
        return "Servicio:" + descripcion;
    }

}
