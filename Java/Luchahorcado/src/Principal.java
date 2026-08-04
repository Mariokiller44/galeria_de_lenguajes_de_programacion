import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase Principal.
 * 
 * @author Mario Escribano
 * @version 1.0.0
 * @since 2026-08-04
 */
public class Principal {

    // importante: el número máximo de errores permitidos antes de perder el juego.
    private static final int MAX_ERRORS = 6;

    private static final String RUTA_JSON =
            "luchadores_famosos.json";

    /**
     * Método principal que inicia el juego de adivinanza de luchadores.
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Se intenta cargar la lista de luchadores desde un archivo JSON.
         * Si la lista está vacía, se informa al usuario y se termina el programa.
         * Si se cargan luchadores, se selecciona uno al azar y se inicia el juego de adivinanza.
         */
        try {
            ArrayList<Luchadores> listaLuchadores =
                    Luchadores.cargarLuchadores(RUTA_JSON);

            if (listaLuchadores.isEmpty()) {
                System.out.println(
                        "No se encontraron luchadores en " + RUTA_JSON
                );
                return;
            }

            Random random = new Random();

            Luchadores elegido =
                    listaLuchadores.get(
                            random.nextInt(listaLuchadores.size())
                    );

            jugar(elegido);

        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de luchadores.");
            System.out.println("Ruta utilizada: " + RUTA_JSON);
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    /**
     * Método que implementa la lógica del juego de adivinanza de luchadores.
     * @param luchador
     * @return  false si el jugador pierde, true si gana.
     * Ojo, también puede devolver false si ocurre un error inesperado durante el juego.
     */
    private static boolean jugar(Luchadores luchador) {

        try {
            Scanner scanner = new Scanner(System.in);
            String nombre = luchador.getNombreArtistico();
            if (nombre == null || nombre.isBlank()) {
                System.out.println(
                        "El luchador seleccionado no tiene nombre artístico."
                );
                return false;
            }

            StringBuilder progreso = crearProgresoInicial(nombre);

            ArrayList<Character> letrasUsadas = new ArrayList<>();

            int errores = 0;

            System.out.println("========================================");
            System.out.println("Adivina el nombre artístico del luchador");
            System.out.println("========================================");
            System.out.println(
                    "Tienes " + MAX_ERRORS + " errores máximos."
            );
            System.out.println("Progreso: " + progreso);

            while (errores < MAX_ERRORS) {

                boolean hayError = false;

                System.out.print("\nIngresa una letra: ");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    System.out.println(
                            "Debes ingresar al menos una letra."
                    );
                    hayError = true;
                }

                char letra = '\0';

                if (!hayError) {
                    letra = normalizarCaracter(entrada.charAt(0));

                    if (!Character.isLetter(letra)) {
                        System.out.println("Solo se aceptan letras.");
                        hayError = true;
                    }
                }

                if (!hayError && letrasUsadas.contains(letra)) {
                    System.out.println(
                            "Ya intentaste con esa letra."
                    );
                    hayError = true;
                }

                if (hayError) {
                    continue;
                }

                letrasUsadas.add(letra);

                boolean acerto = actualizarProgreso(
                        nombre,
                        progreso,
                        letra
                );

                if (acerto) {
                    System.out.println("¡Correcto!");
                } else {
                    errores++;

                    System.out.println(
                            "Incorrecto. Error "
                                    + errores
                                    + "/"
                                    + MAX_ERRORS
                    );

                    mostrarPistaPorError(errores, luchador);
                }

                System.out.println("Progreso: " + progreso);
                System.out.println("Letras usadas: " + letrasUsadas);

                if (estaCompleto(nombre, progreso)) {
                    mostrarVictoria(luchador);
                    return true;
                }
            }

            mostrarDerrota(luchador);
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Ocurrió un error inesperado: " + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Devuelve un StringBuilder que representa el progreso inicial del nombre del luchador.
     * Cada letra del nombre se reemplaza por un guion bajo '_', mientras que los
     * espacios y otros caracteres no alfabéticos se conservan.
     * @param nombre
     * @return el progreso inicial del nombre del luchador.
     * si el nombre es nulo o vacío, devuelve un StringBuilder vacío.
     */
    private static StringBuilder crearProgresoInicial(String nombre) {

        StringBuilder progreso = new StringBuilder();

        for (int i = 0; i < nombre.length(); i++) {

            char caracter = nombre.charAt(i);

            if (Character.isLetter(caracter)) {
                progreso.append('_');
            } else {
                /*
                 * Conserva espacios, guiones, apóstrofos,
                 * puntos y otros símbolos del nombre.
                 */
                progreso.append(caracter);
            }
        }

        return progreso;
    }

    /*
     * Actualiza el progreso del nombre del luchador según la letra introducida.
     * @param nombre
     * @param progreso
     * @param letraIntroducida
     * @return true si la letra está en el nombre, false en caso contrario.
     */
    private static boolean actualizarProgreso(
            String nombre,
            StringBuilder progreso,
            char letraIntroducida) {

        boolean acerto = false;

        for (int i = 0; i < nombre.length(); i++) {

            char caracterNombre = nombre.charAt(i);

            if (!Character.isLetter(caracterNombre)) {
                continue;
            }

            char caracterNormalizado =
                    normalizarCaracter(caracterNombre);

            if (caracterNormalizado == letraIntroducida) {
                progreso.setCharAt(i, caracterNombre);
                acerto = true;
            }
        }

        return acerto;
    }

    /**
     * Normaliza un carácter a su forma base y lo convierte a mayúscula.
     * Por ejemplo, 'á' se convierte en 'A'.
     * @param caracter
     * @return el carácter normalizado y en mayúscula.
     */
    private static char normalizarCaracter(char caracter) {

        String texto = String.valueOf(caracter)
                .toUpperCase(Locale.ROOT);

        String normalizado = Normalizer.normalize(
                texto,
                Normalizer.Form.NFD
        );

        normalizado = normalizado.replaceAll(
                "\\p{M}",
                ""
        );

        return normalizado.charAt(0);
    }

    /**
     * Verifica si el nombre del luchador ha sido completado.
     * @param nombre
     * @param progreso
     * @return true si el nombre está completo, false en caso contrario.
     */
    private static boolean estaCompleto(
            String nombre,
            StringBuilder progreso) {

        for (int i = 0; i < nombre.length(); i++) {

            if (Character.isLetter(nombre.charAt(i))
                    && progreso.charAt(i) == '_') {

                return false;
            }
        }

        return true;
    }
    /**
     * Muestra pistas al jugador según el número de errores cometidos.
     * @param errores
     * @param luchador
     */
    private static void mostrarPistaPorError(
            int errores,
            Luchadores luchador) {

        switch (errores) {

            case 2 -> System.out.println(
                    "Pista: división " + valorODesconocido(
                            luchador.getDivision()
                    )
            );

            case 3 -> System.out.println(
                    "Pista: estuvo relacionado con "
                            + valorODesconocido(
                                    luchador.getPromocionPrincipal()
                            )
            );

            case 4 -> System.out.println(
                    "Pista: comenzó su etapa principal en "
                            + valorODesconocido(
                                    luchador.getInicioEtapa()
                            )
            );

            case 5 -> {
                System.out.println(
                        "Pista: su etapa registrada termina en "
                                + valorODesconocido(
                                        luchador.getFinEtapa()
                                )
                );

                if (luchador.tieneNombreReal()) {
                    System.out.println(
                            "Nombre real: "
                                    + ocultarParteNombreReal(
                                            luchador.getNombreReal()
                                    )
                    );
                }
            }

            case 6 -> {
                if (luchador.tieneNombreReal()) {
                    System.out.println(
                            "Última pista — nombre real: "
                                    + luchador.getNombreReal()
                    );
                } else {
                    System.out.println(
                            "Última pista: no hay nombre real "
                                    + "registrado para este luchador."
                    );
                }
            }

            default -> {
                // No se muestra ninguna pista en el primer error.
            }
        }
    }

    /**
     * Oculta parte del nombre real del luchador, mostrando solo la primera letra de cada palabra y reemplazando el resto con guiones bajos.
     * @param nombreReal
     * @return una cadena con el nombre real parcialmente oculto.
     * Si el nombre real es nulo o vacío, devuelve una cadena vacía.
     * Por ejemplo, "John Doe" se convierte en "J___ D__".
     * Si el nombre real contiene múltiples espacios, se tratan como un solo espacio.
     * Si el nombre real contiene caracteres no alfabéticos, se conservan en la salida.
     * Si el nombre real contiene acentos o caracteres especiales, se conservan en la salida.
     * Si el nombre real contiene palabras de una sola letra, se muestran completas.
     * Si el nombre real contiene palabras con guiones, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con apóstrofos, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con puntos, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con comas, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con paréntesis, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con corchetes, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con llaves, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con barras, se tratan como una sola palabra.
     * Si el nombre real contiene palabras con signos de interrogación, se tratan como una sola palabra.
     */
    private static String ocultarParteNombreReal(String nombreReal) {

        String[] partes = nombreReal.trim().split("\\s+");

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < partes.length; i++) {

            String parte = partes[i];

            if (parte.isEmpty()) {
                continue;
            }

            resultado.append(parte.charAt(0));

            for (int j = 1; j < parte.length(); j++) {
                resultado.append('_');
            }

            if (i < partes.length - 1) {
                resultado.append(' ');
            }
        }

        return resultado.toString();
    }

    /**
     * Devuelve el valor proporcionado o "dato desconocido" si el valor es nulo o está vacío.
     * @param valor el valor a verificar
     * @return el valor proporcionado o "dato desconocido" si el valor es nulo o está vacío
     * Si el valor contiene solo espacios en blanco, también se considera vacío.
     * Si el valor contiene caracteres especiales, se conservan en la salida.
     */
    private static String valorODesconocido(String valor) {

        if (valor == null || valor.isBlank()) {
            return "dato desconocido";
        }

        return valor;
    }

    /**
     * Muestra un mensaje de victoria al jugador, incluyendo información sobre el luchador adivinado.
     * @param luchador el luchador que fue adivinado correctamente
     * Si el luchador tiene un nombre real, también se muestra.
     * Si el luchador tiene una promoción principal, también se muestra.
     * Si el luchador tiene una división, también se muestra.
     * Si el luchador tiene una etapa de inicio, también se muestra.
     * Si el luchador tiene una etapa de fin, también se muestra.
     * Si el luchador no tiene un nombre real, se indica que no hay nombre real registrado.
     * Si el luchador no tiene una promoción principal, se indica que no hay promoción principal registrada.
     * Si el luchador no tiene una división, se indica que no hay división
     */
    private static void mostrarVictoria(Luchadores luchador) {

        System.out.println("\n========================================");
        System.out.println("¡Felicitaciones, has ganado!");
        System.out.println(
                "Luchador: " + luchador.getNombreArtistico()
        );

        if (luchador.tieneNombreReal()) {
            System.out.println(
                    "Nombre real: " + luchador.getNombreReal()
            );
        }

        System.out.println(
                "Promoción: "
                        + valorODesconocido(
                                luchador.getPromocionPrincipal()
                        )
        );

        System.out.println("========================================");
    }

    /**
     *  Muestra un mensaje de derrota al jugador, incluyendo información sobre el luchador que no pudo adivinar.
     *  @param luchador el luchador que no fue adivinado correctamente
     *  Se mostrará el nombre artístico del luchador, y si tiene un nombre real, también se mostrará.
     * Si tiene una promoción principal, se mostrará; de lo contrario, se indicará que es un dato desconocido.
     */
    private static void mostrarDerrota(Luchadores luchador) {

        System.out.println("\n========================================");
        System.out.println(
                "¡Has perdido! El luchador era: "
                        + luchador.getNombreArtistico()
        );

        if (luchador.tieneNombreReal()) {
            System.out.println(
                    "Nombre real: " + luchador.getNombreReal()
            );
        }

        System.out.println(
                "Promoción principal: "
                        + valorODesconocido(
                                luchador.getPromocionPrincipal()
                        )
        );

        System.out.println(
                "Etapa: "
                        + valorODesconocido(
                                luchador.getInicioEtapa()
                        )
                        + " - "
                        + valorODesconocido(
                                luchador.getFinEtapa()
                        )
        );

        System.out.println("========================================");
    }
}