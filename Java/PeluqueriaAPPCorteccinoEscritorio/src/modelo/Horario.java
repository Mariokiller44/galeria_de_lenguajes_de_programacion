/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
import java.util.*;

import controlador.ConexionBD;

/**
 *
 * Clase Horario: representa un horario con sus atributos.
 * 
 * @author Mario
 */
public class Horario {
    // Atributos
    private int id, idPersonal, idServicio; // ID del horario
    private String fecha; // Fecha del horario
    private String hora; // Hora del horario
    private Personal personal; // Empleado asignado al horario
    private Servicio servicio; // Servicio asociado al horario
    private String descripcion; // Descripción del horario
    private String precio; // Precio del horario
    private String empleado;
    private Connection conexionBD; // Empleado asignado al horario

    // Constructores
    public Horario(int id, String fecha, String hora, String descripcion, String precio, String empleado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.precio = precio;
        this.empleado = empleado;
    }

    public Horario(int id, String fecha, String hora, Personal personal, Servicio servicio, Connection connect) {
        setId(id);
        setFecha(fecha);
        setHora(hora);
        setPersonal(personal);
        setServicio(servicio);
        this.conexionBD = connect;
    }

    public Horario(int id, Connection connect) {
        setId(id);
        this.conexionBD = connect;
    }

    public Horario(int id, String fecha, String hora, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public Horario(int id, String empleado) {
        this.id = id;
        this.empleado = empleado;
    }

    // Métodos de acceso (getters)
    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getEmpleado() {
        return empleado;
    }

    public Personal getPersonal() {
        if (idPersonal != 0) {
            this.personal = Personal.obtenerPersonalPorId(idPersonal, conexionBD);
        }
        return this.personal;
    }

    public Servicio getServicio() {
        if (idServicio != 0) {
            this.servicio = Servicio.obtenerServicioPorId(idServicio, conexionBD);
        }
        return this.servicio;
    }

    public Connection getConexionBD() {
        return ConexionBD.conectarSinLogin();
    }

    // Métodos de modificación (setters)
    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean setPersonal(Personal personal) {
        boolean devo = false;
        try {
            this.personal = personal;
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    public boolean setServicio(Servicio servicio) {
        boolean devo = false;
        try {
            if (idServicio != 0) {
                Servicio.obtenerServicioPorId(idPersonal, conexionBD);
            } else {
                this.servicio = servicio;
            }
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    // Métodos de utilidad
    private boolean inicializarDesdeBD() {
        boolean devo = false;
        String sql;
        ResultSet rs;
        PreparedStatement ps;
        try {
            sql = "SELECT * FROM horario WHERE id = ?";
            ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.fecha = rs.getString("fecha");
                this.hora = rs.getString("hora");
                this.idPersonal = rs.getInt("ID_PERSONAL");
                this.idServicio = rs.getInt("ID_SERVICIO");
                getPersonal();
                getServicio();
                devo = true;
            } else {
                System.out.println("No se encontró el horario con ID: " + this.id);
                devo = true;
            }
        } catch (Exception e) {
            devo=false;
        }
        return devo;
    }

    // Métodos CRUD
    public boolean crearHorario() {
        boolean exito = false;
        try {
            String sql = "INSERT INTO horario (fecha, hora, ID_PERSONAL, ID_SERVICIO) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setInt(3, idPersonal);
            ps.setInt(4, idServicio);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean leerHorario() {
        boolean exito = false;
        try {
            String sql = "SELECT * FROM horario WHERE id = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.fecha = rs.getString("fecha");
                this.hora = rs.getString("hora");
                this.idPersonal = rs.getInt("ID_PERSONAL");
                this.idServicio = rs.getInt("ID_SERVICIO");
                exito = true;
            }
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean actualizarHorario() {
        boolean exito = false;
        try {
            String sql = "UPDATE horario SET fecha=?, hora=?, ID_PERSONAL=?, ID_SERVICIO=? WHERE id=?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setInt(3, idPersonal);
            ps.setInt(4, idServicio);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean eliminarHorario() {
        boolean exito = false;
        try {
            String sql = "DELETE FROM horario WHERE id = ?";
            PreparedStatement ps = conexionBD.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    // Buscar un Horario por su id
    public static Horario buscarPorId(int id, Connection conn) {
        try {
            String sql = "SELECT * FROM horario WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Horario h = new Horario(rs.getInt("id"), conn);
                return h;
            }
        } catch (Exception e) {
            // Manejo de error
        }
        return null;
    }

    // Buscar Horarios filtrando por atributos
    public static ArrayList<Horario> buscarFiltrado(
            boolean filtrarFecha, String fecha,
            boolean filtrarHora, String hora,
            boolean filtrarIdPersonal, int idPersonal,
            boolean filtrarIdServicio, int idServicio, Connection conn) {
        ArrayList<Horario> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM horario WHERE 1=1");
        if (filtrarFecha)
            sql.append(" AND fecha = ?");
        if (filtrarHora)
            sql.append(" AND hora = ?");
        if (filtrarIdPersonal)
            sql.append(" AND ID_PERSONAL = ?");
        if (filtrarIdServicio)
            sql.append(" AND ID_SERVICIO = ?");
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (filtrarFecha)
                ps.setString(idx++, fecha);
            if (filtrarHora)
                ps.setString(idx++, hora);
            if (filtrarIdPersonal)
                ps.setInt(idx++, idPersonal);
            if (filtrarIdServicio)
                ps.setInt(idx++, idServicio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Horario h = new Horario(rs.getInt("id"), conn);
                h.inicializarDesdeBD();
                lista.add(h);
            }
        } catch (Exception e) {
            // Manejo de error
            lista = null;
        }
        return lista;
    }

    @Override
    public String toString() {
        return "fecha: " + fecha + ", hora: " + hora + ", descripcion: " + descripcion + ", precio: " + precio
                + ", empleado: " + empleado;
    }
}
