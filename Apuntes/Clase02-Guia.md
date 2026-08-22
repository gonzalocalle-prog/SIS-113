# Clase 2

**SIS-113 Programación II**

---

## Objetivo de la sesión

Al terminar la clase, cada estudiante:

1. Sabe explicar qué es un sistema operativo, qué es una terminal y por qué un programador vive en ella
2. Tiene una VM Linux corriendo en su navegador (webvm.io) - ALTERNATIVAS
3. Se mueve por el sistema de archivos con `pwd`, `ls`, `cd`
4. Ha creado su primera estructura de directorios y archivos con `mkdir`, `touch`, `cat`, `tree`
5. Sabe resolver dudas de un comando por su cuenta con `man` / `--help` y revisar lo que hizo con `history`

---

## Reglas de la sesión

- **Sin IA**: esta unidad empieza a mano. El hábito de terminal se construye tecleando, no preguntando
- Todo comando que aparezca en pantalla, el estudiante lo teclea — no se copia/pega

---

## Verificación de salida

Antes de irse, cada estudiante muestra en su VM:

- [ ] `pwd` desde su directorio home
- [ ] Una estructura de al menos 3 directorios anidados creada por él mismo, vista con `tree`
- [ ] Su `history` con los comandos de la sesión

---

## Tarea para la casa

**Repositorio `bitacora-terminal`** — se construye completo desde la terminal (Git Bash en su máquina o la VM), sin tocar el explorador de archivos ni crear archivos desde el editor.

### Parte 1 — Refuerzo (terminal + Git juntos)

1. Desde la terminal, crear un directorio `bitacora-terminal` y dentro esta estructura:

   ```
   bitacora-terminal/
   ├── comandos/
   ├── evidencias/
   └── investigacion/
   ```

2. En `comandos/`, crear un archivo `COMANDOS.md` con la lista de comandos vistos en clase (`pwd`, `ls`, `cd`, `mkdir`, `touch`, `cat`, `tree`, `man`, `history`): qué hace cada uno y **un ejemplo propio**, no el de clase
3. Guardar la evidencia de su práctica: `history > evidencias/sesion-casa.txt`
4. Convertirlo en repositorio Git: `git init`, commits con mensajes descriptivos y subirlo a su cuenta de GitHub

### Parte 2 — Investigación (prepara la siguiente clase)

En `investigacion/rutas-y-directorios.md`, responder con sus palabras:

1. ¿Qué guardan los directorios `/home`, `/etc`, `/var` y `/tmp` en Linux? (pueden explorarlos con `ls` y `cat` en la VM)
2. ¿Cuál es la diferencia entre una **ruta absoluta** y una **ruta relativa**? Dar 3 ejemplos de cada una desde su propia estructura de `bitacora-terminal`
3. ¿Qué significan `.` y `..` en una ruta? Probarlo con `cd` y anotar qué pasó

### Entrega

- Link al repositorio en GitHub, antes de la siguiente clase
- El historial de commits cuenta la historia

> **Regla de IA:** se puede usar IA solo para *entender* un concepto de la Parte 2 (semáforo verde: tutor, no autor). Los ejemplos y respuestas se escriben con sus palabras — en clase cualquier estudiante puede ser invitado a explicar su respuesta en vivo.
