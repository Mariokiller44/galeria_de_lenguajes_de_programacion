package CalculadoraBásica;
import javax.swing.*;
import java.awt.*;

/*
 * CalculadoraInterfaz es la interfaz gráfica para una calculadora que contiene varias funciones:
 * suma, resta, multiplicación, división y potenciación.
 * Utiliza Layout de Rejillas para organizar los componentes y tiene un diseño responsivo.
 * Los botones realizan las operaciones correspondientes y muestran el resultado en una etiqueta.
 * Maneja excepciones para que cuando pulse un botón y no haya nada no se cuelgue, ademas de
 * entradas no numéricas y división por cero.
 * @author MarioKiller44
 * @version 1.0.3
 * @since 2023-10-01
 */

public class CalculadoraInterfaz extends JFrame {

    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel etiquetaResultado;

    public CalculadoraInterfaz() {
        setTitle("Calculadora Académica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 350));
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel principal con fondo y borde redondeado
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 248, 255)); // Azul muy claro
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        /**
         * El panel tiene un fondo azul claro y bordes redondeados para un aspecto moderno.
         * Utiliza la clase Graphics2D para dibujar un rectángulo redondeado que sirve
         * como fondo del panel.
         * @author MarioKiller44
         * @version 1.0.1
         * @since 2023-10-02
         */
        panel.setOpaque(false);

        JLabel etiqueta1 = new JLabel("Número 1:");
        etiqueta1.setFont(new Font("Arial", Font.BOLD, 16));
        campoNumero1 = new JTextField(10);
        campoNumero1.setFont(new Font("Arial", Font.PLAIN, 16));
        campoNumero1.setToolTipText("Introduce el primer número");

        JLabel etiqueta2 = new JLabel("Número 2:");
        etiqueta2.setFont(new Font("Arial", Font.BOLD, 16));
        campoNumero2 = new JTextField(10);
        campoNumero2.setFont(new Font("Arial", Font.PLAIN, 16));
        campoNumero2.setToolTipText("Introduce el segundo número");

        etiquetaResultado = new JLabel("Resultado: ");
        etiquetaResultado.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaResultado.setForeground(new Color(25, 25, 112));

        // Botones decorados
        JButton botonSumar = crearBoton("Sumar", new Color(144, 238, 144));
        JButton botonRestar = crearBoton("Restar", new Color(255, 182, 193));
        JButton botonMultiplicar = crearBoton("Multiplicar", new Color(173, 216, 230));
        JButton botonDividir = crearBoton("Dividir", new Color(255, 255, 153));
        JButton botonPotenciar = crearBoton("Potenciar", new Color(221, 160, 221));

        botonSumar.setToolTipText("Suma los dos números");
        botonRestar.setToolTipText("Resta el segundo al primero");
        botonMultiplicar.setToolTipText("Multiplica los dos números");
        botonDividir.setToolTipText("Divide el primero entre el segundo");
        botonPotenciar.setToolTipText("Eleva el primero al segundo");

        // Añadir componentes al panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(etiqueta1, gbc);
        gbc.gridx = 1;
        panel.add(campoNumero1, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(etiqueta2, gbc);
        gbc.gridx = 1;
        panel.add(campoNumero2, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(etiquetaResultado, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(botonSumar, gbc);
        gbc.gridx = 1;
        panel.add(botonRestar, gbc);
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(botonMultiplicar, gbc);
        gbc.gridx = 1;
        panel.add(botonDividir, gbc);
        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(botonPotenciar, gbc);

        // Responsive: el panel se adapta al tamaño de la ventana
        add(panel, new GridBagConstraints());

        // Acciones de los botones
        botonSumar.addActionListener(e -> operar("sumar"));
        botonRestar.addActionListener(e -> operar("restar"));
        botonMultiplicar.addActionListener(e -> operar("multiplicar"));
        botonDividir.addActionListener(e -> operar("dividir"));
        botonPotenciar.addActionListener(e -> operar("potenciar"));
    }

    // Método para crear botones decorados
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        boton.setPreferredSize(new Dimension(140, 35));
        return boton;
    }

    private void operar(String operacion) {
        try {
            double num1 = Double.parseDouble(campoNumero1.getText());
            double num2 = Double.parseDouble(campoNumero2.getText());
            double resultado = 0;
            switch (operacion) {
                case "sumar": resultado = num1 + num2; break;
                case "restar": resultado = num1 - num2; break;
                case "multiplicar": resultado = num1 * num2; break;
                case "dividir":
                    if (num2 == 0) {
                        etiquetaResultado.setText("Resultado: No se puede dividir por cero");
                        return;
                    }
                    resultado = num1 / num2; break;
                case "potenciar": resultado = Math.pow(num1, num2); break;
            }
            etiquetaResultado.setText("Resultado: " + resultado);
        } catch (NumberFormatException ex) {
            etiquetaResultado.setText("Resultado: Error en los datos");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraInterfaz().setVisible(true);
        });
    }
}
