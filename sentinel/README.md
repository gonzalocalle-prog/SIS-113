# Sentinel — el proyecto del curso

Este es el punto de partida del **proyecto del curso**: un monitor de sistema en vivo que construiremos varias veces — primero el colector en Bash, luego el dashboard en Java (dos veces: antes y después de la POO), y más adelante el colector en C. La visión completa está en [sentinel-readme.md](../sentinel-readme.md).

> **Nota sobre el orden.** La visión original pone C como segunda fase. En esta gestión Java va segundo, porque la Unidad 2 (POO) empieza en la semana 5; C queda como fase posterior. Las carpetas siguen el orden real del curso.

## La única regla

> **El colector emite un objeto JSON por línea en stdout, una vez por segundo.**

```json
{"ts": 1756750000, "cpu_pct": 23.4, "mem_used_kb": 3145728, "mem_total_kb": 8388608, "load1": 0.52, "procs": 312, "top_proc": "firefox"}
```

Todo lo demás del semestre **produce** o **consume** esta línea. Nada más.

## Fase 1 — Bash: el colector (clase 4)

| Script | Qué hace |
|--------|----------|
| [`phase1-bash/explorar-proc.sh`](phase1-bash/explorar-proc.sh) | Tour guiado por `/proc`: dónde vive cada dato que el colector necesita. Se ejecuta primero. |
| [`phase1-bash/collector.sh`](phase1-bash/collector.sh) | El colector, con las misiones A y B (procesos y `top_proc`) ya resueltas. Emite `ts`, memoria, carga, procesos y proceso top; `cpu_pct` sigue en `0.0` (necesita dos lecturas de `/proc/stat`, reto de la fase en C). |
| [`phase1-bash/alert.sh`](phase1-bash/alert.sh) | Consumidor del stream: lee las líneas JSON y colorea alertas. No sabe nada de `/proc` — solo entiende el contrato. |
| [`phase1-bash/samples/`](phase1-bash/samples/) | Muestras capturadas del stream (una limpia, una con líneas rotas). Alimentan al dashboard de Java sin colector vivo. |

### Cómo empezar (en WSL o macOS)

```bash
cd sentinel/phase1-bash
chmod u+x *.sh            # clase 3: sin permiso no hay ejecución
./explorar-proc.sh        # 1. conocer la fuente de datos
./collector.sh | head -3  # 2. ver el contrato salir (con las misiones pendientes)
sudo apt install jq       # 3. el juez de calidad
./collector.sh | head -5 | jq .   # ¿parsea? entonces el contrato se cumple
./collector.sh | ./alert.sh 50    # 4. composición: dos programas, un pipe
```

`Ctrl+C` detiene el colector (es un `while true` — así debe ser: un monitor no termina solo).

### La evaluación de esta fase

```bash
./collector.sh | head -5 | jq .
```

Cinco líneas JSON válidas, o falla. `jq` es el juez: no hay "casi JSON".

## Fase 2 — Java: el dashboard, antes y después de la POO (clases 5 y 6)

El consumidor del stream, escrito dos veces con **el mismo comportamiento**. Detalle completo en [phase2-java/README.md](phase2-java/README.md).

| Carpeta | Qué hay | Marcas en el código |
|---------|---------|---------------------|
| [`phase2-java/procedural/`](phase2-java/procedural/) | `Sentinel.java`: un archivo, un `main`, 18 variables sueltas. Funciona — hasta que hay que cambiarlo. | `PAIN 1` … `PAIN 6` |
| [`phase2-java/oop/`](phase2-java/oop/) | Seis clases (`Snapshot`, `Contract`, `History`, `MemoryRule`, `Console`, `Main`). Misma salida. | `ABSTRACTION` · `ENCAPSULATION` |

### Cómo correr

```bash
cd sentinel/phase2-java/procedural
java Sentinel.java 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt   # un archivo: sin compilar
../../phase1-bash/collector.sh | head -20 | java Sentinel.java 50

cd ../oop
javac -d out *.java                                                        # varios archivos: se compila
java -cp out Main 80 < ../../phase1-bash/samples/muestra-2026-09-17.txt
../../phase1-bash/collector.sh | head -20 | java -cp out Main 50
```

### La evaluación de esta fase

Dos pruebas, ambas con herramientas de la Fase 1:

```bash
diff <(java procedural/Sentinel.java 80 < ../phase1-bash/samples/muestra-2026-09-17.txt) \
     <(java -cp oop/out Main 80        < ../phase1-bash/samples/muestra-2026-09-17.txt)
# solo difiere el encabezado: la reescritura no cambió la conducta

java -cp oop/out Main 80 < ../phase1-bash/samples/muestra-rota.txt
# rechaza cada línea inválida con motivo y termina con resumen correcto
```

## Guías

- [Clase 4 — Bash: el colector](../Apuntes/Clase04-Guia.md)
- [Clase 5 — Java procedural y sus dolores](../Apuntes/Clase05-Guia.md)
- [Clase 6 — Abstracción y encapsulamiento](../Apuntes/Clase06-Guia.md)
