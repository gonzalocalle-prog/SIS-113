# Clase 3

**SIS-113 Programación II**

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Sabe explicar la diferencia entre ruta absoluta y relativa, y qué guardan `/home`, `/etc`, `/var`, `/tmp` (repaso de la investigación de la tarea)
2. Copia, mueve y elimina archivos y directorios con `cp`, `mv`, `rm` — y entiende por qué `rm -rf` merece respeto
3. Lee e interpreta la salida de `ls -la`: el modelo de permisos usuario/grupo/otros, y cambia permisos con `chmod`
4. Redirige y encadena la salida de comandos con `>`, `>>`, `<` y `|`, combinándolos con `wc`, `head` y `tail`
5. Ha usado `grep` por primera vez para buscar texto dentro de archivos
6. Ha completado el laboratorio **"Respaldo automático de proyectos"**: copiar, comprimir y verificar un directorio desde la terminal

---

## Reglas de la sesión

- **Sin IA**: seguimos construyendo el hábito de terminal a mano
- Todo comando que aparezca en pantalla, el estudiante lo teclea — no se copia/pega
- Antes de cualquier `rm`, se verifica con `ls` qué se va a borrar. No hay papelera en la terminal

---

## Desarrollo

### Parte 1 — Repaso de la tarea (rutas y directorios)

- 2 o 3 estudiantes explican en vivo una respuesta de su `investigacion/rutas-y-directorios.md`
- Dudas comunes de `.`, `..` y rutas relativas se resuelven en pizarra y en la VM

### Parte 2 — Manipular archivos: `cp`, `mv`, `rm`

- Copiar archivos y directorios (`cp`, `cp -r`), renombrar y mover (`mv`)
- Eliminar con `rm` y `rm -r`; qué hace `-f` y por qué `rm -rf` sin mirar es la forma más rápida de perder una semana de trabajo

### Parte 3 — Permisos: `ls -la` y `chmod`

- Anatomía de una línea de `ls -la`: tipo, permisos `rwx` por usuario/grupo/otros, dueño, tamaño, fecha
- `chmod` en notación simbólica (`u+x`, `go-w`) y numérica (`755`, `644`)
- `chown` se presenta como concepto (cambiar de dueño); se usará poco en la VM

### Parte 4 — Redirección y tuberías

- `>` (sobrescribe), `>>` (agrega), `<` (entrada desde archivo)
- `|`: la salida de un comando es la entrada del siguiente
- Combinaciones con `wc`, `head`, `tail`; primer contacto con `grep` para filtrar líneas

### Parte 5 — Laboratorio: "Respaldo automático de proyectos"

Laboratorio interactivo con verificación automática, en la carpeta [`laboratorio/`](../laboratorio/README.md) del repositorio del curso:

```bash
git clone https://github.com/gonzalocalle-prog/SIS-113.git
cd SIS-113/laboratorio
bash setup.sh        # genera el proyecto de práctica
bash verificar.sh    # muestra el avance misión por misión
```

6 misiones: reconocimiento, limpieza de archivos basura, permisos con `chmod`, reporte con tuberías, respaldo con `tar -czf` verificado con `tar -tzf`, y documentación en `RESPALDO.md`. El laboratorio termina cuando `verificar.sh` marca las 6 misiones con `[OK]`.

---

## Verificación de salida

Antes de irse, cada estudiante muestra en su VM:

- [ ] Un `ls -la` interpretado en voz alta: qué significa cada campo de una línea elegida por el docente
- [ ] Un archivo cuyo permiso cambió con `chmod` (antes y después visibles en `history`)
- [ ] Una tubería propia de al menos 2 comandos (por ejemplo `cat archivo | grep palabra | wc -l`)
- [ ] Su `.tar.gz` de respaldo creado y verificado

---

## Tarea para la casa

Se continúa en el mismo repositorio `bitacora-terminal` — todo desde la terminal, con commits por parte.

### Parte 1 — Refuerzo

1. Agregar a `comandos/COMANDOS.md` los comandos de hoy (`cp`, `mv`, `rm`, `chmod`, `ls -la`, `>`, `>>`, `|`, `wc`, `head`, `tail`, `grep`): qué hace cada uno y **un ejemplo propio**
2. Escribir en `comandos/RESPALDO.md` la secuencia de comandos del laboratorio, con una línea explicando cada paso
3. Guardar la evidencia: `history > evidencias/sesion-clase03.txt`
4. Mínimo 2 commits nuevos con mensajes descriptivos, y push a GitHub

### Parte 2 — Investigación (prepara la siguiente clase)

En `investigacion/procesar-texto.md`, responder con sus palabras:

1. ¿Qué hacen `grep`, `cut`, `sort` y `uniq`? Probar cada uno sobre un archivo de texto propio y anotar el comando y el resultado
2. ¿Por qué `uniq` suele usarse después de `sort` y no solo? Demostrarlo con un ejemplo
3. ¿Qué es un archivo de log? Buscar uno en la VM (pista: `/var/log`) y extraer 3 líneas que contengan una palabra elegida, usando una tubería

### Entrega

- Link al repositorio actualizado en GitHub, antes de la siguiente clase
- El historial de commits cuenta la historia: si todo aparece en un solo commit final, la tarea está incompleta

> **Regla de IA:** se puede usar IA solo para *entender* un concepto de la Parte 2 (semáforo verde: tutor, no autor). Los ejemplos y respuestas se escriben con sus palabras — en clase cualquier estudiante puede ser invitado a explicar su respuesta en vivo.

---

## Referencias y práctica interactiva

*(contenido en inglés — leer documentación técnica en inglés es parte del oficio)*

### Documentación y lectura

| Recurso | Descripción |
|---------|-------------|
| [The Linux Command Line — W. Shotts](https://linuxcommand.org/tlcl.php) | Libro gratuito (PDF) de referencia. Para esta clase: caps. 4 (manipulación de archivos), 6 (redirección) y 9 (permisos). |
| [Linux Journey](https://linuxjourney.com/) | Lecciones cortas con quiz al final de cada tema: *Command Line*, *Permissions*, *Text-Fu* (redirección y pipes). |
| [explainshell.com](https://explainshell.com/) | Pega cualquier comando (por ejemplo `tar -czf respaldo.tar.gz proyecto/`) y te explica qué hace cada parte, flag por flag. |
| [man7.org — Linux man pages](https://man7.org/linux/man-pages/) | Las páginas `man` oficiales en línea, para consultar `cp`, `chmod`, `grep`, `tar`, etc. sin salir del navegador. |

### Ejercicios interactivos (en el navegador)

| Recurso | Descripción |
|---------|-------------|
| [OverTheWire — Bandit](https://overthewire.org/wargames/bandit/) | Juego por niveles vía terminal: cada nivel se resuelve con los comandos de esta clase (`ls`, `cat`, `grep`, pipes, permisos). Empezar en el nivel 0. |
| [cmdchallenge.com](https://cmdchallenge.com/) | Retos de una línea: resolver cada uno con un solo comando o tubería. Ideal para practicar `|`, `grep`, `wc`, `sort`. |
| [Linux Survival](https://linuxsurvival.com/) | Tutorial interactivo con terminal simulada en el navegador; los módulos 1-3 cubren exactamente `cp`, `mv`, `rm` y permisos. |


> Sugerencia: los niveles 0-10 de **Bandit** son la mejor preparación posible para la parte Linux de la evaluación de la semana 8 — se puede avanzar de a un nivel por día.
