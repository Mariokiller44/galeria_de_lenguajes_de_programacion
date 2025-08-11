/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 * Ventana para gestionar los productos
 *
 * @author Mario
 */
import com.formdev.flatlaf.FlatLightLaf;
import controlador.ConexionBD;
import controlador.ConsultasPersonal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import modelo.Personal;
import modelo.Producto;
import modelo.Usuario;
import styles.GestionProductosStyle;

public class GestionProductosVentana extends javax.swing.JFrame {

    /**
     * Declaracion de atributos
     */
    private DefaultTableModel modeloTabla;
    private ConsultasPersonal consultas;
    private static Connection con;
    private JPopupMenu popupMenu = new JPopupMenu();
    private VentanaPrincipal ventanaPrincipal;
    private Usuario usuarioActivo;
    private Personal personalActivo;

    private GestionProductosVentana() {
        initComponents();
        setIconImage(getIconImage());
        setTitle("Gestión de Productos");
        // Establecer el título de la ventana
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Cantidad"}, 0);
        scrollPane.getViewport().setBackground(new Color(186, 179, 179)); // Establecer color de fondo del panel de desplazamiento
        personalActivo = Personal.obtenerPersonalPorId(1, con);//Usar el administrador por defecto
        // Personalizar el renderizador de encabezado para cambiar el color de los títulos
        JTableHeader header = productosTable.getTableHeader();
        header.setBackground(new Color(74, 159, 255));
        header.setForeground(Color.WHITE);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) productosTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setFont(new Font("Arial", Font.BOLD, 14));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Configurar el comportamiento al cerrar la ventana
        setIconImage(getIconImage()); // Establecer la imagen del ícono de la ventana
        setResizable(false); // Deshabilitar la capacidad de redimensionar la ventana

        setLocationRelativeTo(null); // Mostrar la ventana en el centro de la pantalla

        cargarProductos(); // Cargar los productos en la tabla

        ventanaPrincipal = new VentanaPrincipal(); // Crear una instancia de la ventana principal

        // Agregar un WindowListener para controlar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                if (decision == JOptionPane.YES_OPTION) {
                    dispose(); // Cerrar la ventana actual
                    ventanaPrincipal.setVisible(true); // Mostrar la ventana principal
                }
            }
        });
        aniadirmenuPopUp();
    }

    /**
     * Creates new form GestionProductosVentanaFrame
     */
    public GestionProductosVentana(Usuario usu, Connection conexion) {
        initComponents();
        setIconImage(getIconImage());
        setTitle("Gestión de Productos");
        // Establecer el título de la ventana
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Cantidad"}, 0);
        scrollPane.getViewport().setBackground(new Color(186, 179, 179)); // Establecer color de fondo del panel de desplazamiento
        con = conexion;
        usuarioActivo = usu;
        // Personalizar el renderizador de encabezado para cambiar el color de los títulos
        JTableHeader header = productosTable.getTableHeader();
        header.setBackground(new Color(74, 159, 255));
        header.setForeground(Color.WHITE);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) productosTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setFont(new Font("Arial", Font.BOLD, 14));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Configurar el comportamiento al cerrar la ventana
        setIconImage(getIconImage()); // Establecer la imagen del ícono de la ventana
        setResizable(false); // Deshabilitar la capacidad de redimensionar la ventana

        setLocationRelativeTo(null); // Mostrar la ventana en el centro de la pantalla

        cargarProductos(); // Cargar los productos en la tabla

        ventanaPrincipal = new VentanaPrincipal(); // Crear una instancia de la ventana principal

        // Agregar un WindowListener para controlar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                if (decision == JOptionPane.YES_OPTION) {
                    dispose(); // Cerrar la ventana actual
                    ventanaPrincipal.setVisible(true); // Mostrar la ventana principal
                }
            }
        });
        aniadirmenuPopUp();
    }

    @Override
    public Image getIconImage() {
        ImageIcon icono = new ImageIcon("src/images/iconoDeAppEscritorio.png");
        return super.getIconImage(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        agregarButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        eliminarButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        salirButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        productosTable = new JTable(modeloTabla){
            @Override
            public boolean isCellEditable(int row, int columns){
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        agregarButton.setForeground(new java.awt.Color(0, 51, 51));
        agregarButton.setText("Agregar Producto");
        agregarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(agregarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(agregarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new java.awt.GridBagConstraints());

        jPanel3.setPreferredSize(new java.awt.Dimension(139, 25));

        eliminarButton.setForeground(new java.awt.Color(0, 51, 51));
        eliminarButton.setText("Eliminar Producto");
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(eliminarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(eliminarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new java.awt.GridBagConstraints());

        jPanel4.setPreferredSize(new java.awt.Dimension(123, 25));

        salirButton.setForeground(new java.awt.Color(0, 51, 51));
        salirButton.setText("Atrás");
        salirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salirButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(salirButton))
        );

        jPanel1.add(jPanel4, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        scrollPane.setToolTipText("");

        scrollPane.setViewportView(productosTable);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarButtonActionPerformed
        // TODO add your handling code here:
        agregarProducto();
    }//GEN-LAST:event_agregarButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        // TODO add your handling code here:
        eliminarProducto();
    }//GEN-LAST:event_eliminarButtonActionPerformed

    private void salirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirButtonActionPerformed
        // TODO add your handling code here:
        confirmarSalir();
    }//GEN-LAST:event_salirButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarButton;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTable productosTable;
    private javax.swing.JButton salirButton;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
        if (nombre != null && !nombre.isEmpty()) {
            String stockStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad de stock:");
            if (stockStr != null && !stockStr.isEmpty()) {
                try {
                    int stock = Integer.parseInt(stockStr);

                    // Agregar el nuevo producto a la tabla y a la base de datos
                    modeloTabla.addRow(new Object[]{nombre, stock});
                    consultas.realizarConexion();
                    con = consultas.getCon();
                    PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTOS (NOMBRE, STOCK) VALUES (?, ?)");
                    statement.setString(1, nombre);
                    statement.setInt(2, stock);
                    statement.executeUpdate();
                    statement.close();
                    con.close();
                } catch (NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al agregar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Este método añade un menú emergente a la tabla de productos.
     */
    public void aniadirmenuPopUp() {
        // Crear el menú emergente "modificarCantidad"
        if (popupMenu.getComponentCount() == 0) {
            // Crear el elemento de menú "Modificar Cantidad"
            JMenuItem modificarCantidadItem = new JMenuItem("Modificar Cantidad");
            modificarCantidadItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener la fila seleccionada en la tabla
                    int filaSeleccionada = productosTable.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Obtener el nombre y la cantidad actual del producto seleccionado
                        String nombreProducto = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
                        String cantidadActualStr = String.valueOf(modeloTabla.getValueAt(filaSeleccionada, 1));

                        // Mostrar un cuadro de diálogo para ingresar la nueva cantidad
                        String nuevaCantidadStr = JOptionPane.showInputDialog("Ingrese la nueva cantidad para el producto \"" + nombreProducto + "\":", cantidadActualStr);
                        if (nuevaCantidadStr != null) {
                            try {
                                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                                // Lógica para actualizar el campo STOCK del producto con la nueva cantidad en la base de datos
                                if (nuevaCantidad < 0) {
                                    throw new NumberFormatException("No se puede insertar una cantidad menor a 0");
                                } else {
                                    // Actualizar la cantidad en la tabla y en la base de datos
                                    modeloTabla.setValueAt(nuevaCantidadStr, filaSeleccionada, 1);
                                    Producto p = new Producto(con);
                                    p.setNombre(nombreProducto);
                                    p.inicializarDesdeBD();
                                    actualizarStockProducto(p.getId(), nuevaCantidad);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                JOptionPane.showMessageDialog(GestionProductosVentana.this, "Ingrese un valor numérico válido", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });

            // Agregar el elemento de menú al menú emergente
            popupMenu.add(modificarCantidadItem);
        } else if (popupMenu.getComponentCount() == 1) {
            // Crear el elemento de menú "Modificar Nombre"
            JMenuItem modificarNombre = new JMenuItem("Modificar Nombre");
            modificarNombre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener la fila seleccionada en la tabla
                    int filaSeleccionada = productosTable.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Obtener el nombre del producto seleccionado
                        String nombreProducto = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

                        // Mostrar un cuadro de diálogo para ingresar el nuevo nombre
                        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto: ");
                        if (nuevoNombre != null) {
                            if (esNombreValido(nuevoNombre)) {
                                // Actualizar el nombre en la tabla y en la base de datos
                                modeloTabla.setValueAt(nuevoNombre, filaSeleccionada, 0);
                                Producto p = new Producto(con);
                                p.setNombre(nuevoNombre);
                                p.inicializarDesdeBD();
                                p.actualizar();
                            } else {
                                JOptionPane.showMessageDialog(null, "El nombre ingresado contiene números y no es válido.");
                            }
                        }
                    }
                }
            });

            // Obtener el tipo de usuario
            if (usuarioActivo.getApellidos().equals("Administrador")) {
                // Agregar el elemento de menú al menú emergente si el tipo de usuario es "Administrador"
                popupMenu.add(modificarNombre);
            }
        }

        // Asignar el menú emergente a la tabla de productos
        productosTable.setComponentPopupMenu(popupMenu);
    }

    /**
     * Este método verifica si un nombre es válido, es decir, si no contiene
     * números.
     *
     * @param nuevoNombre El nombre a verificar.
     * @return true si el nombre es válido, false si contiene números.
     */
    private boolean esNombreValido(String nuevoNombre) {
        // Verificar si el nombre contiene números
        for (char c : nuevoNombre.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Actualiza el stock de un producto en la base de datos.
     *
     * @param idProducto El ID del producto a actualizar.
     * @param nuevaCantidad La nueva cantidad de stock del producto.
     */
    private boolean actualizarStockProducto(int idProducto, int nuevaCantidad) {
        boolean devo = false;
        try {
            Producto productoActual = Producto.buscarPorId(idProducto, con);
            Producto productoNuevo = new Producto(idProducto, productoActual.getNombre(), nuevaCantidad, con);
            if (productoNuevo.getStock() < 2) {
                JOptionPane.showMessageDialog(null, "Se está agotando el stock", "Aviso de falta de Stock", JOptionPane.WARNING_MESSAGE);
            }
            if (productoNuevo.actualizar()) {
                JOptionPane.showMessageDialog(this, "Stock actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el stock");
            }
            devo = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock del producto" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            devo = false;
        }
        return devo;
    }

    /**
     * Carga los productos y los muestra en la tabla.
     */
    private boolean cargarProductos() {
        // Limpia la tabla antes de cargar los productos
        boolean devo = false;

        ArrayList<Producto> listaProductos = new ArrayList<>();
        try {
            modeloTabla.setRowCount(0);

            // Realizar consulta
            listaProductos = Producto.buscarTodos(con);
            // Llenar la tabla con los resultados de la consulta
            for (Producto producto : listaProductos) {
                String nombre = producto.getNombre();
                int stock = producto.getStock();
                modeloTabla.addRow(new Object[]{nombre, stock});
            }
            productosTable.setModel(modeloTabla);
            devo = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return devo;
    }

    /**
     * Elimina un producto de la tabla y de la base de datos.
     */
    private void eliminarProducto() {
        int selectedRow = productosTable.getSelectedRow();
        try {
            if (selectedRow != -1) {
                String nombre = (String) modeloTabla.getValueAt(selectedRow, 0);
                Producto producto = (Producto) modeloTabla.getValueAt(selectedRow, 0);
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el producto '" + nombre + "'?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    // Eliminar el producto de la tabla y de la base de datos
                    modeloTabla.removeRow(selectedRow);
                    producto.eliminar();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Comprueba el tipo de usuario y habilita/deshabilita los botones según
     * corresponda.
     */
    private boolean comprobarTipoUsuario() {
        boolean devo = false;
        try {
            if (!usuarioActivo.getApellidos().equals("Administrador")) {
                agregarButton.setEnabled(false);
                eliminarButton.setEnabled(false);
            } else {
                agregarButton.setEnabled(true);
                eliminarButton.setEnabled(true);
            }
            devo = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en la configuracion. " + e.getMessage());
        }
        return devo;
    }

    private boolean confirmarSalir() {
        boolean devo = false;
        try {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir de la ventana?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion == JOptionPane.YES_OPTION) {
                // Restablecer el Look and Feel a la configuración predeterminada
                try {

                    // Cerrar la ventana
                    ventanaPrincipal = new VentanaPrincipal(usuarioActivo, con);
                    ventanaPrincipal.setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al salir", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en la configuracion. " + e.getMessage());
        }
        return devo;
    }
}
