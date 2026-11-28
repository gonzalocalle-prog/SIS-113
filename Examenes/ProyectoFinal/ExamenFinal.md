# Examen Final — Tu sistema en uso

**SIS-113 Programación II · Gestión 2-2026**

> En la revisión intermedia se preguntó: *¿funciona tu sistema y puedes explicarlo?*
> En el final la pregunta es: **¿le sirve a alguien de verdad, de principio a fin, y puedes cambiarlo cuando la realidad cambia?**

Hoy una IA puede escribir en una tarde un sistema que pasa una demo. Lo que no decide sola es **para quién** es, qué recorrido hace esa persona de principio a fin, y qué pasa cuando algo sale mal. Eso es lo que se evalúa, junto con la regla de todo el curso: **si no puedes explicar y modificar tu código en vivo, no es tuyo y no cuenta.**

Todo el examen gira alrededor de **un usuario y dos flujos**. Se presentan, se ejecutan, se cambian y se defienden.

---

## 1. Lo que se prepara: `docs/flujos.md`

Un solo documento en el repositorio, con tres secciones.

### El usuario

Una persona concreta, no "usuarios": *"la encargada de una farmacia de barrio"*, *"el administrador de un parqueo de 40 lugares"*.

- Qué necesita hacer.
- Cómo lo hace hoy, sin tu sistema: cuaderno, Excel, WhatsApp, memoria.

### Flujo 1 — Un día normal

El recorrido completo que esa persona hace un día cualquiera, **de principio a fin**, en pasos:

1. Al menos **tres operaciones encadenadas**, donde cada una usa lo que dejó la anterior. Ejemplo: *registrar los productos que llegaron → vender → ver cuánto se vendió hoy*.
2. Para cada paso: qué hace el usuario y qué debe mostrar el sistema.
3. Los datos con los que se va a ejecutar.

### Flujo 2 — Un día en que algo sale mal

El mismo usuario, otro día. El flujo incluye **al menos dos** de estas situaciones:

| Situación | Ejemplo |
|---|---|
| **Error al ingresar** | un precio negativo, letras donde va un número, un campo vacío, un registro duplicado |
| **Corregir algo ya registrado** | un producto se cargó con el precio equivocado y ya tiene ventas |
| **Cerrar y volver a abrir** | el programa se cierra a mitad del día: ¿qué se conserva? |
| **Mucho volumen** | 200 registros o más en lugar de 5: ¿se puede buscar, listar, leer? |

Para cada situación: qué hace el usuario y qué **debería** hacer el sistema.

> Un flujo que solo funciona con los datos que eligió su autor no es un flujo: es una demo. En el examen se pueden pedir otros datos.

---

## 2. Lo nuevo está permitido

Desde la revisión intermedia se pueden agregar funcionalidades, clases y mejoras **sin límite**. Con dos condiciones:

- **Lo nuevo aparece en algún flujo.** Una funcionalidad que el usuario no usa no suma.
- **Lo nuevo se defiende primero.** En la defensa, el docente empieza por lo que se agregó después de la revisión intermedia. Si se hizo con IA, va en `BITACORA-IA.md`: qué se pidió, qué se aceptó, qué se cambió a mano.

---

## 3. El examen (30 min por estudiante)

| Bloque | Min | Qué pasa |
|---|---|---|
| **1. El usuario** | 3 | Quién es, cómo lo hace hoy, qué cambia con el sistema. Sin código. |
| **2. Flujo 1 en vivo** | 6 | Se ejecuta de principio a fin. El docente puede pedir que se use otro dato en cualquier paso. |
| **3. Flujo 2 en vivo** | 6 | Igual. Si algo falla, se dice qué falló y por qué; no se esconde. |
| **4. El flujo cambia** | 8 | El docente cambia **una condición** de uno de los dos flujos. El estudiante lo implementa en vivo, **sin IA**, y vuelve a ejecutar el flujo. |
| **5. La defensa** | 7 | Primero lo nuevo desde la revisión intermedia. Después, una clase elegida por el docente: qué hace, por qué existe, qué principio aplica. Por último, una entrada de la bitácora: algo que la IA propuso y se **rechazó**. |

### Cómo cambia un flujo (bloque 4)

El cambio sale del mismo usuario. Es algo que en la vida real le pedirían al sistema la semana siguiente:

| Tipo de cambio | Ejemplo para la farmacia |
|---|---|
| **Una regla nueva** | "desde hoy los jubilados tienen 10 % de descuento" |
| **Un dato más** | "cada producto necesita la fecha de vencimiento" |
| **Una validación** | "no se puede vender un producto vencido" |
| **Una pregunta nueva** | "¿qué producto se vendió más esta semana?" |

- Pequeño a propósito: entre 10 y 30 líneas si el diseño es bueno.
- Se mira **cómo se busca** el lugar del cambio: ¿sabe en qué clase va?, ¿cuántos archivos toca? Un diseño con encapsulamiento, herencia y polimorfismo bien aplicados hace que el cambio sea corto. Ahí se demuestra la POO del curso.
- Se puede consultar la documentación de Java. No se puede usar IA.

---

## 4. Rúbrica

| Criterio | Excelente (90-100) | Bueno (70-89) | Suficiente (51-69) | Insuficiente (0-50) | Peso |
|---|---|---|---|---|---|
| **Los flujos** | Los dos flujos corren de principio a fin, también con otros datos; los errores se manejan con mensajes claros, sin excepciones en pantalla | Corren con tropiezos menores | Corre el flujo 1; el flujo 2 falla en parte | No hay flujos o no corren | **25 %** |
| **El cambio en vivo** | Encuentra el lugar correcto, lo implementa solo y el flujo vuelve a correr | Lo implementa con ayuda mínima | Lo implementa a medias o rompe otra parte | No puede empezar | **25 %** |
| **La defensa** | Explica lo nuevo, la clase elegida y la bitácora sin dudar; conecta con principios | Explica bien con alguna duda | Explica a grandes rasgos | No puede explicar su código | **30 %** |
| **Diseño POO** | Encapsulamiento, herencia y polimorfismo donde hacen falta, con razones | POO correcta, alguna justificación débil | POO presente pero decorativa | Sin POO real | **20 %** |

**Regla de autoría:** si el estudiante no puede explicar su código **ni** empezar el cambio en vivo, los otros criterios quedan en **Suficiente como máximo**. Un sistema que funciona pero que su autor no entiende no demuestra lo que el curso enseña.

**Entregables mínimos (sin estos no se presenta):** repositorio con historial de commits, `README` con instalación y uso, diagrama de clases, `BITACORA-IA.md` y `docs/flujos.md`.

---

## 5. Segunda instancia

Mismo formato, con los mismos flujos. Otro cambio en el bloque 4 y otra clase en la defensa.
