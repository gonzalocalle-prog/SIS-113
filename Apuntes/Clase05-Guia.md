# Clase 5

**SIS-113 Programación II**

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Explica con sus palabras qué son la **JVM** y el **JDK**, qué hace `javac` y qué hace `java`, y por qué Bash se interpreta y Java se compila (repaso de la investigación)
2. Traduce lo que ya sabe de Bash a Java: variables con tipo, `if`/`while`, argumentos, leer stdin, imprimir
3. Ha conectado por primera vez **dos lenguajes por un pipe**: `./collector.sh | java Echo.java`
4. Tiene corriendo el **dashboard procedural de Sentinel**: lee el contrato, alerta y resume
5. Ha sentido en vivo tres **dolores** del código procedural y sabe nombrarlos: *datos sin guardia*, *lógica repetida*, *estado sin dueño*
6. Sabe decir, en una frase cada uno, qué son **abstracción** y **encapsulamiento** — los dos pilares con los que el sábado vamos a curar esos dolores

---

## Reglas de la sesión

- **Sin IA**: el primer programa Java del curso se escribe a mano, como el primer script
- Todo se compila y ejecuta **desde la terminal** (`javac`, `java`), nunca con el botón del IDE
- Un programa que no corrió contra el stream real (o contra la muestra) no existe

---

## Desarrollo

### Parte 1 — Repaso de la tarea: JDK, compilar, y "no debe saber" (15 min)

- **JVM / JRE / JDK**: la máquina que ejecuta, la máquina más las librerías, y todo eso más las herramientas para *programar*. El programador instala el JDK.
- **`javac` y `java`**: en la pizarra, el viaje completo:

  ```
  Hello.java  ──javac──►  Hello.class (bytecode)  ──java──►  corre en la JVM
  ```

  Bash lee y ejecuta línea por línea; Java primero traduce **todo** y recién después ejecuta. Consecuencia que importa hoy: en Java, muchos errores aparecen **antes** de correr. El compilador es el primer guardia.

- **¿Por qué el dashboard no debe saber si el colector es Bash o C?** Porque lo único que necesita es el contrato. Hoy ese dashboard empieza a existir, y empieza en Java: **la tercera capa llega antes que la segunda**.

Verificación del entorno, todos a la vez: `java --version` y `javac --version`. Quien no lo tenga: `sudo apt install openjdk-21-jdk` (WSL) o `brew install openjdk@21` (macOS). Cinco minutos, mientras el resto avanza.

### Parte 2 — Java para quien viene de Bash (30 min)

No empezamos de cero: todo lo de la clase 4 tiene su traducción. Se construye la tabla en la pizarra, un concepto a la vez, probando cada uno:

| Bash (clase 4) | Java (hoy) | Lo que cambia |
|---|---|---|
| `umbral=80` | `int threshold = 80;` | El **tipo** es obligatorio. El `;` también |
| `echo "Umbral: $umbral"` | `System.out.println("Threshold: " + threshold);` | Se concatena con `+`, no con `$` |
| `$1`, `$#` | `args[0]`, `args.length` | Los argumentos son un arreglo de `String` |
| `if [ $# -eq 0 ]; then … fi` | `if (args.length == 0) { … }` | Paréntesis y llaves; no hay `then`/`fi` |
| `while read -r linea; do … done` | `while (input.hasNextLine()) { String line = input.nextLine(); … }` | Se lee stdin con un `Scanner` |
| `exit 1` | `System.exit(1);` | |
| `# comentario` | `// comentario` | |
| `saludar() { … }` | `static void greet() { … }` | Por hoy, `static`. El sábado se entiende por qué |
| `bash hola.sh` | `java Hello.java` | Un archivo se ejecuta directo. Lo formal es `javac Hello.java && java Hello` |

Para experimentar sin crear archivos: `jshell` (viene con el JDK). Escribir `int x = 5;` y `x * 2` — Java como calculadora.

**Programa 1, a mano — `Echo.java`.** Doce líneas: leer stdin, numerar cada línea, contar al final.

```java
import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            n++;
            System.out.println(n + ": " + line);
        }
        System.out.println("lines: " + n);
    }
}
```

Probarlo primero con un archivo (`java Echo.java < /etc/hostname`) y después con **el momento de la clase**:

```bash
cd sentinel/phase1-bash
./collector.sh | head -5 | java ../../bitacora-terminal/java/Echo.java
```

Dos lenguajes, un pipe, el contrato en el medio. Java no sabe que del otro lado hay Bash; Bash no sabe que del otro lado hay Java. Es la tubería de la clase 3 con un programa nuestro en cada punta.

Errores clásicos mostrados a propósito: olvidar el `;`, escribir `string` en minúscula, nombre de archivo distinto del nombre de la clase, comparar `String` con `==`.

**Mini-ejercicio (5 min):** modificar `Echo` para que cuente solo las líneas que contienen `"stress-ng"` (pista: `line.contains(...)`).

### Parte 3 — El dashboard procedural de Sentinel (35 min)

El dashboard ya existe, en su versión **procedural**: [sentinel/phase2-java/procedural/Sentinel.java](../sentinel/phase2-java/procedural/Sentinel.java). Un archivo, un `main`, todo adentro.

```bash
cd sentinel/phase2-java/procedural
java Sentinel.java 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt   # con la muestra
../../phase1-bash/collector.sh | head -20 | java Sentinel.java 50           # con el colector vivo
```

Quien esté en Windows sin WSL puede trabajar toda la clase con la muestra: el dashboard no sabe de dónde viene la línea. El `head -20` importa: el resumen sale cuando stdin **termina**, y `Ctrl+C` mata el pipe entero antes.

Leerlo **línea por línea, entre todos**, conectando con lo que ya se sabe:

1. El bloque de 18 variables al inicio: las 7 de una lectura, las 7 del resumen, los arreglos paralelos
2. El `while` con `Scanner`: el mismo `while read` de `alert.sh`
3. Los siete bloques `indexOf` / `substring`: lo que `jq '.mem_used_kb'` hacía en una línea
4. El cálculo de `pct` y el `printf` con colores: `alert.sh`, en Java
5. La contabilidad del resumen y el resumen mismo

Pregunta al grupo: *"Este programa compila, pasa la muestra, corre con el colector vivo. ¿Cuál es el problema?"* — Nadie lo ve todavía. Es normal: el código procedural no duele cuando se escribe, duele cuando se **cambia**. Vamos a cambiarlo.

### Parte 4 — Los dolores, en vivo (30 min)

Tres experimentos. Cada uno termina con una frase en la pizarra.

**Experimento 1 — Datos sin guardia.** Hay una segunda muestra con líneas que rompen el contrato:

```bash
java Sentinel.java 80 < ../../phase1-bash/samples/muestra-rota.txt
```

Observar en orden: la línea 2 (sin `mem_total_kb`) imprime `mem 1045092%` — **no falló, mintió**: el número mágico agarró otro campo. La línea 4 imprime `107%` sin protestar. La línea 6 (`mem_total_kb: 0`) mata el programa con `ArithmeticException: / by zero`.

> Pizarra: **"El dato entró sin que nadie le preguntara si tenía sentido."**

Preguntas: ¿dónde pondrías el `if`? ¿Cuántos `if` más hacen falta? ¿Quién garantiza que el próximo que toque este archivo los respete?

**Experimento 2 — Cambiar el contrato.** Suponer que el contrato gana un campo: `"hostname": "laptop-ana"`, al final de la línea. Agregarlo al programa, contando en voz alta cada lugar que se toca: una variable nueva, un bloque `indexOf` nuevo con su número mágico, los dos `printf`… y el bloque de `top_proc`, que buscaba la `}` para terminar, **ahora lee mal** aunque nadie lo tocó.

> Pizarra: **"Un cambio de una palabra en el contrato tocó cinco lugares y rompió un sexto."**

**Experimento 3 — Estado sin dueño.** Dentro del `while`, en cualquier parte, insertar una línea: `read = 0;` o `times[0] = 999;`. Compila. Corre. El resumen miente y nada avisó.

> Pizarra: **"Nada en el lenguaje impide que cualquier línea pise cualquier variable."**

En Bash pasaba lo mismo (todas las variables son globales). Java **puede** hacerlo mejor — pero solo si se lo pedimos.

### Parte 5 — Cierre: ponerle nombre a la cura (10 min)

Los tres dolores tienen dos curas, y las dos tienen nombre:

- **Encapsulamiento** — *el dato tiene un dueño, y el dueño decide quién lo lee, quién lo cambia y qué valores acepta.* Cura los experimentos 1 y 3.
- **Abstracción** — *quien usa algo ve el **qué** (un nombre) y no el **cómo** (el detalle).* Cura el experimento 2: `Contract.parse(line)` en lugar de siete `indexOf`.

Anuncio para el sábado: el mismo programa, reescrito con clases. **El comportamiento no cambia ni un carácter** — como el paso Bash → C del proyecto. Lo que cambia es *quién sabe qué*. La versión ya está en el repositorio ([sentinel/phase2-java/oop/](../sentinel/phase2-java/oop/)); se puede espiar, pero se entiende mejor después de haber hecho la tarea.

---

## Verificación de salida

Antes de irse, cada estudiante:

- [ ] Compila y ejecuta un `.java` desde la terminal y explica qué hace `javac` y qué hace `java`
- [ ] Tiene `Echo.java` corriendo con `./collector.sh | head -5 | java Echo.java`
- [ ] Corre el dashboard procedural contra la muestra y contra el colector vivo
- [ ] Nombra los tres dolores con sus palabras y dice cuál de los dos pilares cura cada uno

---

## Tarea para el sábado

En el repositorio propio, en `bitacora-terminal/java/`, con commits por parte.

### Parte 1 — Sentir el dolor con las manos

1. `Echo.java` commiteado, con el mini-ejercicio de `contains` resuelto
2. Copiar `Sentinel.java` como `SentinelCpu.java` y agregar una **segunda alerta**: `cpu_pct` mayor o igual a un segundo umbral que llega como `args[1]`. Al terminar, anotar en `investigacion/dolores.md`: cuántas líneas se tocaron, cuántas variables nuevas aparecieron, y qué pasaría con una tercera alerta
3. Hacer que `Sentinel.java` **no muera** con `muestra-rota.txt`: proteger la división por cero y rechazar `mem_used_kb > mem_total_kb`. Anotar en el mismo documento dónde quedaron los `if` y cuántos son. (Guardar los números: el sábado se comparan con la versión OOP.)

### Parte 2 — Lectura (prepara el sábado)

Leer [*What Is an Object?*](https://docs.oracle.com/javase/tutorial/java/concepts/object.html) y [*What Is a Class?*](https://docs.oracle.com/javase/tutorial/java/concepts/class.html) del tutorial de Oracle (dos páginas cortas) y responder en `investigacion/objetos.md`, en tres frases: ¿qué es una clase? ¿qué es un objeto? ¿qué diferencia hay entre `Snapshot` (un nombre de clase) y `reading` (un objeto de esa clase)?

### Entrega

- Link al repositorio actualizado, antes del sábado
- El historial de commits cuenta la historia: un solo commit final es una tarea incompleta

> **Regla de IA:** verde para *entender* un concepto de la Parte 2 (tutor, no autor). El código de la Parte 1 se escribe a mano; el sábado cualquier estudiante puede ser invitado a explicar su `SentinelCpu.java` línea por línea.

---

## Referencias y práctica interactiva

*(contenido en inglés — leer documentación técnica en inglés es parte del oficio)*

### Documentación y lectura

| Recurso | Descripción |
|---------|-------------|
| [dev.java — Getting Started](https://dev.java/learn/getting-started/) | Instalar el JDK, escribir, compilar y ejecutar el primer programa. Cubre la Parte 1 y 2. |
| [Learn X in Y minutes — Java](https://learnxinyminutes.com/docs/java/) | Toda la sintaxis de Java en una página, como devhints para Bash. Para tener abierto durante la clase. |
| [Oracle Tutorial — OOP Concepts](https://docs.oracle.com/javase/tutorial/java/concepts/) | La lectura de la tarea: objeto, clase, y lo que viene después. |
| [JEP 330 — Launch Single-File Source-Code Programs](https://openjdk.org/jeps/330) | Por qué `java Echo.java` funciona sin `javac`, y cuándo deja de alcanzar. |
| [`Scanner` (Java 21 API)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html) | La clase con la que leemos stdin. Mirar `hasNextLine` y `nextLine`. |
| [jshell User's Guide](https://docs.oracle.com/en/java/javase/21/jshell/) | El REPL del JDK: probar una expresión sin crear un archivo. |

### Ejercicios interactivos

| Recurso | Descripción |
|---------|-------------|
| [Java Programming MOOC — Part 1](https://java-programming.mooc.fi/part-1) | Universidad de Helsinki: entrada, variables, condicionales y bucles con ejercicios autocorregidos. Exactamente la Parte 2 de esta clase. |
| [Exercism — Java track](https://exercism.org/tracks/java) | Ejercicios cortos con revisión automática; empezar por los de sintaxis básica. |
