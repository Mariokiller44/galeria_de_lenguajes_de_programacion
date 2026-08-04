# Galería de lenguajes de programación

Repositorio personal de aprendizaje y práctica con proyectos desarrollados en distintos lenguajes de programación.

El objetivo es reunir ejercicios y pequeñas aplicaciones que permitan comparar sintaxis, estructuras, herramientas y enfoques de desarrollo entre diferentes tecnologías.

---

## Tecnologías

Actualmente el repositorio incluye o está preparado para incluir proyectos realizados con:

![Java](https://img.shields.io/badge/Java-21%2B-orange?logo=openjdk)
![Python](https://img.shields.io/badge/Python-3.x-blue?logo=python)
![PHP](https://img.shields.io/badge/PHP-8.x-777BB4?logo=php)
![Git](https://img.shields.io/badge/Git-Control%20de%20versiones-F05032?logo=git)
![VS Code](https://img.shields.io/badge/VS%20Code-Editor-007ACC?logo=visualstudiocode)

---

## Contenido del repositorio

```text
galeria_de_lenguajes_de_programacion/
├── Java/
│   ├── CalculadoraBasica/
│   └── Luchahorcado/
├── Python/
├── PHP/
└── README.md
```

La estructura podrá crecer a medida que se añadan nuevos proyectos y lenguajes.

---

## Proyectos destacados

### Luchahorcado

Juego de consola desarrollado en Java inspirado en el ahorcado tradicional.

La aplicación selecciona aleatoriamente un luchador desde un archivo JSON y permite adivinar su nombre artístico letra por letra.

#### Características principales

- Lectura de datos desde JSON.
- Conversión de JSON a objetos Java mediante Gson.
- Selección aleatoria de luchadores.
- Control de letras utilizadas.
- Sistema de errores y pistas progresivas.
- Uso de nombres artísticos, nombres reales, promociones y etapas.
- Compatibilidad con nombres que contienen espacios, guiones y caracteres acentuados.

#### Tecnologías utilizadas

```text
Java
Gson
JSON
VS Code
```

#### Estructura aproximada

```text
Java/Luchahorcado/
├── src/
│   ├── Luchadores.java
│   └── Principal.java
├── lib/
│   └── gson-2.8.0.jar
├── luchadores_famosos.json
└── .vscode/
```

#### Compilación

Desde la carpeta `Java/Luchahorcado`:

```bash
mkdir -p bin

javac \
  -cp "lib/gson-2.8.0.jar" \
  -d bin \
  src/*.java
```

#### Ejecución en Linux

```bash
java \
  -cp "bin:lib/gson-2.8.0.jar" \
  Principal
```

En Windows, el separador del classpath es `;`:

```powershell
java -cp "bin;lib/gson-2.8.0.jar" Principal
```

---

### Calculadora básica

Proyecto introductorio en Java orientado a practicar:

- Entrada de datos.
- Operaciones matemáticas.
- Condicionales.
- Métodos.
- Organización básica de una aplicación de consola.

---

## Objetivos del repositorio

Este repositorio se utiliza para:

- Practicar fundamentos de programación.
- Comparar distintos lenguajes.
- Mejorar la organización de proyectos.
- Aprender control de versiones con Git y GitHub.
- Trabajar con archivos externos como JSON.
- Incorporar progresivamente librerías y herramientas de construcción.
- Documentar la evolución técnica de los proyectos.

---

## Cómo clonar el repositorio

```bash
git clone https://github.com/Mariokiller44/galeria_de_lenguajes_de_programacion.git
cd galeria_de_lenguajes_de_programacion
```

---

## Estado del proyecto

El repositorio se encuentra en desarrollo continuo.

Algunos proyectos son ejercicios pequeños y otros evolucionarán hacia aplicaciones más completas. La estructura, las dependencias y la documentación podrán cambiar conforme avance el aprendizaje.

---

## Próximas mejoras

- Migrar los proyectos Java a Maven.
- Eliminar dependencias `.jar` gestionadas manualmente.
- Añadir pruebas automatizadas.
- Mejorar la separación por paquetes.
- Incorporar documentación individual para cada proyecto.
- Añadir proyectos de backend y bases de datos.
- Incluir ejemplos con APIs y persistencia.
- Automatizar compilaciones con GitHub Actions.

---

## Buenas prácticas del repositorio

No deben subirse al repositorio:

```text
JDK completos
archivos .class
carpetas bin/
contraseñas
credenciales
archivos temporales del editor
```

Ejemplo de `.gitignore`:

```gitignore
# Java
*.class
**/bin/
**/target/
**/oracleJdk-*/
**/jdk-*/

# VS Code
.vscode/*.log

# Sistema
.DS_Store
Thumbs.db
```

---

## Autor

**Mario Escribano**

Perfil orientado al desarrollo backend con Java, bases de datos, Linux, Docker e infraestructura.

---

## Licencia

Este repositorio tiene una finalidad educativa y personal.

Antes de reutilizar código o recursos externos, comprueba las licencias correspondientes.
