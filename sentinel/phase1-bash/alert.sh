#!/bin/bash
# Sentinel — Fase 1: consumidor del stream (demostración de composición)
#
# No sabe NADA de /proc ni de cómo se producen los datos: solo lee
# líneas JSON por stdin y decide. Esa ignorancia es el diseño.
#
# Uso:   ./collector.sh | ./alert.sh [umbral_mem_%]
# Necesita jq:  sudo apt install jq
#
# Colores: rojo = alerta, verde = normal.

UMBRAL="${1:-80}"

if ! command -v jq > /dev/null; then
    echo "alert.sh necesita jq: sudo apt install jq" >&2
    exit 1
fi

while read -r linea; do
    usada=$(echo "$linea" | jq '.mem_used_kb')
    total=$(echo "$linea" | jq '.mem_total_kb')
    load1=$(echo "$linea" | jq '.load1')

    if [ -z "$total" ] || [ "$total" -eq 0 ] 2>/dev/null; then
        echo "línea inválida (¿el colector respeta el contrato?): $linea" >&2
        continue
    fi

    pct=$((100 * usada / total))

    if [ "$pct" -ge "$UMBRAL" ]; then
        printf '\033[31m[ALERTA]\033[0m memoria al %s%% (umbral %s%%) · carga %s\n' "$pct" "$UMBRAL" "$load1"
    else
        printf '\033[32m[  ok  ]\033[0m memoria al %s%% · carga %s\n' "$pct" "$load1"
    fi
done
