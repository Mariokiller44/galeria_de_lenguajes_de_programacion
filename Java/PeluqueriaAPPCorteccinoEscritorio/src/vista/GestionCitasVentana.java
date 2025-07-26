/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

//import com.github.lgooddatepicker.components.DatePicker;
//import com.github.lgooddatepicker.components.TimePicker;
import controlador.ConexionBD;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Date;
import java.time.LocalDate;
import javax.swing.JPanel;
//import com.toedter.calendar.*;
import controlador.ConsultasPersonal;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import static javax.swing.UIManager.put;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import modelo.Cita;
import modelo.Horario;
import modelo.Personal;
import modelo.Servicio;
import modelo.Usuario;

/**
 * Clase para gestionar la ventana de citas.
 *
 * @author Mario
 */
public class GestionCitasVentana extends javax.swing.JFrame {

    // Variables de instancia
    private ConexionBD conexion; // Objeto de conexión a la base de datos
    private Connection con; // Conexión a la base de datos
    private int id; // Identificador de la cita
    private String[] columnas = {"Fecha", "Hora", "Servicio", "Precio", "Cliente"}; // Arreglo de nombres de columnas para la tabla
    private String[] columnasAdmin = {"Fecha", "Hora", "Servicio", "Precio", "Cliente", "Personal"}; // Arreglo de nombres de columnas para la tabla de administrador
    private ConsultasPersonal consultas = new ConsultasPersonal(); // Objeto para realizar consultas relacionadas con el personal
    private VentanaPrincipal vc; // Objeto de la clase VentanaPrincipal
    private String tipoUsu; // Variable para almacenar el tipo de usuario
    private JComboBox<String> comboBoxHorarios; // Cuadro de lista desplegable para horarios
    private String[] opcionesHorario; // Arreglo de opciones de horario
    private int idS; // Identificador del servicio
    private int filaSeleccionada;
    private String fechaH;
    private String horaH;
    private String servicioH;
    private String precioH;
    private String clienteH;
    private String personalH;
    private String precio;
    private Cita citaElegida;
    private Personal empleadoEscogido;
    private Horario horarioEscogido,horarioNuevo;
    private ArrayList<Cita> listadoCitas;
    private ArrayList<Personal> listaEmpleados;
    private ArrayList<Horario> listaHorario;
    

    /**
     * Método para obtener el valor de la variable idS.
     *
     * @return El valor de idS.
     */
    public int getIdS() {
        return idS;
    }

    /**
     * Método para establecer el valor de la variable idS.
     *
     * @param idS El nuevo valor de idS a establecer.
     */
    public void setIdS(int idS) {
        this.idS = idS;
    }

    /**
     * Método para obtener la tabla.
     *
     * @return La referencia a la tabla.
     */
    public JTable getTabla() {
        return tabla;
    }

    /**
     * Método para establecer la tabla.
     *
     * @param tabla La nueva tabla a establecer.
     */
    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Método para obtener el valor de la variable tipoUsu.
     *
     * @return El valor de tipoUsu.
     */
    public String getTipoUsu() {
        return tipoUsu;
    }

    /**
     * Método para establecer el valor de la variable tipoUsu.
     *
     * @param tipoUsu El nuevo valor de tipoUsu a establecer.
     */
    public void setTipoUsu(String tipoUsu) {
        this.tipoUsu = tipoUsu;
    }

    /**
     * Constructor de la clase.
     */
    public GestionCitasVentana() {
        initComponents(); // Inicialización de componentes gráficos
        setIconImage(getIconImage()); // Establecer la imagen del icono de la aplicación
        comprobarTabla(); // Comprobar si la tabla está vacía o no
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Establecer la ventana en pantalla completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establecer la acción de cierre de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        modificarDiseño(); // Modificar el diseño de la ventana
        setTitle("Gestionar citas");
    }

    public GestionCitasVentana(Personal personal,Connection conexionBaseData) {
        try {
            con = conexionBaseData;
        } catch (Exception e) {
            System.err.println("Error al iniciar la ventana de gestión de Citas: "+e.getMessage());
        }
    }

    
    
    
    /**
     * Método para obtener la imagen del icono de la aplicación.
     */
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage("./src/images/iconoDeAppEscritorio.png"); // Ruta de la imagen del icono
        return retValue;
    }

    /**
     * Método para comprobar si la tabla está vacía y actualizarla en
     * consecuencia.
     */
    public void comprobarTabla() {
        if (tabla.getRowCount() > 0) { // Si la tabla tiene filas
            DefaultTableModel model = (DefaultTableModel) tabla.getModel(); // Obtener el modelo de la tabla
            model.setRowCount(0); // Establecer el número de filas en 0 para vaciar la tabla
        } else { // Si la tabla está vacía
            llenarTabla(); // Llenar la tabla con datos
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        espaciado = new javax.swing.JLabel();
        panelSuperior = new javax.swing.JPanel();
        espacio1 = new javax.swing.JLabel();
        btnPedir = new javax.swing.JButton();
        espacio2 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        espacio3 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        espacio4 = new javax.swing.JLabel();
        espacio5 = new javax.swing.JLabel();
        panelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        panelInferior = new javax.swing.JPanel();
        espaciado2 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        espaciado3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(102, 102, 255));
        panelPrincipal.setPreferredSize(this.getSize());
        panelPrincipal.setLayout(new java.awt.BorderLayout(0, 9));

        espaciado.setText("    ");
        panelPrincipal.add(espaciado, java.awt.BorderLayout.WEST);

        panelSuperior.setOpaque(false);

        espacio1.setText("                  ");
        panelSuperior.add(espacio1);

        btnPedir.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        btnPedir.setText("Pedir Cita");
        btnPedir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirActionPerformed(evt);
            }
        });
        panelSuperior.add(btnPedir);

        espacio2.setText("              ");
        panelSuperior.add(espacio2);

        btnModificar.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        btnModificar.setText("Modificar Cita");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        panelSuperior.add(btnModificar);

        espacio3.setText("             ");
        panelSuperior.add(espacio3);

        btnBorrar.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        btnBorrar.setText("Anular Cita");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        panelSuperior.add(btnBorrar);

        espacio4.setText("              ");
        panelSuperior.add(espacio4);

        espacio5.setText("                  ");
        panelSuperior.add(espacio5);

        panelPrincipal.add(panelSuperior, java.awt.BorderLayout.NORTH);

        panelTabla.setOpaque(false);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[]{
                    "Fecha", "Hora", "Servicio", "Precio", "Empleado"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setResizable(false);
            tabla.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
                panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
        );
        panelTablaLayout.setVerticalGroup(
                panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );

        panelPrincipal.add(panelTabla, java.awt.BorderLayout.CENTER);

        panelInferior.setOpaque(false);
        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        espaciado2.setText("                                                                                                                                              ");
        panelInferior.add(espaciado2);

        btnSalir.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        btnSalir.setText("VOLVER A MENU PRINCIPAL");
        btnSalir.setActionCommand("");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        panelInferior.add(btnSalir);

        panelPrincipal.add(panelInferior, java.awt.BorderLayout.PAGE_END);

        espaciado3.setText("    ");
        panelPrincipal.add(espaciado3, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método para manejar el evento de acción del botón de modificación.
     *
     * @param evt El evento de acción.
     */
    @SuppressWarnings("empty-statement")
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {
        filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                // Comprobar el tipo de usuario y mostrar diferentes opciones según el tipo
                if (tipoUsu.contains("Administrador")) {
                    String horarioEscogido = obtenerHorarioSeleccionado();
                    definirVariablesHorarioAdmin(horarioEscogido);
                    // Obtener el identificador del horario
                    int horarioAdminId = obtenerIdHorario(fechaH, horaH, servicioH);
                    // Mostrar el JOptionPane para seleccionar una opción
                    String[] opciones = {"Fecha", "Hora", "Servicio", "Precio", "Personal"};
                    String seleccionAdmin = (String) JOptionPane.showInputDialog(null, "Seleccione una opción", "Opciones", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                    if (seleccionAdmin==null) {
                    } else {
                        // Realizar acciones según la opción seleccionada
                        switch (seleccionAdmin) {
                            case "Fecha":
                                modificarFecha(horarioAdminId);
                                llenarTablaTodosLosClientes();
                                break;
                            case "Hora":
                                modificarServicioHora(horarioAdminId);
                                llenarTablaTodosLosClientes();
                                break;
                            case "Servicio":
                                Horario horAdmin = new Horario(horarioAdminId, fechaH, horaH, servicioH);
                                modificarServicio(horAdmin);
                                llenarTablaTodosLosClientes();
                                break;
                            case "Precio":
                                Horario horAdminPrecio = new Horario(horarioAdminId, fechaH, horaH, servicioH);
                                modificarPrecio(horAdminPrecio);
                                llenarTablaTodosLosClientes();
                                break;
                            case "Personal":
                                Horario horAdminPersonal = new Horario(horarioAdminId, fechaH, horaH, servicioH, precioH, personalH);
                                modificarPersonal(horAdminPersonal);
                                llenarTablaTodosLosClientes();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opción no válida");
                        }
                    }
                } else {
                    String citasPersonal = obtenerHorarioEscogidoPersonal();
                    definirVariablesServicioPersonal(citasPersonal);
                    if (clienteH.contains("No hay cliente")) {
                        JOptionPane.showMessageDialog(null, "No se puede modificar esta cita. Por favor seleccione otra");
                    } else {
                        // Obtener el identificador del horario
                        int horarioId = obtenerIdHorario(fechaH, horaH, servicioH);
                        // Mostrar el JOptionPane para seleccionar una opción
                        String[] opciones = {"Fecha", "Hora", "Servicio"};
                        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una opción", "Opciones", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                        if (seleccion==null) {
                        } else {
                            // Realizar acciones según la opción seleccionada
                            switch (seleccion) {
                                case "Fecha":
                                    modificarServicioFecha(horarioId);
                                    llenarTablaHorario(id);
                                    break;
                                case "Hora":
                                    modificarServicioHora(horarioId);
                                    llenarTablaHorario(id);
                                    break;
                                case "Servicio":
                                    Horario horario = new Horario(horarioId, fechaH, horaH, servicioH);
                                    modificarServicio(horario);
                                    llenarTablaHorario(id);
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Seleccione una opcion");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Hubo un error al modificar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un horario");
        }
    }

    /**
     * Método para modificar la fecha del servicio.
     *
     * @param fechaActual La fecha actual del servicio.
     */
    public void modificarFecha(int fechaActual) {
        // Crear un cuadro de diálogo con un DatePicker para que el usuario seleccione una nueva fecha
      //  DatePicker nuevaFecha = new DatePicker();
      //  int resultado = JOptionPane.showConfirmDialog(null, nuevaFecha, "Introduce nueva fecha", JOptionPane.OK_CANCEL_OPTION);
      //  if (resultado != JOptionPane.CANCEL_OPTION) {
      //      // Obtener la fecha actualizada como un objeto LocalDate
      //      String fechaActualizada = nuevaFecha.getDate().toString();
      //      // Llamar a un método de consulta para modificar el servicio con la nueva fecha
      //      consultas.modificarServicioPorFecha(fechaActualizada, fechaActual);
      //  } else {
      //  }
    }

    /**
     * Método para modificar el precio del servicio.
     *
     * @param precioActual El objeto Horario con el precio actual del servicio.
     */
    public void modificarPrecio(Horario precioActual) {
        // Mostrar un cuadro de diálogo para que el usuario ingrese el nuevo precio
        String nuevoPrecioStr = JOptionPane.showInputDialog(null, "Introduce el nuevo precio:","Actualizar precio",JOptionPane.OK_CANCEL_OPTION);

        if (nuevoPrecioStr==null) {

        } else {
            double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

            // Obtener la descripción del servicio y el ID del servicio
            String descripcion = precioActual.getDescripcion();
            int idServicio = obtenerIdServicio(descripcion);

            // Crear la consulta SQL para actualizar el precio del servicio
            String sql = "UPDATE servicios SET precio = ? WHERE id = ?";

            try {
                realizarConexion();
                PreparedStatement pstmt = con.prepareStatement(sql);

                // Establecer los valores de los parámetros de la consulta
                pstmt.setDouble(1, nuevoPrecio);
                pstmt.setInt(2, idServicio);

                // Ejecutar la consulta
                pstmt.executeUpdate();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error modificando el precio");
            }
        }
    }

    /**
     * Método para modificar el personal del servicio.
     *
     * @param personalActual El objeto Horario con el personal actual del
     * servicio.
     */
    public void modificarPersonal(Horario personalActual) {
        // Obtener la lista de personal disponible
        ArrayList<String> personalPelu = obtenerPersonal();

        // Mostrar un cuadro de lista desplegable con el personal disponible para que el usuario seleccione uno nuevo
        JComboBox comboboxPers = new JComboBox(personalPelu.toArray());
        int resultado = JOptionPane.showConfirmDialog(null, comboboxPers, "Seleccione Nuevo Personal", JOptionPane.OK_OPTION);

        if (resultado == JOptionPane.CANCEL_OPTION) {
        } else {
            // Obtener el personal seleccionado y extraer el nombre y apellidos
            String nuevoS = comboboxPers.getSelectedItem().toString();
            String[] personal = nuevoS.split(", ");
            String nombreP = personal[0].split(": ")[1];
            String apellidosP = personal[1].split(": ")[1];

            // Obtener el ID del nuevo personal
            int idP = consultas.obtenerIdUsuario(nombreP, apellidosP);

            // Llamar a un método de consulta para modificar el servicio con el nuevo personal
            modificarPersonal(personalActual, idP);
        }
    }

    // Método para obtener los horarios con cita asignada
    public ArrayList<String> obtenerHorariosConCita() {
        ArrayList<String> horariosConCita = new ArrayList<>();

        try {
            // Establecer la conexión a la base de datos (debes configurar tus propios datos de conexión)
            realizarConexion();

            // Consulta SQL para obtener los horarios con cita asignada
            String query = "SELECT horario.fecha, horario.hora, servicios.precio,servicios.descripcion, usuario.nombre,usuario.apellidos FROM horario JOIN cita ON horario.id = cita.id_horario JOIN usuario ON cita.id_cliente = usuario.id JOIN servicios ON horario.id_servicio = servicios.id WHERE horario.id_personal=? AND horario.fecha >= CURDATE()";

            // Crear un Statement para ejecutar la consulta
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            // Ejecutar la consulta y obtener el resultado
            ResultSet resultSet = statement.executeQuery();

            // Recorrer el resultado y agregar los campos a la lista
            while (resultSet.next()) {
                String fecha = resultSet.getString("fecha");
                String hora = resultSet.getString("hora");
                String servicio = resultSet.getString("descripcion");
                Double precio = resultSet.getDouble("precio");
                String nombreCliente = resultSet.getString("nombre");
                String apellidosCliente = resultSet.getString("apellidos");
                String cliente = nombreCliente + " " + apellidosCliente;
                // Construir la cadena con los campos y agregarla a la lista
                String horarioConCita = "Fecha: " + fecha + ", Hora: " + hora + ", Servicio: " + servicio + ", Cliente: " + cliente + ", Precio: " + String.valueOf(precio);
                horariosConCita.add(horarioConCita);
            }

            // Cerrar el ResultSet, el Statement y la conexión
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horariosConCita;
    }

    /**
     * Método para modificar la fecha de un servicio.
     *
     * @param idHorario El ID del horario del servicio a modificar.
     * @throws HeadlessException Si se produce un error al mostrar el cuadro de
     * diálogo.
     */
    private void modificarServicioFecha(int idHorario) throws HeadlessException {
        // Mostrar un cuadro de diálogo con un DatePicker para que el usuario seleccione una nueva fecha
        //DatePicker nuevaFecha = new DatePicker();
        //int resultado = JOptionPane.showConfirmDialog(null, nuevaFecha, "Introduce nueva fecha", JOptionPane.OK_CANCEL_OPTION);
        //if (resultado == JOptionPane.CANCEL_OPTION) {
//
        //} else {
        //    // Obtener la fecha seleccionada y convertirla a una cadena de texto
        //    String fechaActualizada = nuevaFecha.getDate().toString();
//
        //    LocalDate fechaEscogida = nuevaFecha.getDate();
        //    // Comprobación de que no sea menor a hoy
        //    LocalDate fechaHoy = LocalDate.now();
        //    if (fechaEscogida.isBefore(fechaHoy)) {
        //        JOptionPane.showMessageDialog(null, "La fecha seleccionada no puede ser anterior a hoy");
        //    } else {
        //        // Obtener el ID de la cita
        //        int idCita = idHorario;
        //        // Llamar a un método de consulta para modificar el servicio con la nueva fecha
        //        consultas.modificarServicioPorFecha(fechaActualizada, idCita);
        //    }
        //}
    }

    /**
     * Método para modificar el servicio de un horario.
     *
     * @param horarioSeleccionado El objeto Horario del horario seleccionado.
     * @throws HeadlessException Si se produce un error al mostrar el cuadro de
     * diálogo.
     */
    private void modificarServicio(Horario horarioSeleccionado) throws HeadlessException {
        // Obtener la lista de servicios disponibles
        ArrayList<String> listaServ = consultas.serviciosBD();

        // Mostrar un cuadro de lista desplegable con los servicios disponibles para que el usuario seleccione uno nuevo
        JComboBox comboboxServ = new JComboBox(listaServ.toArray());
        int resultado = JOptionPane.showConfirmDialog(null, comboboxServ, "Seleccione nuevo servicio", JOptionPane.OK_OPTION);

        if (resultado != JOptionPane.CANCEL_OPTION) {
            // Obtener el servicio seleccionado y su ID
            String nuevoS = comboboxServ.getSelectedItem().toString();
            idS = consultas.cogerIdServicio(nuevoS);

            // Obtener el ID del horario de servicio
            int horarioServId = horarioSeleccionado.getId();

            // Llamar a un método de consulta para modificar el servicio del horario
            consultas.modificarServicio(idS, horarioServId);
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Pedir cita".
     *
     * @param evt El evento de acción generado por el botón.
     */
    private void btnPedirActionPerformed(java.awt.event.ActionEvent evt) {
        if (tipoUsu.contains("Administrador")) {
            // Método para administrador
            obtenerDatos();
        } else {
            // Método para personal
            insertarCitaPersonal();
        }
    }

    /**
     * Método para insertar una cita para el personal.
     *
     * @throws HeadlessException Si se produce un error al mostrar el cuadro de
     * diálogo.
     */
    private void insertarCitaPersonal() throws HeadlessException {
        filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            String citasPersonal = obtenerHorarioEscogidoPersonal();
            definirVariablesServicioPersonal(citasPersonal);
            if (!clienteH.contains("No hay cliente")) {
                JOptionPane.showMessageDialog(null, "Este horario ya esta asignado");
            } else {
                // Crear un modelo de ComboBox con los horarios disponibles
                // Obtener el ID del horario seleccionado
                int idHorario = obtenerIdHorarioPersonal(fechaH, horaH, servicioH);

                // Mostrar un cuadro de lista desplegable con los clientes disponibles para que el usuario seleccione uno
                ArrayList<String> listaClientes = obtenerClientes();
                DefaultComboBoxModel<String> clienteModel = new DefaultComboBoxModel<>(listaClientes.toArray(new String[0]));
                comboBoxHorarios = new JComboBox<>(clienteModel);
                int resultadoCliente = JOptionPane.showConfirmDialog(null, comboBoxHorarios, "Elige el cliente", JOptionPane.OK_CANCEL_OPTION);

                if (resultadoCliente == JOptionPane.OK_OPTION) {
                    // Obtener el cliente seleccionado
                    String seleccion = comboBoxHorarios.getSelectedItem().toString();
                    String[] partes = seleccion.split(", ");
                    String nombreC = partes[0].split(": ")[1];
                    String apellidoC = partes[1].split(": ")[1];

                    // Obtener el ID del cliente seleccionado
                    int idCliente = consultas.obtenerIdUsuario(nombreC, apellidoC);

                    // Insertar la cita con el cliente y el horario seleccionado
                    insertarCita(idCliente, idHorario);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Horario no seleccionado");
        }
    }

    /**
     * Método para insertar una nueva cita en la base de datos.
     *
     * @param idcliente El ID del cliente de la cita.
     * @param idHorario El ID del horario de la cita.
     */
    public void insertarCita(int idcliente, int idHorario) {
        try {
            realizarConexion();

            // Crear la consulta preparada para insertar la cita en la base de datos
            PreparedStatement psC = con.prepareStatement("INSERT INTO cita (ID_CLIENTE, ID_HORARIO) VALUES (?,?)");
            psC.setInt(1, idcliente);
            psC.setInt(2, idHorario);

            // Ejecutar la consulta
            int filasAfec = psC.executeUpdate();
            if (filasAfec <= 0) {
                JOptionPane.showMessageDialog(null, "No se pudo hacer la cita");
            } else {
                JOptionPane.showMessageDialog(null, "Cita creada exitosamente");
            }
            llenarTablaHorario(id);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo hacer la cita");
        }
    }

    /**
     * Método para obtener la lista de clientes disponibles.
     *
     * @return Una lista de cadenas con los nombres y apellidos de los clientes.
     */
    public ArrayList<String> obtenerClientes() {
        ArrayList<String> clientes = new ArrayList<>();
        realizarConexion();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, apellidos FROM usuario WHERE tipo_de_usuario='Cliente'");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                clientes.add("nombre: " + nombre + ", apellidos: " + apellidos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes");
        }
        return clientes;
    }

    /**
     * Método para obtener la lista de personal disponible en el sistema. Filtra
     * los usuarios por tipo_de_usuario = 'Personal' y excluye al usuario
     * 'administrador'.
     *
     * @return Una lista de cadenas con los nombres y apellidos del personal.
     */
    public ArrayList<String> obtenerPersonal() {
        ArrayList<String> personal = new ArrayList<>();
        realizarConexion(); // Establece la conexión a la base de datos.
        try {
            Statement stmt = con.createStatement();
            // Consulta SQL para obtener el nombre y los apellidos del personal.
            ResultSet rs = stmt.executeQuery("SELECT nombre, apellidos FROM usuario WHERE tipo_de_usuario='Personal' AND nombre != 'administrador'");

            // Recorre los resultados de la consulta.
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                personal.add("Nombre: " + nombre + ", Apellidos: " + apellidos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar personal");
        }
        return personal; // Devuelve la lista de personal.
    }

    /**
     * Método para obtener la lista de horarios disponibles sin citas para un
     * personal.
     *
     * @param idPersonal El ID del personal.
     * @return Una lista de cadenas con los horarios disponibles.
     */
    public ArrayList<String> obtenerHorariosSinCitas(int idPersonal) {
        ArrayList<String> horariosSinCitas = new ArrayList<>();

        try {
            // Establecer la conexión a la base de datos
            realizarConexion();

            // Consulta SQL para obtener los horarios sin citas
            String sql = "SELECT h.fecha_es, h.hora_es, s.descripcion AS servicio FROM horario h LEFT JOIN cita c ON h.id = c.ID_HORARIO LEFT JOIN servicios s ON h.id_servicio = s.id WHERE c.ID_HORARIO IS NULL AND h.ID_PERSONAL = ? AND STR_TO_DATE(h.fecha_es, '%d-%m-%Y') >= CURDATE();";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idPersonal);
            ResultSet rs = stmt.executeQuery();

            // Recorre los resultados de la consulta
            while (rs.next()) {
                String fecha = rs.getString("fecha_es");
                String hora = rs.getString("hora_es");
                String servicio = rs.getString("servicio");
                String horarioString = "Fecha: " + fecha + ", Hora: " + hora + ", Servicio: " + servicio;
                horariosSinCitas.add(horarioString);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar los horarios");
        }

        return horariosSinCitas;
    }

    /**
     * Método para obtener datos relacionados con la programación de un horario.
     * Muestra un diálogo con componentes para seleccionar la fecha, hora,
     * empleado y servicio. Luego, realiza la inserción de los datos en la tabla
     * de horario.
     */
    public void obtenerDatos() {
        // Crear los componentes de fecha y hora
        //DatePicker datePicker = new DatePicker();
        //TimePicker timePicker = new TimePicker();

        // Crear el combobox para empleados
        ArrayList<Usuario> listaEmpleados = consultas.obtenerListaEmpleados();
        DefaultComboBoxModel<Usuario> empleadoModel = new DefaultComboBoxModel<>(listaEmpleados.toArray(new Usuario[0]));
        JComboBox<Usuario> empleadoComboBox = new JComboBox<>(empleadoModel);

        // Crear el combobox para servicios
        ArrayList<Servicio> listaServicios = consultas.obtenerListaServicios();
        DefaultComboBoxModel<Servicio> servicioModel = new DefaultComboBoxModel<>(listaServicios.toArray(new Servicio[0]));
        JComboBox<Servicio> servicioComboBox = new JComboBox<>(servicioModel);

        // Crear el panel de entrada de datos
        //JPanel panel = new JPanel(new GridLayout(4, 2));
        //panel.add(new JLabel("Fecha:"));
        //panel.add(datePicker);
        //panel.add(new JLabel("Hora:"));
        //panel.add(timePicker);
        //panel.add(new JLabel("Empleado:"));
        //panel.add(empleadoComboBox);
        //panel.add(new JLabel("Servicio:"));
        //panel.add(servicioComboBox);

        // Mostrar el panel de entrada de datos en un diálogo
        //int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese los datos", JOptionPane.OK_CANCEL_OPTION);
        //if (result == JOptionPane.OK_OPTION) {
        //    // Obtener los valores seleccionados
        //    String fecha = datePicker.getDate().toString();
        //    String hora = timePicker.getTime().toString();
        //    Usuario empleado = (Usuario) empleadoComboBox.getSelectedItem();
        //    Servicio servicio = (Servicio) servicioComboBox.getSelectedItem();
//
        //    // Realizar la inserción en la tabla de horario
        //    insertarHorario(fecha, hora, empleado.getId(), servicio.getId());
        //}
    }

    /**
     * Método para insertar un nuevo registro en la tabla de horarios.
     *
     * @param fecha La fecha del horario a insertar.
     * @param hora La hora del horario a insertar.
     * @param idEmpleado El ID del empleado asociado al horario.
     * @param idServicio El ID del servicio asociado al horario.
     */
    public void insertarHorario(String fecha, String hora, int idEmpleado, int idServicio) {
        try {
            // Consulta de inserción para la tabla horario
            realizarConexion();
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO horario (fecha, hora, id_personal, id_servicio) VALUES (?, ?, ?, ?)");
            statement.setString(1, fecha);
            statement.setString(2, hora);
            statement.setInt(3, idEmpleado);
            statement.setInt(4, idServicio);

            // Ejecutar la consulta
            statement.executeUpdate();
            llenarTablaTodosLosClientes();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar\n" + e.getMessage());
        }
    }

    /**
     * Evento de acción del botón "btnSalir".
     *
     * @param evt El evento de acción.
     */
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        vc = new VentanaPrincipal();
        vaciarTabla();
        dispose();
        vc.setTipoUsu(tipoUsu);
        vc.setVisible(true);
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * Evento de acción del botón "btnBorrar".
     *
     * @param evt El evento de acción.
     */
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {
        filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            if (tipoUsu.contains("Administrador")) {
                try {
                    borradoAdmin();
                    llenarTablaTodosLosClientes();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar la tabla");
                }
            } else {
                String horarioSeleccionado;
                horarioSeleccionado = obtenerHorarioEscogidoPersonal();
                definirVariablesServicioPersonal(horarioSeleccionado);
                if (clienteH.contains("No hay clientes")) {
                    JOptionPane.showMessageDialog(null, "No hay ninguna cita que borrar. No existen citas");
                } else {
                    // Realizar la acción correspondiente según la selección del usuario
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar la cita seleccionada?", "Borrar cita", JOptionPane.OK_CANCEL_OPTION);
                    if (confirmacion == JOptionPane.OK_OPTION) {
                        try {
                            int idClienteSeleccionado = obtenerIdCliente(clienteH);
                            int idHorarioSeleccionado = obtenerIdHorario(horarioSeleccionado);
                            realizarConexion();
                            String sqlBorradoCita = "DELETE FROM CITA WHERE ID_CLIENTE=? AND ID_HORARIO=?";
                            PreparedStatement psBorradoCita = con.prepareStatement(sqlBorradoCita);
                            psBorradoCita.setInt(1, idClienteSeleccionado);
                            psBorradoCita.setInt(2, idHorarioSeleccionado);
                            int resultado = psBorradoCita.executeUpdate();
                            if (resultado == 0) {
                                JOptionPane.showMessageDialog(null, "No se pudo borrar la cita");
                            } else {
                                JOptionPane.showMessageDialog(null, "Borrado exitoso");
                            }
                            llenarTablaHorario(id);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al mostrar las citas");
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un horario");
        }
    }

    private void definirVariablesServicioPersonal(String horarioSeleccionado) {
        String[] subcadenas = horarioSeleccionado.split(",\\s*", 6);
        // Asignar las subcadenas a variables separadas
        fechaH = subcadenas[0].trim();
        horaH = subcadenas[1].trim();
        servicioH = subcadenas[2].trim();
        precioH = subcadenas[3].trim();
        clienteH = subcadenas[4].trim();
    }

    private String obtenerHorarioEscogidoPersonal() {
        String horarioSeleccionado;
        TableModel modelo = tabla.getModel();
        Object[] rowData = new Object[modelo.getColumnCount()];
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            rowData[i] = modelo.getValueAt(filaSeleccionada, i);
        }
        String horarioEscogido = Arrays.toString(rowData);
        horarioSeleccionado = horarioEscogido.substring(horarioEscogido.indexOf("[") + 1, horarioEscogido.length() - 1);
        return horarioSeleccionado;
    }

    /**
     * Obtiene el ID del horario para la cita seleccionada.
     *
     * @param citaSeleccionada La cita seleccionada.
     * @return El ID del horario.
     * @throws SQLException Si ocurre un error al realizar la consulta SQL.
     */
    private int obtenerIdHorario(String citaSeleccionada) throws SQLException {
        int identHorario = -1;
        String[] subcadenas = citaSeleccionada.split(",\\s*", 6);

        // Asignar las subcadenas a variables separadas
        String fechaH = subcadenas[0].trim();
        String horaH = subcadenas[1].trim();
        String servicioH = subcadenas[2].trim();
        int idServicio = obtenerIdServicio(servicioH);
        realizarConexion();
        String consultaHorario = "SELECT id FROM horario WHERE fecha_es = ? AND hora_es = ? AND id_servicio = ?;";
        PreparedStatement psIdHorario = con.prepareStatement(consultaHorario);
        psIdHorario.setString(1, fechaH);
        psIdHorario.setString(2, horaH);
        psIdHorario.setInt(3, idServicio);
        ResultSet rs = psIdHorario.executeQuery();
        while (rs.next()) {
            identHorario = rs.getInt("id");
        }
        return identHorario;
    }

    /**
     * Método privado para obtener el ID del cliente asociado a una cita
     * seleccionada.
     *
     * @param citaSeleccionada La cita seleccionada en formato de cadena.
     * @return El ID del cliente asociado a la cita.
     * @throws SQLException Si ocurre algún error al realizar la consulta SQL.
     */
    private int obtenerIdCliente(String citaSeleccionada) throws SQLException {
        int idCliente = -1;
        realizarConexion();
        String consultaCliente = "SELECT ID FROM USUARIO WHERE CONCAT(NOMBRE, ' ', APELLIDOS) LIKE ?";
        PreparedStatement psCliente = con.prepareStatement(consultaCliente);
        psCliente.setString(1, citaSeleccionada);
        ResultSet rsCli = psCliente.executeQuery();
        if (rsCli.next()) {
            idCliente = rsCli.getInt("ID");
        }

        return idCliente;
    }

    /**
     * Método privado para realizar el borrado de un horario por parte de un
     * administrador.
     *
     * @throws HeadlessException Si ocurre algún error relacionado con la
     * interfaz gráfica.
     */
    private void borradoAdmin() throws HeadlessException {
        // Mostrar un cuadro de diálogo de confirmación para asegurarse de que se desea borrar el horario seleccionado
        int decision = JOptionPane.showConfirmDialog(null, "Seguro que desea borrar el horario seleccionado (ten en cuenta que puede contener un cliente)", "Confirmacion", JOptionPane.YES_NO_OPTION);
        if (decision == JOptionPane.YES_OPTION) {
            try {
                // Realizar la conexión a la base de datos
                realizarConexion();
                String horarioSeleccionado = obtenerHorarioSeleccionado();
                // Obtener el ID del horario seleccionado
                int idHorario = buscarIdHorario(horarioSeleccionado);

                // Preparar la sentencia SQL para borrar el horario por su ID
                String sql = "DELETE FROM HORARIO WHERE ID=?";
                PreparedStatement psD = con.prepareStatement(sql);
                psD.setInt(1, idHorario);

                // Ejecutar la sentencia SQL de borrado
                int resultado = psD.executeUpdate();

                // Mostrar un mensaje de éxito o error dependiendo del resultado de la operación de borrado
                if (resultado == 0) {
                    JOptionPane.showMessageDialog(null, "No se pudo borrar la cita.");
                } else {
                    JOptionPane.showMessageDialog(null, "Borrado exitoso");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al intentar borrar el horario");
            }
        }
    }

    private String obtenerHorarioSeleccionado() {
        String horarioSeleccionado;
        TableModel modelo = tabla.getModel();
        Object[] rowData = new Object[modelo.getColumnCount()];
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            rowData[i] = modelo.getValueAt(filaSeleccionada, i);
        }
        String horarioEscogido = Arrays.toString(rowData);
        horarioSeleccionado = horarioEscogido.substring(horarioEscogido.indexOf("[") + 1, horarioEscogido.length() - 1);
        return horarioSeleccionado;
    }

    /**
     * Método privado para buscar y obtener el ID de un horario en la base de
     * datos.
     *
     * @param horarioSeleccionado Cadena que representa el horario seleccionado.
     * @return El ID del horario encontrado.
     * @throws SQLException Si ocurre algún error relacionado con la base de
     * datos.
     */
    private int buscarIdHorario(String horarioSeleccionado) throws SQLException {
        int idH = -1;
        String busquedaIdHorario = "SELECT ID FROM HORARIO WHERE fecha_es LIKE ? AND hora_es LIKE ?";

        definirVariablesHorarioAdmin(horarioSeleccionado);

        // Preparar la sentencia SQL para buscar el ID del horario con la fecha y hora proporcionadas
        PreparedStatement ps = con.prepareStatement(busquedaIdHorario);
        ps.setString(1, fechaH);
        ps.setString(2, horaH);

        // Ejecutar la consulta y obtener el resultado en un ResultSet
        ResultSet rs = ps.executeQuery();

        // Recorrer el ResultSet y obtener el ID del horario
        while (rs.next()) {
            idH = rs.getInt("id");
        }

        return idH;
    }

    private void definirVariablesHorarioAdmin(String horarioSeleccionado) {
        // Dividir el String en 6 subcadenas
        String[] subcadenas = horarioSeleccionado.split(",\\s*", 6);

        // Asignar las subcadenas a variables separadas
        fechaH = subcadenas[0].trim();
        horaH = subcadenas[1].trim();
        servicioH = subcadenas[2].trim();
        clienteH = subcadenas[3].trim();
        personalH = subcadenas[4].trim();
        precio = subcadenas[5].trim();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionCitasVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionCitasVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionCitasVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionCitasVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionCitasVentana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnPedir;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel espaciado;
    private javax.swing.JLabel espaciado2;
    private javax.swing.JLabel espaciado3;
    private javax.swing.JLabel espacio1;
    private javax.swing.JLabel espacio2;
    private javax.swing.JLabel espacio3;
    private javax.swing.JLabel espacio4;
    private javax.swing.JLabel espacio5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public void modificarDiseño() {
        // Botones
        if (tipoUsu != null) {
            if (tipoUsu.contains("Administrador")) {
                // Si el tipo de usuario contiene "Administrador", se modifican los textos de los botones
                btnPedir.setText("Añadir horario");
                btnModificar.setText("Modificar horario");
                btnBorrar.setText("Borrar horario");
            } else {
                // Si el tipo de usuario no es "Administrador", se modifican los textos de los botones de otra forma
                btnPedir.setText("Concertar Cita");
                btnModificar.setText("Modificar Cita");
                btnBorrar.setText("Anular Cita");
            }
        }

        // Propiedades de los botones
        btnSalir.putClientProperty(this, "roundrect");
        btnPedir.putClientProperty(this, "roundrect");
        btnModificar.putClientProperty(this, "roundrect");
        btnBorrar.putClientProperty(this, "roundrect");

        // Tabla
        tabla.setFillsViewportHeight(true);
        tabla.setCellSelectionEnabled(false);

    }

    /**
     *
     */
    public void llenarTabla() {
        try {
            int idC = id;
            if (tipoUsu != null) {
                if (idC != 0 && !tipoUsu.equals("Administrador")) {
                    // Si el tipo de usuario no es "Administrador" y el ID no es 0, se llama al método llenarTablaHorario()
                    llenarTablaHorario(idC);
                } else {
                    // Si el tipo de usuario es "Administrador" o el ID es 0, se llama al método llenarTablaTodosLosClientes()
                    llenarTablaTodosLosClientes();
                }
            }
        } catch (NullPointerException npe) {
            // Si se produce una NullPointerException, se muestra un mensaje de error relacionado con la conexión a la base de datos
            JOptionPane.showMessageDialog(null, "Error intentando conectar a la base de datos");
        } catch (SQLException ex) {
            // Si se produce una SQLException, se muestra un mensaje de error indicando que no se pudo llenar la tabla
            JOptionPane.showMessageDialog(null, "No se pudo llenar la tabla");
        }
    }

    /**
     *
     * @throws SQLException
     */
    private void llenarTablaTodosLosClientes() throws SQLException {
        // Se crea un nuevo DefaultTableModel con las columnas específicas para la vista de administrador
        DefaultTableModel modeloGeneral = new DefaultTableModel(columnasAdmin, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Se establece la conexión a la base de datos
        conexion = realizarConexion();

        // Consulta SQL para obtener todos los datos de la tabla
        String mostrarTodas = "SELECT horario.fecha_es AS fecha, horario.hora_es AS hora, servicios.descripcion, servicios.precio, IFNULL(CONCAT(cliente.nombre, ' ', cliente.apellidos), 'No hay cliente') AS cliente, CONCAT(personal.nombre, ' ', personal.apellidos) AS empleado "
                + "FROM horario "
                + "LEFT JOIN cita ON horario.id = cita.ID_HORARIO "
                + "LEFT JOIN usuario AS cliente ON cita.ID_CLIENTE = cliente.ID "
                + "JOIN usuario AS personal ON horario.id_personal = personal.id "
                + "JOIN servicios ON horario.id_servicio = servicios.id ORDER BY horario.fecha DESC;";

        // Se crea una instancia de Statement y se ejecuta la consulta SQL
        Statement statement = con.createStatement();
        ResultSet resultado = statement.executeQuery(mostrarTodas);

        // Se recorre el resultado de la consulta y se van obteniendo los valores de cada columna
        while (resultado.next()) {
            String fecha = resultado.getString("fecha");
            String hora = resultado.getString("hora");
            String descripcion = resultado.getString("descripcion");
            String precio = resultado.getString("precio");
            String cliente = resultado.getString("cliente");
            String empleado = resultado.getString("empleado");
            String[] fila = {fecha, hora, descripcion, precio, cliente, empleado};

            // Se agrega la fila al modelo de la tabla
            modeloGeneral.addRow(fila);
        }

        // Se establece el modeloGeneral como el modelo de la tabla
        tabla.setModel(modeloGeneral);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1 && isSelectedIndex(index0)) {
                    removeSelectionInterval(index0, index1);
                } else {
                    super.setSelectionInterval(index0, index1);
                }
            }
        });
    }

    /**
     *
     * @param idPersonal
     * @throws SQLException
     */
    private void llenarTablaHorario(int idPersonal) throws SQLException {
        // Se crea un nuevo DefaultTableModel con las columnas específicas para el horario
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Se establece la conexión a la base de datos
        conexion = realizarConexion();

        // Consulta SQL para obtener los datos del horario personalizado por idPersonal
        String mostrarHorarioPersonal = "SELECT DISTINCT horario.fecha_es AS fecha, horario.hora_es AS hora, servicios.descripcion, servicios.precio, IFNULL(CONCAT(usuario.nombre, ' ', usuario.apellidos), 'No hay cliente') AS cliente FROM horario JOIN personal ON horario.id_personal = personal.id LEFT JOIN cita ON horario.id = cita.ID_HORARIO LEFT JOIN usuario ON cita.ID_CLIENTE = usuario.ID JOIN servicios ON horario.id_servicio = servicios.id WHERE personal.id = ? AND YEARWEEK(horario.fecha) >= YEARWEEK(CURDATE()) ORDER BY horario.fecha DESC;";

        // Se prepara una sentencia parametrizada y se asigna el valor de idPersonal al parámetro
        PreparedStatement stmt = con.prepareStatement(mostrarHorarioPersonal);
        stmt.setInt(1, idPersonal);

        // Se ejecuta la consulta SQL y se obtiene el resultado
        ResultSet resultado = stmt.executeQuery();

        // Se recorre el resultado de la consulta y se van obteniendo los valores de cada columna
        while (resultado.next()) {
            String fecha = resultado.getString("fecha");
            String hora = resultado.getString("hora");
            String descripcion = resultado.getString("descripcion");
            String precio = resultado.getString("precio");
            String cliente = resultado.getString("cliente");
            String[] fila = {fecha, hora, descripcion, precio, cliente};

            // Se agrega la fila al modelo de la tabla
            modelo.addRow(fila);
        }

        // Se establece el modelo como el modelo de la tabla
        tabla.setModel(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1 && isSelectedIndex(index0)) {
                    removeSelectionInterval(index0, index1);
                } else {
                    super.setSelectionInterval(index0, index1);
                }
            }
        });
    }

    /**
     *
     * @return
     */
    private ConexionBD realizarConexion() {
        // Se crea una nueva instancia de la clase ConexionBD con los parámetros de usuario y contraseña
        ConexionBD conexion = new ConexionBD("admin", "123pelu");

        // Se obtiene la conexión a la base de datos mediante el método getConnection() de la clase ConexionBD
       // con = conexion.getConnection();

        // Se devuelve el objeto de tipo ConexionBD
        return conexion;
    }

    /**
     *
     * @param id
     */
    void setValor(int id) {
        // Se asigna el valor del parámetro id a la variable de instancia this.id
        this.id = id;
    }

    /**
     *
     */
    private void vaciarTabla() {
        // Se establece un nuevo modelo de tabla vacío, sin filas ni columnas
        tabla.setModel(new DefaultTableModel(0, 0));

        // Se establece el ID en el objeto vc
        vc.setId(id);
    }

    /**
     *
     * @param horarioId
     */
    private void modificarServicioHora(int horarioId) {
        // Se crea un nuevo objeto TimePicker para permitir al usuario seleccionar una nueva hora
        //TimePicker nuevaFecha = new TimePicker();

        // Se muestra un cuadro de diálogo para que el usuario introduzca la nueva hora utilizando el objeto TimePicker
        //int resultado = JOptionPane.showConfirmDialog(null, nuevaFecha, "Introduce nueva hora", JOptionPane.OK_CANCEL_OPTION);
//
        //if (resultado != JOptionPane.CANCEL_OPTION) {
        //    // Se obtiene la hora actualizada seleccionada por el usuario en formato de cadena de texto
        //    String horaActualizada = nuevaFecha.getTime().toString();
//
        //    // Se asigna el valor del horarioId a una variable local llamada idCita
        //    int idCita = horarioId;
//
        //    // Se llama al método modificarServicioPorHora() del objeto consultas, pasando la hora actualizada y el idCita como argumentos
        //    consultas.modificarServicioPorHora(horaActualizada, idCita);
        //}
    }

    /**
     *
     * @param fecha
     * @param hora
     * @param servicio
     * @return
     */
    public int obtenerIdHorario(String fecha, String hora, String servicio) {
        // Se inicializan las variables de identificación de servicio y horario con un valor por defecto de -1
        int idServicio = -1;
        int idHorario = -1;

        // Se establece la conexión a la base de datos
        realizarConexion();

        try {
            // Se ejecuta una consulta para obtener el id del servicio en base a su descripción
            PreparedStatement pstmt = con.prepareStatement("SELECT id FROM servicios WHERE descripcion=?");
            pstmt.setString(1, servicio);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Si se encuentra un registro, se asigna el valor del id del servicio a la variable idServicio
                    idServicio = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el servicio");
        }

        // Si se ha obtenido correctamente el id del servicio
        if (idServicio != -1) {
            try {
                // Se ejecuta una consulta para obtener el id del horario en base a la fecha, hora y id del servicio
                PreparedStatement pstmt = con.prepareStatement("SELECT id FROM horario WHERE fecha_es=? AND hora_es=? AND id_servicio=?");
                pstmt.setString(1, fecha);
                pstmt.setString(2, hora);
                pstmt.setInt(3, idServicio);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Si se encuentra un registro, se asigna el valor del id del horario a la variable idHorario
                        idHorario = rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al coger el horario");
            }
        }

        // Se devuelve el id del horario encontrado (o -1 si no se encontró ningún horario)
        return idHorario;
    }

    /**
     *
     * @param fecha
     * @param hora
     * @param servicio
     * @return
     */
    public int obtenerIdHorarioPersonal(String fecha, String hora, String servicio) {
        // Se inicializan las variables de identificación de servicio y horario con un valor por defecto de -1
        int idServicio = -1;
        int idHorario = -1;

        // Se establece la conexión a la base de datos
        realizarConexion();

        try {
            // Se ejecuta una consulta para obtener el id del servicio en base a su descripción
            PreparedStatement pstmt = con.prepareStatement("SELECT id FROM servicios WHERE descripcion=?");
            pstmt.setString(1, servicio);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Si se encuentra un registro, se asigna el valor del id del servicio a la variable idServicio
                    idServicio = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el servicio");
        }

        // Si se ha obtenido correctamente el id del servicio
        if (idServicio != -1) {
            try {
                // Se ejecuta una consulta para obtener el id del horario en base a la fecha, hora y id del servicio
                PreparedStatement pstmt = con.prepareStatement("SELECT id FROM horario WHERE fecha_es=? AND hora_es=? AND id_servicio=?");
                pstmt.setString(1, fecha);
                pstmt.setString(2, hora);
                pstmt.setInt(3, idServicio);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Si se encuentra un registro, se asigna el valor del id del horario a la variable idHorario
                        idHorario = rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al coger el horario");
            }
        }

        // Se devuelve el id del horario encontrado (o -1 si no se encontró ningún horario)
        return idHorario;
    }

    /**
     *
     * @return
     */
    private ArrayList<String> obtenerHorarios() {
        // Se crea una lista para almacenar los horarios
        ArrayList<String> horarios = new ArrayList<>();

        try {
            // Se establece la conexión a la base de datos
            realizarConexion();

            // Consulta SQL para obtener los horarios y la información relacionada
            String query = "SELECT DISTINCT horario.fecha, horario.hora, servicios.precio, servicios.descripcion, usuario.nombre as nombre_cliente, usuario.apellidos as apellidos_cliente, personal.nombre as nombre_personal, personal.apellidos as apellidos_personal FROM horario LEFT JOIN cita ON horario.id = cita.id_horario LEFT JOIN usuario ON cita.id_cliente = usuario.id JOIN servicios ON horario.id_servicio = servicios.id LEFT JOIN usuario as personal ON horario.id_personal = personal.id";

            // Se crea un Statement para ejecutar la consulta
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Se recorre el resultado de la consulta
            while (resultSet.next()) {
                // Se obtienen los valores de los campos correspondientes
                String fecha = resultSet.getString("fecha");
                String hora = resultSet.getString("hora");
                String servicio = resultSet.getString("descripcion");
                Double precio = resultSet.getDouble("precio");
                String cliente = "";
                if (resultSet.getString("nombre_cliente") == null && resultSet.getString("apellidos_cliente") == null) {
                    cliente = "No hay cliente asignado";
                } else {
                    String nombreCliente = resultSet.getString("nombre_cliente");
                    String apellidosCliente = resultSet.getString("apellidos_cliente");
                    cliente = nombreCliente + " " + apellidosCliente;
                }
                String personal = "";
                if (resultSet.getString("nombre_personal") == null && resultSet.getString("apellidos_personal") == null) {
                    personal = "No hay personal asignado";
                } else {
                    String nombrePersonal = resultSet.getString("nombre_personal");
                    String apellidosPersonal = resultSet.getString("apellidos_personal");
                    personal = nombrePersonal + " " + apellidosPersonal;
                }
                String precioS = String.valueOf(precio);

                // Se construye una cadena con la información del horario y se agrega a la lista de horarios
                String horario = "Fecha: " + fecha + ", Hora: " + hora + ", Servicio: " + servicio + ", Cliente: " + cliente + ", Personal: " + personal + ", Precio: " + precioS;
                horarios.add(horario);
            }

            // Se cierran los recursos utilizados
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar todos los horarios");
        }

        // Se devuelve la lista de horarios
        return horarios;
    }

    /**
     *
     * @param descripcion
     * @return
     */
    public int obtenerIdServicio(String descripcion) {
        // Variable para almacenar el ID del servicio, se inicializa con un valor por defecto de -1
        int idServicio = -1;

        // Crear la consulta SQL para obtener el ID del servicio
        String sql = "SELECT id FROM servicios WHERE descripcion = ?";

        // Conectar a la base de datos y ejecutar la consulta
        try {
            // Establecer la conexión a la base de datos
            realizarConexion();

            // Preparar la sentencia SQL con el parámetro de la descripción del servicio
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, descripcion);

            // Ejecutar la consulta y procesar el resultado
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Si se encuentra un registro, se obtiene el valor del campo "id" y se asigna a la variable idServicio
                idServicio = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Devolver el ID del servicio
        return idServicio;
    }

    /**
     *
     * @param personalActual
     * @param idP
     */
    private void modificarPersonal(Horario personalActual, int idP) {
        try {
            // Establecer la conexión a la base de datos
            realizarConexion();

            // Crear la sentencia SQL para la actualización del personal en el horario
            String actualizacion = "UPDATE HORARIO SET ID_PERSONAL=? WHERE ID LIKE ?";
            PreparedStatement ps = con.prepareStatement(actualizacion);

            // Obtener el ID del horario actual a partir de la fecha, hora y descripción proporcionados
            int idH = obtenerIdHorario(personalActual.getFecha(), personalActual.getHora(), personalActual.getDescripcion());

            // Establecer los parámetros de la sentencia SQL
            ps.setInt(1, idP);
            ps.setInt(2, idH);

            // Ejecutar la actualización y obtener el resultado
            int result = ps.executeUpdate();

            // Comprobar el resultado de la actualización y mostrar mensajes correspondientes
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el servicio, vuelva a intentarlo");
            } else {
                JOptionPane.showMessageDialog(null, "Servicio modificado exitosamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error intentando modificar el servicio");
        }
    }

}
