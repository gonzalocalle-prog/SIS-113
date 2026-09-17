#!/bin/bash
# Ejercicio 2 — argumentos + if

if [ $# -eq 0 ]; then
    echo "Uso: $0 <nombre>"
    exit 1
fi

echo "Hola, $1"
