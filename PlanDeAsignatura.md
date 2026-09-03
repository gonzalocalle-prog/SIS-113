# Plan de Asignatura - Programación II (SIS-113)
### Propuesta de actualización: IA aplicada, CLI y agentes de codificación

| Campo | Detalle |
|---|---|
| Sigla y código | SIS-113 |
| Nombre de la asignatura | PROGRAMACIÓN II |
| Prerrequisito | SIS-112 PROGRAMACIÓN I |
| Gestión propuesta | 2-2026 |
| Docente | Gonzalo Ivan Calle Clavel |

---

## 1. Justificación

### 1.1 Aspecto disciplinar

Programación II es la asignatura donde el estudiante da el salto de *escribir instrucciones* (Programación I) a *diseñar sistemas*. La Programación Orientada a Objetos -clases, encapsulamiento, herencia, polimorfismo y concurrencia- es el paradigma dominante para construir y mantener software de tamaño medio y grande, y constituye el lenguaje conceptual común de asignaturas posteriores: estructuras de datos, bases de datos, ingeniería de software y desarrollo de aplicaciones. Java se adopta como vehículo del paradigma por su tipado estricto, su modelo de objetos explícito y su madurez: obliga a pensar en términos de contratos, responsabilidades y jerarquías antes de escribir código.

El dominio de la línea de comandos y de entornos Linux completa la formación disciplinar: la mayor parte de la infraestructura donde vive el software (servidores, contenedores, nubes, pipelines) es Linux operado por terminal, y el control de versiones con Git es hoy parte del método de la disciplina, no una herramienta opcional.

### 1.2 Aspecto profesional

El perfil que demanda la industria -local y remota- exige exactamente las competencias que articula esta asignatura: Java se mantiene entre los lenguajes de mayor demanda laboral (sistemas empresariales, backend, Android), y el manejo de terminal, Git y GitHub es requisito de entrada en prácticamente cualquier equipo de desarrollo. El repositorio que cada estudiante construye durante el semestre, con un historial de commits que documenta cómo creció su proyecto, es a la vez instrumento de evaluación y primera evidencia de portafolio profesional.

A esto se suma una realidad nueva del ejercicio profesional: la asistencia de IA y los agentes operados desde la línea de comandos ya forman parte del flujo real de trabajo del desarrollo de software. El profesional que la industria necesita no es el que genera código con IA, sino el que puede **leer, verificar, corregir, integrar y responder** por ese código. Esta asignatura forma ese criterio de manera deliberada: cada unidad empieza sin asistencia para construir dominio propio, y el uso de IA queda declarado y sujeto a defensa oral en las evaluaciones.

### 1.3 Aspecto sociocultural

La transformación digital de Bolivia y de la región necesita profesionales capaces de construir soluciones propias y no solo de consumir tecnología. La asignatura asume ese contexto en dos frentes:

- **Equidad de acceso:** todo el entorno de trabajo del curso es gratuito y multiplataforma - WSL (Ubuntu) sobre Windows, terminal nativa en macOS, JDK y Git de libre distribución - y quien tenga un equipo limitado cuenta con GitHub Codespaces (cuota gratuita de estudiante) como alternativa en la nube: ningún estudiante queda fuera por su equipo ni por licencias pagadas.
- **Formación ética ante la IA:** menos de la mitad de las universidades de América Latina cuenta con lineamientos formales sobre IA generativa. Formar el hábito de declarar qué se hizo con asistencia y con qué herramienta -y de defender oralmente el trabajo propio- es formación en honestidad académica y en responsabilidad profesional, no solo una regla de curso. El estudiante que puede decir "esto lo hice yo, esto lo hice con ayuda y así lo verifiqué" ejerce una transparencia que la sociedad hoy exige a quien construye tecnología.

### 1.4 Actualización 2026 - IA, CLI y agentes de codificación: fundamento y respaldo académico

La incorporación de estas herramientas se hace como competencia digital transversal del Ingeniero de Software, **sin sustituir** el desarrollo de los fundamentos de la POO, que siguen siendo el objeto central de la asignatura. El diseño se apoya en precedentes de universidades de referencia:

---

## 2. Competencias a desarrollar

### 2.1 Competencia de la asignatura

Proyectar y desarrollar programas de tamaño medio en Java, aplicando principios de la Programación Orientada a Objetos (clases, herencia, polimorfismo, encapsulamiento y concurrencia), y utilizando la línea de comandos y herramientas de IA -incluidas las agénticas- de forma crítica, verificable y trazable, garantizando la reutilización, abstracción y calidad del software, sea este producido manualmente o con asistencia de IA.

### 2.2 Competencias genéricas (transversalización)

Se mantienen el pensamiento lógico y crítico, la resolución de problemas, el trabajo colaborativo, la comunicación efectiva y la ética profesional del plan original. Se añaden:

- **Alfabetización crítica en IA:** capacidad de evaluar, verificar, depurar y responsabilizarse del código producido con asistencia de agentes de IA.
- **Transparencia:** hábito de declarar qué se hizo con asistencia de IA y capacidad de explicarlo y defenderlo oralmente en las evaluaciones.

### 2.3 Derivación de la competencia (contenidos)

| Dimensión | Saber Hacer | Saber Conocer | Saber Ser | Unidad de aprendizaje |
|---|---|---|---|---|
| 1. Manejo de sistemas operativos y CLI | Manejar Linux desde terminal. Programar en Shell Script. Usar un asistente de IA en la CLI para tareas acotadas de automatización, verificando y explicando lo generado. | Fundamentos de Linux y ShellScript. Capacidades y límites de los agentes de IA aplicados a scripting. | Metódico y crítico ante el código generado por IA; verifica antes de aceptar. | Sistemas Operativos Linux, ShellScript y CLI asistida por IA |
| 2. Uso de la POO | Crear clases y objetos en Java. Implementar encapsulamiento, herencia, polimorfismo y concurrencia. Revisar, depurar y defender oralmente código propio y código asistido por IA. | Paradigma POO. Principios de encapsulamiento y abstracción. Uso responsable de IA generativa en el ciclo de desarrollo. | Responsable en la documentación del código. Honesto al declarar el uso de herramientas de IA. Colaborador en proyectos de equipo. | POO en Java: Encapsulamiento, Herencia, Polimorfismo, Concurrencia |

---

## 3. Planificación y cronograma

### 3.1 Semanas 1–3: fundamentos de CLI con WSL

Ambiente: **WSL 2 (Ubuntu) sobre Windows** - la mayoría de los equipos del aula corre Windows; quienes usan macOS trabajan con su terminal nativa, que ya es un entorno Unix. Así cada estudiante tiene un Linux real en su propia máquina desde la semana 1, sin depender de servicios externos. [webvm.io](https://webvm.io) se usa **una sola vez y de forma demostrativa** en la primera sesión (mostrar una VM Linux corriendo en el navegador); no es infraestructura del curso.

**Semana 1 - Orientación y fundamentos del shell**
- Sesión 1: qué es un SO, qué es una terminal. Demo de webvm.io (solo demostrativa). Activación de WSL (Ubuntu) / terminal de macOS. `pwd`, `ls`, `cd`, `mkdir`, `touch`, `cat`, `tree`, `man`/`--help`, `history`.
- Sesión 2: sistema de archivos Linux (`/home`, `/etc`, `/var`), rutas absolutas vs. relativas.
- Sesión 3: actividad "Organización de datos de sensores" aplicando lo anterior.

**Semana 2 - Archivos, permisos y flujo de datos**
- Sesión 1: `cp`, `mv`, `rm`, `chmod`, `chown`, lectura de `ls -la`.
- Sesión 2: redirección y tuberías (`>`, `>>`, `<`, `|`) combinadas con `wc`, `head`, `tail`; primer contacto con `grep`.
- Sesión 3: actividad "Respaldo automático de proyectos".

**Semana 3 - Procesamiento de texto, scripting y migración**
- Sesión 1: `grep`, `cut`, `sort`, `uniq` sobre un archivo de log (prepara el ejercicio de evaluación de la semana 8).
- Sesión 2: variables, `if`, `for`, funciones en Bash; shebang y permisos de ejecución; primer script real.
- Sesión 3 - **instalación del JDK**: cada estudiante instala el JDK en su entorno (`sudo apt install openjdk-21-jdk` en WSL; Homebrew o SDKMAN en macOS); quien tenga problemas de equipo abre un Codespace desde la plantilla del curso como alternativa. Cierre con `java --version` y un "Hello World" compilado y ejecutado desde la terminal.

### 3.2 Entorno de Java: JDK local, con GitHub Codespaces como alternativa

- Entorno principal: **JDK sobre WSL (Windows) o macOS** + VS Code (con la extensión *WSL* en Windows). Todo local, gratuito y sin dependencia de cuotas.
- Alternativa en la nube (equipos limitados o problemas de instalación): plantilla del curso con `.devcontainer/devcontainer.json` (imagen `mcr.microsoft.com/devcontainers/java`) + `README.md` con instrucciones.
- Cada estudiante usa su propia cuenta gratuita de GitHub - cuota personal de 120 core-hours/mes (≈60 h reales en 2 núcleos) + 15 GB, ampliable a 180 core-hours con el GitHub Student Developer Pack (gratis, verificación propia del estudiante).
- El `devcontainer.json` trae Copilot **apagado por defecto**; se habilita unidad por unidad según el semáforo de la sección 3.1.
- Camino simple (recomendado para empezar): repositorio marcado como *Template*, cada estudiante hace *"Use this template"* y abre su Codespace - sin necesidad de organización ni verificación docente.
- Camino escalable (opcional, más adelante): verificación como docente en GitHub Global Campus + GitHub Classroom, para roster automático y un repo por estudiante.

### 3.3 Planificación semanal (Unidades 1 a 4)

| Unidad | Semana | Temas y actividades | Integración IA/CLI |
|---|---|---|---|
| 1. SO Linux y ShellScript | 1 | Linux esenciales, terminal (WSL). Organización de datos de sensores. | Sin IA: construir el hábito de terminal a mano. |
| | 2 | Operaciones de directorios y archivos. Respaldo automático de proyectos. | IA como tutor conceptual (verde) para explicar comandos. |
| | 3 | Procesamiento de texto, scripting, instalación del JDK. | Agente CLI (ámbar). |
| | 4 | Análisis de uso de disco. Filtrado de datos, reportes, comparación de configuraciones. | Consolidación del semáforo ámbar. |
| 2. POO y Encapsulamiento | 5-6 | Ejercicios 1-2: Sistema Bancario, Gestión de Biblioteca (programación secuencial). | Verde/ámbar acotado: dudas conceptuales sí, solución completa no. |
| | 7-8 | Ejercicios 3-4: Calificaciones, Inventario. Análisis de logs y sistema hospitalario con encapsulación. | Punto de control: comparar código propio vs. sugerencia de IA. |
| 3. Herencia y Polimorfismo | 9-11 | Reservas de Cine, Agenda de Contactos, Control de Estacionamiento (herencia). | Ámbar: asistentes de código sobre diseño propio, con verificación propia. |
| | 13-14 | Polimorfismo sobre los mismos sistemas. Revisión intermedia de proyecto (Entrega 1). | Defensa oral de decisiones de diseño y del uso de IA. |
| 4. Concurrencia | 15 | Conceptos de concurrencia y paralelismo. | Sin IA en el análisis conceptual inicial. |
| | 16-17 | Hilos, ExecutorService, CompletableFuture. Sincronización. Cierre del proyecto final. | Uso agéntico habilitado (ámbar/verde), sujeto a defensa técnica final. |

### 3.4 Sistema de evaluación

| Fase | Sem. | Actividad de evaluación | Criterios (incluye uso de IA) | % | Política IA |
|---|---|---|---|---|---|
| Evaluación continua | 8 | Análisis de logs con comandos Linux. Sistema POO de gestión hospitalaria con encapsulamiento. | Uso correcto de comandos y encapsulamiento; si se usó IA, el estudiante lo declara y explica cada línea a pedido. | 50% | Verde/Ámbar |
| Evaluación continua | 14 | Revisión intermedia de proyecto: arquitectura, estructuras de datos, polimorfismo básico. | Identifica clases, aplica herencia y encapsulamiento; defensa oral de decisiones de diseño y del uso de IA. | 50% | Ámbar (con defensa oral) |
| Examen final | 16-20 | Sistema completo funcional. Presentación y defensa técnica. | Sistema funcional y documentado; la defensa cubre también las partes asistidas por IA (qué, cómo y por qué). | 100% | Ámbar/Verde con defensa |

Nota de habilitación ≥ 60/100. Nota final = (E.C. + E.F.) / 2 ≥ 51/100. *(Se mantienen los umbrales del plan original.)*

### 3.5 Cronograma de avance

| Mes | Semanas | Hitos |
|---|---|---|
| Febrero | 1-4 | Unidad 1: Linux/ShellScript sobre WSL. Instalación del JDK (semana 3). |
| Marzo | 5-9 | Unidad 2: POO y Encapsulamiento. |
| Abril | 10-13 | Unidad 3: Herencia y Polimorfismo. |
| Mayo | 14-17 | Revisión intermedia (14) · Unidad 4: Concurrencia · Recuperatorios (17). |
| Junio | 18-20 | Examen final primera y segunda instancia. |

---

## 4. Bibliografía y webgrafía

**Base**
- Deitel, H., & Deitel, P. (2018). *Java How to Program, Early Objects*. Pearson.
- Horstmann, C. (2022). *Core Java, Volume I: Fundamentals*. Oracle Press / Pearson.
- Linux Professional Institute. (2024). *Linux Essentials*.
- Oracle. *The Java Tutorials - Object-Oriented Programming Concepts*. https://docs.oracle.com/javase/tutorial/java/concepts/

**Nuevas - IA, CLI y agentes**
- ACM/IEEE. (2023). *Computer Science Curricula 2023 (CS2023)* - capítulo "Generative AI and the Curriculum". https://csed.acm.org
- Anthropic. (2026). *How AI assistance impacts the formation of coding skills*. https://www.anthropic.com/news/AI-assistance-coding-skills
- Brown University. (2026). *Brown professors devise course to explore generative AI in computer science education*. https://www.brown.edu/news/2026-06-11/agentic-studio-ai-programming
- Carnegie Mellon University. *15-113: Effective Coding with AI*. https://www.cs.cmu.edu/~113
- Harvard University, CS50. *Artificial Intelligence policy*. https://cs50.harvard.edu/college/2024/fall/notes/ai/
- Stanford University. *CS146S - The Modern Software Developer* (de Vibe Coding a Agentic Engineering).
- Infobae. (2025). *Las claves de México, Colombia y Chile para incorporar la IA en la universidad* (semáforo de usos, Tec de Monterrey / UNAM).
- Microsoft. *Install WSL*. https://learn.microsoft.com/windows/wsl/install
- GitHub. *GitHub Codespaces documentation*. https://docs.github.com/en/codespaces

---

DOCENTE: ___________________  FIRMA: ___________________  FECHA: ___________________