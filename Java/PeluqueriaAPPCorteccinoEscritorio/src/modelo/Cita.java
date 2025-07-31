
/**
 * Paquete modelo
 */
package modelo;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase Cita: representa una cita con sus atributos.
 * 
 * @author Mario
 */
public class Cita {
    // Atributos
    private int id, idCliente, idHorario; // Empleado asignado a la cita
    private Horario horario; // Horario de la cita
    private Cliente cliente; // Cliente de la cita
    private Connection conexion; // Conexión a la base de datos
    /*
     * Constructor de la clase Cita.
     * 
     * @param id ID de la cita.
     * 
     * @param conexionBD Conexión a la base de datos.
     */

    public Cita(int id, Connection conexionBD) {
        this.id = id;
        this.conexion = conexionBD;
    }

    /**
     * Constructor por defecto de la clase Cita.
     * Inicializa los atributos a sus valores por defecto.
     */
    public Cita() {
    }

    /*
     * Obtener el ID de la cita.
     *
     * @return El ID de la cita.
     */
    public int getId() {
        return id;
    }

    /*
     * Establecer el ID de la cita.
     *
     * @param id El ID de la cita.
     */
    public boolean setId(int id) {
        boolean devo = false;
        try {
            this.id = id;
            devo = true;
        } catch (Exception e) {
            devo = false;
        } // TODO: handle exception
        return devo;
    }

    public Horario getHorario() {
        if (idHorario > 0 && horario == null) {
            horario = Horario.buscarPorId(idHorario, conexion);
        }
        return horario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public boolean setIdCliente(int idCliente) {
        boolean devo = false;
        try {
            this.idCliente = idCliente;
            devo = true;
        } catch (Exception e) {
            // TODO: handle exception
            devo = false;
        }
        return devo;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public boolean setIdHorario(int idHorario) {
        boolean devo = false;
        try {
            this.idHorario = idHorario;
            devo = true;
        } catch (Exception e) {
            // TODO: handle exception
            devo = false;
        }
        return devo;
    }

    public boolean setHorario(Horario horario) {
        boolean devo = false;
        try {
            this.horario = horario;
            devo = true;
        } catch (Exception e) {
            // TODO: handle exception
            devo = false;
        }
        return devo;
    }

    public Cliente getCliente() {
        if (idCliente > 0 && cliente == null) {
            cliente = Cliente.obtenerClientePorId(idCliente, conexion);
        }
        return cliente;
    }

    public boolean setCliente(Cliente cliente) {
        boolean devo = false;
        try {
            this.cliente = cliente;
            devo = true;
        } catch (Exception e) {
            // TODO: handle exception
            devo = false;
        }
        return devo;
    }

    public Connection getConexion() {
        return conexion;
    }

    public boolean setConexion(Connection conexion) {
        boolean devo = false;
        try {
            this.conexion = conexion;
            devo = true;
        } catch (Exception e) {
            // TODO: handle exception
            devo = false;
        }
        return devo;
    }

    public boolean inicializarDesdeBD() {
        boolean devo = false;
        try {
            String sql = "SELECT * FROM cita WHERE id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt("id_cliente");
                idHorario = rs.getInt("id_horario");
                getHorario(); // Cargar el horario asociado
                getCliente(); // Cargar el cliente asociado
                devo = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inicializar. " + e.getMessage());
            devo = false;
        }
        return devo;
    }

    public boolean aniadirCita() {
        boolean devo = false;
        try {
            String sql = "INSERT INTO cita (id_cliente, id_horario) VALUES (?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idCliente);
            ps.setInt(2, idHorario);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1); // Obtener el ID generado
                    devo = true;
                }
                rs.close();
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al añadir Cita. " + e.getMessage());
            devo = false;
        }
        return devo;
    }

    public boolean actualizarCita() {
        boolean devo = false;
        try {
            String sql = "UPDATE cita SET id_cliente = ?, id_horario = ? WHERE id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.setInt(2, idHorario);
            ps.setInt(3, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                devo = true;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar Cita. " + e.getMessage());
            devo = false;
        }
        return devo;
    }

    public boolean eliminarCita() {
        boolean devo = false;
        try {
            String sql = "DELETE FROM cita WHERE id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                devo = true;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al elminar Cita. " + e.getMessage());
            devo = false;
        }
        return devo;
    }

    public static Cita buscarPorId(int id, Connection conexionBD) {
        Cita cita = new Cita(id, conexionBD);
        if (cita.inicializarDesdeBD()) {
            return cita;
        } else {
            return null; // No se encontró la cita
        }
    }

    public static ArrayList<Cita> buscarPorCliente(int idCliente, Connection conexionBD) {
        ArrayList<Cita> devo = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cita WHERE id_cliente = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cita cita = new Cita(rs.getInt("id"), conexionBD);
                cita.inicializarDesdeBD(); // Inicializar la cita
                
                // Añadir la cita a la lista
                devo.add(cita);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar Cita por cliente. " + e.getMessage());
            devo = null;
        }
        return devo;
    }
    
    public static ArrayList<Cita> buscarTodas(Connection conexionBD) {
        ArrayList<Cita> devo = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cita";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cita cita = new Cita(rs.getInt("id"), conexionBD);
                cita.inicializarDesdeBD(); // Inicializar la cita
                // Añadir la cita a la lista
                devo.add(cita);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar Cita por cliente. " + e.getMessage());
            devo = null;
        }
        return devo;
    }

    public static ArrayList<Cita> buscarPorHorario(int idHorario, Connection conexionBD) {
        ArrayList<Cita> devo = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cita WHERE id_horario = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, idHorario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cita cita = new Cita(rs.getInt("id"), conexionBD);
                cita.inicializarDesdeBD(); // Inicializar la cita
                devo.add(cita); // Se encontró al menos una cita para el horario
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar Cita por horario. " + e.getMessage());
            devo = null;
        }
        return devo;
    }

}