# Clase 4 — Ejercicios de Bash

**SIS-113 Programación II** · Variables, argumentos, `if`, `while`/`for` y funciones

Reglas: cada script se escribe **a mano** (sin IA), lleva **shebang**, se hace ejecutable con `chmod u+x` y se ejecuta con `./`. Se trabaja en `bitacora-terminal/scripts/`, un commit por ejercicio.

Antes de pedir ayuda, revisar los 4 errores clásicos: espacios alrededor del `=`, olvidar el `$` al leer, olvidar las comillas en `"$variable"`, y ejecutar sin permiso.

---

## Núcleo (todos en clase)

### Ejercicio 1 — `presentacion.sh` · variables

Script con tres variables (`nombre`, `materia`, `comando_favorito`) que imprime:

```
Soy Ana, estudiante de SIS-113.
Mi comando favorito hasta ahora es: grep
```

**Reto extra:** que la tercera línea diga cuántos caracteres tiene el nombre — pista: `echo "$nombre" | wc -c`.

---

### Ejercicio 2 — `saludo.sh` · argumentos + if

Recibe un nombre como argumento. Si no llega ninguno, muestra el uso y termina con error:

```
$ ./saludo.sh
Uso: ./saludo.sh <nombre>
$ echo $?
1
$ ./saludo.sh Ana
Hola, Ana
```

La prueba de las **dos rutas** es parte del ejercicio: se debe mostrar funcionando con y sin argumento, y verificar el código de salida con `echo $?`.

---

### Ejercicio 3 — `clasificar.sh` · if / elif con archivos

Recibe una ruta y responde qué es:

```
$ ./clasificar.sh /etc/hostname
/etc/hostname es un archivo
$ ./clasificar.sh /proc
/proc es un directorio
$ ./clasificar.sh /no/existe
/no/existe no existe
```

Pistas: `[ -f ruta ]`, `[ -d ruta ]`, `elif`, `else`.

---

### Ejercicio 4 — `tabla.sh` · for

Recibe un número e imprime su tabla de multiplicar del 1 al 10:

```
$ ./tabla.sh 7
7 x 1 = 7
7 x 2 = 14
...
7 x 10 = 70
```

Pistas: `for i in $(seq 1 10)` y aritmética con `$((...))`. Debe validar el argumento como en el Ejercicio 2.

---

### Ejercicio 5 — `latido.sh` · while + sleep (el corazón de un monitor)

Recibe un número `n` y late `n` veces, una por segundo, mostrando el número de latido y la hora:

```
$ ./latido.sh 3
latido 1 — 10:42:01
latido 2 — 10:42:02
latido 3 — 10:42:03
fin
```

Pistas: `while [ $i -le $n ]`, `sleep 1`, `date +%T`, incrementar con `i=$((i + 1))`. Este es exactamente el esqueleto del `while` del colector — solo que el colector nunca termina.

---

## Integradores (quien termina el núcleo)

### Ejercicio 6 — `linea.sh` · funciones

Refactorizar `tabla.sh`: una función `separador()` que imprime `====================` y una función `fila()` que recibe dos números y imprime una línea de la tabla. El cuerpo principal solo llama funciones:

```
$ ./linea.sh 7
====================
7 x 1 = 7
...
7 x 10 = 70
====================
```

---

### Ejercicio 7 — `vigia.sh` · TODO JUNTO (mini-Sentinel propio)

Un vigilante de memoria escrito desde cero, sin `jq`: recibe un umbral (%), y cada segundo lee `/proc/meminfo`, calcula el porcentaje usado y decide:

```
$ ./vigia.sh 10
[ok] memoria al 5%
[ok] memoria al 5%
[ALERTA] memoria al 12%
```

Estructura sugerida (¡solo comandos ya conocidos!):

1. Validar el argumento (`if [ $# -eq 0 ]` → uso y `exit 1`)
2. Una función `porcentaje_memoria()` que haga los `grep`/`cut` sobre `/proc/meminfo` y el cálculo con `$((...))`
3. Un `while true` con `sleep 1` que llame la función y compare contra `$1`

**Comprobación en vivo:** con el vigía corriendo en una terminal, en otra ejecutar `dd if=/dev/zero of=/dev/shm/hog bs=1M count=2500` y ver aparecer la ALERTA; `rm /dev/shm/hog` la apaga.

Quien completa este ejercicio ya entendió el colector y el alertador de Sentinel — porque acaba de escribir los dos en uno.

---

## Entrega

- Los 5 del núcleo funcionando, en `bitacora-terminal/scripts/`, un commit por ejercicio
- En clase, cualquiera puede ser invitado a explicar su script línea por línea y a modificarlo en vivo (por ejemplo: "haz que la tabla llegue al 12")
