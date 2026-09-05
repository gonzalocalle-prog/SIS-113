# Clase 4

**SIS-113 Programación II**

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Conoce **Sentinel**, el proyecto del curso: qué vamos a construir, en qué tres capas, y cuál es **la única regla** (el contrato JSON)
2. Distingue con precisión **terminal**, **shell**, **CLI** y **Bash** — y puede explicar la diferencia con sus palabras
3. Sabe qué es el **shebang** (`#!/bin/bash`), para qué sirve y qué pasa si falta - https://bash.cyberciti.biz/guide/Shebang
4. Escribe Bash real: variables, argumentos, `if`, `while`/`for` y funciones

5. Ha leído datos del kernel en `/proc` con los comandos de las clases 2 y 3 (`grep`, `cut`, tuberías)
6. Tiene el **colector de Sentinel corriendo**: una línea JSON por segundo saliendo de su terminal

---

## Reglas de la sesión

- **Sin IA** para escribir los scripts: el primer script de la vida se escribe a mano, línea por línea
- Todo comando que aparezca en pantalla, el estudiante lo teclea — no se copia/pega
- Un script que no se probó, no existe: cada script se ejecuta al menos una vez con datos reales

---

## Desarrollo

### Parte 1 — Presentación del proyecto: Sentinel

Hacia dónde va todo lo que hemos hecho: vamos a construir un **monitor de sistema en vivo**, tres veces, una por capa:

1. **Bash** (ahora): un colector que lee el kernel y emite métricas — *Bash une programas entre sí*
2. **C** (después): el mismo colector, reescrito — *C toca la máquina*
3. **Java** (el plato fuerte): un dashboard orientado a objetos que consume el stream — *Java modela el problema*

**La única regla, fija desde hoy hasta el final:**

> El colector emite **un objeto JSON por línea en stdout, una vez por segundo**:
> ```json
> {"ts": 1756750000, "cpu_pct": 23.4, "mem_used_kb": 3145728, "mem_total_kb": 8388608, "load1": 0.52, "procs": 312, "top_proc": "firefox"}
> ```

Todo lo que construyamos **produce** o **consume** esta línea, y ninguna capa sabe cómo está hecha la otra. Cuando lleguemos a POO esto tendrá nombre ("programar contra la interfaz, no la implementación") - hoy es simplemente un hecho del sistema. Visión completa: [sentinel-readme.md](../sentinel-readme.md).

### Parte 2 — Los conceptos que todos confunden: terminal, shell, CLI y Bash

Antes de escribir el colector, precisión con las palabras. Hasta hoy las usamos como sinónimos; no lo son:

| Concepto | Qué es | Ejemplos |
|----------|--------|----------|
| **Terminal** | El *programa ventana*: dibuja texto y captura tu teclado. No entiende nada — solo muestra y pasa | Windows Terminal, Terminal de macOS, la ventana de WSL |
| **Shell** | El *intérprete*: recibe lo que tecleaste, lo entiende y lo ejecuta. Vive "dentro" de la terminal | Bash, Zsh, Fish, PowerShell |
| **Bash** | Un shell concreto (*Bourne Again SHell*), el estándar en Linux y el de WSL. En macOS el defecto es Zsh (compatible para lo que hacemos) | `bash --version` |
| **CLI** | *Command-Line Interface*: el **concepto** de operar un programa por comandos, en oposición a una GUI | `git`, `java`, `tar` tienen CLI |

**Analogía** la terminal es el teléfono, el shell es la persona que contesta y entiende tu idioma, Bash es *esa* persona en particular, y la CLI es el hecho de comunicarse hablando en vez de por señas.

Demostración en vivo: `echo $SHELL`, `bash --version`.

### Parte 3 — La fuente de datos: `/proc` (repaso activo de la tarea)

El kernel de Linux publica sus métricas como **archivos de texto** en `/proc` — exactamente el tipo de archivo que ya sabemos procesar. Tour guiado con el script del curso:

```bash
cd SIS-113/sentinel/phase1-bash
./explorar-proc.sh
```

En el camino se repasa la tarea: `grep` para filtrar `MemTotal` y `MemAvailable` de `/proc/meminfo`, `cut` para el primer campo de `/proc/loadavg`, `grep -c` para contar. Los mismos comandos de los logs de sensores, ahora sobre datos vivos del kernel.

### Parte 4 — Del comando al script: shebang y sintaxis

Un script es un archivo con comandos que ya sabemos, ejecutados en orden. Lo que lo convierte en *programa*:

1. **El shebang** — la primera línea `#!/bin/bash` le dice al sistema *qué intérprete* ejecuta este archivo
2. **El permiso de ejecución** — `chmod u+x script.sh` (clase 3)

Las 3 formas de ejecutar y por qué existen:

```bash
bash hola.sh      # funciona sin shebang y sin permiso: el intérprete lo pusiste tú
./hola.sh         # necesita shebang Y permiso: el sistema lee la primera línea
sh hola.sh        # ¡puede fallar! sh no es bash — el shebang existe para evitar esto
```

Sintaxis mínima, construida un concepto a la vez (cada uno probado antes de seguir):
https://devhints.io/bash

```bash
#!/bin/bash
umbral=80                     # variables: sin espacios alrededor del =
echo "Umbral: $umbral"        # se leen con $

if [ $# -eq 0 ]; then         # $1, $2... argumentos; $# cuántos llegaron
    echo "Uso: $0 <umbral>"
    exit 1                    # salir con código de error
fi

while true; do                # el latido de un monitor: repetir por siempre
    echo "midiendo..."
    sleep 1                   # una vez por segundo — como pide el contrato
done
```

Errores clásicos mostrados a propósito: `umbral = 80` (espacios), olvidar `$`, olvidar comillas en variables con espacios.

**Práctica individual:** hoja de ejercicios en [Clase04-Ejercicios.md](Clase04-Ejercicios.md) — núcleo de 5 ejercicios en clase, 2 integradores para quien avanza rápido.

### Parte 5 — Laboratorio: el colector de Sentinel

El colector ya existe en `sentinel/phase1-bash/collector.sh`, pero está **incompleto a propósito**: emite `ts`, memoria y carga; el resto son misiones.

```bash
chmod u+x collector.sh
./collector.sh | head -3      # el contrato, saliendo — con huecos
```

Leerlo **línea por línea** entre todos: ahí están el shebang, las variables, el `while true`, el `sleep`, y los `grep`/`cut` de la Parte 3.

**Misión A (en clase):** completar `procs` — contar los procesos vivos. Pista: cada proceso es un directorio con nombre numérico en `/proc`; `ls`, `grep -c` y nada más.

**El juez de calidad:** instalar `jq` (`sudo apt install jq`) y pasar la prueba de la fase:

```bash
./collector.sh | head -5 | jq .
```

Cinco líneas que parsean, o falla. No hay "casi JSON" — la audiencia de un formato de datos es una máquina, no una persona.

**Composición (demo de cierre):** dos programas que no se conocen, unidos por un pipe y el contrato:

```bash
./collector.sh | ./alert.sh 50
```

`alert.sh` no sabe qué es `/proc` — solo entiende líneas JSON. Cambiar el umbral, ver el color cambiar, y notar que esto es la misma tubería de la clase 3 con programas propios.


(un apt install, como hicieron con jq): `sudo apt install stress-ng` y luego `stress-ng --vm 1 --vm-bytes 2G -t 15s` — es lo que se usa en pruebas de carga reales, y se auto-limpia a los 15 segundos.
OR `python3 -c 'x = bytearray(2_500_000_000); import time; time.sleep(10)'`

---

## Verificación de salida

Antes de irse, cada estudiante:

- [ ] Explica en una frase la diferencia entre terminal, shell y CLI (pregunta oral al azar)
- [ ] Recita la única regla de Sentinel sin mirar
- [ ] Muestra su `collector.sh` con la Misión A completada, corriendo con `./collector.sh`
- [ ] Pasa el juez: `./collector.sh | head -5 | jq .` sin errores

---

## Tarea para la casa

Se trabaja en el repositorio propio: copiar `sentinel/phase1-bash/` a `bitacora-terminal/sentinel/` y continuar ahí, con commits por parte.

### Parte 1 — Completar el colector

1. **Misión B:** completar `top_proc` — el proceso que más CPU consume. Pista: `ps -eo comm --sort=-%cpu` lista los nombres ordenados; `head` y `tail` hacen el resto
2. Agregar validación al inicio del script: si `/proc/meminfo` no existe (`if [ ! -f ... ]`), avisar que esto necesita Linux/WSL y salir con `exit 1`
3. Capturar una muestra del stream: `./collector.sh | head -20 > samples/muestra-$(date +%F).txt` — estas muestras alimentarán al dashboard de Java más adelante (guardarlas es parte del proyecto)
4. Pasar `collector.sh` por [ShellCheck](https://www.shellcheck.net/) y corregir lo que señale
5. Evidencia (`history > evidencias/sesion-clase04.txt`) y mínimo 2 commits con push

### Parte 2 — Investigación (prepara la siguiente clase: Java)

En `investigacion/java-y-jdk.md`, responder con sus palabras:

1. ¿Qué son la **JVM**, el **JRE** y el **JDK**? ¿Cuál necesita instalar un programador y por qué?
2. ¿Qué diferencia hay entre un lenguaje **interpretado** (como Bash, que acabamos de usar) y uno **compilado** (como Java)? ¿Qué hace `javac` y qué hace `java`?
3. Leer las secciones 1 y 2 de [sentinel-readme.md](../sentinel-readme.md) y responder: ¿por qué el dashboard de Java **no debe saber** si el colector está escrito en Bash o en C?

### Entrega

- Link al repositorio actualizado en GitHub, antes de la siguiente clase
- El historial de commits cuenta la historia: si todo aparece en un solo commit final, la tarea está incompleta

> **Regla de IA:** se puede usar IA solo para *entender* un concepto de la Parte 2 (semáforo verde: tutor, no autor). Las misiones del colector se escriben a mano — en clase cualquier estudiante puede ser invitado a explicar su script línea por línea.

---

## Referencias y práctica interactiva

*(contenido en inglés — leer documentación técnica en inglés es parte del oficio)*

### Documentación y lectura

| Recurso | Descripción |
|---------|-------------|
| [Ryan's Tutorials — Bash Scripting](https://ryanstutorials.net/bash-scripting-tutorial/) | La mejor introducción didáctica a scripting: variables, if, loops y funciones con ejemplos progresivos. Cubre exactamente esta clase. |
| [The Linux Command Line — W. Shotts](https://linuxcommand.org/tlcl.php) | Parte 4 del libro (caps. 24-27): *Writing Your First Script*, variables y decisiones. |
| [MIT — Shell Tools and Scripting](https://missing.csail.mit.edu/2020/shell-tools/) | Clase 2 del Missing Semester: scripting, shebang y herramientas de shell, con video y ejercicios. |
| [man7 — proc(5)](https://man7.org/linux/man-pages/man5/proc.5.html) | La página man de `/proc`: qué significa cada archivo y cada campo que lee el colector. |
| [jq manual](https://jqlang.github.io/jq/manual/) | El manual de `jq`, el juez de calidad de la Fase 1. Con `jq play` para probar filtros en el navegador. |
| [GNU Bash Reference Manual](https://www.gnu.org/software/bash/manual/) | La referencia oficial de Bash, para consultar sintaxis exacta. |

### Ejercicios interactivos y verificación

| Recurso | Descripción |
|---------|-------------|
| [learnshell.org](https://www.learnshell.org/) | Tutorial interactivo de shell scripting con editor y verificación en el navegador — variables, condicionales, loops y funciones. |
| [ShellCheck](https://www.shellcheck.net/) | Pega tu script y te señala errores y malas prácticas línea por línea. **Pasar `collector.sh` por aquí es parte de la tarea.** |
| [explainshell.com](https://explainshell.com/) | Desarma cualquier línea del colector flag por flag. |
| [cmdchallenge.com](https://cmdchallenge.com/) | Retos de una línea con `grep`, `sort`, `uniq` — refuerza el músculo de tuberías. |
| [OverTheWire — Bandit](https://overthewire.org/wargames/bandit/) | Continuar con los niveles: del 5 en adelante exigen combinar comandos como en el colector. |
