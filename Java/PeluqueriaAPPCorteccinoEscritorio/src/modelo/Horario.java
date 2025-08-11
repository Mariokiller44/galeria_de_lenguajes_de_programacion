/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;
import java.util.*;

import controlador.ConexionBD;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

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
    private static Connection conexionBD; // Empleado asignado al horario

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

    public Horario(String fecha, String hora, Personal personal, Servicio servicio, Connection connect) {
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

    public Horario() {
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

    public int getIdPersonal() {
        return idPersonal;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public String getFecha() {
        try {
            LocalDate fechaOri = LocalDate.parse(fecha);
            String fechaConvertida = fechaOri.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return fechaConvertida;
        } catch (Exception e) {
            return fecha; // Devolver original si hay error
        }
    }

    public String getHora() {
        return hora;
    }

    public String getDescripcion() {
        return descripcion;
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

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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
                // Si es necesario para la obtencion del atributo descripcion puedes descomentar esta parte:
                this.descripcion = servicio.getDescripcion();
                this.precio = String.valueOf(servicio.getPrecio());
                devo = true;
            } else {
                System.out.println("No se encontró el horario con ID: " + this.id);
                devo = true;
            }
        } catch (Exception e) {
            devo = false;
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
            if (idPersonal==0) {
                setIdPersonal(personal.getId());
            }
            if (idServicio==0) {
                setIdServicio(servicio.getId());
            }
            ps.setInt(3, idPersonal);
            ps.setInt(4, idServicio);
            int rows = ps.executeUpdate();
            exito = rows > 0;
        } catch (Exception e) {
            exito = false;
        }
        return exito;
    }

    public boolean actualizarHorario() {
        boolean exito = false;
        try {
            StringBuilder sql = new StringBuilder("UPDATE horario SET ");
            StringBuilder campos = new StringBuilder();
            if (fecha != null) {
                if (campos.length() > 0) {
                    campos.append(", ");
                }
                campos.append("fecha = ?");
            }
            if (hora != null) {
                if (campos.length() > 0) {
                    campos.append(", ");
                }
                campos.append("hora = ?");
            }
            if (idPersonal != 0) {
                if (campos.length() > 0) {
                    campos.append(", ");
                }
                campos.append("ID_PERSONAL = ?");
            }
            if (idServicio != 0) {
                if (campos.length() > 0) {
                    campos.append(", ");
                }
                campos.append("ID_SERVICIO = ?");
            }

            if (campos.length() == 0) {
               exito=false; // No hay nada que actualizar
            }

            sql.append(campos).append(" WHERE id = ?");
            PreparedStatement ps = conexionBD.prepareStatement(sql.toString());
            int idx = 1;
            if (fecha != null) {
                ps.setString(idx++, fecha);
            }
            if (hora != null) {
                ps.setString(idx++, hora);
            }
            if (idPersonal != 0) {
                ps.setInt(idx++, idPersonal);
            }
            if (idServicio != 0) {
                ps.setInt(idx++, idServicio);
            }
            ps.setInt(idx++, id);
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
        Horario h = null;
        try {
            h = new Horario(id, conn);
            h.inicializarDesdeBD();
        } catch (Exception e) {
            // Manejo de error
            h = null;
        }
        return h;
    }

    /**
     * Método para obtener horarios filtrados por empleado
     *
     * @param idPersonal ID del empleado
     * @param modeloGeneral DefaultTableModel donde se cargarán los datos
     * @return boolean true si se obtuvieron datos, false si está vacío o hay
     * error
     */
    public boolean obtenerHorariosPorEmpleado(int idPersonal) {
        boolean devo = false;
        try {

            String consulta = "SELECT DISTINCT horario.fecha_es AS fecha, horario.hora_es AS hora, "
                    + "servicios.descripcion, servicios.precio, "
                    + "IFNULL(CONCAT(cliente.nombre, ' ', cliente.apellidos), 'No hay cliente') AS cliente "
                    + "FROM horario "
                    + "JOIN personal ON horario.id_personal = personal.id "
                    + "LEFT JOIN cita ON horario.id = cita.ID_HORARIO "
                    + "LEFT JOIN usuario AS cliente ON cita.ID_CLIENTE = cliente.ID "
                    + "JOIN servicios ON horario.id_servicio = servicios.id "
                    + "WHERE personal.id = ? "
                    + "ORDER BY horario.fecha DESC";

            try (PreparedStatement statement = conexionBD.prepareStatement(consulta)) {
                statement.setInt(1, idPersonal);

                try (ResultSet resultado = statement.executeQuery()) {
                    while (resultado.next()) {
                        String fecha = resultado.getString("fecha");
                        String hora = resultado.getString("hora");
                        String descripcion = resultado.getString("descripcion");
                        String precio = resultado.getString("precio");
                        String cliente = resultado.getString("cliente");

                    }
                }
            }
            devo = true;
        } catch (SQLException e) {
            System.err.println("Error al obtener horarios por empleado: " + e.getMessage());
            devo = false;
        }
        return devo;
    }

    // Método para obtener los horarios con cita asignada
    public ArrayList<Horario> obtenerHorariosConCita() {
        ArrayList<Horario> horariosConCita = new ArrayList<>();

        try {

            // Consulta SQL para obtener los horarios con cita asignada
            String sql = "SELECT horario.* FROM horario JOIN cita ON horario.id = cita.id_horario";

            PreparedStatement ps = conexionBD.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Horario h = new Horario(rs.getInt("id"), conexionBD);
                h.inicializarDesdeBD();
                horariosConCita.add(h);
            }
        } catch (SQLException e) {
            horariosConCita = null;
        }

        return horariosConCita;
    }

    /**
     * Método para obtener la lista de horarios disponibles sin citas para un
     * personal.
     *
     * @param idPersonal El ID del personal.
     * @return Una lista de cadenas con los horarios disponibles.
     */
    public ArrayList<Horario> obtenerHorariosSinCitas() {
        ArrayList<Horario> horariosSinCitas = new ArrayList<>();

        try {

            // Consulta SQL para obtener los horarios sin citas
            String sql = "SELECT * FROM horario h LEFT JOIN cita c ON h.id = c.ID_HORARIO where c.id IS NULL AND h.fecha >= CURDATE();";
            PreparedStatement stmt = conexionBD.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                throw new Exception("No existen horarios sin citas");
            } else {
                // Recorre los resultados de la consulta
                while (rs.next()) {
                    Horario horario = new Horario();
                    horario.setFecha(rs.getString("fecha_es"));
                    horario.setHora(rs.getString("hora_es"));
                    horario.setIdServicio(rs.getInt("id_servicio"));
                    horario.getServicio();
                    horariosSinCitas.add(horario);
                }
            }

        } catch (Exception e) {
            horariosSinCitas = null;
            JOptionPane.showMessageDialog(null, "Error al buscar los horarios");
        }

        return horariosSinCitas;
    }

    /**
     * Método alternativo que devuelve una lista de objetos Horario
     *
     * @return ArrayList<Horario> lista con todos los horarios, null si hay
     * error
     */
    public static ArrayList<Horario> obtenerTodosLosHorarios(Connection conexionBDatos) {
        ArrayList<Horario> listaHorarios;

        try {
            listaHorarios = buscarHorariosDetallados(false, null, false, null, false, 0, false, 0, conexionBDatos);

        } catch (Exception e) {
            System.err.println("Error al obtener lista de horarios: " + e.getMessage());
            listaHorarios = null;
        }
        return listaHorarios;
    }

    /**
     * Metodo de acceso a datos para buscar los horarios detallados por filtros
     *
     * @param filtrarFecha
     * @param fecha
     * @param filtrarHora
     * @param hora
     * @param filtrarIdPersonal
     * @param idPersonal
     * @param filtrarIdServicio
     * @param idServicio
     * @param conn
     * @return
     */
    public static ArrayList<Horario> buscarHorariosDetallados(
            boolean filtrarFecha, String fecha,
            boolean filtrarHora, String hora,
            boolean filtrarIdPersonal, int idPersonal,
            boolean filtrarIdServicio, int idServicio, Connection conn) {

        ArrayList<Horario> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id FROM horario WHERE id IS NOT NULL");

        if (filtrarFecha) {
            sql.append(" AND fecha = ?");
        }
        if (filtrarHora) {
            sql.append(" AND hora = ?");
        }
        if (filtrarIdPersonal) {
            sql.append(" AND id_personal = ?");
        }
        if (filtrarIdServicio) {
            sql.append(" AND id_servicio = ?");
        }

        sql.append(" ORDER BY fecha ASC, hora ASC");

        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (filtrarFecha) {
                ps.setString(idx++, fecha);
            }
            if (filtrarHora) {
                ps.setString(idx++, hora);
            }
            if (filtrarIdPersonal) {
                ps.setInt(idx++, idPersonal);
            }
            if (filtrarIdServicio) {
                ps.setInt(idx++, idServicio);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Horario h = new Horario(rs.getInt("id"), conn);
                h.inicializarDesdeBD();
                lista.add(h);
            }
        } catch (Exception e) {
            lista = null;
            System.out.println("Error al buscar horarios detallados: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public String toString() {
        return "fecha: " + fecha + ", hora: " + hora + ", descripcion: " + descripcion + ", precio: " + precio
                + ", empleado: " + empleado;
    }
}
