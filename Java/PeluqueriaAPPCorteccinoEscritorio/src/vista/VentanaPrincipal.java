/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.ConsultasPersonal;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import modelo.Personal;
import modelo.Usuario;

/**
 * Clase VentanaPrincipal, la cual representa la ventana de bienvenida
 *
 * @author Mario
 */
public class VentanaPrincipal extends JFrame {

    private int posicionActual, id; // Variables para controlar la posición y el ID
    private int posicionFinal; // Variable para la posición final
    private Timer animacion; // Temporizador para la animación
    private JPanel panelSustituto; // Panel que se sustituirá en la animación
    private ConsultasPersonal consultas; // Objeto para realizar consultas
    private Usuario usu; // Objeto para representar al usuario
    private String tipoUsu; // Tipo de usuario
    private VentanaLog ventanaLog; // Referencia a la ventana de inicio de sesión
    private JTable tablaDatosUsuario;
    private Connection conexion;
    private Personal empleado;

    /**
     * Obtiene el ID.
     *
     * @return El ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID.
     *
     * @param id El ID a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de usuario.
     *
     * @return El tipo de usuario.
     */
    public String getTipoUsu() {
        return tipoUsu;
    }

    /**
     * Establece el tipo de usuario.
     *
     * @param tipoUsu El tipo de usuario a establecer.
     */
    public void setTipoUsu(String tipoUsu) {
        this.tipoUsu = tipoUsu;
    }

    /**
     * Establece la ventana de inicio de sesión.
     *
     * @param ventanaLog La ventana de inicio de sesión a establecer.
     */
    public void setVentanaLog(VentanaLog ventanaLog) {
        this.ventanaLog = ventanaLog;
    }

    /**
     * Constructor de la clase VentanaPrincipal.
     */
    public VentanaPrincipal() {
        initComponents(); // Inicializar componentes de la ventana
        setIconImage(getIconImage()); // Establecer la imagen del ícono de la ventana
        setTitle("Ventana Principal");
        tipoUsu = getTipoUsu(); // Obtener el tipo de usuario
        vaciarPanelOpciones(); // Vaciar el panel de opciones
        panelOpciones.setVisible(true); // Mostrar el panel de opciones

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Configurar el comportamiento al cerrar la ventana
        setLocationRelativeTo(null); // Mostrar la ventana en el centro de la pantalla
        setResizable(false); // Deshabilitar la capacidad de redimensionar la ventana

        if (tipoUsu != null) {
            tipoUsu = getTipoUsu(); // Obtener el tipo de usuario
        }

        ventanaLog = new VentanaLog(); // Crear una instancia de la ventana de inicio de sesión

        // Agregar un WindowListener para controlar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Abre la otra ventana en lugar de cerrar el programa
                int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                if (decision == JOptionPane.YES_OPTION) {
                    dispose(); // Cerrar la ventana actual
                    ventanaLog.setVisible(true); // Mostrar la ventana de inicio de sesión
                }
            }
        });
    }

    public VentanaPrincipal(Usuario usuario, Connection con) {
        initComponents(); // Inicializar componentes de la ventana
        setIconImage(getIconImage()); // Establecer la imagen del ícono de la ventana
        setTitle("Ventana Principal");
        panelOpciones.setVisible(true); // Mostrar el panel de opciones

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Configurar el comportamiento al cerrar la ventana
        setLocationRelativeTo(null); // Mostrar la ventana en el centro de la pantalla
        setResizable(false); // Deshabilitar la capacidad de redimensionar la ventana
        ventanaLog = new VentanaLog(); // Crear una instancia de la ventana de inicio de sesión
        usu = usuario;
        conexion = con;
        inicializarDatos();
        // Agregar un WindowListener para controlar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Abre la otra ventana en lugar de cerrar el programa
                int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                if (decision == JOptionPane.YES_OPTION) {
                    dispose(); // Cerrar la ventana actual
                    ventanaLog.setVisible(true); // Mostrar la ventana de inicio de sesión
                }
            }
        });
    }

    /**
     * Sobrescribe el método getIconImage para obtener la imagen del ícono de la
     * aplicación.
     *
     * @return La imagen del ícono de la aplicación.
     */
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage("./src/images/iconoDeAppEscritorio.png");
        return retValue;
    }

    /**
     * Establece el valor del ID.
     *
     * @param valor El valor del ID a establecer.
     */
    public void setValor(int valor) {
        this.id = valor;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        panelBienvenida = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        calendarPanel1 = new com.github.lgooddatepicker.components.CalendarPanel();
        panelOpciones = new javax.swing.JPanel();
        menuConsultas = new javax.swing.JLabel();
        cerrarSesion = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        barraNav = new javax.swing.JMenuBar();
        menuDatosPersonales = new javax.swing.JMenu();
        menuCitas = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBienvenida.setBackground(new java.awt.Color(51, 102, 255));
        panelBienvenida.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("                           ");
        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setOpaque(true);
        panelBienvenida.add(jLabel2, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName(""); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BIENVENID@");
        jLabel1.setBackground(new java.awt.Color(20, 140, 32));
        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jLabel1.setName(""); // NOI18N
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1045, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
        );

        panelBienvenida.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Hoy es:");
        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel3.setText("                                                                                                                                                        ");
        jPanel4.add(jLabel3);

        jLabel4.setText("                                                                                                                                                        ");
        jPanel4.add(jLabel4);

        calendarPanel1.setSelectedDate(LocalDate.now());
        calendarPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.add(calendarPanel1);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        panelBienvenida.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelOpciones.setBackground(new java.awt.Color(255, 255, 255));
        panelOpciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelOpcionesMouseClicked(evt);
            }
        });

        menuConsultas.setText("-> Consultar");
        menuConsultas.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        menuConsultas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuConsultasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuConsultasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuConsultasMouseExited(evt);
            }
        });

        cerrarSesion.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrarSesionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cerrarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cerrarSesionMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelOpcionesLayout = new javax.swing.GroupLayout(panelOpciones);
        panelOpciones.setLayout(panelOpcionesLayout);
        panelOpcionesLayout.setHorizontalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuConsultas, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(cerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelOpcionesLayout.setVerticalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(menuConsultas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cerrarSesion)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        panelBienvenida.add(panelOpciones, java.awt.BorderLayout.WEST);
        panelOpciones.getAccessibleContext().setAccessibleName("");

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setToolTipText("");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1045, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );

        panelBienvenida.add(jPanel5, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panelBienvenida, java.awt.BorderLayout.CENTER);

        barraNav.setBackground(new java.awt.Color(71, 40, 184));
        barraNav.setName(""); // NOI18N
        barraNav.setOpaque(false);

        menuDatosPersonales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N
        menuDatosPersonales.setName(""); // NOI18N
        menuDatosPersonales.setToolTipText("Home");
        menuDatosPersonales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDatosPersonalesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuDatosPersonalesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuDatosPersonalesMouseExited(evt);
            }
        });
        barraNav.add(menuDatosPersonales);

        menuCitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cita.png"))); // NOI18N
        menuCitas.setToolTipText("Citas");
        menuCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuCitasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCitasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCitasMouseExited(evt);
            }
        });
        menuCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCitasActionPerformed(evt);
            }
        });
        barraNav.add(menuCitas);

        setJMenuBar(barraNav);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuDatosPersonalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDatosPersonalesMouseEntered
        menuDatosPersonales.setSelected(false);
    }//GEN-LAST:event_menuDatosPersonalesMouseEntered

    private void menuDatosPersonalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDatosPersonalesMouseClicked
        menuDatosPersonales.setSelected(false); // Desactiva la selección del menú jMenu1
        // Verifica si el número de clics del evento es menor o igual a 1
        if (evt.getClickCount() <= 1) {
            menuConsultas.setText("-> Consultar mis datos"); // Establece el texto del elemento de menú "menuConsultas" como "-> Consultar mis datos"
            cerrarSesion.setText("-> Cerrar sesión"); // Establece el texto del elemento de menú "cerrarSesion" como "-> Cerrar sesión"
        }
    }//GEN-LAST:event_menuDatosPersonalesMouseClicked

    private void menuCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCitasMouseClicked
        menuCitas.setSelected(false); // Desactiva la selección del menú jMenu2
        // Verifica si el texto del elemento de menú "menuConsultas" contiene la cadena "Consultar citas"
        // y si el texto del elemento de menú "cerrarSesion" no contiene la cadena "-> Cerrar sesión"
        if (menuConsultas.getText().contains("Consultar citas") && !cerrarSesion.getText().contains("-> Cerrar sesión")) {
            // No realiza ninguna acción si se cumple la condición anterior
        } else {
            menuConsultas.setText("-> Consultar citas"); // Establece el texto del elemento de menú "menuConsultas" como "-> Consultar citas"
            cerrarSesion.setText("-> Consultar productos"); // Establece el texto del elemento de menú "cerrarSesion" como "-> Consultar productos"
        }
        mostrarPanelOculto(); // Llama al método mostrarPanelOculto() para mostrar un panel oculto

    }//GEN-LAST:event_menuCitasMouseClicked

    private void menuDatosPersonalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDatosPersonalesMouseExited
        menuDatosPersonales.setSelected(false);// Desactiva la selección del menú jMenu1
    }//GEN-LAST:event_menuDatosPersonalesMouseExited

    private void menuCitasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCitasMouseEntered
        menuCitas.setSelected(false);// Desactiva la selección del menú jMenu2
    }//GEN-LAST:event_menuCitasMouseEntered

    private void menuCitasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCitasMouseExited
        menuCitas.setSelected(false);// Desactiva la selección del menú jMenu2
    }//GEN-LAST:event_menuCitasMouseExited

    private void menuCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCitasActionPerformed

    }//GEN-LAST:event_menuCitasActionPerformed

    private void panelOpcionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelOpcionesMouseClicked

    }//GEN-LAST:event_panelOpcionesMouseClicked

    private void menuConsultasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuConsultasMouseClicked
        // TODO add your handling code here:
        if (menuConsultas.getText().contains("Consultar citas")) {
            // Si el texto del elemento de menú contiene la subcadena "Consultar citas"
            JOptionPane.showMessageDialog(null, "Abriendo el menú de consultas"); // Muestra un mensaje de diálogo indicando que se está abriendo el menú de consultas
            dispose(); // Cierra la ventana actual
            GestionCitasVentana gc = new GestionCitasVentana(); // Crea una instancia de la clase GestionCitasVentana
            gc.setValor(id); // Establece el valor de "id" en la instancia de GestionCitasVentana
            gc.setTipoUsu(tipoUsu); // Establece el valor de "tipoUsu" en la instancia de GestionCitasVentana
            gc.modificarDiseño(); // Modifica el diseño de la ventana de GestionCitasVentana
            gc.comprobarTabla(); // Comprueba la tabla en la ventana de GestionCitasVentana
            gc.setVisible(true); // Hace visible la ventana de GestionCitasVentana
        } else {
            // Si el texto del elemento de menú no contiene la subcadena "Consultar citas"
            JOptionPane.showMessageDialog(null, "Mostrando datos del perfil"); // Muestra un mensaje de diálogo indicando que se están mostrando los datos del perfil
            mostrarTablaDatos(); // Llama al método "mostrarTablaDatos" para mostrar la tabla de datos del perfil
        }

    }//GEN-LAST:event_menuConsultasMouseClicked

    /**
     * Método para mostrar una tabla con los datos del usuario.
     *
     * @throws HeadlessException Si se produce un error de encabezado.
     */
    private void mostrarTablaDatos() throws HeadlessException {
        consultas = new ConsultasPersonal();
        tablaDatosUsuario = new JTable();
        DefaultTableModel modelo = rellenarFilasTabla();
        consultas.realizarConexion();
        llenarTabla(modelo);
        // Agregar el MouseMotionListener para mostrar el tooltip con el valor completo
        tablaDatosUsuario.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tablaDatosUsuario.rowAtPoint(e.getPoint());
                if (row > -1) {
                    StringBuilder tooltip = new StringBuilder();
                    for (int col = 0; col < tablaDatosUsuario.getColumnCount(); col++) {
                        Object value = tablaDatosUsuario.getValueAt(row, col);
                        tooltip.append(tablaDatosUsuario.getColumnName(col)).append(": ").append(value).append("<br>");
                    }
                    tablaDatosUsuario.setToolTipText("<html>" + tooltip.toString() + "</html>");
                } else {
                    tablaDatosUsuario.setToolTipText(null);
                }
            }
        });

        // Crear el menú emergente para modificar el usuario
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Modificar");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultas.modificarUsuario(id);
                tablaDatosUsuario.removeAll();
                llenarTabla(null);
            }
        });
        popupMenu.add(menuItem);

        // Asociar el menú emergente a la tabla
        tablaDatosUsuario.setComponentPopupMenu(popupMenu);

        // Ajustar el ancho de las columnas al contenido
        tablaDatosUsuario.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaDatosUsuario.setRowHeight(tablaDatosUsuario.getRowHeight() + 5);

        // Ajustar el tamaño de las celdas al contenido
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
        tablaDatosUsuario.setDefaultRenderer(String.class, renderer);

        // Crear el JScrollPane para agregar el JTable
        JScrollPane scrollPane = new JScrollPane(tablaDatosUsuario);

        // Mostrar el JTable en el JOptionPane
        JOptionPane.showMessageDialog(null, scrollPane, "Datos del usuario", JOptionPane.INFORMATION_MESSAGE);
    }

    private void llenarTabla(DefaultTableModel modelo) {
        if (modelo == null) {
            modelo = rellenarFilasTabla();
        }
        try {
            String mostrarDatos = "SELECT id, nombre, apellidos, telefono, email, cuenta, contrasenia FROM usuario WHERE id=?";
            PreparedStatement psDatos = consultas.getCon().prepareStatement(mostrarDatos);
            if (id != 0) {
                // Establecer el valor del parámetro en la consulta preparada
                psDatos.setInt(1, id);

                // Ejecutar la consulta
                ResultSet resul = psDatos.executeQuery();

                // Iterar sobre los resultados y agregarlos al modelo de la tabla
                while (resul.next()) {
                    int idCliente = resul.getInt("id");
                    String nombre = resul.getString("nombre");
                    String apellidos = resul.getString("apellidos");
                    String telefono = String.valueOf(resul.getInt("telefono"));
                    String email = resul.getString("email");
                    String cuenta = resul.getString("cuenta");
                    String contrasenia = resul.getString("contrasenia");

                    // Crear un objeto Usuario con los datos obtenidos
                    usu = new Usuario(id, nombre, apellidos, Integer.parseInt(telefono), email, cuenta, contrasenia);

                    // Agregar los datos al modelo de la tabla
                    String fila[] = {nombre, apellidos, telefono, email, cuenta, contrasenia};
                    modelo.addRow(fila);
                }
                // Crear el JTable con el modelo de la tabla
                tablaDatosUsuario.setModel(modelo);
            } else {
                JOptionPane.showMessageDialog(null, "No hay datos que mostrar");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error mostrando datos");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "El dato introducido no es un numero");
        }
    }

    private DefaultTableModel rellenarFilasTabla() {
        // Nombres de las columnas
        String[] columnNames = {"Nombre", "Apellidos", "Teléfono", "Email", "Cuenta", "Contraseña"};
        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return modelo;
    }

    private void menuConsultasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuConsultasMouseEntered
        // Establecer el color del texto del menú de consultas como GRAY
        menuConsultas.setForeground(Color.GRAY);
        // Establecer el cursor del menú de consultas como un cursor de mano
        menuConsultas.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_menuConsultasMouseEntered

    private void menuConsultasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuConsultasMouseExited
        // Establecer el color del texto del menú de consultas como BLACK
        menuConsultas.setForeground(Color.BLACK);
    }//GEN-LAST:event_menuConsultasMouseExited

    private void cerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarSesionMouseClicked
        // TODO add your handling code here:
// Comprobación si el texto del botón cerrarSesion contiene "Cerrar sesión"
        if (cerrarSesion.getText().contains("Cerrar sesión")) {
            // Mostrar un cuadro de diálogo de confirmación para cerrar sesión
            int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cerrar sesión?");
            // Verificar si se ha seleccionado "Sí" en el cuadro de diálogo de confirmación
            if (decision == JOptionPane.YES_OPTION) {
                // Cerrar la ventana actual
                dispose();
                // Crear una nueva instancia de VentanaLog y mostrarla
                VentanaLog vl = new VentanaLog();
                vl.setVisible(true);
            }
        } else {
            // Mostrar un mensaje de diálogo indicando que se está abriendo el menú de productos
            JOptionPane.showMessageDialog(null, "Abriendo el menu de productos...");
            // Cerrar la ventana actual
            dispose();
            // Crear una nueva instancia de GestionProductosVentana y configurar sus propiedades
            GestionProductosVentana gpv = new GestionProductosVentana();
            gpv.setId(id);
            gpv.setTipoUsu(tipoUsu);
            gpv.aniadirmenuPopUp();
            // Mostrar la ventana de gestión de productos
            gpv.setVisible(true);
        }

    }//GEN-LAST:event_cerrarSesionMouseClicked

    private void cerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarSesionMouseEntered
        // Establecer el color del texto del botón cerrarSesion como GRIS
        cerrarSesion.setForeground(Color.GRAY);
        // Establecer el cursor del botón cerrarSesion como CURSOR_MANO
        cerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_cerrarSesionMouseEntered

    private void cerrarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarSesionMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarSesionMouseExited

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraNav;
    private com.github.lgooddatepicker.components.CalendarPanel calendarPanel1;
    private javax.swing.JLabel cerrarSesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JMenu menuCitas;
    private javax.swing.JLabel menuConsultas;
    private javax.swing.JMenu menuDatosPersonales;
    private javax.swing.JPanel panelBienvenida;
    private javax.swing.JPanel panelOpciones;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra el panel oculto.
     */
    private boolean mostrarPanelOculto() {
        boolean devo = false;
        try {
            panelOpciones.setVisible(true); // Establece la visibilidad del panelOpciones como true para mostrarlo
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    /**
     * Vacia el panel de opciones.
     */
    private boolean vaciarPanelOpciones() {
        boolean devo = false;
        try {
            menuConsultas.setText("                    "); // Establece el texto del elemento de menú "menuConsultas" como una cadena vacía con espacios en blanco para vaciarlo visualmente
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
    }

    private boolean inicializarDatos() {
        boolean devo = false;
        try {
            empleado = Personal.obtenerPersonalPorId(usu.getId(), conexion);
        } catch (Exception e) {
            System.err.println("Error al inicializar datos: " + e.getMessage());
            devo = false;
        }
        return devo;
    }
}
