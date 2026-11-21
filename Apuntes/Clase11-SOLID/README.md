# Clase 11 — Principios SOLID

Cinco principios, cada uno en su carpeta, con un ejemplo **antes** y uno **después** que hacen lo mismo. Todos se ejecutan desde la terminal con un solo comando. La guía de la clase está en [Clase11-Guia.md](../Clase11-Guia.md).

| Letra | Principio | Ejemplo | En una frase |
|---|---|---|---|
| **S** | [Responsabilidad Única](01-srp/) — *Single Responsibility* | planilla de empleados | una clase responde a un solo grupo de personas |
| **O** | [Abierto/Cerrado](02-ocp/) — *Open/Closed* | métodos de envío | lo nuevo se agrega como clase nueva, sin editar lo que ya funciona |
| **L** | [Sustitución de Liskov](03-lsp/) — *Liskov Substitution* | cuentas de banco | un hijo cumple todas las promesas de su padre |
| **I** | [Segregación de Interfaces](04-isp/) — *Interface Segregation* | impresoras de oficina | varias interfaces pequeñas antes que una gorda |
| **D** | [Inversión de Dependencias](05-dip/) — *Dependency Inversion* | inscripción de materias | la regla del negocio no depende del detalle; el detalle depende de la regla |

Cada README tiene la misma estructura: el principio, la analogía, el antes, el después, **beneficios**, **costos y cuándo no aplicarlo**, dónde aparece en Sentinel, y una pregunta.

## De dónde viene SOLID

SOLID no es una ley ni un invento de una sola persona. Son cinco ideas de autores distintos, publicadas a lo largo de quince años, que **Robert C. Martin** ("Uncle Bob") reunió alrededor del año 2000 en su artículo *Design Principles and Design Patterns*. El nombre vino después: **Michael Feathers** notó que, puestas en cierto orden, las iniciales formaban la palabra SOLID.

| Letra | Quién y cuándo | La idea original |
|---|---|---|
| S | Robert C. Martin, años 90 | viene de la idea de **cohesión** de los años 70 |
| O | Bertrand Meyer, 1988 | un módulo debe poder extenderse sin modificarse |
| L | Barbara Liskov, 1987 | qué significa de verdad que un tipo sea "subtipo" de otro |
| I | Robert C. Martin, años 90 | nació en un proyecto real de software para impresoras Xerox |
| D | Robert C. Martin, 1996 | las reglas no deben depender de los detalles |

Barbara Liskov ganó el Premio Turing en 2008, en parte por este trabajo. La **L** es la única letra que tiene una definición matemática detrás; las otras cuatro son criterios de diseño.

## El problema que resuelven

Los cinco atacan el mismo enemigo: **el costo de cambiar el código**. Un programa que funciona hoy va a tener que cambiar mañana, y hay tres síntomas de que el diseño no lo va a soportar:

- **Rigidez:** un cambio pequeño obliga a tocar muchos archivos.
- **Fragilidad:** se arregla una cosa y se rompe otra que no tenía nada que ver.
- **Inmovilidad:** una parte útil no se puede reusar porque viene pegada a todo lo demás.

Cada principio corta una de las causas:

| Principio | La pregunta que hace | El síntoma que evita |
|---|---|---|
| S | ¿quién pide los cambios de esta clase? | arreglo algo para Finanzas y rompo algo de RRHH |
| O | ¿puedo agregar sin editar? | cada función nueva reabre código que ya funcionaba |
| L | ¿el hijo sirve en todo lugar donde sirve el padre? | el programa compila y se cae a mitad de una lista |
| I | ¿quien implementa esta interfaz usa todos sus métodos? | métodos vacíos o que lanzan "no soportado" |
| D | ¿hacia dónde apuntan las flechas? | no puedo probar una regla sin base de datos |

## Lo que ya vieron y lo nuevo

Tres de las cinco letras aparecieron en la [Clase 10](../Clase10-Principios/) con otro nombre. Hoy se profundizan y se agregan las dos que faltaban.

| Letra | En la Clase 10 | Qué se agrega hoy |
|---|---|---|
| S | [1. Cohesión y SRP](../Clase10-Principios/01-cohesion-srp/) | la definición precisa: una "razón para cambiar" es **un grupo de personas** |
| O | [4. Extensibilidad](../Clase10-Principios/04-reusability-extensibility/) | el compilador como aliado: lo que antes se olvidaba, ahora no compila |
| L | — **nuevo** | cuándo `extends` es legítimo: la pregunta que le faltaba al "ES-UN" de la Clase 8 |
| I | — **nuevo** | cómo diseñar las interfaces de la Clase 9: pequeñas y por capacidad |
| D | [3. Bajo acoplamiento](../Clase10-Principios/03-coupling-modularity/) | la **dirección** de las flechas, y quién es dueño de la interfaz |

## Beneficios

- **Los cambios quedan locales.** Un pedido nuevo toca una clase, no diez.
- **Se puede probar por partes.** Una regla se verifica sin base de datos, sin red y sin terminal.
- **El compilador trabaja para ti.** Varios errores que antes aparecían al ejecutar ahora aparecen al compilar.
- **Varias personas trabajan a la vez** sin pisarse: cada una en su clase.
- **Vocabulario común.** "Esto viola Liskov" se dice en cinco segundos y todo el equipo entiende lo mismo.

## Costos

SOLID no es gratis. Cada principio se paga con algo:

- **Más clases, más archivos, más saltos.** Para seguir un flujo hay que abrir cinco archivos en lugar de leer un método de arriba abajo.
- **Indirección.** Cuando todo es una interfaz, cuesta más responder "¿qué código se ejecuta aquí de verdad?".
- **Sobre-diseño.** Una interfaz con una sola implementación, preparada para un cambio que nunca llega, es peso muerto. Es el choque con **YAGNI** de la Clase 10.
- **Son criterios, no reglas.** No hay un compilador de SOLID. Dos personas con experiencia pueden no estar de acuerdo sobre si una clase tiene "una" responsabilidad.
- **Aplicados a ciegas, empeoran el código.** Un script de 50 líneas partido en doce clases no es más mantenible: es más difícil de leer.

**La regla práctica:** SOLID se aplica **donde el código cambia**. Primero se escribe lo simple. Cuando un cambio real duele, ese dolor dice qué principio hace falta, y ahí se refactoriza. Los ejemplos de esta carpeta siguen ese orden a propósito: primero el dolor, después el arreglo.

## Cómo correr un ejemplo

```bash
cd Apuntes/Clase11-SOLID/01-srp
java BeforeEmployee.java
java AfterEmployee.java
```

Cada archivo es un programa completo. Se ejecutan **directo con `java`**, sin `javac`: el antes y el después declaran clases con el mismo nombre a propósito, para que se puedan comparar, así que no se pueden compilar juntos.

Para ver exactamente qué cambió entre los dos:

```bash
diff BeforeEmployee.java AfterEmployee.java
```
