package CalculadoraBásica;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Calculadora es una clase que implementa una calculadora básica en modo consola.
 * Permite realizar operaciones de suma, resta, multiplicación, división y potenciación.
 * Utiliza el Objeto BufferedReader para leer la entrada del usuario y maneja excepciones para
 * evitar errores comunes como entradas no numéricas y división por cero.
 * @author MarioKiller44
 * @version 1.0.3
 * @since 2023-10-01
 */
public class Calculadora {

    /**
     * Este atributo se utiliza para controlar el flujo del programa y determinar qué operación realizar.
     * Atributo que almacena la opción seleccionada por el usuario.
     * Inicialmente se establece en -1 para indicar que no se ha seleccionado ninguna opción.
     * 
     */
    private static int opcion = -1;

    /**
     * Método principal que inicia la ejecución del programa.
     * Maneja excepciones para evitar errores comunes y proporciona un menú interactivo.
     * @version 1.0.1
     * @param args Argumentos de la línea de comandos 
     * @return void
     * @throws Exception Si ocurre un error al leer la entrada del usuario o al procesar
     * las operaciones, se captura y muestra un mensaje de error.
     */
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hola chicos, que tal. Este es mi primer programa de Java hecho en Visual Studio Code\n");
        boolean devo = false;
        while (devo != true) {
            /**
             * Bloque try-catch para manejar excepciones al leer la entrada del usuario.
             */ 
            try {

                System.out.println("Elige una opción de esta calculadora\n");
                System.out.println("1: Sumar\n2: Restar\n3: Multiplicar\n4: Dividir\n5: Potenciar\n6: Salir");
                System.out.print("Elija: ");
                String texto = bufferedReader.readLine();

                opcion = Integer.parseInt(texto);
                switch (opcion) {
                    case 1:
                        System.out.println("¡De acuerdo, vamos a sumar!");
                        suma();
                        break;
                    case 2:
                        System.out.println("¡De acuerdo, vamos a restar!");
                        resta();
                        break;
                    case 3:
                        System.out.println("¡De acuerdo, vamos a multiplicar!");
                        multiplicar();
                        break;
                    case 4:
                        System.out.println("¡De acuerdo, vamos a dividir!");
                        dividir();
                        break;
                    case 5:
                        System.out.println("¡De acuerdo, vamos a potenciar!");
                        potenciar();
                        break;
                    case 6:
                        System.out.println("¡De acuerdo, vamos a salir!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opción equivocada");
                        devo = false;
                        break;

                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error, con el siguiente mensaje: " + e.getLocalizedMessage());

            }
        }
    }
//<editor-fold desc="Métodos de operaciones matemáticas" collapsed="true">
    private static boolean suma() {
        boolean devoSuma = false;
        ArrayList<Double> numeroList = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        boolean devoNum = false;
        Double numeroResultante = 0.0;
        String respuesta;
        try {
            while (!devoNum) {
                System.out.println("Introduce número a sumar:");
                numeroList.add(Double.parseDouble(bf.readLine()));
                if (numeroList.size() > 1) {
                    System.out.println("¿Seguimos sumando más? (Sí/No)");
                    respuesta = bf.readLine();
                    if (respuesta.equalsIgnoreCase("No")) {
                        devoNum = true;
                    }
                }
            }
            for (Double num : numeroList) {
                numeroResultante += num;
            }
            System.out.println("El resultado es: " + numeroResultante);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la suma, con el siguiente mensaje: " + e.getLocalizedMessage());
        }
        return devoSuma;
    }

    private static boolean resta() {
        boolean devoResta = false;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Introduce el primer número:");
            double num1 = Double.parseDouble(bf.readLine());
            System.out.println("Introduce el segundo número:");
            double num2 = Double.parseDouble(bf.readLine());
            double resultado = num1 - num2;
            System.out.println("El resultado es: " + resultado);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la resta, con el siguiente mensaje: " + e.getLocalizedMessage());
        }
        return devoResta;
    }

    private static boolean multiplicar() {
        boolean devoMultiplicar = false;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Introduce el primer número:");
            double num1 = Double.parseDouble(bf.readLine());
            System.out.println("Introduce el segundo número:");
            double num2 = Double.parseDouble(bf.readLine());
            double resultado = num1 * num2;
            System.out.println("El resultado es: " + resultado);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la multiplicación, con el siguiente mensaje: " + e.getLocalizedMessage());
        }
        return devoMultiplicar;
    }

    private static boolean dividir() {
        boolean devoDividir = false;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Introduce el dividendo:");
            double num1 = Double.parseDouble(bf.readLine());
            System.out.println("Introduce el divisor:");
            double num2 = Double.parseDouble(bf.readLine());
            if (num2 == 0) {
                System.out.println("No se puede dividir por cero.");
            } else {
                double resultado = num1 / num2;
                System.out.println("El resultado es: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la división, con el siguiente mensaje: " + e.getLocalizedMessage());
        }
        return devoDividir;
    }

    private static boolean potenciar() {
        boolean devoPotenciar = false;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Introduce la base:");
            double base = Double.parseDouble(bf.readLine());
            System.out.println("Introduce el exponente:");
            double exponente = Double.parseDouble(bf.readLine());
            double resultado = Math.pow(base, exponente);
            System.out.println("El resultado es: " + resultado);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la potenciación, con el siguiente mensaje: " + e.getLocalizedMessage());
        }
        return devoPotenciar;
    }
//</editor-fold>

}