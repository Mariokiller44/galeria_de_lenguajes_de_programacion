
/**
 * Paquete modelo
 */
package modelo;

import java.sql.*;

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

}