# D — Principio de Inversión de Dependencias (DIP)

## Principio

> 1. **Los módulos de alto nivel no deben depender de los de bajo nivel. Ambos deben depender de abstracciones.**
> 2. **Las abstracciones no deben depender de los detalles. Los detalles deben depender de las abstracciones.**

Dos palabras que hay que aclarar:

- **Alto nivel:** las **reglas** del negocio. Lo que hace valioso al programa. "Una materia tiene cupo limitado."
- **Bajo nivel:** los **detalles**. Lo que se podría reemplazar sin cambiar el negocio: la base de datos, el archivo, la red, la terminal.

El principio dice que lo importante no puede depender de lo reemplazable.

> Analogía: una lámpara y el enchufe de la pared. La lámpara no viene soldada a la central eléctrica. Entre las dos hay una forma acordada, el enchufe. La central puede cambiar de carbón a solar y la lámpara no se entera.

## ¿Qué se "invierte"?

La dirección de la flecha del detalle.

```
ANTES     EnrollmentService ──────────> MySqlEnrollmentDb
          (la regla)                    (el detalle)

DESPUÉS   EnrollmentService ──> EnrollmentStore <────── MySqlEnrollmentStore
          (la regla)            (interfaz)      <────── MemoryEnrollmentStore
```

Antes, la regla apuntaba al detalle. Después, el detalle apunta **hacia** la interfaz de la regla. La regla quedó sin flechas hacia afuera.

Un punto fino: **la interfaz le pertenece a la regla**, no a la base de datos. Por eso habla con palabras del negocio, `save` y `countFor`, y no con palabras de SQL, `insertRow` y `countRows`.

## Tres nombres que se confunden

| Nombre | Qué es | En el ejemplo |
|---|---|---|
| **Inversión de dependencias** (DIP) | el **principio**: hacia dónde apuntan las flechas | `EnrollmentService` conoce solo `EnrollmentStore` |
| **Inyección de dependencias** (DI) | la **técnica**: recibir la dependencia desde afuera | el constructor `EnrollmentService(EnrollmentStore store)` |
| **Contenedor / framework** | una **herramienta** que hace la inyección sola | Spring, más adelante en la carrera; aquí lo hace `main` a mano |

La Clase 10 enseñó la técnica. Hoy se ve el principio que la justifica.

## Antes — [BeforeEnrollment.java](BeforeEnrollment.java)

```java
class EnrollmentService {
    private final MySqlEnrollmentDb db = new MySqlEnrollmentDb();   // la regla crea su detalle

    boolean enroll(String student, String course) {
        if (db.countRows(course) >= CAPACITY) { ... }
        db.insertRow(student, course);
    }
}
```

- La clase con la regla del negocio nombra a MySQL y lo crea con `new`.
- Cambiar de base de datos obliga a editar la clase más importante del programa.
- Para comprobar una regla de tres líneas hay que conectarse a la base y escribir filas de verdad.

## Después — [AfterEnrollment.java](AfterEnrollment.java)

```java
interface EnrollmentStore {
    void save(String student, String course);
    int countFor(String course);
}

class EnrollmentService {
    private final EnrollmentStore store;

    EnrollmentService(EnrollmentStore store) { this.store = store; }
}

class MySqlEnrollmentStore  implements EnrollmentStore { ... }
class MemoryEnrollmentStore implements EnrollmentStore { ... }
```

- `EnrollmentService` no nombra ninguna base de datos y no tiene ningún `new` de un detalle.
- `main` es el único lugar que elige la clase concreta.
- La misma regla se comprueba con `MemoryEnrollmentStore`: cuatro `PASS`, sin conexión.

## Demo

```bash
java BeforeEnrollment.java
java AfterEnrollment.java
```

## Beneficios

- **La regla se prueba sola.** Sin base de datos, sin red, en milisegundos.
- **Los detalles se cambian sin tocar la regla.** MySQL, un archivo o la memoria: `EnrollmentService` no cambia.
- **Se puede empezar sin el detalle.** El equipo escribe las reglas con el almacén en memoria y decide la base de datos después.
- **Lo más valioso queda protegido.** Las reglas del negocio viven más que cualquier tecnología.
- **Es la base de Clean Architecture:** lo mismo, aplicado a capas enteras.

## Costos y cuándo no aplicarlo

- **Una interfaz más por cada dependencia**, y alguien tiene que armar todo en `main`. En programas grandes ese armado crece tanto que se usan frameworks.
- **Cuesta más seguir el código.** "Ir a la definición" de `store.save()` lleva a una interfaz, no al código que se ejecuta.
- **Interfaces con una sola implementación.** Si nunca va a existir una segunda, y tampoco hace falta un falso para probar, la interfaz no aporta nada. Otra vez YAGNI.
- **Abstracciones que gotean.** Si la interfaz copia los métodos del detalle, como `executeSql(String)`, no invirtió nada: solo le cambió el nombre.
- **Cuándo no:** DIP protege de lo **volátil**, no de todo lo concreto. `String`, `ArrayList` o `Math` son clases concretas y nadie les pone una interfaz encima: son estables. La pregunta es: *¿esto podría cambiar, o me impide probar?* Si la respuesta es no, un `new` directo está bien.

## En Sentinel Fase 3

- Abrir los `import` de [Sentinel](../../../sentinel/phase3-java/src/sentinel/app/Sentinel.java): `Collector`, `Rule`, `Renderer`. Ninguna clase concreta de entrada ni de salida. El motor es el alto nivel; `/proc`, la terminal y el CSV son los detalles, y todos apuntan hacia las interfaces.
- [Factory](../../../sentinel/phase3-java/src/sentinel/app/Factory.java) es el `main` del ejemplo: el único lugar con los `new`.
- [ReplayCollector](../../../sentinel/phase3-java/src/sentinel/collect/ReplayCollector.java) es el `MemoryEnrollmentStore` de Sentinel: por él las 46 pruebas corren sin Linux.
- **Para discutir:** `Sentinel` sí hace `new History()` y llama a `Contract.parse()` directamente. ¿Es una violación? No: `History` y `Contract` son parte del núcleo, no detalles de afuera. Es el mismo criterio que con `ArrayList`.

## Pregunta para la clase

En `AfterEnrollment`, `main` hace `new MySqlEnrollmentStore()`. ¿Eso no viola el principio? ¿Por qué tiene que existir al menos un lugar así en todo programa?
