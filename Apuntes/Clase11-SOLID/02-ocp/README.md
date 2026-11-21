# O — Principio Abierto/Cerrado (OCP)

## Principio

> **Una clase debe estar abierta a la extensión y cerrada a la modificación.**

En palabras simples: para agregar un comportamiento nuevo se escribe **código nuevo**; no se edita el código que ya funcionaba y ya estaba probado.

En Java, la herramienta es la que ya conocen de la Clase 9: una **interfaz** (o una clase abstracta) y una clase por variante.

> Analogía: un teléfono y sus aplicaciones. Instalar una app nueva no obliga a abrir el teléfono y soldar nada. El teléfono está cerrado; la tienda de apps está abierta.

## Antes — [BeforeShipping.java](BeforeShipping.java)

```java
double cost(String method, double kilos) {
    if (method.equals("standard"))      { ... }
    else if (method.equals("express"))  { ... }
    else if (method.equals("pickup"))   { ... }   // agregado la semana pasada
}

int days(String method) {
    if (method.equals("standard"))      { ... }
    else if (method.equals("express"))  { ... }
    return -1;                                    // "pickup" se olvidó aquí
}
```

- Cada método de envío vive repartido en **dos** cadenas de `if`.
- Agregar uno obliga a abrir y editar las dos.
- Alguien agregó `pickup` en una y se olvidó de la otra. El compilador no avisó.
- La salida dice que el retiro en tienda llega en **-1 días**.

## Después — [AfterShipping.java](AfterShipping.java)

```java
interface ShippingMethod {
    String name();
    double cost(double kilos);
    int days();
}

class PickupShipping implements ShippingMethod { ... }
class DroneShipping  implements ShippingMethod { ... }   // nuevo: ninguna otra línea cambió
```

- Una interfaz y una clase por método de envío. Todo lo de un método está **junto**.
- `Checkout` no tiene ningún `if` y no sabe cuántos métodos existen.
- `DroneShipping` se agregó como clase nueva.
- Olvidar `days()` ya no es posible: la clase **no compila**.

## Demo

```bash
java BeforeShipping.java
java AfterShipping.java
```

## Beneficios

- **Lo que funcionaba sigue funcionando.** Código que no se toca no se rompe.
- **El compilador revisa que no falte nada.** La interfaz es la lista de lo que cada variante debe responder.
- **Lo relacionado queda junto.** Todo sobre el envío exprés está en `ExpressShipping`, no repartido en cinco métodos.
- **Trabajo en paralelo.** Dos personas agregan dos métodos de envío sin editar el mismo archivo.

## Costos y cuándo no aplicarlo

- **Más clases y más indirección.** Dos métodos con `if` pasaron a ser una interfaz y cuatro clases.
- **Hay que adivinar por dónde va a crecer.** El diseño quedó abierto a *nuevos métodos de envío*. Si lo que llega es una *nueva pregunta* (por ejemplo `insurance()`), hay que editar la interfaz y **todas** las clases. Ningún diseño está cerrado contra todos los cambios: se elige contra cuál.
- **Choca con YAGNI.** Preparar un punto de extensión que nadie pidió es sobre-diseño. Es la pregunta escrita de la tarea de la Clase 10.
- **Algún lugar sí se modifica.** Alguien tiene que hacer `new DroneShipping()`. El principio protege el núcleo, no el borde.
- **Cuándo no:** con dos o tres casos que no cambian, un `if` es más claro. Una regla práctica: el primer `if` se escribe; cuando el mismo `if` aparece en un **segundo** lugar, o llega la **tercera** variante, es momento de la interfaz.

## En Sentinel Fase 3

- Una regla nueva es una clase nueva que extiende [Rule](../../../sentinel/phase3-java/src/sentinel/rules/Rule.java); una salida nueva implementa [Renderer](../../../sentinel/phase3-java/src/sentinel/render/Renderer.java). El motor, [Sentinel](../../../sentinel/phase3-java/src/sentinel/app/Sentinel.java), no se toca.
- [Factory](../../../sentinel/phase3-java/src/sentinel/app/Factory.java) es el borde que sí se modifica: una línea por clase nueva.
- **El mismo límite que el ejemplo:** `Renderer` está abierta a nuevas salidas, pero no a nuevos métodos. Agregar un quinto método a la interfaz obliga a editar los tres renderers y el `RecordingRenderer` de las pruebas.

## Pregunta para la clase

Para agregar un `JsonRenderer` a Sentinel con la opción `--render=json`, ¿qué archivos se crean y cuáles se editan? ¿Los editados son núcleo o borde?
