# Clase 7

**SIS-113 Programación II**

> Semana 6 del plan: **Colecciones y composición**. La Evaluación Continua 1 usó arreglos de capacidad fija a propósito; hoy llega la `ArrayList`, y con ella la demostración de abstracción que quedó pendiente en la Clase 6.

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Usa `ArrayList` a través de la interfaz `List`: `add`, `get`, `size`, `remove`, `isEmpty`, y la recorre con `for-each`
2. Explica qué gana una lista frente a un arreglo y por qué se declara `List<Book> books = new ArrayList<>()`
3. Reconoce la **composición**: un objeto que contiene otros objetos (`History` tiene `Snapshot`, `Library` tiene `Book`)
4. Sobrescribe `toString()`, sobrecarga un método y escribe **constructores múltiples** encadenados con `this(...)`
5. Demuestra que cambiar la implementación interna de `History` no cambia nada en `Main`
6. Tiene empezado el **Ejercicio 2 — Gestión de Biblioteca** con alta, listado y búsqueda

---

## Reglas de la sesión

- **Sin IA** para escribir las clases. Verde solo para entender un mensaje de error.
- Todo se compila desde la terminal: `javac -d out *.java && java -cp out Main`.
- Antes de ejecutar, alguien dice en voz alta qué va a pasar.

---

## Desarrollo

### Parte 1 — Repaso: lo que falló en la EC1 (15 min)

En el examen, la parte de Bash salió bien. Lo que costó fue lo básico de las clases. Antes de agregar colecciones, cuatro de los programas de [sentinel/phase2-java/clase07/](../sentinel/phase2-java/clase07/), en parejas: uno lee el mensaje en voz alta, el otro señala la línea.

```bash
cd sentinel/phase2-java/clase07
java Bug1VoidConstructor.java     # constructor con void
java Bug2Shadowing.java           # falta this
java Bug3MissingNew.java          # atributo sin new: null
java Bug4LocalHidesField.java     # variable local con el nombre del atributo
```

El Bug 3 importa especialmente hoy: una `List` declarada y nunca creada con `new ArrayList<>()` da exactamente ese `NullPointerException`.

### Parte 2 — Del arreglo a la lista, en Sentinel (30 min)

El problema del arreglo, en [History.java](../sentinel/phase2-java/oop/History.java):

```java
private final Snapshot[] readings = new Snapshot[CAPACITY];   // tamaño fijo, decidido antes de empezar
private int count = 0;                                        // un contador que hay que mantener a mano
```

Dos variables que tienen que moverse siempre juntas, `System.arraycopy` para olvidar la más vieja, y `for (int i = 0; i < count; i++)` en cada método. La lista hace ese trabajo por dentro.

**La sintaxis, construida en `jshell` antes de tocar Sentinel:**

```java
List<String> procs = new ArrayList<>();   // lista vacía de textos
procs.add("firefox");                     // agrega al final
procs.add("java");
procs.size()                              // 2
procs.get(0)                              // "firefox"
procs.contains("java")                    // true
procs.remove(0);                          // quita el primero; los demás se corren
for (String p : procs) { System.out.println(p); }
```

Tres ideas que hay que decir en voz alta:

- **`<String>` es el tipo de lo que guarda.** Una `List<Snapshot>` solo acepta `Snapshot`; el compilador rechaza lo demás.
- **A la izquierda `List`, a la derecha `ArrayList`.** `List` es el contrato (qué se puede hacer); `ArrayList` es una implementación (cómo). Es la misma idea que el contrato JSON entre Bash y Java, ahora entre clases.
- **Las listas no guardan `int` sino `Integer`.** `List<int>` no compila. Por hoy basta saberlo.

**El cambio en vivo.** Copiar `oop/` a la carpeta propia, guardar la salida actual y reescribir **solo** `History.java`:

```bash
javac -d out *.java && java -cp out Main 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt > antes.txt
```

```java
private final List<Snapshot> readings = new ArrayList<>();

public void add(Snapshot reading) {
    if (reading == null) {
        throw new IllegalArgumentException("cannot add a null reading");
    }
    if (readings.size() == CAPACITY) {
        readings.remove(0);                // olvidar la más vieja: una línea
    }
    readings.add(reading);
}

public int getCount() {
    return readings.size();               // el contador desapareció
}
```

Cada `for (int i = 0; i < count; i++)` pasa a ser `for (Snapshot reading : readings)`. Después:

```bash
javac -d out *.java && java -cp out Main 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt > despues.txt
diff antes.txt despues.txt          # sin diferencias
```

`Main` no cambió ni una letra, `Console` tampoco. Esa es la promesa de la abstracción de la Clase 6, cumplida con evidencia.

**La trampa del encapsulamiento.** Preguntar: ¿qué pasa si agrego `public List<Snapshot> getReadings() { return readings; }`? Cualquiera podría hacer `history.getReadings().clear()` y borrar el historial sin pasar por `add`. Si hace falta exponer la lista, se devuelve **una copia**: `return new ArrayList<>(readings);`.

### Parte 3 — `toString`, sobrecarga y constructores múltiples (25 min)

**`toString()`.** En `jshell`, imprimir un objeto de una clase sin `toString`: sale algo como `Book@1b6d3586`, el nombre de la clase y una dirección. [Snapshot.java](../sentinel/phase2-java/oop/Snapshot.java) ya lo sobrescribe: `System.out.println(reading)` usa ese método solo. `@Override` le pide al compilador que verifique que de verdad estamos reemplazando un método existente.

**Sobrecarga: mismo nombre, distintos parámetros.** En `History` ya existe `countOf(String process)`. Agregar:

```java
public int countOf(int minMemoryPercent) {
    int times = 0;
    for (Snapshot reading : readings) {
        if (reading.getMemoryPercent() >= minMemoryPercent) {
            times++;
        }
    }
    return times;
}
```

`history.countOf("firefox")` y `history.countOf(80)` llaman a métodos distintos: Java elige por el **tipo del argumento**. Con la muestra limpia, `countOf(80)` da 3. Pregunta: ¿se puede sobrecargar cambiando solo el tipo de retorno? No, y probarlo en vivo para ver el error.

**Constructores múltiples, con la guardia en un solo lugar.** El error típico es copiar las validaciones en cada constructor. Lo correcto es que uno llame al otro con `this(...)`:

```java
public Book(String isbn, String title, String author) {
    // aquí, y solo aquí, viven las validaciones
}

public Book(String isbn, String title) {
    this(isbn, title, "Unknown author");    // debe ser la primera línea
}
```

Mismo ejercicio sobre Sentinel, para quien avance: un constructor `MemoryRule()` sin parámetros que llama a `this(80)`.

### Parte 4 — Práctica guiada: Gestión de Biblioteca (45 min)

El Ejercicio 2 del plan. Se arma entre todos, una clase a la vez, compilando después de cada método. El código va en inglés.

**`Book`**, un libro:

- Atributos: `isbn`, `title`, `author` (todos `private final`) y `available` (empieza en `true`).
- Constructores: `Book(String isbn, String title, String author)` con las guardias, y `Book(String isbn, String title)` que llama al primero.
- `lend()` y `giveBack()`: no se presta un libro prestado ni se devuelve uno disponible. Sin `setAvailable`.
- `toString()`: `978-0134685991 | Effective Java | Joshua Bloch | available`.

**`Library`**, la composición: una biblioteca **tiene** libros.

- Atributo: `private final List<Book> books = new ArrayList<>();`
- `add(Book book)`: rechaza un ISBN repetido.
- `remove(String isbn)` y `findByIsbn(String isbn)`: si no existe, rechaza.
- `search(String text)`: devuelve una **lista nueva** con los libros cuyo título contiene el texto, sin importar mayúsculas.
- `listAll()`: devuelve una **copia**, nunca la lista interna.

**`Main`**, un menú de consola que repite hasta elegir salir:

```
1) Add  2) Remove  3) List  4) Search  5) Lend  6) Return  0) Exit
```

En clase se llega a **Add, List y Search**. El `try`/`catch` alrededor de cada opción muestra el mensaje de la excepción y vuelve al menú: un error del usuario no debe terminar el programa.

Pregunta de diseño para el cierre: ¿por qué `search` devuelve una lista nueva y no filtra la lista interna? ¿Qué pasaría con la biblioteca si lo hiciera?

---

## Verificación de salida

Antes de irse, cada estudiante:

- [ ] Muestra el `diff` vacío entre la salida de Sentinel con arreglo y con `ArrayList`
- [ ] Explica por qué se declara `List` a la izquierda y `ArrayList` a la derecha
- [ ] Explica qué hace `this(...)` en un constructor y por qué evita duplicar validaciones (pregunta oral al azar)
- [ ] Tiene `Book` y `Library` compilando, y el menú con Add, List y Search

---

## Tarea para la casa

En `bitacora-terminal/java/`, con commits por parte.

### Parte 1 — Gestión de Biblioteca completa (`biblioteca/`)

1. Menú con las seis opciones: alta, baja, listado, búsqueda, préstamo y devolución.
2. Ningún error del usuario termina el programa: ISBN repetido, ISBN inexistente, libro ya prestado, campos vacíos.
3. Opción nueva **7) Available**: lista solo los libros disponibles, con un método nuevo de `Library`.
4. Evidencia: una sesión completa del menú pegada en `investigacion/biblioteca.txt`.

### Parte 2 — Sentinel con colecciones (`sentinel-oop/`)

5. `History` con `ArrayList`, y el `diff` vacío como evidencia en `investigacion/history-diff.txt`.
6. La sobrecarga `countOf(int minMemoryPercent)` y una línea nueva en el resumen de `Console`: `readings over 80%: 3`.
7. Los Bugs 5, 6 y 7 de `clase07/`, con una frase cada uno: qué pasaba y qué cambiaste.

### Entrega

- Link al repositorio actualizado, antes de la próxima clase.
- Commits por parte; un solo commit final es una tarea incompleta.

> **Regla de IA:** verde para entender un mensaje de error o un método de `List` en la documentación. Las clases se escriben a mano. En la próxima clase, cualquier estudiante puede ser invitado a agregar una opción al menú en vivo.

---

## Referencias y práctica interactiva

*(contenido en inglés — leer documentación técnica en inglés es parte del oficio)*

### Documentación y lectura

| Recurso | Descripción |
|---------|-------------|
| [Oracle Tutorial — The List Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/list.html) | Qué ofrece `List` y cómo se recorre. La referencia de la Parte 2. |
| [`ArrayList` (Java 21 API)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html) | Todos los métodos de `ArrayList`. Mirar `add`, `get`, `remove`, `contains` y `isEmpty`. |
| [dev.java — The Collections Framework](https://dev.java/learn/api/collections-framework/) | El panorama de las colecciones de Java: listas, conjuntos y mapas. Leer la parte de listas. |
| [Oracle Tutorial — Defining Methods](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html) | Firmas de métodos y sobrecarga, con las reglas exactas de la Parte 3. |
| [Oracle Tutorial — Using the `this` Keyword](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html) | Incluye `this(...)` para encadenar constructores. |

### Ejercicios interactivos

| Recurso | Descripción |
|---------|-------------|
| [Java Programming MOOC — Part 3: Lists](https://java-programming.mooc.fi/part-3/2-lists) | Universidad de Helsinki: `ArrayList` con ejercicios autocorregidos, desde cero. |
| [Exercism — Java track](https://exercism.org/tracks/java) | Los ejercicios del concepto *lists* del track. |
