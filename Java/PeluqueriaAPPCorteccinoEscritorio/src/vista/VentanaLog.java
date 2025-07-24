/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import controlador.ConexionBD;
import controlador.Configuracion;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.util.logging.*;
import javax.swing.*;
import modelo.Usuario;
import styles.GestionProductosStyle;
import java.nio.charset.StandardCharsets;

/**
 * Clase VentanaLog (Representa el inicio de sesion) Es la ventana principal de
 * la aplicacion
 *
 * @author Mario
 */
public class VentanaLog extends JFrame {

    /**
     * Creates new form VentanaLog
     */
    private static Connection con;
    private Logger logger;
    private Usuario usu;
    private static String SAVEPATH = "";

    public VentanaLog() {
        initComponents();
        setIconImage(getIconImage());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Inicio de sesión");
        jCheckBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jCheckBox1.isSelected()) {
                    textoContrasenia.setEchoChar('\u0000');
                } else {
                    textoContrasenia.setEchoChar('\u2022');
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                realizarCopiaDeSeguridad();
            }

        });

    }

    @Override
    public Image getIconImage() {
        ImageIcon icono = new ImageIcon("src/images/iconoDeAppEscritorio.png");
        return icono.getImage();// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    /**
     * Realiza una copia de seguridad de la base de datos.
     */
    private static void realizarCopiaDeSeguridad() {
        // Obtener la fecha actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String fechaActual = dateFormat.format(new Date());

        if (isDatabaseEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo hacer copia de la base de datos. Esta vacía", "Copia de seguridad", JOptionPane.WARNING_MESSAGE);
        } else {
            // Nombre del archivo de copia de seguridad
            String nombreArchivoBackup = Configuracion.DB_NAME + "_" + fechaActual;
            SAVEPATH = "\"" + Configuracion.DB_FOLDER + "\\" + "" + nombreArchivoBackup + ".sql\"";
            String execudecmd = "mysqldump -u" + Configuracion.DB_USER + " -p" + Configuracion.DB_PASSWORD + " --database " + Configuracion.DB_NAME + " -r " + SAVEPATH;

            // Ruta completa del archivo de copia de seguridad
            String rutaArchivoBackup = nombreArchivoBackup;

            try {
                // Comando para realizar la copia de seguridad
                String comando = "mysql --user=" + Configuracion.DB_USER + " --password=" + Configuracion.DB_PASSWORD + " " + Configuracion.DB_NAME + " > " + rutaArchivoBackup;

                // Ejecutar el comando en el sistema operativo
                Process proceso = Runtime.getRuntime().exec(execudecmd);
                int resultado = proceso.waitFor();
                if (resultado == 0) {
                    JOptionPane.showMessageDialog(null, "Copia de seguridad creada correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al hacer copia de seguridad", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error al crear la copia de seguridad: " + e.getMessage());
            }
        }
    }

    private static void importarBaseDatos() {

        try {

            Connection conn = ConexionBD.conectarSinLogin();
            Statement stmt = conn.createStatement();
            String archivoSQL = "src/bd/bd_alcorteccino.sql";

            try (BufferedReader br = new BufferedReader(new FileReader(archivoSQL))) {
                StringBuilder sentencia = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    linea = linea.trim();
                    if (!linea.startsWith("--") && !linea.isEmpty()) {
                        if (linea.endsWith(";")) {
                            sentencia.append(linea, 0, linea.length() - 1);
                            String sqlStatement = sentencia.toString();

                            if (sqlStatement.startsWith("DELIMITER")) {
                                sentencia.setLength(0);
                                continue;
                            }

                            try {
                                stmt.executeUpdate(sqlStatement);
                            } catch (SQLException e) {
                            }

                            sentencia.setLength(0);
                        } else {
                            sentencia.append(linea);
                        }
                    }
                }
            }

            // Crear los triggers que presentaron errores
            crearTriggerActualizarFechaHorarios(stmt);
            crearTriggerControlFechaInsert(stmt);
            crearTriggerMantenerHorariosSemana(stmt);
            crearTriggerCodificarMD5(stmt);
            crearTriggerInsertarUsuario(stmt);

            JOptionPane.showMessageDialog(null, "Archivo SQL importado correctamente");
        } catch (Exception e) {
        }
    }

    private static void crearTriggerActualizarFechaHorarios(Statement stmt) {
        String triggerSQL = "DELIMITER //\n"
                + "CREATE TRIGGER control_fecha_insert BEFORE INSERT ON horario\n"
                + "FOR EACH ROW\n"
                + "BEGIN\n"
                + "IF NEW.fecha < CURDATE() THEN\n"
                + "SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede insertar un horario con fecha anterior a hoy';\n"
                + "END IF;\n"
                + "END //\n"
                + "DELIMITER ;";
        try {
            stmt.executeUpdate(triggerSQL);
        } catch (SQLException e) {
        }
    }

    private static void crearTriggerControlFechaInsert(Statement stmt) {
        String triggerSQL = "DELIMITER //\n"
                + "CREATE TRIGGER control_fecha_insert BEFORE INSERT ON horario\n"
                + "FOR EACH ROW\n"
                + "BEGIN\n"
                + "IF NEW.fecha < CURDATE() THEN\n"
                + "SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede insertar un horario con fecha anterior a hoy';\n"
                + "END IF;\n"
                + "END //\n"
                + "DELIMITER ;";
        try {
            stmt.executeUpdate(triggerSQL);
        } catch (SQLException e) {

        }
    }

    private static void crearTriggerMantenerHorariosSemana(Statement stmt) {
        String triggerSQL = "DELIMITER //"
                + "CREATE TRIGGER mantener_horarios_semana BEFORE INSERT ON horario "
                + "FOR EACH ROW "
                + "BEGIN "
                + "DECLARE fecha_inicio_semana_actual DATE; "
                + "DECLARE fecha_fin_semana_actual DATE; "
                + "DECLARE dia_semana_actual INT; "
                + "DECLARE hora_asignada TIME; "
                + "SET @fecha_actual = CURDATE(); "
                + "SET @fecha_inicio_semana_actual = @fecha_actual - INTERVAL WEEKDAY(@fecha_actual) DAY; "
                + "SET @fecha_fin_semana_actual = @fecha_inicio_semana_actual + INTERVAL 6 DAY; "
                + "SET @dia_semana_actual = WEEKDAY(NEW.fecha) + 1; "
                + "SELECT hora INTO hora_asignada FROM horarios_asignados "
                + "WHERE id_personal = NEW.id_personal "
                + "AND fecha_inicio_semana = @fecha_inicio_semana_actual "
                + "AND fecha_fin_semana = @fecha_fin_semana_actual "
                + "AND dia_semana = @dia_semana_actual; "
                + "IF hora_asignada IS NOT NULL THEN "
                + "SET NEW.hora = hora_asignada; "
                + "END IF; "
                + "END; //"
                + "DELIMITER ;";
        try {
            stmt.executeUpdate(triggerSQL);
        } catch (SQLException e) {

        }
    }

    private static void crearTriggerCodificarMD5(Statement stmt) {
        String triggerSQL = "CREATE TRIGGER codificar_md5 BEFORE INSERT ON usuario "
                + "FOR EACH ROW "
                + "BEGIN "
                + "SET NEW.contrasenia = MD5(NEW.contrasenia); "
                + "END";
        try {
            stmt.executeUpdate(triggerSQL);
        } catch (SQLException e) {
            ;
        }
    }

    private static void crearTriggerInsertarUsuario(Statement stmt) {
        String triggerSQL = "DELIMITER //\n"
                + "CREATE TRIGGER insertar_usuario AFTER INSERT ON usuario\n"
                + "FOR EACH ROW\n"
                + "BEGIN\n"
                + "IF NEW.tipo_de_usuario = 'Cliente' THEN\n"
                + "INSERT INTO cliente (id) VALUES (NEW.id);\n"
                + "ELSEIF NEW.tipo_de_usuario = 'Personal' THEN\n"
                + "INSERT INTO personal (id) VALUES (NEW.id);\n"
                + "END IF;\n"
                + "END //\n"
                + "DELIMITER ;";
        try {
            stmt.executeUpdate(triggerSQL);
        } catch (SQLException e) {

        }
    }

    private static String obtenerNombreTabla(String createTableStatement) {
        // Extraer el nombre de la tabla de la sentencia CREATE TABLE
        int startIndex = createTableStatement.indexOf("`") + 1;
        int endIndex = createTableStatement.indexOf("`", startIndex);
        return createTableStatement.substring(startIndex, endIndex);
    }

    private static boolean existeTabla(Connection conn, String tableName) throws SQLException {
        // Verificar si una tabla existe en la base de datos
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGeneral = new javax.swing.JPanel();
        panelTitulo = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        panelBotonera = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        botonAceptar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        botonRegistrarse = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        botonCargarDatos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        botonSalir = new javax.swing.JButton();
        panelLI = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelLD = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textoUsuario = new javax.swing.JTextField();
        textoContrasenia = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelGeneral.setBackground(new java.awt.Color(18, 189, 201));
        panelGeneral.setToolTipText("");
        panelGeneral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                panelGeneralKeyTyped(evt);
            }
        });
        panelGeneral.setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(4, 124, 204));
        panelTitulo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panelTitulo.setToolTipText("");
        panelTitulo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelTitulo.setFocusCycleRoot(true);

        titulo.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        titulo.setForeground(new java.awt.Color(1, 41, 38));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("PELUQUERIA AL-CORTECCINO");

        javax.swing.GroupLayout panelTituloLayout = new javax.swing.GroupLayout(panelTitulo);
        panelTitulo.setLayout(panelTituloLayout);
        panelTituloLayout.setHorizontalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
        );
        panelTituloLayout.setVerticalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTituloLayout.createSequentialGroup()
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelGeneral.add(panelTitulo, java.awt.BorderLayout.NORTH);

        panelBotonera.setBackground(new java.awt.Color(24, 173, 156));
        panelBotonera.setToolTipText("");
        panelBotonera.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(24, 173, 156));
        jPanel1.setToolTipText("");

        botonAceptar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonAceptar.setMnemonic(KeyEvent.VK_A);
        botonAceptar.setText("ACEPTAR");
        botonAceptar.setAlignmentY(1.5F);
        botonAceptar.setBorder(null);
        botonAceptar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAceptar.setMargin(new java.awt.Insets(14, 14, 3, 14));
        botonAceptar.setMinimumSize(new java.awt.Dimension(45, 22));
        botonAceptar.setName(""); // NOI18N
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });
        botonAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                botonAceptarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                botonAceptarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel1, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(24, 173, 156));
        jPanel2.setToolTipText("");

        botonRegistrarse.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonRegistrarse.setText("REGISTRATE");
        botonRegistrarse.setBorder(null);
        botonRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel2, new java.awt.GridBagConstraints());

        jPanel6.setBackground(new java.awt.Color(24, 173, 156));
        jPanel6.setToolTipText("");

        botonCargarDatos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonCargarDatos.setText("CARGAR DATOS");
        botonCargarDatos.setBorder(null);
        botonCargarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCargarDatosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonCargarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonCargarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel6, new java.awt.GridBagConstraints());

        jPanel3.setBackground(new java.awt.Color(24, 173, 156));

        botonSalir.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonSalir.setText("SALIR");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel3, new java.awt.GridBagConstraints());

        panelGeneral.add(panelBotonera, java.awt.BorderLayout.SOUTH);

        panelLI.setBackground(new java.awt.Color(18, 189, 201));
        panelLI.setToolTipText("");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconoPeluqueria.png"))); // NOI18N

        javax.swing.GroupLayout panelLILayout = new javax.swing.GroupLayout(panelLI);
        panelLI.setLayout(panelLILayout);
        panelLILayout.setHorizontalGroup(
            panelLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );
        panelLILayout.setVerticalGroup(
            panelLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );

        panelGeneral.add(panelLI, java.awt.BorderLayout.WEST);
        panelLI.getAccessibleContext().setAccessibleName("");

        panelLD.setBackground(new java.awt.Color(18, 189, 201));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/peine.png"))); // NOI18N
        jLabel3.setToolTipText("");
        jLabel3.setAlignmentY(0.0F);

        javax.swing.GroupLayout panelLDLayout = new javax.swing.GroupLayout(panelLD);
        panelLD.setLayout(panelLDLayout);
        panelLDLayout.setHorizontalGroup(
            panelLDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLDLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLDLayout.setVerticalGroup(
            panelLDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );

        panelGeneral.add(panelLD, java.awt.BorderLayout.EAST);

        panelCentral.setBackground(new java.awt.Color(18, 189, 201));
        panelCentral.setToolTipText("");
        panelCentral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(18, 189, 201));
        jPanel4.setToolTipText("");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tijera.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );

        panelCentral.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 160, 158));

        jPanel5.setBackground(new java.awt.Color(18, 189, 201));
        jPanel5.setToolTipText("");
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CONTRASEÑA:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 150, 46));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("USUARIO:");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, 46));

        textoUsuario.setForeground(new java.awt.Color(204, 204, 204));
        textoUsuario.setText("Introduce usuario");
        textoUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textoUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textoUsuarioFocusLost(evt);
            }
        });
        textoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoUsuarioKeyTyped(evt);
            }
        });
        jPanel5.add(textoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 220, 30));

        textoContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoContraseniaKeyTyped(evt);
            }
        });
        jPanel5.add(textoContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 190, 30));

        panelCentral.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 202, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(18, 189, 201));
        jCheckBox1.setText("mostrarContraseña");
        panelCentral.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 130, -1));

        panelGeneral.add(panelCentral, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:
        if (textoUsuario.getText().isEmpty() || textoUsuario.getText().contains("Introduce usuario")) {
            JOptionPane.showMessageDialog(null, "Usuario incompleto");
        } else if (textoContrasenia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Contraseña inválida");
        } else {
            iniciarSesion();
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonAceptarKeyPressed

    }//GEN-LAST:event_botonAceptarKeyPressed

    private void botonRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarseActionPerformed
        // TODO add your handling code here:
        irARegistro();
    }//GEN-LAST:event_botonRegistrarseActionPerformed

    private void textoUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textoUsuarioFocusGained
        // TODO add your handling code here:
        if (textoUsuario.getText().equals("Introduce usuario")) {
            textoUsuario.setText("");
            textoUsuario.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_textoUsuarioFocusGained

    private void textoUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textoUsuarioFocusLost
        // TODO add your handling code here:
        if (textoUsuario.getText().isEmpty()) {
            textoUsuario.setForeground(Color.GRAY);
            textoUsuario.setText("Introduce usuario");
        }
    }//GEN-LAST:event_textoUsuarioFocusLost

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        // TODO add your handling code here:
        realizarCopiaDeSeguridad();
        System.exit(0);
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonAceptarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonAceptarKeyTyped
        char cTeclapresionada = evt.getKeyChar();

        if (cTeclapresionada == KeyEvent.VK_ENTER) {
            botonAceptar.doClick();
        }
    }//GEN-LAST:event_botonAceptarKeyTyped

    private void textoUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoUsuarioKeyTyped
        char cTeclapresionada = evt.getKeyChar();

        if (cTeclapresionada == KeyEvent.VK_ENTER) {
            botonAceptar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoUsuarioKeyTyped

    private void textoContraseniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoContraseniaKeyTyped
        char cTeclapresionada = evt.getKeyChar();

        if (cTeclapresionada == KeyEvent.VK_ENTER) {
            botonAceptar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoContraseniaKeyTyped

    private void panelGeneralKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelGeneralKeyTyped

    }//GEN-LAST:event_panelGeneralKeyTyped

    private void botonCargarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCargarDatosActionPerformed
        try {
            // TODO add your handling code here:
            checkAndImportDatabase();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_botonCargarDatosActionPerformed
    public static void checkAndImportDatabase() throws IOException, SQLException {
        if (isDatabaseEmpty() || isDatabaseCorrupted()) {
            renameAndImportLatestBackup();
            importarBaseDatos();
        }

    }

    private static boolean isDatabaseEmpty() {
        // Lógica para verificar si la base de datos está vacía
        boolean isEmpty = false;
        // Conexión a la base de datos
        Connection connection = null;
        try {
            connection = ConexionBD.conectarSinLogin();
            // Consulta SQL para contar el número de registros en una tabla
            String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'bd_alcorteccino';";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isEmpty = (count == 0);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return isEmpty;
    }

    private static boolean isDatabaseCorrupted() {
        // Lógica para verificar si la base de datos está corrupta
        boolean isCorrupted = false;

        // Conexión a la base de datos
        Connection connection = null;
        try {
            connection = ConexionBD.conectarSinLogin();

            // Consulta SQL para realizar una operación que identifique la corrupción
            // Puedes realizar una consulta específica según tu SGBD
            // Ejemplo: Verificar la existencia de una tabla particular
            String query = "SELECT 1";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            isCorrupted = !resultSet.next(); // Si no hay resultados, se considera que la base de datos está corrupta

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Base de datos corrupta o no existente", "Error", JOptionPane.ERROR_MESSAGE);
            isCorrupted = true; // Se considera que la base de datos está corrupta si hay una excepción SQL
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return isCorrupted;
        // Devuelve true si está corrupta, false en caso contrario

    }

    private static void renameAndImportLatestBackup() {
        File folder = new File(Configuracion.DB_FOLDER);
        FilenameFilter filter = (dir, name) -> name.startsWith(Configuracion.DB_NAME);

        File[] backupFiles = folder.listFiles(filter);
        if (backupFiles == null || backupFiles.length == 0) {
            System.out.println("No se encontraron archivos de respaldo de la base de datos.");
            return;
        }

        Arrays.sort(backupFiles, Comparator.comparing(File::lastModified).reversed());

        File latestBackup = backupFiles[0];
        String newFileName = getNewFileName(latestBackup.getName());

        String sobreescribirArchivo = "bd_alcorteccino.sql";
        File nuevoArchivo = new File(sobreescribirArchivo);
        Path sourcePath = latestBackup.toPath();
        Path targetPath = new File(Configuracion.DB_FOLDER + File.separator + nuevoArchivo).toPath();

        try {
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo renombrado y movido con éxito.");

            // Lógica para importar el archivo a la base de datos
        } catch (IOException e) {
            System.out.println("Error al renombrar y mover el archivo: " + e.getMessage());
        }
    }

    private static String getNewFileName(String oldFileName) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date currentDate = new Date();

        String formattedDate = dateFormat.format(currentDate);
        String newFileName = oldFileName.split("\\.")[0] + "_" + formattedDate + ".sql";

        return newFileName;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        con = ConexionBD.conectarSinLogin();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaLog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCargarDatos;
    private javax.swing.JButton botonRegistrarse;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel panelBotonera;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPanel panelLD;
    private javax.swing.JPanel panelLI;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JPasswordField textoContrasenia;
    private javax.swing.JTextField textoUsuario;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    private boolean iniciarSesion() {
        boolean devo = false;
        String passwd, contraseniaMD5, usuario, contrasenia;
        try {
            usuario = textoUsuario.getText();
            char[] passwordChars = textoContrasenia.getPassword();
            contrasenia= new String(passwordChars);
            passwd = obtenerContrasenia(usuario);
            contraseniaMD5 = calcularMD5(contrasenia);
            if (contraseniaMD5.contains(passwd)) {
                usu = Usuario.buscarPorCuentaYContrasenia(usuario, contraseniaMD5, con);
                if (usu == null) {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña no validos");
                    textoContrasenia.setText("");
                    textoUsuario.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usu.getNombre());
                    try {
                        if (usu.getTipo_de_usuario().contains("Personal")) {
                            this.dispose();
                            VentanaPrincipal vc = new VentanaPrincipal();
                            vc.setValor(usu.getId());
                            vc.setTipoUsu(usu.getTipo_de_usuario());
                            vc.setVisible(true);
                            devo = true;
                        } else {
                            throw new ClassCastException("Esta aplicación solo la puede utilizar el personal. Pruebe nuestra aplicación Android");
                        }
                    } catch (ClassCastException cse) {
                        JOptionPane.showMessageDialog(null, cse.getMessage());
                    }
                }
                con.close();
            }
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Error intentando conectar a la base de datos");
            devo = false;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hubo un problema con la base de datos.");
            devo = false;
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "Falló la comprobación de contraseña");
            devo = false;
        }
        return devo;
    }

    /**
     *
     * @param texto
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String calcularMD5(String texto) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(texto.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private void irARegistro() {
        VentanaRegistro vr = new VentanaRegistro();
        vr.setVisible(true);
    }

    /**
     *
     * @param usuario
     * @return
     */
    private String obtenerContrasenia(String usuario) {
        String contrasenia = null;
        Usuario usuarioEncontrado = null;
        try {
            usuarioEncontrado = Usuario.buscarPorCuenta(usuario, con);
            contrasenia = usuarioEncontrado.getContrasenia();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en la conexion.");
        }
        return contrasenia;
    }
}
