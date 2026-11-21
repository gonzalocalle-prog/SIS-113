# S — Principio de Responsabilidad Única (SRP)

## Principio

> **Una clase debe tener una, y solo una, razón para cambiar.**

La Clase 10 se quedó ahí. Hoy la definición precisa: una "razón para cambiar" es **un grupo de personas que pide cambios**. Robert C. Martin lo llama un **actor**.

> **Una clase debe responder a un solo actor.**

SRP **no** significa "una clase hace una sola cosa" ni "una clase tiene un solo método". Una clase puede tener diez métodos y cumplir SRP, si los diez cambian por pedido de la misma gente.

> Analogía: una secretaria que trabaja para tres jefes a la vez. Cada jefe le cambia las prioridades sin avisar a los otros dos. El problema no es la cantidad de trabajo, es que recibe órdenes de tres lados.

## Antes — [BeforeEmployee.java](BeforeEmployee.java)

```java
class Employee {
    private int regularHours() { ... }   // compartido

    double calculatePay() { ... }        // lo pide Finanzas
    String reportHours()  { ... }        // lo pide RRHH
    String save()         { ... }        // lo pide Sistemas
}
```

- Tres actores, una clase.
- `calculatePay()` y `reportHours()` comparten el método privado `regularHours()`.
- Finanzas pide que las horas regulares bajen de 44 a 40. Alguien cambia `regularHours()`.
- El pago queda bien. El reporte de RRHH ahora dice que Ana, que trabajó 52 horas, tuvo una **semana incompleta**.
- Nadie tocó `reportHours()`. Se rompió igual.

## Después — [AfterEmployee.java](AfterEmployee.java)

```java
final class Employee      { /* solo datos */ }
class PayCalculator       { double calculatePay(Employee e) { ... } }   // Finanzas
class HoursReporter       { String reportHours(Employee e)  { ... } }   // RRHH
class EmployeeRepository  { String save(Employee e)         { ... } }   // Sistemas
```

- Una clase por actor. `Employee` queda solo con los datos.
- Finanzas y RRHH tienen **cada uno su propia** idea de "horas regulares": 40 y 44.
- Un cambio de Finanzas toca `PayCalculator` y nada más.

## Demo

```bash
java BeforeEmployee.java
java AfterEmployee.java
```

Mirar la línea de RRHH en las dos salidas.

## Beneficios

- **Un cambio no rompe lo de otro.** El pedido de un actor no puede afectar a los demás.
- **Menos conflictos en Git.** Dos personas con dos pedidos distintos editan dos archivos distintos.
- **Clases que se pueden probar solas.** `PayCalculator` se prueba sin reporte y sin base de datos.
- **Nombres que explican.** `HoursReporter` dice más que "el método 7 de `Employee`".

## Costos y cuándo no aplicarlo

- **Más clases.** Una pasó a ser cuatro. En un programa pequeño eso puede ser más ruido que ayuda.
- **Datos separados del comportamiento.** `Employee` quedó como una bolsa de datos con getters. Llevado al extremo, se pierde lo que se ganó con el encapsulamiento de la Clase 6.
- **Parece que viola DRY.** Hay dos cálculos de horas regulares casi iguales. No es duplicación: son **dos reglas distintas que hoy se parecen**. DRY habla de no repetir *conocimiento*, no de no repetir *líneas*. Unirlas fue justamente el error del antes.
- **Dividir de más.** Una clase por método tampoco es SRP. Si dos métodos siempre cambian juntos y por la misma razón, van juntos.
- **Cuándo no:** si hay un solo actor, o el código es un script que no va a crecer, no hace falta partir nada. La pregunta útil no es "¿cuántas cosas hace?" sino "¿quién me va a pedir que la cambie?".

## En Sentinel Fase 3

Los actores de Sentinel no son departamentos, pero la pregunta es la misma: ¿quién pide el cambio?

| Clase | Quién pide que cambie |
|---|---|
| [Contract](../../../sentinel/phase3-java/src/sentinel/model/Contract.java) | quien define el formato de la línea JSON: el colector |
| [ConsoleRenderer](../../../sentinel/phase3-java/src/sentinel/render/ConsoleRenderer.java) | quien mira la terminal |
| [CsvRenderer](../../../sentinel/phase3-java/src/sentinel/render/CsvRenderer.java) | quien abre la hoja de cálculo |
| [Config](../../../sentinel/phase3-java/src/sentinel/app/Config.java) | quien escribe el comando |
| [las reglas](../../../sentinel/phase3-java/src/sentinel/rules/) | quien decide qué es una alerta |

## Pregunta para la clase

En su proyecto del Cine, ¿hay alguna clase que responda a dos actores, por ejemplo al cajero y al administrador? ¿Qué método compartido podría romperse como `regularHours()`?
