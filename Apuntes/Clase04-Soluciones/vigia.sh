#!/bin/bash
# Ejercicio 7 — todo junto: un mini-Sentinel sin jq

if [ $# -eq 0 ]; then
    echo "Uso: $0 <umbral_%>"
    exit 1
fi

porcentaje_memoria() {
    total=$(grep '^MemTotal' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
    disponible=$(grep '^MemAvailable' /proc/meminfo | tr -s ' ' | cut -d' ' -f2)
    usada=$((total - disponible))
    echo $((100 * usada / total))
}

while true; do
    pct=$(porcentaje_memoria)
    if [ "$pct" -ge "$1" ]; then
        echo "[ALERTA] memoria al ${pct}%"
    else
        echo "[ok] memoria al ${pct}%"
    fi
    sleep 1
done
