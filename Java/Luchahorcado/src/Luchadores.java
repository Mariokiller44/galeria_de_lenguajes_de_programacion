import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Clase que representa un luchador profesional y sus atributos.
 * Luchadores
 * 
 * @author Mario Escribano
 * @version 1.0.0
 * @since 2026-08-04
 */
public class Luchadores {

    /**
     * Se definen los atributos de la clase Luchadores, que corresponden a los
     * campos del archivo JSON.
     */
    private int id;
    private String nombreArtistico, nombreReal, promocionPrincipal, inicioEtapa, finEtapa, division;

    /*
     * Constructor vacío por defecto.
     */
    public Luchadores() {
    }

    /**
     * Getters para acceder a los atributos de la clase Luchadores.
     * 
     * @return
     */

    // <editor-fold desc="Getters" default-state="collapsed">
    public int getId() {
        return id;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public String getPromocionPrincipal() {
        return promocionPrincipal;
    }

    public String getInicioEtapa() {
        return inicioEtapa;
    }

    public String getFinEtapa() {
        return finEtapa;
    }

    public String getDivision() {
        return division;
    }
    // </editor-fold>

    /**
     * Comprueba si el luchador tiene un nombre real definido en el JSON.
     * Devuelve true si el nombre real no es nulo y no está vacío, false en caso
     * contrario.
     */
    public boolean tieneNombreReal() {
        return nombreReal != null && !nombreReal.isBlank();
    }

    /**
     * Método estático que lee todos los luchadores de un archivo JSON.
     *
     * @param ruta ruta del archivo JSON
     * @return lista de luchadores encontrados
     * @throws IOException si no puede leerse el archivo
     */
    public static ArrayList<Luchadores> cargarLuchadores(String ruta)
            throws IOException {

        // Creamos un objeto Gson para manejar la deserialización del JSON y definimos el tipo de lista que vamos a leer.
        // TypeToken nos permite capturar el tipo genérico de ArrayList<Luchadores> para que Gson pueda deserializar correctamente.
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<ArrayList<Luchadores>>() {}.getType();

        // Intentamos leer el archivo JSON y deserializarlo en una lista de luchadores.
        try{

            FileReader reader = new FileReader(ruta);
            ArrayList<Luchadores> luchadores = gson.fromJson(reader, tipoLista);

            // Si la lista es nula (por ejemplo, si el archivo JSON está vacío), devolvemos una lista vacía en lugar de null.
            if (luchadores == null) {
                return new ArrayList<>();
            }

            return luchadores;

        } catch (FileNotFoundException e) {
            throw new IOException(
                    "No se encontró el archivo JSON: " + ruta, e
            );

        } catch (JsonSyntaxException e) {
            throw new IOException(
                    "El archivo no contiene un JSON válido: " + ruta, e
            );

        } catch (JsonIOException e) {
            throw new IOException(
                    "No se pudo leer el archivo: " + ruta, e
            );
        }
    }

    /**
     * Devuelve una representación en cadena del luchador.
     *
     * @return nombre artístico del luchador
     */
    @Override
    public String toString() {
        return nombreArtistico;
    }
}