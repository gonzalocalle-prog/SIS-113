# Sentinel — arranque de la Fase 1

Este es el punto de partida del **proyecto del curso**: un monitor de sistema en vivo que construiremos tres veces — primero en Bash, luego en C, y finalmente como dashboard orientado a objetos en Java. La visión completa está en [sentinel-readme.md](../sentinel-readme.md).

## La única regla

> **El colector emite un objeto JSON por línea en stdout, una vez por segundo.**

```json
{"ts": 1756750000, "cpu_pct": 23.4, "mem_used_kb": 3145728, "mem_total_kb": 8388608, "load1": 0.52, "procs": 312, "top_proc": "firefox"}
```

Todo lo demás del semestre **produce** o **consume** esta línea. Nada más.

## Qué hay aquí

| Script | Qué hace |
|--------|----------|
| [`phase1-bash/explorar-proc.sh`](phase1-bash/explorar-proc.sh) | Tour guiado por `/proc`: dónde vive cada dato que el colector necesita. Se ejecuta primero. |
| [`phase1-bash/collector.sh`](phase1-bash/collector.sh) | El colector, **incompleto a propósito**: emite `ts`, memoria y carga; las misiones A y B (procesos y top_proc) las completas tú. |
| [`phase1-bash/alert.sh`](phase1-bash/alert.sh) | Consumidor del stream: lee las líneas JSON y colorea alertas. No sabe nada de `/proc` — solo entiende el contrato. |

## Cómo empezar (en WSL o macOS)

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

## La evaluación de esta fase

```bash
./collector.sh | head -5 | jq .
```

Cinco líneas JSON válidas, o falla. `jq` es el juez: no hay "casi JSON".
