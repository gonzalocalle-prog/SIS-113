# Caza de errores — 7 bugs de POO

Cada programa tiene **un** error típico de quien empieza con clases. Dos no compilan, uno se cae, y cuatro corren pero dan una respuesta equivocada. Se ejecutan uno por uno, sin compilar aparte:

```bash
java Bug1VoidConstructor.java
```

## Respuestas (docente)

| Bug | Qué pasa | Causa | Corrección |
|---|---|---|---|
| 1 | No compila: *constructor Limit cannot be applied to given types; required: no arguments* | `public void Limit(...)` tiene tipo de retorno, así que es un **método** que se llama igual que la clase, no un constructor. Java pone el constructor vacío por defecto. | Quitar `void`. |
| 2 | 37 % "supera" el límite | `threshold = threshold` asigna el parámetro a sí mismo. El atributo queda en `0` y todo supera 0. | `this.threshold = threshold;` Mejor aún: hacerlo `final`, y el compilador lo detecta. |
| 3 | `NullPointerException: ... because "this.counter" is null` | El atributo se declaró pero nunca se creó el objeto. Una variable de tipo clase empieza en `null`. | `private final Counter counter = new Counter();` o crearlo en un constructor. |
| 4 | `alerts: 0` | `int count = 0;` dentro de `register()` crea una variable **local** con el mismo nombre. Se incrementa la local, que muere al terminar el método. | Borrar la declaración local; queda `count++;`. |
| 5 | `cpu: 2 hits` | `static` hace que `hits` sea uno solo para todas las reglas. Se comparte entre `memory` y `cpu`. | Quitar `static`: cada objeto con su propio contador. |
| 6 | No compila: *missing return statement* | El compilador no razona que `< 50` cubre lo que falta: ve un `if` / `else if` / `else if` sin `else`, y un camino sin `return`. | Cambiar el último `else if (percent < 50)` por `else`. |
| 7 | Imprime `alert` | `String` es inmutable: `toUpperCase()` y `concat()` **devuelven** un texto nuevo, no cambian el original. El valor devuelto se tiró. | `text = text.toUpperCase() + "!";` |

