# I — Principio de Segregación de Interfaces (ISP)

## Principio

> **Ninguna clase debe estar obligada a depender de métodos que no usa.**

En palabras simples: mejor **varias interfaces pequeñas**, cada una con un propósito, que una sola interfaz gorda que sirve para todo.

Es el SRP aplicado a las interfaces. Y mira en las dos direcciones:

- **quien implementa** no debería escribir métodos que no puede cumplir
- **quien usa** no debería recibir más de lo que necesita

> Analogía: el menú de un restaurante. Nadie te obliga a pedir entrada, sopa, segundo, postre y café para poder tomar un café. Pides lo que vas a consumir.

El principio nació en un caso real: Robert C. Martin asesoraba a Xerox, donde una sola clase atendía todas las tareas de una impresora multifunción y cada cambio pequeño obligaba a recompilar todo. El ejemplo de hoy es ese mismo problema, en chico.

## Antes — [BeforePrinter.java](BeforePrinter.java)

```java
interface OfficeMachine {
    void print(String document);
    void scan(String document);
    void fax(String document, String number);
}

class BasicPrinter implements OfficeMachine {
    public void print(String document) { ... }
    public void scan(String document)  { throw new UnsupportedOperationException(); }
    public void fax(String d, String n) { throw new UnsupportedOperationException(); }
}
```

- La interfaz obliga a `BasicPrinter` a prometer tres cosas. Puede hacer una.
- No hay nada honesto que escribir en `scan()`: o queda vacío, o lanza una excepción.
- Quien recibe un `OfficeMachine` cree que puede escanear. Compila, y se cae al ejecutar.
- Una interfaz gorda **fabrica** violaciones de Liskov: por eso la I y la L suelen aparecer juntas.

## Después — [AfterPrinter.java](AfterPrinter.java)

```java
interface Printer         { void print(String document); }
interface DocumentScanner { void scan(String document); }
interface Fax             { void fax(String document, String number); }

class BasicPrinter implements Printer { ... }
class OfficeCenter implements Printer, DocumentScanner, Fax { ... }
class PhoneCamera  implements DocumentScanner { ... }
```

- Una interfaz por **capacidad**.
- Cada clase firma solo los contratos que cumple. Una clase puede implementar varias interfaces: es la diferencia con `extends` que vieron en la Clase 9.
- `PhoneCamera` solo escanea, y entra sin inventar métodos.
- Meter una `BasicPrinter` en la lista de escáneres **no compila**.
- `Office.sendByFax(Fax fax, ...)` pide un `Fax`, no una máquina completa: el parámetro dice exactamente qué necesita.

## Demo

```bash
java BeforePrinter.java
java AfterPrinter.java
```

## Beneficios

- **Sin métodos de mentira.** Desaparecen los cuerpos vacíos y los `UnsupportedOperationException`.
- **Firmas que documentan.** Un método que recibe `Printer` avisa que solo va a imprimir.
- **Dobles de prueba pequeños.** Para probar algo que imprime, el falso implementa un método, no tres.
- **Cambios que no se propagan.** Agregar un método a `Fax` no obliga a tocar `BasicPrinter`.
- **Clases nuevas que encajan.** `PhoneCamera` no tenía lugar en el diseño anterior.

## Costos y cuándo no aplicarlo

- **Muchas interfaces.** Llevado al extremo, una interfaz por método: cuesta encontrar cuál es cuál y ponerles nombre.
- **Firmas más largas.** Quien necesita imprimir **y** escanear ahora recibe dos parámetros, o hace falta una interfaz que combine las dos.
- **Hay que saber cómo se usa.** Las interfaces se cortan según quién las usa, y eso no siempre se sabe al principio. Cortar antes de tiempo es adivinar.
- **Cuándo no:** si **todas** las clases que implementan la interfaz usan **todos** sus métodos, la interfaz está bien, aunque tenga seis. ISP no dice "interfaces de un método"; dice "sin métodos forzados". La señal para dividir es concreta: la primera vez que una clase tiene que dejar un método vacío.

## En Sentinel Fase 3

- [Collector](../../../sentinel/phase3-java/src/sentinel/collect/Collector.java) tiene tres métodos y [Renderer](../../../sentinel/phase3-java/src/sentinel/render/Renderer.java) cuatro. Ninguna implementación lanza `UnsupportedOperationException`. Están bien cortadas.
- **Un caso para discutir:** `close()` en [StdinCollector](../../../sentinel/phase3-java/src/sentinel/collect/StdinCollector.java) está vacío. ¿Es una violación? No: "no tengo nada que liberar" es una respuesta **verdadera** al contrato, y quien llama no nota la diferencia. Vacío porque no hay nada que hacer no es lo mismo que vacío porque no se puede.
- **Dónde podría aparecer mañana:** si alguien pide una salida que solo muestre el resumen final, tendría que implementar `start()`, `show()` e `invalid()` sin hacer nada. Uno solo así es tolerable. Si llegan tres, es la señal para separar `Renderer`.

## Pregunta para la clase

En la Clase 9 escribieron `PaymentMethod` para el Cine. Si mañana se le agrega `refund()`, ¿todas las formas de pago pueden cumplirlo? ¿El efectivo se devuelve igual que una tarjeta? ¿Agregarían el método a la interfaz, o crearían otra?
