#!/bin/bash
# Ejercicio 6 — funciones: el cuerpo principal solo llama funciones

separador() {
    echo "===================="
}

fila() {
    echo "$1 x $2 = $(($1 * $2))"
}

if [ $# -eq 0 ]; then
    echo "Uso: $0 <numero>"
    exit 1
fi

separador
for i in $(seq 1 10); do
    fila "$1" "$i"
done
separador
