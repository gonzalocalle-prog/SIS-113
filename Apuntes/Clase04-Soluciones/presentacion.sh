#!/bin/bash
# Ejercicio 1 — variables

nombre="Ana"
materia="SIS-113"
comando_favorito="grep"

echo "Soy $nombre, estudiante de $materia."
echo "Mi comando favorito hasta ahora es: $comando_favorito"

# Reto extra: echo agrega un salto de línea que wc -c también cuenta;
# echo -n lo omite para que el número sea exacto
caracteres=$(echo -n "$nombre" | wc -c)
echo "Mi nombre tiene $caracteres caracteres."
