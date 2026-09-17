# Clase 6

**SIS-113 Programación II**

> Sábado 19 de septiembre · segunda sesión sobre Java. Continúa [Clase05-Guia.md](Clase05-Guia.md): mismo programa, ahora con dueños.

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Define **abstracción** y **encapsulamiento** con sus palabras, y da un ejemplo de Sentinel para cada uno
2. Distingue **clase** de **objeto**, y escribe una clase con atributos, constructor, `this`, `new` y métodos de instancia
3. Usa `private`, `public`, `final` y getters — y explica por qué `Snapshot` **no tiene setters**
4. Valida en el constructor: sabe que *el estado inválido no debe poder existir* y lo demuestra con `muestra-rota.txt`
5. Ubica en [sentinel/phase2-java/oop/](../sentinel/phase2-java/oop/) cada marca `ABSTRACTION` / `ENCAPSULATION` y la conecta con el `PAIN` que cura
6. Ha hecho **el mismo cambio en las dos versiones** y midió la diferencia con números

---

## Reglas de la sesión

- **Sin IA** para escribir clases: la primera clase de la vida se escribe a mano
- Antes de escribir una clase, se dice en voz alta **qué sabe y qué esconde**. Si no se puede decir, no se escribe
- Todo se compila desde la terminal: `javac -d out *.java && java -cp out Main`

---

## Desarrollo

### Parte 1 — Repaso: los dolores del jueves (15 min)

Con la tarea abierta. Tres estudiantes al azar, uno por experimento:

- ¿Cuántas líneas tocaste para la alerta de CPU? ¿Cuántas variables nuevas? ¿Y una tercera alerta?
- ¿Dónde pusiste los `if` para que no muera con `muestra-rota.txt`? ¿Cuántos? ¿Quién impide que alguien los borre?
- ¿Qué respondiste sobre clase vs. objeto?

En la pizarra, dos columnas que se van a llenar durante la clase:

| Dolor (jueves) | Pilar que lo cura (hoy) |
|---|---|
| 1. El dato entró sin que nadie preguntara | ? |
| 2. Un cambio de una palabra tocó cinco lugares | ? |
| 3. Cualquier línea pisa cualquier variable | ? |

### Parte 2 — Clase y objeto: `Snapshot` en la pizarra (30 min)

Las 7 variables sueltas de una lectura (`ts`, `cpuPct`, `memUsedKb`…) viajan **siempre juntas**: son *una cosa*. Esa cosa merece un nombre: una **lectura**, un `Snapshot`. Dibujarla como caja: nombre arriba, atributos en el medio, lo que sabe hacer abajo.

Sintaxis, un paso a la vez, sobre [Snapshot.java](../sentinel/phase2-java/oop/Snapshot.java):

1. `public final class Snapshot { … }` — el molde
2. `private final long ts;` — un atributo: la variable que ahora **tiene dueño**
3. El constructor: `public Snapshot(long ts, …) { this.ts = ts; … }` — `this.ts` es el atributo, `ts` el parámetro
4. `new Snapshot(…)` — fabricar un objeto con el molde
5. `reading.getMemoryPercent()` — preguntarle algo **a ese objeto**

**Clase vs. objeto:** `Snapshot` es el molde y hay uno. `reading` es una instancia y hay una por segundo. Demostrarlo sin crear archivos, con `jshell` desde la carpeta `oop/`:

```
jshell> /open Snapshot.java
jshell> var s = new Snapshot(1758121200, 12.5, 3145728, 8388608, 0.52, 312, "firefox")
jshell> s.getMemoryPercent()
jshell> s
jshell> var t = new Snapshot(1758121201, 99.0, 8000000, 8388608, 3.1, 400, "java")
jshell> t.getMemoryPercent()
```

Dos objetos, un molde, cada uno con su estado. Luego:

```
jshell> s.memTotalKb = 0
```

Error: `memTotalKb has private access in Snapshot`. **El compilador es el guardia** — la frase del jueves. Y:

```
jshell> new Snapshot(1758121202, 10.0, 3145728, 0, 0.5, 300, "firefox")
```

`IllegalArgumentException: mem_total_kb must be > 0`. El objeto **nunca existió**. Comparar con el jueves: el mismo dato llegó a la línea 107 y mató el programa.

**`static` vs. instancia** (la deuda del jueves): `Contract.parse(line)` no necesita ningún objeto — es una herramienta. `reading.getTime()` necesita *esa* lectura — es una pregunta sobre ella. Por eso `main` es `static`: cuando arranca, todavía no existe ningún objeto.

### Parte 3 — Encapsulamiento: el dato tiene dueño (25 min)

Las tres palabras y lo que prometen:

- `private` — lo que se esconde. Nadie afuera lo lee ni lo escribe
- `public` — lo que se promete. Una vez que alguien lo usa, no se puede retirar sin romperlo
- `final` — lo que no se reasigna. Un `Snapshot` nace válido y muere válido

**Getters, y la ausencia de setters.** `getTs()`, `getMemTotalKb()`: ventanas de solo lectura. No hay `setMemTotalKb()` y **no es un olvido**: una lectura del pasado no se edita. Cada setter que no se escribe es un bug que no puede pasar.

**Un campo público es una promesa que no puedes retirar.** Si `memTotalKb` fuera `public` y alguien escribiera `s.memTotalKb = 0` en su código, el día que queramos validar ya es tarde: validar rompe al que confió en la promesa.

**Validación en el constructor — la única puerta tiene guardia.** Leer los cinco `if` de `Snapshot`. Después:

```bash
cd sentinel/phase2-java/oop
javac -d out *.java
java -cp out Main 80 < ../../phase1-bash/samples/muestra-rota.txt
```

Cada línea rota se rechaza **con motivo** (`missing field mem_total_kb`, `mem_used_kb out of range`, `mem_total_kb must be > 0`), el resumen es correcto y el programa termina bien. Comparar con la tarea: *"¿cuántos `if` pusiste tú, y en cuántos lugares? Aquí hay cinco, en un solo lugar, y nadie puede saltárselos porque no hay otra puerta."*

Dos encapsulamientos más, rápidos:

- [MemoryRule.java](../sentinel/phase2-java/oop/MemoryRule.java): `java -cp out Main 300` muere en la línea 29 de `Main` con un mensaje claro, **antes de leer nada**. El jueves, `java Sentinel.java 300` corrió feliz y nunca alertó (PAIN 1)
- [History.java](../sentinel/phase2-java/oop/History.java): el arreglo y `count` son privados. El experimento 3 del jueves (`read = 0;` en medio del `while`) **ya no compila** contra el historial

Llenar la pizarra: dolores 1 y 3 → **encapsulamiento**.

### Parte 4 — Abstracción: ver el qué, esconder el cómo (25 min)

Apoyo visual para toda la parte: [Clase06-C4.excalidraw](Clase06-C4.excalidraw), el modelo C4 del dashboard en tres niveles. El nivel 3 muestra las seis clases y quién llama a quién; leerlo después de `Main`, no antes.

**La prueba.** Abrir solo [Main.java](../sentinel/phase2-java/oop/Main.java). Treinta líneas. Leerlo en voz alta sin abrir los otros cinco archivos: *"crea una regla, un historial, una consola; por cada línea: la parsea, la agrega, la muestra; al final, el resumen"*. ¿Se entiende qué hace el programa? Entonces la abstracción funcionó: cada línea dice **qué** pasa, ninguna dice **cómo**.

**[Contract.java](../sentinel/phase2-java/oop/Contract.java) — siete `indexOf` que se volvieron uno.** La búsqueda `"clave": valor` está escrita una sola vez, en `raw()`, y es `private`: nadie afuera sabe que existe. Repetir el experimento 2 del jueves, agregar `"hostname"`:

- `Contract.parse`: una línea
- `Snapshot`: un atributo, un parámetro, un getter
- `Console.show`: solo si se quiere ver
- `Main`: **intacto**

Contar y anotar al lado del número del jueves: dos archivos con cambios previsibles, contra cinco lugares y uno roto sin tocarlo.

**[Console.java](../sentinel/phase2-java/oop/Console.java) y [History.java](../sentinel/phase2-java/oop/History.java)** — `Main` no sabe de códigos de color ni de arreglos. Cuando en la clase 8 `History` cambie el arreglo por una `ArrayList`, `Main` no se entera. Esa será la demostración de abstracción más contundente del curso, y ya está preparada.

**El puente con el proyecto.** Es lo mismo que el contrato JSON hace entre Bash y Java — *programar contra el qué, no contra el cómo* — ahora **dentro** de Java, entre clases. Y un adelanto: en la Unidad 3, `Console` y `MemoryRule` se convertirán en familias (`Renderer`, `Rule`) con varias implementaciones. Hoy basta con que vivan separadas.

Llenar la pizarra: dolor 2 → **abstracción**.

### Parte 5 — Laboratorio: el mismo cambio, dos veces (25 min)

Copiar `sentinel/phase2-java/oop/` a `bitacora-terminal/java/sentinel-oop/` y trabajar ahí.

**Misión A (en clase):** la alerta de CPU de la tarea, ahora en la versión OOP. Primero decidir *dónde vive* y decirlo en voz alta: ¿una clase `CpuRule` nueva? ¿un segundo umbral dentro de `MemoryRule`? La opción simple es copiar `MemoryRule` como `CpuRule` — y notar la duplicación: eso que molesta es exactamente lo que la herencia resuelve en la Unidad 3. Anotar en `investigacion/dolores.md`, al lado de los números del jueves: líneas tocadas, archivos tocados, ¿`Main` cambió?

**Misión B (en clase):** `read` e `invalid` siguen sueltas en `Main`. Darles un dueño: ¿`History`? ¿una clase nueva `Counter`? ¿`Console`? No hay una respuesta única; hay que **elegir y justificar** en un comentario de una línea. Eso es diseño.

**Misión C (para quien avanza rápido):** `Snapshot.getMemFreeKb()` y mostrarla en `Console`. Verificar que nada más cambia.

Cierre del laboratorio: correr las dos versiones con la muestra limpia y hacer `diff`. Solo difiere el encabezado. Misma conducta, distinta estructura — el paso Bash → C del proyecto, dentro de un solo lenguaje.

---

## Verificación de salida

Antes de irse, cada estudiante:

- [ ] Define abstracción y encapsulamiento en una frase cada uno, con un ejemplo de Sentinel (pregunta oral al azar)
- [ ] Explica por qué `Snapshot` no tiene setters y qué pasaría si `memTotalKb` fuera `public`
- [ ] Muestra `java -cp out Main 80 < muestra-rota.txt` terminando con resumen correcto
- [ ] Misión A hecha, con el conteo de líneas y archivos al lado del conteo del jueves

---

## Tarea para la semana

En el repositorio propio, `bitacora-terminal/java/banco/`. Es el **Sistema Bancario** del plan de la materia, con los dos pilares de hoy aplicados de verdad.

### Parte 1 — Sistema Bancario con dueños

1. `Account`: `balance` y `number` privados; el constructor rechaza un saldo inicial negativo; `deposit(amount)` y `withdraw(amount)` validan (monto positivo, fondos suficientes) y lanzan `IllegalArgumentException` con un mensaje claro. **No hay `setBalance`**: el saldo cambia solo por depósitos y retiros
2. `Customer`: nombre privado y **sus** cuentas (un arreglo, como `History`); `openAccount(...)`, `getTotalBalance()`. Nadie afuera recibe el arreglo
3. `Main` que cuenta una historia: crear un cliente, abrir dos cuentas, depositar, retirar de más (y ver el rechazo), imprimir el total. Debe leerse sin abrir las otras clases
4. Marcar en el código, con el mismo estilo del proyecto, dónde está la `ABSTRACTION` y dónde el `ENCAPSULATION`, y qué estado inválido cada guardia hace imposible

### Parte 2 — Sentinel OOP

5. Misiones A y B terminadas y commiteadas en `bitacora-terminal/java/sentinel-oop/`
6. En `investigacion/pilares.md`: por cada clase de `oop/`, una fila con tres columnas — *qué sabe*, *qué esconde*, *qué estado inválido hace imposible*

### Entrega

- Link al repositorio actualizado, antes de la próxima clase
- Commits por parte; el historial cuenta la historia

> **Regla de IA:** verde para entender un concepto o un mensaje de error del compilador. Las clases se escriben a mano. En la próxima clase, cualquier estudiante puede ser invitado a explicar `Account.withdraw()` línea por línea y a agregar una validación en vivo.

---

## Referencias y práctica interactiva

*(contenido en inglés — leer documentación técnica en inglés es parte del oficio)*

### Documentación y lectura

| Recurso | Descripción |
|---------|-------------|
| [dev.java — Objects, Classes, Interfaces, Packages, and Inheritance](https://dev.java/learn/oop/) | El capítulo oficial de POO en Java. Leer las secciones de objetos y clases; el resto llega en la Unidad 3. |
| [Oracle Tutorial — Classes and Objects](https://docs.oracle.com/javase/tutorial/java/javaOO/index.html) | Declarar clases, constructores, `this`, y control de acceso. La referencia de sintaxis de la clase. |
| [Oracle Tutorial — Controlling Access to Members](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html) | La tabla de `private` / `public` / `protected` / sin modificador. |
| [Bloch, *Effective Java* — Items 15 y 17](https://www.oreilly.com/library/view/effective-java/9780134686097/) | *Minimize the accessibility of classes and members* y *Minimize mutability*: la justificación profesional de `private`, `final` y de no escribir setters. |
| [Horstmann, *Core Java Vol. I* — Cap. 4 *Objects and Classes*](https://horstmann.com/corejava/) | El capítulo del libro base sobre lo que hicimos hoy, con más ejemplos. |
| [jshell User's Guide](https://docs.oracle.com/en/java/javase/21/jshell/) | `/open Archivo.java` para probar una clase sin escribir un `main`. |

### Ejercicios interactivos

| Recurso | Descripción |
|---------|-------------|
| [Java Programming MOOC — Part 4 (Objects)](https://java-programming.mooc.fi/part-4) | Helsinki: introducción a POO con ejercicios autocorregidos. Las partes 5 y 6 profundizan. |
| [Exercism — Java track](https://exercism.org/tracks/java) | Los ejercicios de "classes" y "encapsulation" del track. |
