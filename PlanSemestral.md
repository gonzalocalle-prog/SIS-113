# Programación II

## Información General

| Campo | Valor |
|-------|-------|
| **Código** | SIS-113 |
| **Prerrequisito** | SIS-112 Programación I |
| **Gestión** | 2-2026 |
| **Duración** | 20 semanas |
| **Horas/semana** | 6 horas (3 sesiones de 2 h) |
| **Lenguaje** | C# (.NET) + Bash |
| **Docente** | Gonzalo Ivan Calle Clavel |
| **Repositorio del curso** | [SIS-113](https://github.com/gonzalocalle-prog/SIS-113) |

---

## Sobre el Curso

Este curso convierte a alguien que *sabe escribir un programa* en alguien que *sabe construir un sistema*. Al finalizar, cada estudiante tendrá:

- Un sistema de tamaño medio, funcional, con POO aplicada de verdad (no clases decorativas)
- Manejo real de terminal Linux y scripting — sin depender del botón "Ejecutar" del IDE
- Un repositorio en GitHub con historial de commits que cuenta cómo creció el proyecto
- Criterio para usar IA como herramienta y no como muleta: capacidad de leer, verificar, corregir y **defender oralmente** código que no escribió a mano


> **La regla que ordena todo el curso:** puedes usar la herramienta que quieras, pero si no puedes explicar y modificar tu código en vivo, no es tuyo y no cuenta.

---

## Plan Semanal

### BLOQUE 1: Linux, Terminal y ShellScript (Semanas 1-4)
> *Unidad 1*

#### Semana 1 — Orientación y fundamentos del shell

**Sesión 1:**
- Qué es un sistema operativo, qué es una terminal, por qué un programador vive en ella
- Onboarding a webvm.io — todos con una VM Linux corriendo al final de la sesión
- `pwd`, `ls`, `cd`, `mkdir`, `touch`, `cat`, `tree`, `man` / `--help`, `history`

**Sesión 2:**
- Sistema de archivos de Linux: `/home`, `/etc`, `/var`, `/tmp`
- Rutas absolutas vs. relativas, `.` y `..`

**Sesión 3:**
- Laboratorio: **"Organización de datos de sensores"** — crear una estructura de directorios a partir de una especificación y poblarla desde la terminal

**Entregable:** Captura de `tree` con la estructura completa + lista de comandos usados

---

#### Semana 2 — Archivos, permisos y flujo de datos

**Sesión 1:**
- `cp`, `mv`, `rm` (y por qué `rm -rf` merece respeto), `chmod`, `chown`
- Leer e interpretar `ls -la`: el modelo de permisos usuario/grupo/otros

**Sesión 2:**
- Redirección y tuberías: `>`, `>>`, `<`, `|`
- Combinaciones con `wc`, `head`, `tail`. Primer contacto con `grep`

**Sesión 3:**
- Laboratorio: **"Respaldo automático de proyectos"** — copiar, comprimir y verificar

**Entregable:** Secuencia de comandos documentada que respalda un directorio y verifica el resultado

---

#### Semana 3 — Procesamiento de texto, scripting y migración

**Sesión 1:**
- `grep`, `cut`, `sort`, `uniq` sobre un archivo de log real *(esto prepara directamente la evaluación de la Semana 8)*

**Sesión 2:**
- Variables, `if`, `for`, funciones en Bash. Shebang (`#!/bin/bash`) y permisos de ejecución
- Primer script real, escrito a mano

**Sesión 3 — Migración guiada a Codespaces:**
- Se muestra **en vivo** que `dotnet` no instala en WebVM por ser Debian de 32 bits
- Cada estudiante abre su primer Codespace desde la plantilla del curso (*Use this template*)
- Se repiten los mismos comandos ya dominados, ahora sobre Ubuntu
- Cierre: `dotnet --version` y un "Hello World" en C#

**Entregable:** Script `.sh` funcional + Codespace operativo con el Hello World commiteado

---

#### Semana 4 — Consolidación en Codespaces

**Sesión 1:**
- Análisis de uso de disco: `du`, `df`, `find`. Filtrado y generación de reportes

**Sesión 2:**
- Comparación de archivos de configuración: `diff`, `sed` básico
- Introducción a Git desde la terminal: `init`, `add`, `commit`, `push`

**Sesión 3:**
- Laboratorio integrador: un script que analiza un directorio y produce un reporte legible

**Entregable:** Repositorio con la colección de scripts de la unidad + `BITACORA-IA.md` iniciada

---

### BLOQUE 2: POO y Encapsulamiento (Semanas 5-8)
> *Unidad 2*

#### Semana 5 — Del programa secuencial al objeto

**Sesión 1:**
- El problema que resuelve la POO: por qué 300 líneas de `Main` con variables sueltas no escalan
- Clase vs. objeto, atributos, métodos, constructores, `new`
- Sintaxis de C# para quien viene de C/C++/Java/Python

**Sesión 2:**
- Práctica guiada: **Ejercicio 1 — Sistema Bancario** (versión secuencial → versión con clases)

**Sesión 3:**
- Laboratorio: completar `Cuenta`, `Cliente` y las operaciones de depósito/retiro

**Entregable:** Sistema Bancario funcional con al menos 2 clases

---

#### Semana 6 — Colecciones y composición

**Sesión 1:**
- `List<T>`, recorrido y búsqueda. Un objeto que contiene otros objetos
- `ToString()`, sobrecarga de métodos, constructores múltiples

**Sesión 2:**
- Práctica guiada: **Ejercicio 2 — Gestión de Biblioteca**

**Sesión 3:**
- Laboratorio: menú de consola completo (alta, baja, listado, búsqueda)

**Entregable:** Gestión de Biblioteca con menú funcional y persistencia en memoria

---

#### Semana 7 — Encapsulamiento

**Sesión 1:**
- `private` vs `public` vs `protected`. Propiedades de C# (`get`/`set`)
- **Validación en el setter**: el estado inválido no debe poder existir
- Por qué un campo público es una promesa que no puedes retirar

**Sesión 2:**
- Práctica guiada: **Ejercicio 3 — Calificaciones** y **Ejercicio 4 — Inventario**
- Refactorizar el Sistema Bancario de la Semana 5 aplicando encapsulamiento

**Sesión 3 — Punto de control de IA:**
- Cada estudiante resuelve un problema corto **a mano**, luego le pide la misma solución a una IA y **escribe las diferencias**: qué hizo mejor la máquina, qué hizo peor, qué no habría entendido sin haberlo intentado primero

**Entregable:** Ejercicios 3 y 4 + documento de comparación código propio vs. sugerencia de IA

---

#### Semana 8 — EVALUACIÓN CONTINUA 1

**Sesión 1:**
- Repaso integrador: comandos Linux de la Unidad 1 + encapsulamiento

**Sesión 2:**
- Resolución de dudas, banco de ejercicios de práctica

**Sesión 3 — EVALUACIÓN (presencial, en Codespace):**
1. **Parte Linux:** análisis de un archivo de logs con comandos de terminal
2. **Parte POO:** sistema de gestión hospitalaria con encapsulamiento correcto

**Criterios:**
- Uso correcto de comandos y pipes
- Atributos privados con validación real en las propiedades
- Clases con responsabilidad clara
- Si se usó IA: prompt registrado y **explicación línea por línea a pedido**

**Peso:** 50% de la Evaluación Continua

---

### BLOQUE 3: Herencia y Polimorfismo (Semanas 9-14)
> *Unidad 3*

#### Semana 9 — Herencia

**Sesión 1:**
- Clase base y clase derivada, `:`, `base()`, `protected`
- Cuándo **no** usar herencia (la prueba del "es un" vs. "tiene un")

**Sesión 2:**
- Práctica guiada: **Reservas de Cine** — jerarquía de tipos de sala y de entrada

**Sesión 3:**
- Laboratorio: implementar la jerarquía y el flujo de reserva

**Entregable:** Sistema de Reservas de Cine con al menos 2 niveles de herencia


---

#### Semana 10 — Sobrescritura y clases abstractas

**Sesión 1:**
- `virtual` / `override` / `sealed`. Diferencia entre sobrecarga y sobrescritura
- Clases abstractas y métodos abstractos

**Sesión 2:**
- Práctica guiada: **Agenda de Contactos** con jerarquía de contactos

**Sesión 3:**
- Laboratorio: refactorizar Reservas de Cine usando una clase base abstracta

**Entregable:** Agenda de Contactos + refactor documentado del ejercicio anterior


---

#### Semana 11 — Interfaces

**Sesión 1:**
- Interfaces: el contrato sin implementación. `IComparable`, `IEnumerable`
- Interfaz vs. clase abstracta: cuándo cada una

**Sesión 2:**
- Práctica guiada: **Control de Estacionamiento**

**Sesión 3:**
- Laboratorio: implementar una interfaz propia en los tres sistemas de la unidad

**Entregable:** Control de Estacionamiento con al menos una interfaz propia

---

#### Semana 12 — Arranque formal del Proyecto Final · CHECKPOINT DE RESCATE

**Sesión 1:**
- Presentación de la consigna del proyecto: sistema de gestión a elección, con requisitos por capa
- Cómo pasar de un enunciado a un diagrama de clases

**Sesión 2:**
- Taller: cada estudiante define dominio, clases candidatas, jerarquía y capa objetivo
- Revisión cruzada entre compañeros

**Sesión 3 — Checkpoint individual:**
- Revisión 1:1 del avance acumulado de las Unidades 2 y 3
- **Se identifica quién pasa a proyecto de rescate**

**Entregable:** Propuesta de proyecto (dominio, diagrama de clases, capa objetivo declarada) + repositorio creado


---

#### Semana 13 — Polimorfismo

**Sesión 1:**
- Polimorfismo en acción: una colección del tipo base que ejecuta comportamientos distintos
- *Upcasting* y *downcasting*, `is`, `as`, patrones de coincidencia

**Sesión 2:**
- Práctica guiada: aplicar polimorfismo real sobre Cine, Agenda y Estacionamiento
- Eliminar el `switch` gigante y reemplazarlo por despacho polimórfico

**Sesión 3:**
- Laboratorio: aplicar polimorfismo al proyecto propio

**Entregable:** Los tres sistemas de la unidad con polimorfismo aplicado + avance de proyecto



---

#### Semana 14 — EVALUACIÓN CONTINUA 2 · Revisión Intermedia

**Sesión 1:**
- Preparación: qué se evalúa, cómo se defiende un diseño

**Sesión 2 y 3 — REVISIÓN INTERMEDIA (Entrega 1 del proyecto):**
- Presentación individual (15 min): arquitectura, estructuras de datos, herencia y polimorfismo aplicados
- **Defensa oral de decisiones de diseño**
- Entrega de `BITACORA-IA.md`
- Modificación en vivo del código a pedido del docente

**Criterios:**
- Identifica correctamente las clases del dominio
- Aplica herencia y encapsulamiento con justificación, no por decoración
- Bitácora entregada y coherente con el código
- Defiende sus decisiones y modifica su propio código sin ayuda

**Peso:** 50% de la Evaluación Continua

---

### BLOQUE 4: Concurrencia y Cierre del Proyecto (Semanas 15-17)
> *Unidad 4*

#### Semana 15 — Concurrencia y paralelismo

**Sesión 1:**
- Concurrencia vs. paralelismo: la diferencia que casi todos confunden
- Proceso vs. hilo. Por qué un programa se congela mientras espera
- Análisis conceptual en papel: identificar qué partes de un sistema pueden ejecutarse a la vez

**Sesión 2:**
- Condiciones de carrera demostradas en vivo: el mismo programa dando resultados distintos en cada ejecución

**Sesión 3:**
- Laboratorio: identificar y documentar los puntos concurrentes del proyecto propio

**Entregable:** Documento de análisis de concurrencia del proyecto


---

#### Semana 16 — Task, async/await e hilos

**Sesión 1:**
- `Thread`, `Task`, `async` / `await`. Qué significa realmente "esperar sin bloquear"
- Cuándo `async` no sirve de nada

**Sesión 2:**
- Práctica guiada: procesar varios archivos en paralelo, operación larga sin congelar el menú

**Sesión 3:**
- Laboratorio: implementar la operación concurrente del proyecto propio

**Entregable:** Al menos una operación concurrente funcionando en el proyecto


---

#### Semana 17 — Sincronización y cierre del proyecto · Recuperatorios

**Sesión 1:**
- `lock`, `Mutex`, `Semaphore`. Deadlocks: cómo se producen y cómo se evitan
- Colecciones seguras para concurrencia

**Sesión 2:**
- Laboratorio: corregir las condiciones de carrera detectadas en la Semana 15
- **Cierre de funcionalidad del proyecto** — a partir de aquí solo se pule, no se agrega

**Sesión 3:**
- **Recuperatorios** de evaluación continua

**Entregable:** Proyecto funcionalmente completo, con la capa declarada cumplida


---

### BLOQUE 5: Pulido y Examen Final (Semanas 18-20)
> *Junio*

#### Semana 18 — Pulido, documentación y ensayo

**Sesión 1:**
- Qué hace legible a un proyecto: nombres, estructura de carpetas, README técnico
- Documentar decisiones de diseño, no solo funciones

**Sesión 2:**
- Taller: limpiar código muerto, manejo de excepciones donde falta, pruebas manuales del menú completo
- Cierre y revisión final de `BITACORA-IA.md`

**Sesión 3:**
- **Ensayo de defensa** en parejas: cada estudiante presenta y el compañero pregunta
- Mentoría 1:1 sobre los puntos débiles detectados

**Entregable:** Proyecto congelado + README + bitácora final

---

#### Semana 19 — EXAMEN FINAL (Primera instancia)

**Evaluación individual (20 min presentación + 10 min preguntas):**

1. El problema que resuelve el sistema
2. Demo en vivo, funcionando
3. Diagrama de clases y decisiones de diseño (por qué esa jerarquía y no otra)
4. Dónde se aplicó concurrencia y por qué ahí
5. **Recorrido por la bitácora de IA:** qué se hizo con asistencia, qué se rechazó y por qué
6. **Modificación en vivo:** el docente pide un cambio y el estudiante lo implementa

**Criterios:**
- Sistema funcional y documentado
- POO aplicada con justificación (encapsulamiento, herencia, polimorfismo, concurrencia)
- La defensa cubre **también** las partes asistidas por IA: qué, cómo y por qué
- Capacidad de modificar el propio código sin ayuda

**Peso:** 100% del Examen Final

---

#### Semana 20 — Segunda instancia y cierre

**Sesión 1 y 2:**
- Examen final, segunda instancia

**Sesión 3 — Cierre:**
- Retrospectiva grupal: qué funcionó del semáforo de IA, qué no
- Feedback individual
- Qué sigue: hacia dónde llevar el proyecto y qué materias lo retoman

---

## Herramientas

| Propósito | Herramienta |
|-----------|-------------|
| **Terminal Linux (sem. 1-3)** | [webvm.io](https://webvm.io) — Debian en el navegador |
| **Entorno de desarrollo (sem. 3-20)** | GitHub Codespaces + `.devcontainer` del curso |
| **Lenguaje / SDK** | C# sobre .NET (imagen `mcr.microsoft.com/devcontainers/dotnet`) |
| **Editor** | VS Code (en Codespaces) |
| **Control de versiones** | Git + GitHub |
| **Cuota de estudiante** | GitHub Student Developer Pack (180 core-hours/mes) |
| **Asistentes de IA** | GitHub Copilot (apagado por defecto), agentes CLI según semáforo |
| **Diagramas de clases** | Draw.io, Excalidraw, o papel y lápiz |

---

## Recursos

### Base
- Deitel, H., & Deitel, P. (2017). *C# How to Program*. Pearson.
- Rob Miles. (2016). *C# Programming Yellow Book*. University of Hull.
- Linux Professional Institute. (2024). *Linux Essentials*.
- Microsoft. [*Object-oriented programming (C#)*](https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/tutorials/oop) — Microsoft Learn

### Sobre IA en el aula
- ACM/IEEE. (2023). [*CS2023 — Generative AI and the Curriculum*](https://csed.acm.org)
- Anthropic. (2026). [*How AI assistance impacts the formation of coding skills*](https://www.anthropic.com/news/AI-assistance-coding-skills)
- Harvard CS50. [*Artificial Intelligence policy*](https://cs50.harvard.edu/college/2024/fall/notes/ai/)
- Carnegie Mellon University. [*15-113: Effective Coding with AI*](https://www.cs.cmu.edu/~113)

### Entornos
- [WebVM](https://github.com/leaningtech/webvm) — Leaning Technologies
- [GitHub Codespaces documentation](https://docs.github.com/en/codespaces)

---

## Contacto y Soporte

- **Horario de consultas:** [por definir]
- **Canal de comunicación:** [por definir]
- **Repositorio del curso:** [SIS-113](https://github.com/gonzalocalle-prog/SIS-113)
