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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * Clase Usuario: representa un usuario con sus atributos.
 *
 * @author Mario
 */
public class Usuario {

    private int id; // ID del usuario
    private int telefono; // Número de teléfono del usuario
    private String nombre; // Nombre del usuario
    private String apellidos; // Apellidos del usuario
    private String email; // Email del usuario
    private String cuenta; // Cuenta del usuario
    private String contrasenia; // Contraseña del usuario
    private String tipo_de_usuario; // Tipo de usuario
    private Connection conexionBaseDatos; // Conexion a la base de datos

    /*
     * Constructor vacío de la clase Usuario.
     */
    public Usuario() {
    }

    public Usuario(Connection conexionBaseDatos) {
        this.conexionBaseDatos = conexionBaseDatos;
    }

    
    /*
     * Constructor de la clase Usuario.
     *
     * @param id ID del usuario
     * 
     * @param telefono Número de teléfono del usuario
     * 
     * @param nombre Nombre del usuario
     * 
     * @param apellidos Apellidos del usuario
     * 
     * @param email Email del usuario
     * 
     * @param cuenta Cuenta del usuario
     * 
     * @param contrasenia Contraseña del usuario
     * 
     * @param tipo_de_usuario Tipo de usuario
     */
    public Usuario(int id, int telefono, String nombre, String apellidos, String email, String cuenta,
            String contrasenia, String tipo_de_usuario) {
        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.cuenta = cuenta;
        this.contrasenia = contrasenia;
        this.tipo_de_usuario = tipo_de_usuario;
    }

    public Usuario(int id, Connection conexionBD) {
        setId(id);
        this.conexionBaseDatos = conexionBD;
    }

    public Usuario(int id, String nombre, String apellidos, String email, int telefono, String cuenta,
            String contrasenia, String tipo_de_usuario, Connection conexionBD) {
        setId(id);
        setNombre(nombre);
        setApellidos(apellidos);
        setEmail(email);
        setTelefono(telefono);
        setCuenta(cuenta);
        setContrasenia(contrasenia);
        setTipo_de_usuario(tipo_de_usuario);
        this.conexionBaseDatos = conexionBD;
    }

    /*
     * Constructor de la clase Usuario que recibe ID, nombre, apellidos, teléfono,
     * email, cuenta y contraseña.
     *
     * @param id ID del usuario
     * 
     * @param nombre Nombre del usuario
     * 
     * @param apellidos Apellidos del usuario
     * 
     * @param telefono Número de teléfono del usuario
     * 
     * @param email Email del usuario
     * 
     * @param cuenta Cuenta del usuario
     * 
     * @param contrasenia Contraseña del usuario
     */
    public Usuario(int id, String nombre, String apellidos, int telefono, String email, String cuenta,
            String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.cuenta = cuenta;
        this.contrasenia = contrasenia;
    }

    /*
     * Constructor de la clase Usuario que recibe ID, nombre y apellidos.
     *
     * @param id ID del usuario
     * 
     * @param nombre Nombre del usuario
     * 
     * @param apellidos Apellidos del usuario
     */
    public Usuario(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }


    /*
     * Obtener el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /*
     * Establecer el ID del usuario.
     *
     * @param id El ID del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /*
     * Obtener el número de teléfono del usuario.
     *
     * @return El número de teléfono del usuario.
     */
    public int getTelefono() {
        return telefono;
    }

    /*
     * Establecer el número de teléfono del usuario.
     *
     * @param telefono El número de teléfono del usuario.
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /*
     * Obtener el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /*
     * Establecer el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*
     * Obtener los apellidos del usuario.
     *
     * @return Los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /*
     * Establecer los apellidos del usuario.
     *
     * @param apellidos Los apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /*
     * Obtener el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Establecer el email del usuario.
     *
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Obtener la cuenta del usuario.
     *
     * @return La cuenta del usuario.
     */
    public String getCuenta() {
        return cuenta;
    }

    /*
     * Establecer la cuenta del usuario.
     *
     * @param cuenta La cuenta del usuario.
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /*
     * Obtener la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /*
     * Establecer la contraseña del usuario.
     *
     * @param contrasenia La contraseña del usuario.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /*
     * Obtener el tipo de usuario.
     *
     * @return El tipo de usuario.
     */
    public String getTipo_de_usuario() {
        return tipo_de_usuario;
    }

    /*
     * Establecer el tipo de usuario.
     *
     * @param tipo_de_usuario El tipo de usuario.
     */
    public boolean setTipo_de_usuario(String tipo_de_usuario) {
        boolean devo = false;
        try {
            this.tipo_de_usuario = tipo_de_usuario;
            if (tipo_de_usuario.equalsIgnoreCase("Cliente")) {
                Cliente.obtenerClientePorId(this.id, conexionBaseDatos);
            } else {
                Personal.obtenerPersonalPorId(this.id, conexionBaseDatos);
            }
            devo = true;
        } catch (Exception e) {
            // TODO: handle exception
            devo = false;
        }

        return devo;
    }

    // Métodos de acceso y modificación de los atributos (Del cual hereden las clases hijas)
    protected boolean inicializarDesdeBD(int id,Connection con) {
        boolean devo = false;
        try {
            String sql = "SELECT * FROM usuario WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setNombre(rs.getString("NOMBRE"));
                setApellidos(rs.getString("APELLIDOS"));
                setEmail(rs.getString("EMAIL"));
                setTelefono(rs.getInt("TELEFONO"));
                setCuenta(rs.getString("CUENTA"));
                setContrasenia(rs.getString("CONTRASENIA"));
                tipo_de_usuario = rs.getString("TIPO_DE_USUARIO");
                devo = true;
            } else {
                System.out.println("No se encontró el usuario con ID: " + this.id);
            }
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }
    
    // Métodos de acceso y modificación de los atributos propio de la clase
    private boolean inicializarDesdeBD() {
        boolean devo = false;
        try {
            String sql = "SELECT * FROM usuario WHERE ID = ?";
            PreparedStatement ps = conexionBaseDatos.prepareStatement(sql);
            ps.setInt(1, getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setNombre(rs.getString("NOMBRE"));
                setApellidos(rs.getString("APELLIDOS"));
                setEmail(rs.getString("EMAIL"));
                setTelefono(rs.getInt("TELEFONO"));
                setCuenta(rs.getString("CUENTA"));
                setContrasenia(rs.getString("CONTRASENIA"));
                tipo_de_usuario = rs.getString("TIPO_DE_USUARIO");
                devo = true;
            } else {
                System.out.println("No se encontró el usuario con ID: " + this.id);
            }
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    public Usuario buscarPorId(int id, Connection conexion) {
        Usuario devo = null;
        try {
            devo = new Usuario(id, conexion);
            devo.inicializarDesdeBD();
        } catch (Exception e) {
            devo = null;
        }
        return devo;
    }

    public static Usuario buscarPorCuentaYContrasenia(String usuario, String contrasenia, Connection con) {
        Usuario devo = null;
        String sql = "SELECT id FROM usuario WHERE cuenta LIKE ? AND CONTRASENIA LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                devo = new Usuario(rs.getInt("id"), con);
                devo.inicializarDesdeBD();
            }
        } catch (Exception e) {
            devo = null;
        }
        return devo;
    }

    public static Usuario buscarPorCuenta(String usuario, Connection con) {
        Usuario devo = null;
        String sql = "SELECT id FROM usuario WHERE cuenta LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                devo = new Usuario(rs.getInt("id"), con);
                devo.inicializarDesdeBD();
            }
        } catch (Exception e) {
            devo = null;
        }
        return devo;
    }

    // Método para modificar usuario
    public boolean modificarUsuario() {
        try {
            String[] opciones = {"Nombre", "Apellidos", "Telefono", "Email", "Cuenta", "Contrasenia"};
            int seleccion = JOptionPane.showOptionDialog(null, "¿Qué quieres modificar?", "Opciones",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            if (seleccion >= 0) {
                String campo = opciones[seleccion];
                String valor = obtenerValorCampo(campo);

                if (valor != null && validarCampo(campo, valor)) {
                    return actualizarCampoUsuario(campo, valor);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + ex.getMessage());
        }
        return false;
    }

    private String obtenerValorCampo(String campo) {
        switch (campo) {
            case "Nombre":
                return JOptionPane.showInputDialog(null, "Por favor, ingrese su nombre:");
            case "Apellidos":
                return JOptionPane.showInputDialog(null, "Por favor, ingrese sus apellidos:");
            case "Telefono":
                return JOptionPane.showInputDialog(null, "Por favor, ingrese su número de teléfono:");
            case "Email":
                String email;
                do {
                    email = JOptionPane.showInputDialog(null, "Por favor, ingrese su email:");
                } while (!validarEmail(email));
                return email;
            case "Cuenta":
                return JOptionPane.showInputDialog(null, "Por favor, ingrese su cuenta:");
            case "Contrasenia":
                return JOptionPane.showInputDialog(null, "Por favor, ingrese su contraseña:");
            default:
                return null;
        }
    }

    private boolean validarCampo(String campo, String valor) {
        switch (campo) {
            case "Telefono":
                if (valor.length() != 9) {
                    JOptionPane.showMessageDialog(null, "Teléfono no válido. Introduce 9 dígitos");
                    return false;
                }
                return true;
            case "Email":
                return validarEmail(valor);
            default:
                return valor != null && !valor.isEmpty();
        }
    }

    private boolean actualizarCampoUsuario(String campo, String valor) throws SQLException {
        String sql = "UPDATE usuario SET " + obtenerNombreColumna(campo) + " = ? WHERE ID = ?";

        try (PreparedStatement ps = conexionBaseDatos.prepareStatement(sql)) {
            if (campo.equals("Contrasenia")) {
                ps.setString(1, "MD5(" + valor + ")");
            } else {
                ps.setString(1, valor);
            }
            ps.setInt(2, this.id);

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                actualizarAtributoLocal(campo, valor);
                JOptionPane.showMessageDialog(null, "Actualización realizada con éxito");
                return true;
            }
        }
        return false;
    }

    private boolean actualizarAtributoLocal(String campo, String valor) {
        boolean devo=false;
        try {
            switch (campo) {
                case "Nombre":
                    this.nombre = valor;
                    break;
                case "Apellidos":
                    this.apellidos = valor;
                    break;
                case "Telefono":
                    this.telefono = Integer.parseInt(valor);
                    break;
                case "Email":
                    this.email = valor;
                    break;
                case "Cuenta":
                    this.cuenta = valor;
                    break;
                case "Contrasenia":
                    this.contrasenia = valor;
                    break;
            }
            devo=true;
        } catch (Exception ex) {
            System.out.println("Ocurrio un error: "+ex.getMessage());
        }
        return devo;
    }

    private String obtenerNombreColumna(String campo) {
        switch (campo) {
            case "Nombre":
                return "NOMBRE";
            case "Apellidos":
                return "APELLIDOS";
            case "Telefono":
                return "TELEFONO";
            case "Email":
                return "EMAIL";
            case "Cuenta":
                return "CUENTA";
            case "Contrasenia":
                return "CONTRASENIA";
            default:
                return "";
        }
    }

    private boolean validarEmail(String email) {
        // Implementar validación de email
        return email != null && email.contains("@") && email.contains(".");
    }

    // Método para buscar todos los usuarios
    public static ArrayList<Usuario> buscarTodos(Connection conexion) {
        return buscarUsuariosFiltrados(false, null, false, null, false, null, false, null, conexion);
    }

    // Método para buscar usuarios con filtros
    public static ArrayList<Usuario> buscarUsuariosFiltrados(
            boolean usarNombre, String filtroNombre,
            boolean usarApellidos, String filtroApellidos,
            boolean usarEmail, String filtroEmail,
            boolean usarCuenta, String filtroCuenta,
            Connection conexion) {

        ArrayList<Usuario> usuarios = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT ID, NOMBRE, APELLIDOS, TELEFONO, EMAIL, CUENTA FROM usuario WHERE 1=1");

            if (usarNombre && filtroNombre != null && !filtroNombre.isEmpty()) {
                sql.append(" AND NOMBRE LIKE ?");
            }
            if (usarApellidos && filtroApellidos != null && !filtroApellidos.isEmpty()) {
                sql.append(" AND APELLIDOS LIKE ?");
            }
            if (usarEmail && filtroEmail != null && !filtroEmail.isEmpty()) {
                sql.append(" AND EMAIL LIKE ?");
            }
            if (usarCuenta && filtroCuenta != null && !filtroCuenta.isEmpty()) {
                sql.append(" AND CUENTA LIKE ?");
            }

            stmt = conexion.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (usarNombre && filtroNombre != null && !filtroNombre.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtroNombre + "%");
            }
            if (usarApellidos && filtroApellidos != null && !filtroApellidos.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtroApellidos + "%");
            }
            if (usarEmail && filtroEmail != null && !filtroEmail.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtroEmail + "%");
            }
            if (usarCuenta && filtroCuenta != null && !filtroCuenta.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtroCuenta + "%");
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(conexion);
                usuario.setId(rs.getInt("ID"));
                usuario.setNombre(rs.getString("NOMBRE"));
                usuario.setApellidos(rs.getString("APELLIDOS"));
                usuario.setTelefono(rs.getInt("TELEFONO"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuario.setCuenta(rs.getString("CUENTA"));

                usuarios.add(usuario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage());
            usuarios = null;
        }
        return usuarios;
    }

    // Método para cargar un usuario por ID
    public boolean cargarPorId(int id) {
        String sql = "SELECT NOMBRE, APELLIDOS, TELEFONO, EMAIL, CUENTA FROM usuario WHERE ID = ?";

        try (PreparedStatement ps = conexionBaseDatos.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                this.id = id;
                this.nombre = rs.getString("NOMBRE");
                this.apellidos = rs.getString("APELLIDOS");
                this.telefono = rs.getInt("TELEFONO");
                this.email = rs.getString("EMAIL");
                this.cuenta = rs.getString("CUENTA");
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar usuario: " + e.getMessage());
        }
        return false;
    }
    /*
     * Sobrescritura del método toString() para representar el objeto como una
     * cadena de texto.
     *
     * @return La representación en cadena de texto del objeto Usuario.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellidos: " + apellidos;
    }
}
