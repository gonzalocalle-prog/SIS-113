# Clase 11

**SIS-113 Programación II**

> La Clase 10 dio ocho criterios generales. Hoy, los cinco más citados de la programación orientada a objetos, con nombre propio: **SOLID**. Tres ya los conocen con otro nombre; dos son nuevos, la **L** y la **I**. Para cada uno: el dolor, el arreglo, lo que se gana y lo que cuesta.

Material: [Clase11-SOLID/](Clase11-SOLID/), una carpeta por letra, con un ejemplo **antes** y uno **después**.

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Nombra los cinco principios SOLID y explica cada uno con una frase propia
2. Reconoce en un código **qué letra se viola** y qué dolor concreto causa
3. Explica qué cambió entre el antes y el después, y **qué costó** ese cambio
4. Usa la pregunta de Liskov, *¿se comporta como su padre?*, para decidir si un `extends` es legítimo
5. Distingue inversión de dependencias (el principio) de inyección de dependencias (la técnica)
6. Da, para cada principio, un caso donde **no** conviene aplicarlo

---

## Reglas de la sesión

- **Primero el dolor, después el nombre.** Cada principio empieza ejecutando el antes. El nombre llega cuando ya se vio el problema.
- **Predecir antes de ejecutar.** Antes de correr cada archivo, alguien dice qué va a imprimir.
- **Todo principio tiene precio.** Ninguno se da por visto sin que alguien diga cuánto costó el después.
- **Sin IA.** Hoy se lee, se ejecuta y se discute.

---

## Desarrollo

### Parte 1 — De ocho criterios a cinco letras (10 min)

En la pizarra, las cinco letras en vertical, vacías. Pregunta: de los ocho principios de la semana pasada, ¿cuáles creen que entran aquí?

Se llenan tres con lo que ya saben:

| Letra | Nombre | En la Clase 10 era |
|---|---|---|
| **S** | Single Responsibility | 1. Cohesión y SRP |
| **O** | Open/Closed | 4. Extensibilidad |
| **L** | Liskov Substitution | — nuevo |
| **I** | Interface Segregation | — nuevo |
| **D** | Dependency Inversion | 3. Bajo acoplamiento |

Dos datos de contexto, del [README](Clase11-SOLID/README.md):

- SOLID no lo inventó una persona. Son ideas de 1987 a 1996 que Robert C. Martin reunió y Michael Feathers bautizó.
- Los cinco atacan lo mismo: **el costo de cambiar**. Los síntomas son tres: rigidez, fragilidad e inmovilidad.

### Cómo se trabaja cada letra (≈18 min cada una)

1. **Ejecutar el antes** y encontrar el dolor en la salida.
2. **Nombrar** el principio y leer la analogía.
3. **Ejecutar el después** y señalar qué cambió. `diff` ayuda.
4. **Beneficios y costos:** dos de cada uno, dichos por estudiantes antes de abrir el README.
5. **La pregunta** del final del README.

### S — [Responsabilidad Única](Clase11-SOLID/01-srp/)

**Una clase responde a un solo actor.**
Analogía: la secretaria con tres jefes.
Finanzas pide un cambio en las horas regulares, y el reporte de RRHH empieza a decir que quien trabajó 52 horas tuvo una semana incompleta. Nadie tocó ese reporte.

Lo nuevo respecto a la Clase 10: una "razón para cambiar" es **un grupo de personas**. La pregunta deja de ser "¿cuántas cosas hace?" y pasa a ser "¿quién me va a pedir que la cambie?".

Para discutir: el después tiene dos cálculos de horas casi iguales. ¿Viola DRY? No: son dos reglas distintas que hoy se parecen.

### O — [Abierto/Cerrado](Clase11-SOLID/02-ocp/)

**Lo nuevo se agrega como clase nueva.**
Analogía: el teléfono y las apps.
El retiro en tienda llega en **-1 días**: se agregó en una cadena de `if` y se olvidó en la otra.

Lo nuevo respecto a la Clase 10: el compilador como aliado. En el después, olvidar `days()` no compila.

Para discutir: el diseño está abierto a nuevos métodos de envío. ¿Y si piden un método nuevo en la interfaz, como `insurance()`? Hay que tocar todas las clases. Ningún diseño está cerrado contra todo.

### L — [Sustitución de Liskov](Clase11-SOLID/03-lsp/) — la letra nueva

**Un hijo cumple todas las promesas de su padre.**
Analogía: la llave de repuesto.
El banco cobra la comisión a una lista de cuentas: cobra a Ana, **se cae** en el plazo fijo de Luis, y Rosa nunca es cobrada. Y compilaba.

Esta es la parte central de la clase. Tres momentos:

1. **La pregunta cambia.** En la Clase 8 era "¿un X *es un* Y?". Desde hoy es "¿un X *se comporta como* un Y?". Un plazo fijo es una cuenta; no se comporta como una.
2. **El cuadrado y el rectángulo**, en la pizarra, sin ejecutar. Pedir que predigan el área antes de mostrar el resultado. Está en el README.
3. **El arreglo:** el padre promete solo lo que es verdad para todos. En vivo, quitar el `//` de `withFee.add(luis);` y leer el error del compilador.

Señales de alerta para anotar: un método heredado que lanza `UnsupportedOperationException`, y un `instanceof` en el código que usa al padre.

### I — [Segregación de Interfaces](Clase11-SOLID/04-isp/) — la otra letra nueva

**Varias interfaces pequeñas antes que una gorda.**
Analogía: el menú del restaurante.
`BasicPrinter` fue obligada a prometer que escanea y manda fax. Al pedirle escanear, se cae.

Conectar con la L: es el mismo síntoma, `UnsupportedOperationException`. Una interfaz gorda **fabrica** violaciones de Liskov.

Para discutir: `close()` en `StdinCollector` está vacío. ¿Es una violación? No: "no hay nada que liberar" es una respuesta verdadera. Vacío porque no hace falta no es lo mismo que vacío porque no se puede.

### D — [Inversión de Dependencias](Clase11-SOLID/05-dip/)

**La regla no depende del detalle; el detalle depende de la regla.**
Analogía: la lámpara y el enchufe.
Para comprobar la regla "el cupo es 2" hay que conectarse a MySQL.

Dibujar las flechas en la pizarra, antes y después, y preguntar: ¿qué se invirtió? La flecha del detalle.

Lo nuevo respecto a la Clase 10, en una tabla para copiar:

| Nombre | Qué es |
|---|---|
| Inversión de dependencias | el principio: hacia dónde apuntan las flechas |
| Inyección de dependencias | la técnica: recibir por el constructor |
| Framework de inyección | la herramienta: lo hace solo. Spring, más adelante |

> **Si el tiempo no alcanza:** la S y la O son repaso y pueden ir en 10 minutos cada una. La L y la I no se recortan.

### Parte 3 — Lo que cuesta SOLID (10 min)

Los cinco después tienen algo en común: **más clases que el antes**. Contar en la pizarra:

| Letra | Clases antes | Clases después |
|---|---|---|
| S | 1 | 4 |
| O | 1 | 1 interfaz + 5 clases |
| L | 4 | 5 |
| I | 1 interfaz + 2 clases | 3 interfaces + 4 clases |
| D | 2 | 1 interfaz + 3 clases |

Pregunta: ¿siempre vale la pena? Respuesta a construir entre todos: **SOLID se aplica donde el código cambia.** Primero lo simple. Cuando un cambio real duele, ese dolor dice qué letra hace falta.

Tres maneras de aplicarlo mal, para dejar anotadas:

- una interfaz para una clase que nunca va a tener una segunda implementación
- una clase por método, en nombre de la S
- un programa de 50 líneas repartido en doce archivos

### Parte 4 — Sentinel bajo la lupa (10 min)

Cada README tiene una sección **En Sentinel**. La tabla completa:

| Letra | Dónde lo cumple la Fase 3 | Para discutir |
|---|---|---|
| S | una clase por razón de cambio: `Contract`, `Config`, cada renderer | — |
| O | una regla o una salida nueva es una clase nueva | `Factory` cambia una línea; `Renderer` no está cerrada a métodos nuevos |
| L | `evaluate()` es `final`; el umbral 1–100 bajó de `Rule` a `PercentRule` para que `LoadRule` quepa; `CsvRenderer` no hereda de `ConsoleRenderer` | — |
| I | `Collector` y `Renderer`: ninguna implementación lanza "no soportado" | `close()` vacío en `StdinCollector` |
| D | los `import` del motor solo nombran `Collector`, `Rule` y `Renderer` | el motor hace `new History()`: ¿detalle o núcleo? |

El punto para cerrar: el arreglo de `LoadRule` en la Clase 8 **fue** un arreglo de Liskov. Ya lo habían hecho; hoy tiene nombre.

### Cierre — Cómo se sostienen entre sí (5 min)

Las cinco letras no son independientes:

- sin la **L**, la **O** no funciona: no se puede agregar una clase "sin tocar nada" si la nueva no se comporta como las demás
- la **I** previene violaciones de la **L**: una interfaz pequeña no obliga a prometer de más
- la **D** necesita interfaces, y la **I** dice cómo cortarlas
- la **S** decide qué va en cada clase antes de que las otras cuatro entren en juego

Lo que viene después, Clean Architecture, es la **D** aplicada a capas enteras.

---

## Verificación de salida

Antes de irse, cada estudiante responde en una hoja:

- [ ] Escribe las cinco letras con su nombre y una frase propia para cada una
- [ ] Mira este código y dice qué letras viola:
  ```java
  interface Worker {
      void work();
      void eat();
  }

  class Robot implements Worker {
      public void work() { /* ... */ }
      public void eat()  { throw new UnsupportedOperationException(); }
  }

  class Factory {
      private final Robot robot = new Robot();

      void run(String shift) {
          if (shift.equals("day"))        { robot.work(); }
          else if (shift.equals("night")) { robot.work(); robot.work(); }
      }
  }
  ```
  Respuesta esperada: **I**, porque `Worker` obliga a `Robot` a tener `eat()`; **L**, porque `Robot` no sirve donde se espera un `Worker` que come; **D**, porque `Factory` crea su `Robot` con `new` en lugar de recibir un `Worker`; **O**, porque un turno nuevo obliga a editar `run()`.
- [ ] Explica por qué un cuadrado no debería heredar de un rectángulo, aunque en matemáticas lo sea
- [ ] Da un caso donde aplicar un principio SOLID sería un error

---

## Tarea para la casa

En `bitacora-terminal/java/clase11/`, con commits por parte.

### Parte 1 — SOLID en tu propio código

Sobre lo que ya escribiste: Biblioteca, Cine o Estacionamiento.

1. En `investigacion/solid.md`, una tabla con las **cinco** letras:

   | Letra | ¿La cumplo o la violo? (archivo y clase) | Evidencia | Si la violo: cómo lo arreglaría y qué costaría |
   |---|---|---|---|

   Para la **L**, revisar cada `extends` del proyecto con la pregunta: ¿la hija se comporta como el padre en todo lugar donde se usa el padre?

2. Arreglar **dos** violaciones, de letras distintas. Un commit antes y uno después de cada arreglo.

### Parte 2 — Liskov y Abierto/Cerrado en Sentinel

En la copia propia de `phase3-java`:

1. **L — una prueba de contrato.** En `SentinelCheck`, agregar una sección que reciba una lista con **una regla de cada clase** y compruebe, para todas con el mismo bucle, tres promesas de `Rule`:
   - `describe()` no devuelve vacío
   - `evaluate(null)` lanza `IllegalArgumentException`
   - después de un `evaluate()` que devuelve `true`, `getHits()` subió exactamente en uno

   Si una regla futura rompe una promesa, esta prueba lo detecta. `./build.sh` tiene que dar más de 46, todas en verde.

2. **O — agregar sin modificar.** Crear un `JsonRenderer` con la opción `--render=json`. En `investigacion/ocp-sentinel.md`, la lista de archivos **creados** y **editados**, y para cada editado: ¿es núcleo o es borde?

### Parte 3 — Una pregunta escrita

En `investigacion/costos-solid.md`, media página: elegir **una** letra y describir una situación concreta donde aplicarla empeoraría el código. Qué se perdería y qué harían en su lugar.

### Entrega

- Link al repositorio actualizado, antes de la próxima clase.
- Commits por parte; un solo commit final es una tarea incompleta.

> **Regla de IA:** verde para entender un principio o pedir un ejemplo distinto. Los arreglos de las Partes 1 y 2 se hacen a mano. En la próxima clase, cualquier estudiante puede ser invitado a explicar uno de sus `extends` con la pregunta de Liskov.

---

## Referencias

*(contenido en inglés — leer documentación técnica en inglés es parte del oficio)*

| Recurso | Descripción |
|---------|-------------|
| [Wikipedia — SOLID](https://en.wikipedia.org/wiki/SOLID) | El resumen de las cinco letras, con el origen de cada una. |
| [Robert C. Martin — The Single Responsibility Principle](https://blog.cleancoder.com/uncle-bob/2014/05/08/SingleReponsibilityPrinciple.html) | La definición por actores, explicada por su autor. Es la base del ejemplo de la S. |
| [Wikipedia — Liskov substitution principle](https://en.wikipedia.org/wiki/Liskov_substitution_principle) | Las reglas formales: precondiciones, postcondiciones e invariantes. |
| [Brett Schuchert — DIP in the Wild](https://martinfowler.com/articles/dipInTheWild.html) | Inversión de dependencias en proyectos reales, en el sitio de Martin Fowler. |
| [Robert C. Martin — Solid Relevance](https://blog.cleancoder.com/uncle-bob/2020/10/18/Solid-Relevance.html) | Su respuesta a quienes dicen que SOLID ya no aplica. Útil para la Parte 3 de la tarea. |
| [Baeldung — A Solid Guide to SOLID Principles](https://www.baeldung.com/solid-principles) | Los cinco principios con ejemplos cortos en Java. |
