#!/bin/bash
# Ejercicio 5 — while + sleep: el corazón de un monitor

if [ $# -eq 0 ]; then
    echo "Uso: $0 <latidos>"
    exit 1
fi

i=1
while [ $i -le $1 ]; do
    echo "latido $i — $(date +%T)"
    sleep 1
    i=$((i + 1))
done
echo "fin"
