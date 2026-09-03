#!/bin/bash
# Sentinel — Fase 1 · Paso 0: conocer la fuente de datos
# Tour guiado por /proc: el kernel de Linux visto como archivos de texto.
# Uso: ./explorar-proc.sh

pausa() {
    echo ""
    read -rp "── ENTER para continuar ──"
    echo ""
}

echo "======================================================"
echo " /proc — el kernel como archivos de texto"
echo "======================================================"
echo ""
echo "Todo lo que Sentinel va a medir ya está publicado por el"
echo "kernel en /proc. No son archivos reales en disco: se generan"
echo "al momento de leerlos. Vamos a visitar los tres que usa el colector."
pausa

echo "1) /proc/meminfo — la memoria, línea por línea"
echo "   (solo las 3 líneas que nos importan, con grep):"
echo ""
grep -E "^MemTotal|^MemFree|^MemAvailable" /proc/meminfo
echo ""
echo "   MemTotal - MemAvailable = memoria realmente usada."
echo "   Pregunta: ¿por qué usar MemAvailable y no MemFree?"
echo "   (pista: man free — el kernel guarda caché que puede liberar)"
pausa

echo "2) /proc/loadavg — la carga del sistema en una sola línea:"
echo ""
cat /proc/loadavg
echo ""
echo "   Campos: carga promedio a 1, 5 y 15 minutos, procesos"
echo "   ejecutando/total, último PID. Al colector le importa el campo 1:"
echo ""
echo "   $ cut -d' ' -f1 /proc/loadavg"
cut -d' ' -f1 /proc/loadavg
pausa

echo "3) /proc/stat — los contadores de CPU (la línea 'cpu'):"
echo ""
head -n 1 /proc/stat
echo ""
echo "   Son contadores ACUMULADOS desde el arranque: user, nice,"
echo "   system, idle... Un número solo no dice nada: el % de CPU"
echo "   necesita DOS lecturas y una resta. Ese es el reto que"
echo "   resolveremos más adelante — por ahora basta saber dónde vive."
pausa

echo "4) Los directorios numéricos — un directorio por proceso vivo:"
echo ""
ls /proc | grep '^[0-9]' | head -n 5
echo "   ..."
echo ""
echo "   Cada número es el PID de un proceso. Entonces, contar"
echo "   procesos = contar directorios numéricos. ¿Con qué comando"
echo "   de las clases 3 y 4 se cuenta esto? Lo necesitas para el colector."
pausa

echo "======================================================"
echo " Fin del tour. Siguiente paso: ./collector.sh"
echo " La regla del proyecto: UNA línea JSON por segundo en stdout."
echo "======================================================"
