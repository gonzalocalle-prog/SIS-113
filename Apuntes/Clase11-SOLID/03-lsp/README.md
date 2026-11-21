# L — Principio de Sustitución de Liskov (LSP)

## Principio

> **Donde el programa espera un objeto de la clase padre, debe poder recibir un objeto de cualquier clase hija, y seguir funcionando bien.**

Lo formuló Barbara Liskov en 1987. Es el principio que le pone una prueba seria al "ES-UN" de la Clase 8.

En la Clase 8 la pregunta era: *¿un X es un Y?* Esa pregunta engaña, porque se responde pensando en el mundo real. La pregunta de Liskov es sobre **comportamiento**:

> *¿Un X **se comporta** como un Y en todo lugar donde se usa un Y?*

Una clase padre es un **contrato**: una lista de promesas. La hija puede prometer más, pero no puede romper ninguna.

> Analogía: una llave de repuesto. Sirve si abre **todas** las puertas que abría la original. Si abre la casa pero no el garaje, no es un repuesto, aunque se vea igual.

## Las reglas, en concreto

Una clase hija cumple LSP si, al sobrescribir un método:

| Regla | Qué significa | Ejemplo de violación |
|---|---|---|
| No pide más | acepta todo lo que aceptaba el padre | el padre acepta cualquier monto positivo; la hija exige múltiplos de 100 |
| No da menos | cumple todo lo que el padre garantizaba al terminar | el padre garantiza que el saldo baja; en la hija no baja |
| No lanza sorpresas | no lanza excepciones que el padre no anunciaba | `UnsupportedOperationException` en un método heredado |
| Mantiene lo que siempre es cierto | no rompe las invariantes del padre | el padre garantiza saldo ≥ 0; la hija lo deja negativo |

**Señales de alerta** en el código:

- un método sobrescrito que lanza `UnsupportedOperationException` o queda vacío
- un `if (x instanceof Hija)` en el código que usa al padre: es alguien parchando una hija que no se comporta como las demás
- un comentario que dice "no llamar a este método en esta subclase"

## Antes — [BeforeAccounts.java](BeforeAccounts.java)

```java
class Account {
    void withdraw(double amount) { ... }          // promesa: se puede retirar
}

class FixedTermAccount extends Account {
    @Override
    void withdraw(double amount) {
        throw new UnsupportedOperationException(); // promesa rota
    }
}
```

- "Un depósito a plazo fijo **es una** cuenta": en el mundo real, sí.
- Pero `Account` promete `withdraw()`, y la hija no puede cumplirlo.
- `Bank.chargeMonthlyFee(List<Account>)` es código correcto: usa solo lo que `Account` ofrece.
- Compila. Al ejecutar: cobra a Ana, **se cae** en Luis, y Rosa nunca es cobrada.
- El parche típico, `if (account instanceof FixedTermAccount) continue;`, es el Drill 1 de la Clase 9: funciona hasta que aparece la siguiente hija especial.

## Después — [AfterAccounts.java](AfterAccounts.java)

```java
abstract class Account { deposit(), getBalance() }                    // lo común a TODAS

abstract class WithdrawableAccount extends Account { withdraw() }     // la promesa, un nivel abajo

class SavingsAccount   extends WithdrawableAccount { }
class FixedTermAccount extends Account { }                            // nunca prometió retirar
```

- El padre promete solo lo que es verdad para **todos** sus hijos.
- `chargeMonthlyFee` pide `List<WithdrawableAccount>`.
- Pasarle un plazo fijo **no compila**: el error se mudó de la ejecución al compilador.
- `payBonus(List<Account>)` sí recibe todas, porque todas pueden recibir un depósito.

## Demo

```bash
java BeforeAccounts.java
java AfterAccounts.java
```

En `AfterAccounts.java`, quitar el `//` de la línea `withFee.add(luis);` y volver a ejecutar: leer el mensaje del compilador.

## El ejemplo clásico: el cuadrado y el rectángulo

En matemáticas, un cuadrado es un rectángulo. En código:

```java
class Rectangle {
    void setWidth(int w)  { width = w; }
    void setHeight(int h) { height = h; }
    int area()            { return width * height; }
}

class Square extends Rectangle {
    @Override void setWidth(int w)  { width = w; height = w; }   // mantiene los lados iguales
    @Override void setHeight(int h) { width = h; height = h; }
}

void resize(Rectangle r) {
    r.setWidth(5);
    r.setHeight(10);
    // cualquiera espera area() == 50. Con un Square da 100.
}
```

`Rectangle` promete, sin decirlo, que cambiar el ancho **no toca** el alto. `Square` rompe esa promesa. La lección: la herencia se decide por el **comportamiento**, no por lo que dice el diccionario.

## Beneficios

- **El polimorfismo funciona de verdad.** Un bucle sobre `List<Account>` no necesita saber qué hijas existen.
- **Adiós al `instanceof`.** Si todas las hijas cumplen, nadie necesita preguntar cuál es cuál.
- **Hace posible Abierto/Cerrado.** Solo se puede agregar una clase "sin tocar nada" si la nueva se comporta como las demás. Sin la L, la O no funciona.
- **Errores al compilar, no al ejecutar.** Una jerarquía honesta deja que el compilador rechace el mal uso.

## Costos y cuándo no aplicarlo

- **Jerarquías más finas.** Apareció una clase intermedia, `WithdrawableAccount`. Con más capacidades, los niveles se multiplican. Cuando eso pasa, suele convenir **interfaces** o **composición** en lugar de más herencia.
- **El compilador no lo verifica.** Java revisa que las firmas coincidan, no que el comportamiento se respete. `Square` compila perfecto. LSP se cuida con pruebas y con revisión.
- **Los contratos suelen estar implícitos.** Nadie escribió "cambiar el ancho no cambia el alto". Hay que pensar qué asume quien usa la clase.
- **Menos reuso por herencia.** Respetar LSP a veces obliga a no heredar aunque haya código en común. Es el precio correcto: reusar código no es razón suficiente para `extends`.
- **Cuándo no:** LSP no se "desactiva". Lo que sí se puede evitar es la herencia: si no hay `extends` ni `implements`, no hay nada que sustituir.

## En Sentinel Fase 3

- **`evaluate()` es `final`** en [Rule](../../../sentinel/phase3-java/src/sentinel/rules/Rule.java): ninguna hija puede cambiar cómo se cuentan las alertas. Es LSP reforzado por el compilador: la promesa "cada alerta suma uno" no se puede romper.
- **El arreglo de la Clase 8 fue un arreglo de Liskov.** `Rule` exigía un umbral entre 1 y 100, y `LoadRule` no cabía. La solución fue bajar esa exigencia a [PercentRule](../../../sentinel/phase3-java/src/sentinel/rules/PercentRule.java): el mismo movimiento que `WithdrawableAccount`.
- [PlainRenderer](../../../sentinel/phase3-java/src/sentinel/render/PlainRenderer.java) extiende `ConsoleRenderer` y solo cambia dos etiquetas: sustituye sin sorpresas.
- [CsvRenderer](../../../sentinel/phase3-java/src/sentinel/render/CsvRenderer.java) **no** hereda de `ConsoleRenderer`, a propósito: un CSV no se comporta como un reporte de consola.
- `Popcorn extends Pass`, el Drill 5 de la Clase 8, era una violación de Liskov: una palomita no se comporta como una entrada.

## Pregunta para la clase

Java tiene `List.of(1, 2, 3)`, que devuelve una `List`. Si le llaman `.add(4)`, lanza `UnsupportedOperationException`. ¿Es una violación de Liskov dentro del propio Java? ¿Qué habrían hecho distinto?
