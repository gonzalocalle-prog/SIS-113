#!/bin/bash
# Ejercicio 4 — for

if [ $# -eq 0 ]; then
    echo "Uso: $0 <numero>"
    exit 1
fi

for i in $(seq 1 10); do
    echo "$1 x $i = $(($1 * i))"
done
