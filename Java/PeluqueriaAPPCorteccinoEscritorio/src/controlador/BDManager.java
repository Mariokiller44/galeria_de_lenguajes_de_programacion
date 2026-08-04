/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author marioescr
 */
public class BDManager {
    
    private static Connection con;

    public BDManager() {
    }
    
    public static boolean conectarBD(Connection conexion){
        boolean devo=false;
        try {
            con = conexion;
            devo=true;
        } catch (Exception e) {
            devo=false;
        }
        return devo;
    }
    
     /**
     * Realiza una copia de seguridad de la base de datos.
     */
    public static boolean realizarCopiaDeSeguridad() {
        boolean exito = false;
        File configFile = null;
        try {
            // Verificar si la base de datos está vacía
            if (comprobarBDVacia()) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo hacer copia de la base de datos. Está vacía",
                        "Copia de seguridad",
                        JOptionPane.WARNING_MESSAGE);
                throw new Exception("La base de datos está vacía; no se puede crear una copia de seguridad.");
            }

            // Crear archivo de configuración temporal
            configFile = crearArchivoConfiguracionTemporal();

            // Obtener la fecha actual
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            String fechaActual = formatoFecha.format(new Date());

            // Obtener ruta base del proyecto (src/bd)
            String proyectoPath = new File("").getAbsolutePath();
            String bdFolderPath = proyectoPath + File.separator + "src" + File.separator + "bd";

            // Construir el nombre del archivo de copia
            String nombreArchivoBackup = Configurable.DB_NAME + "_" + fechaActual + ".sql";
            File directorioBackup = new File(bdFolderPath);

            // Crear directorio si no existe
            if (!directorioBackup.exists()) {
                if (!directorioBackup.mkdirs()) {
                    JOptionPane.showMessageDialog(null,
                            "No se pudo crear el directorio de backup: " + bdFolderPath,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    throw new Exception("Error creando directorio de backup");
                }
            }

            File archivoBackup = new File(directorioBackup, nombreArchivoBackup);
            String rutaBackup = archivoBackup.getAbsolutePath();

            // Detectar sistema operativo
            String sistemaOperativo = System.getProperty("os.name").toLowerCase();
            boolean esWindows = sistemaOperativo.contains("win");

            // Construir el comando mysqldump
            String[] comando;
            if (esWindows) {
                // Windows necesita rutas con comillas y barras invertidas
                String rutaWindows = rutaBackup.replace("/", "\\");
                comando = new String[]{
                    "cmd.exe", "/c",
                    "mysqldump",
                    "--host=" + Configurable.DB_SERVER,
                    "--port=" + Configurable.SERVER_PORT,
                    "--user=" + Configurable.DB_USER,
                    "--password=" + Configurable.DB_PASSWORD,
                    "--protocol=TCP",
                    "--databases",
                    Configurable.DB_NAME,
                    "--result-file=\"" + rutaWindows + "\""
                };
            } else {
                // Linux/Mac
                comando = new String[]{
                    "mysqldump",
                    "--host=" + Configurable.DB_SERVER,
                    "--port=" + Configurable.SERVER_PORT,
                    "--user=" + Configurable.DB_USER,
                    "--password=" + Configurable.DB_PASSWORD,
                    "--protocol=TCP",
                    "--databases",
                    Configurable.DB_NAME,
                    "--result-file=" + rutaBackup
                };
            }

            // Ejecutar el comando
            Process proceso = Runtime.getRuntime().exec(comando);
            int resultado = proceso.waitFor();

            // Leer errores si los hay
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            StringBuilder errores = new StringBuilder();
            String linea;
            while ((linea = errorReader.readLine()) != null) {
                errores.append(linea).append("\n");
            }

            if (resultado == 0) {
                JOptionPane.showMessageDialog(null,
                        "Copia de seguridad creada correctamente:\n" + rutaBackup);
                exito = true;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error al hacer copia de seguridad:\n" + errores.toString(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                // Eliminar archivo vacío si hubo error
                if (archivoBackup.exists()) {
                    archivoBackup.delete();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Excepción al crear la copia de seguridad:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            exito = false;
        } finally {
            if (configFile != null && configFile.exists()) {
                configFile.delete();
            }
        }
        return exito;
    }

    private static File crearArchivoConfiguracionTemporal() throws IOException {
        File configFile = File.createTempFile("mysql_config_", ".cnf");
        configFile.deleteOnExit(); // Asegurar que se elimine al salir

        try (PrintWriter writer = new PrintWriter(configFile)) {
            writer.println("[client]");
            writer.println("host=" + Configurable.DB_SERVER);
            writer.println("port=" + Configurable.SERVER_PORT);
            writer.println("user=" + Configurable.DB_USER);
            writer.println("password=" + Configurable.DB_PASSWORD);
        }

        // Establecer permisos seguros (solo para el propietario)
        configFile.setReadable(false, false);
        configFile.setReadable(true, true);
        configFile.setWritable(false, false);
        configFile.setWritable(true, true);

        return configFile;
    }

    private static boolean importarBaseDatos() {
        boolean devo = false;
        try {
            Statement stmt = con.createStatement();
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
            devo = true;
        } catch (Exception e) {
            devo = false;
        }
        return devo;
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
    // Lógica para importar el archivo a la base de datos
    public static boolean importarBD() {
        boolean devo = false;
        try {
            if (comprobarBDVacia() || comprobarCorrupcionBD()) {
                renombrarArchivoBD();
                importarBaseDatos();
            }
            devo = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return devo;
    }

    private static boolean comprobarBDVacia() {
        // Lógica para verificar si la base de datos está vacía
        boolean isEmpty = false;
        // Conexión a la base de datos
        try {

            // Consulta SQL para contar el número de registros en una tabla
            String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'bd_alcorteccino';";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isEmpty = (count == 0);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }

        return isEmpty;
    }

    private static boolean comprobarCorrupcionBD() {
        // Lógica para verificar si la base de datos está corrupta
        boolean corrupta = false;

        try {
            // Consulta SQL para realizar una operación que identifique la corrupción
            // Puedes realizar una consulta específica según tu SGBD
            // Ejemplo: Verificar la existencia de una tabla particular
            String query = "SELECT 1";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            corrupta = !rs.next(); // Si no hay resultados, se considera que la base de datos está corrupta

            rs.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Base de datos corrupta o no existente", "Error", JOptionPane.ERROR_MESSAGE);
            corrupta = true; // Se considera que la base de datos está corrupta si hay una excepción SQL
        }

        return corrupta;
        // Devuelve true si está corrupta, false en caso contrario

    }

    private static boolean renombrarArchivoBD() {
        boolean devo = false;
        File archivoBD, ultimaBackup, nuevoArchivo;
        File[] listaBackups;
        FilenameFilter filtro;
        Path rutaOrigen, rutaDestino;
        String sobreescribirArchivo;
        try {

            archivoBD = new File(Configurable.DB_FOLDER);
            filtro = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(Configurable.DB_NAME);
                }
            };

            listaBackups = archivoBD.listFiles(filtro);
            if (listaBackups == null || listaBackups.length == 0) {
                System.out.println("No se encontraron archivos de respaldo de la base de datos.");
                throw new Exception("Error al buscar Backups de la base de datos");
            }

            Arrays.sort(listaBackups, new Comparator<File>() {
                @Override
                public int compare(File a, File b) {
                    return Long.compare(b.lastModified(), a.lastModified()); // Orden descendente
                }
            });

            ultimaBackup = listaBackups[0];

            sobreescribirArchivo = "bd_alcorteccino.sql";
            nuevoArchivo = new File(sobreescribirArchivo);
            rutaOrigen = ultimaBackup.toPath();
            rutaDestino = new File(Configurable.DB_FOLDER + File.separator + nuevoArchivo).toPath();

            Files.move(rutaOrigen, rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo renombrado y movido con éxito.");

        } catch (Exception e) {
            System.err.println("Error al renombrar y mover el archivo: " + e.getMessage());
        }
        return devo;
    }
}
