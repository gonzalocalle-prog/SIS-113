#!/bin/bash
# Ejercicio 3 — if / elif con archivos

if [ $# -eq 0 ]; then
    echo "Uso: $0 <ruta>"
    exit 1
fi

if [ -f "$1" ]; then
    echo "$1 es un archivo"
elif [ -d "$1" ]; then
    echo "$1 es un directorio"
else
    echo "$1 no existe"
fi
