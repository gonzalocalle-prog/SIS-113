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

Programación II es el curso donde el estudiante consolida la Programación Orientada a Objetos (POO) en Java -clases, herencia, polimorfismo, encapsulamiento y concurrencia- como base para asignaturas y proyectos posteriores. Esto se mantiene sin cambios respecto al plan original.

**Actualización 2026 - IA, CLI y agentes de codificación**

La asistencia de IA y, cada vez más, los agentes de IA operados desde la línea de comandos ya forman parte del flujo real de trabajo del desarrollo de software y de la formación de pregrado en universidades de referencia. Esta actualización incorpora esas herramientas como una competencia digital transversal del Ingeniero de Software, **sin sustituir** el desarrollo de los fundamentos de la POO, que siguen siendo el objeto central de la asignatura.

### Fundamento y respaldo académico

- **Ubicación curricular:** en CMU, el curso dedicado a codificar con IA (15-113, *"Effective Coding with AI"*) se dicta justo después del curso de fundamentos (15-112) -posición análoga a SIS-113 tras SIS-112- pero como asignatura separada, no mezclada con el curso de fundamentos de POO.
- **Gradualidad:** Northwestern University concluyó que sus estudiantes deben pasar los dos primeros años construyendo fundamentos antes de trabajar con IA generativa a fondo, y ubicó su curso de IA aplicada en niveles avanzados.
- **No aislar el tema:** las guías CS2023 (ACM/IEEE) recomiendan integrar la IA generativa dentro de las unidades existentes en vez de aislarla en sesiones separadas.
- **Cautela basada en evidencia:** un ensayo controlado de Anthropic (enero 2026) encontró que quienes programan con asistencia de IA rinden peor en pruebas de comprensión de código -argumento a favor de proteger tramos de práctica manual.
- **Precedente cercano:** Brown University no tocó su secuencia introductoria; creó un curso pequeño y supervisado (*"Agentic Studio"*) solo para estudiantes con al menos un curso previo, con bitácoras de uso de IA y defensas orales de código.
- **Mecanismos de verificación:** Stanford (CS106B) reforzó evaluaciones presenciales; Harvard (CS50) restringe IA generalista y solo habilita una herramienta propia de tutoría ("tutor, no autor").
- **Contexto regional:** menos de la mitad de las universidades de América Latina tiene lineamientos formales de IA; el "semáforo de usos" del Tec de Monterrey / UNAM es el modelo más replicado y se adapta en la sección 3.1.

> **En síntesis:** es razonable llevar IA/CLI a Programación II siempre que (a) cada unidad empiece sin asistencia para formar criterio propio, (b) el uso de IA quede documentado y sujeto a defensa oral, y (c) el uso agéntico más autónomo se reserve para las etapas donde el estudiante ya puede verificar lo que el agente produce (proyecto final, Unidad 4).

---

## 2. Competencias a desarrollar

### 2.1 Competencia de la asignatura

Proyectar y desarrollar programas de tamaño medio en Java, aplicando principios de la Programación Orientada a Objetos (clases, herencia, polimorfismo, encapsulamiento y concurrencia), y utilizando la línea de comandos y herramientas de IA -incluidas las agénticas- de forma crítica, verificable y trazable, garantizando la reutilización, abstracción y calidad del software, sea este producido manualmente o con asistencia de IA.

### 2.2 Competencias genéricas (transversalización)

Se mantienen el pensamiento lógico y crítico, la resolución de problemas, el trabajo colaborativo, la comunicación efectiva y la ética profesional del plan original. Se añaden:

- **Alfabetización crítica en IA:** capacidad de evaluar, verificar, depurar y responsabilizarse del código producido con asistencia de agentes de IA.
- **Trazabilidad y transparencia:** hábito de documentar qué se hizo con asistencia de IA, con qué herramienta y bajo qué prompt.

### 2.3 Derivación de la competencia (contenidos)

| Dimensión | Saber Hacer | Saber Conocer | Saber Ser | Unidad de aprendizaje |
|---|---|---|---|---|
| 1. Manejo de sistemas operativos y CLI | Manejar Linux desde terminal. Programar en Shell Script. Usar un asistente de IA en la CLI para tareas acotadas de automatización, documentando el prompt usado. | Fundamentos de Linux y ShellScript. Capacidades y límites de los agentes de IA aplicados a scripting. | Metódico y crítico ante el código generado por IA; verifica antes de aceptar. | Sistemas Operativos Linux, ShellScript y CLI asistida por IA |
| 2. Uso de la POO | Crear clases y objetos en Java. Implementar encapsulamiento, herencia, polimorfismo y concurrencia. Revisar, depurar y defender oralmente código propio y código asistido por IA. | Paradigma POO. Principios de encapsulamiento y abstracción. Uso responsable de IA generativa en el ciclo de desarrollo. | Responsable en la documentación del código. Honesto al declarar el uso de herramientas de IA. Colaborador en proyectos de equipo. | POO en Java: Encapsulamiento, Herencia, Polimorfismo, Concurrencia |

---

## 3. Planificación y cronograma

### 3.1 Semanas 1–3: fundamentos de CLI con WebVM

Ambiente: [webvm.io](https://webvm.io) - VM Linux (Debian) que corre 100% en el navegador, sin instalación. El uso institucional (no solo individual) puede requerir licencia de Leaning Technologies (sales@leaningtech.com); confirmar antes de adoptarlo como infraestructura oficial. Su imagen por defecto es Debian de **32 bits**, por lo que no es apta para instalar un JDK moderno más adelante - de ahí el puente a Codespaces en la semana 3.

**Semana 1 - Orientación y fundamentos del shell**
- Sesión 1: qué es un SO, qué es una terminal. Onboarding a webvm.io. `pwd`, `ls`, `cd`, `mkdir`, `touch`, `cat`, `tree`, `man`/`--help`, `history`.
- Sesión 2: sistema de archivos Linux (`/home`, `/etc`, `/var`), rutas absolutas vs. relativas.
- Sesión 3: actividad "Organización de datos de sensores" aplicando lo anterior.

**Semana 2 - Archivos, permisos y flujo de datos**
- Sesión 1: `cp`, `mv`, `rm`, `chmod`, `chown`, lectura de `ls -la`.
- Sesión 2: redirección y tuberías (`>`, `>>`, `<`, `|`) combinadas con `wc`, `head`, `tail`; primer contacto con `grep`.
- Sesión 3: actividad "Respaldo automático de proyectos".

**Semana 3 - Procesamiento de texto, scripting y migración**
- Sesión 1: `grep`, `cut`, `sort`, `uniq` sobre un archivo de log (prepara el ejercicio de evaluación de la semana 8).
- Sesión 2: variables, `if`, `for`, funciones en Bash; shebang y permisos de ejecución; primer script real.
- Sesión 3 - **migración guiada a Codespaces**: se muestra en vivo la limitación de WebVM (el JDK moderno no instala por ser 32 bits) y cada estudiante abre su primer Codespace (Ubuntu + JDK) a partir de la plantilla del curso, repitiendo ahí los mismos comandos ya dominados. Cierre con `java --version` y un "Hello World".

### 3.2 Puente hacia el entorno de Java (GitHub Codespaces)

- Plantilla del curso: repositorio con `.devcontainer/devcontainer.json` (imagen `mcr.microsoft.com/devcontainers/java`) + `README.md` con instrucciones.
- Cada estudiante usa su propia cuenta gratuita de GitHub - cuota personal de 120 core-hours/mes (≈60 h reales en 2 núcleos) + 15 GB, ampliable a 180 core-hours con el GitHub Student Developer Pack (gratis, verificación propia del estudiante).
- El `devcontainer.json` trae Copilot **apagado por defecto**; se habilita unidad por unidad según el semáforo de la sección 3.1.
- Camino simple (recomendado para empezar): repositorio marcado como *Template*, cada estudiante hace *"Use this template"* y abre su Codespace - sin necesidad de organización ni verificación docente.
- Camino escalable (opcional, más adelante): verificación como docente en GitHub Global Campus + GitHub Classroom, para roster automático y un repo por estudiante.

### 3.3 Planificación semanal (Unidades 1 a 4)

| Unidad | Semana | Temas y actividades | Integración IA/CLI |
|---|---|---|---|
| 1. SO Linux y ShellScript | 1 | Linux esenciales, terminal, WebVM. Organización de datos de sensores. | Sin IA: construir el hábito de terminal a mano. |
| | 2 | Operaciones de directorios y archivos. Respaldo automático de proyectos. | IA como tutor conceptual (verde) para explicar comandos. |
| | 3 | Procesamiento de texto, scripting, migración a Codespaces. | Agente CLI (ámbar) + puente al entorno con el JDK. |
| | 4 | Análisis de uso de disco. Filtrado de datos, reportes, comparación de configuraciones (ya en Codespaces). | Consolidación del semáforo ámbar. |
| 2. POO y Encapsulamiento | 5-6 | Ejercicios 1-2: Sistema Bancario, Gestión de Biblioteca (programación secuencial). | Verde/ámbar acotado: dudas conceptuales sí, solución completa no. |
| | 7-8 | Ejercicios 3-4: Calificaciones, Inventario. Análisis de logs y sistema hospitalario con encapsulación. | Punto de control: comparar código propio vs. sugerencia de IA. |
| 3. Herencia y Polimorfismo | 9-11 | Reservas de Cine, Agenda de Contactos, Control de Estacionamiento (herencia). | Ámbar: asistentes de código sobre diseño propio, con registro. |
| | 13-14 | Polimorfismo sobre los mismos sistemas. Revisión intermedia de proyecto (Entrega 1). | Bitácora de IA obligatoria + defensa oral. |
| 4. Concurrencia | 15 | Conceptos de concurrencia y paralelismo. | Sin IA en el análisis conceptual inicial. |
| | 16-17 | Hilos, ExecutorService, CompletableFuture. Sincronización. Cierre del proyecto final. | Uso agéntico habilitado (ámbar/verde), con bitácora y defensa técnica final. |

### 3.4 Sistema de evaluación

| Fase | Sem. | Actividad de evaluación | Criterios (incluye uso de IA) | % | Política IA |
|---|---|---|---|---|---|
| Evaluación continua | 8 | Análisis de logs con comandos Linux. Sistema POO de gestión hospitalaria con encapsulamiento. | Uso correcto de comandos y encapsulamiento; si se usó IA, prompt registrado y el estudiante explica cada línea. | 50% | Verde/Ámbar |
| Evaluación continua | 14 | Revisión intermedia de proyecto: arquitectura, estructuras de datos, polimorfismo básico. | Identifica clases, aplica herencia y encapsulamiento; bitácora de IA entregada; defensa oral de decisiones de diseño. | 50% | Ámbar (bitácora obligatoria) |
| Examen final | 16-20 | Sistema completo funcional. Presentación y defensa técnica. | Sistema funcional y documentado; la defensa cubre también las partes asistidas por IA (qué, cómo y por qué). | 100% | Ámbar/Verde con bitácora y defensa |

Nota de habilitación ≥ 60/100. Nota final = (E.C. + E.F.) / 2 ≥ 51/100. *(Se mantienen los umbrales del plan original.)*

### 3.5 Cronograma de avance

| Mes | Semanas | Hitos |
|---|---|---|
| Febrero | 1-4 | Unidad 1: Linux/ShellScript. WebVM → migración a Codespaces (semana 3). |
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
- LeaningTech. *WebVM*. https://github.com/leaningtech/webvm
- GitHub. *GitHub Codespaces documentation*. https://docs.github.com/en/codespaces

---

DOCENTE: ___________________  FIRMA: ___________________  FECHA: ___________________