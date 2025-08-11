package modelo;

import java.sql.*;
import java.util.*;

/**
 * Clase Producto : representa un producto con sus atributos y metodos de la
 * clase.
 *
 * @author Mario
 * @version 1.0.2
 * @since 19-7-2025
 */
public class Producto {

    // Atributos
    private int id;
    private String nombre;
    private int stock;
    private Connection conexion;

    // Constructores
    public Producto() {
    }

    public Producto(int id, Connection conexion) {
        this.id = id;
        this.conexion = conexion;
    }
    
    public Producto(Connection conexion) {
        this.conexion = conexion;
    }

    public Producto(int id, String nombre, int stock, Connection conexion) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.conexion = conexion;
    }

    // Métodos de acceso (Getters)
    public int getId() {
        return id;
    }

    public Connection getConexion() {
        return conexion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    // Métodos de modificación (Setters booleanos)
    public boolean setId(int id) {
        boolean exito = false;
        if (id > 0) {
            this.id = id;
            exito = true;
        }
        return exito;
    }

    public boolean setNombre(String nombre) {
        boolean exito = false;
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
            exito = true;
        }
        return exito;
    }

    public boolean setStock(int stock) {
        boolean exito = false;
        if (stock >= 0) {
            this.stock = stock;
            exito = true;
        }
        return exito;
    }

    public boolean setConexion(Connection conexion) {
        boolean exito = false;
        if (conexion != null) {
            this.conexion = conexion;
            exito = true;
        }
        return exito;
    }

    // Métodos de negocio
    public boolean inicializarDesdeBD() {
        boolean exito = false;
        try {
            String sql = "SELECT * FROM productos WHERE ID = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                setNombre(rs.getString("NOMBRE"));
                setStock(rs.getInt("STOCK"));
            }
            exito = true;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean insertar() {
        boolean exito = false;
        try {
            String sql = "INSERT INTO productos (NOMBRE, STOCK) VALUES (?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, getNombre());
            stmt.setInt(2, getStock());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (java.sql.ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
                exito = true;
            }
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean actualizar() {
        boolean exito = false;
        try {
            String sql = "UPDATE productos SET NOMBRE = ?, STOCK = ? WHERE ID = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, getNombre());
            stmt.setInt(2, getStock());
            stmt.setInt(3, getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                exito = true;
            }
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean eliminar() {
        boolean exito = false;
        try {
            String sql = "DELETE FROM productos WHERE ID = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                exito = true;
            }
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    // Métodos estáticos de búsqueda
    public static Producto buscarPorId(int id, Connection conexion) {
        Producto producto = null;
        try {
            producto = new Producto(id, conexion);
            producto.inicializarDesdeBD();
        } catch (Exception e) {
            producto = null;
        }
        return producto;
    }

    public static ArrayList<Producto> buscarTodos(Connection conexion) {
        ArrayList<Producto> producto = new ArrayList<>();
        try {
            producto = buscarPorNombreYStock(false, null, false, 0, conexion);
        } catch (Exception e) {
            producto = null;
        }
        return producto;
    }

    public static ArrayList<Producto> buscarPorNombreYStock(boolean usarNombre, String filtroNombre, boolean usarStock,
            int stock, Connection conexion) {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT ID FROM productos WHERE ID IS NOT NULL");
            if (usarNombre) {
                sql.append(" AND NOMBRE = ?");
            }
            if (usarStock) {
                sql.append(" AND STOCK = ?");
            }
            PreparedStatement ps = conexion.prepareStatement(sql.toString());
            int idx = 1;
            if (usarNombre) {
                ps.setString(idx++, filtroNombre);
            }
            if (usarStock) {
                ps.setInt(idx++, stock);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(rs.getInt("ID"), conexion);
                p.inicializarDesdeBD();
                productos.add(p);
            }
        } catch (Exception e) {
            productos = null;
            System.out.println("Error al buscar horarios detallados: " + e.getMessage());
        }

        return productos;
    }
}
