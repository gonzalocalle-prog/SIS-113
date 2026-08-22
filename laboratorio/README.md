# Laboratorio — Respaldo automático de proyectos

**SIS-113 Programación II · Clase 3 (Semana 2 — Unidad 1)**

Laboratorio interactivo de terminal: un proyecto de datos de sensores necesita limpieza, permisos correctos, un reporte y un respaldo comprimido y verificado. Cada misión se resuelve **tecleando comandos** — y el script `verificar.sh` te dice en vivo qué misiones ya están cumplidas y cuáles faltan.

> **Reglas:** sin IA (esta unidad se construye a mano), nada de copiar/pegar comandos, y antes de cualquier `rm` se mira con `ls` qué se va a borrar.

---

## Cómo empezar

En tu terminal (WebVM, Codespace o Git Bash):

```bash
git clone https://github.com/gonzalocalle-prog/SIS-113.git
cd SIS-113/laboratorio
bash setup.sh
```

`setup.sh` crea el directorio `proyecto-sensores/` con el que vas a trabajar. Si algo sale muy mal, puedes volver a ejecutarlo para empezar de cero (borra y regenera el proyecto).

En cualquier momento, comprueba tu avance con:

```bash
bash verificar.sh
```

---

## Misiones

### Misión 1 — Reconocimiento

Explora el proyecto **sin modificar nada**:

- Dibuja su estructura con `tree proyecto-sensores` (o `ls -R`)
- Mira los detalles con `ls -la proyecto-sensores/src`
- Lee algún archivo de datos con `cat` o `head`

*Pregunta de control (para la puesta en común): ¿qué archivo de `src/` no tiene permiso de ejecución?*

### Misión 2 — Limpieza

El proyecto está lleno de basura: archivos `.tmp` y respaldos de editor (`*~`).

- Encuéntralos: `find proyecto-sensores -name "*.tmp"` y `find proyecto-sensores -name "*~"`
- Elimínalos con `rm` — **primero lista, después borra**

### Misión 3 — Permisos

El script `proyecto-sensores/src/analizar.sh` no se puede ejecutar:

- Compruébalo: `ls -la proyecto-sensores/src` y `bash: permiso denegado` si intentas `./analizar.sh`
- Dale permiso de ejecución a su dueño con `chmod`
- Ejecútalo y lee lo que imprime

### Misión 4 — Reporte con tuberías

Los archivos de `proyecto-sensores/datos/` registran lecturas de sensores; algunas líneas contienen `ERROR`.

Construye con **una tubería** un reporte `proyecto-sensores/reporte-errores.txt` que contenga **todas las líneas con `ERROR` de todos los archivos `.log`**, y compruébalo:

```bash
wc -l proyecto-sensores/reporte-errores.txt
```

*Pistas: `cat datos/*.log | grep ...` y redirección con `>`.*

### Misión 5 — Respaldo comprimido

1. Crea el directorio `respaldos/` (junto a `proyecto-sensores/`, no adentro)
2. Genera el respaldo con fecha:

   ```bash
   tar -czf respaldos/proyecto-$(date +%F).tar.gz proyecto-sensores
   ```

3. **Verifícalo** — un respaldo que no se verifica no existe:
   - Lista su contenido: `tar -tzf respaldos/proyecto-*.tar.gz`
   - Compara el conteo de archivos con el original: `find proyecto-sensores -type f | wc -l`

### Misión 6 — Documentar

Crea `RESPALDO.md` (en `laboratorio/`) con la secuencia completa de comandos que usaste, **una línea de explicación por cada paso**. Este archivo es tu entregable: mañana tú mismo (o tu compañero) debe poder repetir el respaldo leyéndolo.

---

## Verificación final

```bash
bash verificar.sh
```

Cuando las 6 misiones aparezcan con `[OK]`, guarda la evidencia y entrégala en tu repositorio `bitacora-terminal`:

```bash
history > sesion-laboratorio.txt
```

- Copia `RESPALDO.md` y `sesion-laboratorio.txt` a tu `bitacora-terminal/`
- Haz commit y push (mínimo 2 commits: limpieza+permisos, reporte+respaldo)

---

## ¿Te atascaste?

- `man <comando>` o `<comando> --help` antes que preguntar
- [explainshell.com](https://explainshell.com/) desarma cualquier comando flag por flag
- La guía de la clase: [Apuntes/Clase03-Guia.md](../Apuntes/Clase03-Guia.md)
