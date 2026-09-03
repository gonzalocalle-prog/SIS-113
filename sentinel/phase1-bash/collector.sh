#!/bin/bash
# Sentinel — Fase 1: colector de métricas en Bash (esqueleto de la Clase 4)
#
# LA REGLA DEL PROYECTO (no cambia en todo el semestre):
#   el colector emite UNA línea JSON por segundo en stdout:
#
#   {"ts": 1756750000, "cpu_pct": 23.4, "mem_used_kb": 3145728, "mem_total_kb": 8388608, "load1": 0.52, "procs": 312, "top_proc": "firefox"}
#
# Todo lo que construyamos después (alertas, el colector en C, el
# dashboard en Java) produce o consume esta línea. Nada más.
#
# Uso:      ./collector.sh
# Probar:   ./collector.sh | head -5
# Calidad:  ./collector.sh | head -5 | jq .   (cada línea debe parsear)

INTERVALO=1

while true; do
    # Marca de tiempo: /proc no tiene fechas — poner el timestamp es NUESTRO trabajo
    ts=$(date +%s)

    # Memoria (kB) desde /proc/meminfo — usada = total - disponible
    mem_total=$(grep '^MemTotal' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
    mem_disp=$(grep '^MemAvailable' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
    if [ -z "$mem_disp" ]; then
        # Entornos sin MemAvailable (p. ej. Git Bash): degradar a MemFree
        mem_disp=$(grep '^MemFree' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
    fi
    mem_usada=$((mem_total - mem_disp))

    # Carga a 1 minuto: primer campo de /proc/loadavg
    load1=$(cut -d' ' -f1 /proc/loadavg)

    # ── MISIÓN A (clase 4) ─────────────────────────────────────────
    # Contar los procesos vivos. Pista: cada proceso es un directorio
    # con nombre numérico en /proc — ls, grep y wc bastan.
    procs=0

    # ── MISIÓN B (tarea) ───────────────────────────────────────────
    # El proceso que más CPU consume. Pista: ps -eo comm --sort=-%cpu
    # imprime los nombres ordenados; head y tail hacen el resto.
    top_proc="desconocido"

    # cpu_pct de verdad exige DOS lecturas de /proc/stat y una resta
    # entre ellas. Es el reto de la siguiente fase — por ahora, 0.0.
    cpu_pct=0.0

    echo "{\"ts\": $ts, \"cpu_pct\": $cpu_pct, \"mem_used_kb\": $mem_usada, \"mem_total_kb\": $mem_total, \"load1\": $load1, \"procs\": $procs, \"top_proc\": \"$top_proc\"}"

    sleep "$INTERVALO"
done
