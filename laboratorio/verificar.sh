#!/bin/bash
# Laboratorio "Respaldo automático de proyectos" — SIS-113 Clase 3
# Verifica el avance de las 6 misiones y da pistas sobre lo que falta.

cd "$(dirname "$0")"
OK=0
TOTAL=6

check() {   # check <numero> <nombre> <condicion(0/1)> <pista>
    if [ "$3" -eq 0 ]; then
        echo "[OK] Misión $1 — $2"
        OK=$((OK + 1))
    else
        echo "[--] Misión $1 — $2"
        echo "       pista: $4"
    fi
}

echo "=============================================="
echo " Verificación del laboratorio de respaldo"
echo "=============================================="

# Misión 1 — el proyecto existe (setup ejecutado y explorado)
[ -d proyecto-sensores/src ] && [ -d proyecto-sensores/datos ] && [ -d proyecto-sensores/docs ]
check 1 "Reconocimiento (proyecto generado)" $? \
    "ejecuta primero: bash setup.sh — y explóralo con tree y ls -la"

# Misión 2 — sin basura
basura=$(find proyecto-sensores \( -name "*.tmp" -o -name "*~" \) 2>/dev/null | wc -l)
[ -d proyecto-sensores ] && [ "$basura" -eq 0 ]
check 2 "Limpieza (sin *.tmp ni *~)" $? \
    "quedan $basura archivo(s) basura — encuéntralos con: find proyecto-sensores -name '*.tmp' -o -name '*~'"

# Misión 3 — script ejecutable
[ -x proyecto-sensores/src/analizar.sh ]
check 3 "Permisos (analizar.sh es ejecutable)" $? \
    "mira los permisos con ls -la proyecto-sensores/src y usa chmod para dar ejecución al dueño"

# Misión 4 — reporte de errores por tubería
m4=1
if [ -s proyecto-sensores/reporte-errores.txt ]; then
    errores_reales=$(cat proyecto-sensores/datos/*.log 2>/dev/null | grep -c ERROR)
    errores_reporte=$(grep -c ERROR proyecto-sensores/reporte-errores.txt)
    lineas_reporte=$(wc -l < proyecto-sensores/reporte-errores.txt)
    if [ "$errores_reporte" -eq "$errores_reales" ] && [ "$lineas_reporte" -eq "$errores_reales" ]; then
        m4=0
    fi
fi
check 4 "Reporte (reporte-errores.txt correcto)" $m4 \
    "debe tener exactamente todas las líneas ERROR de datos/*.log y nada más — cat + grep + > es el camino"

# Misión 5 — respaldo comprimido y verificable
m5=1
respaldo=$(ls respaldos/proyecto-*.tar.gz 2>/dev/null | head -n 1)
if [ -n "$respaldo" ]; then
    en_tar=$(tar -tzf "$respaldo" 2>/dev/null | grep -v '/$' | wc -l)
    en_disco=$(find proyecto-sensores -type f | wc -l)
    if [ "$en_tar" -ge "$en_disco" ] && [ "$en_tar" -gt 0 ]; then
        m5=0
    fi
fi
check 5 "Respaldo (tar.gz en respaldos/ con el proyecto completo)" $m5 \
    "crea respaldos/ y usa: tar -czf respaldos/proyecto-\$(date +%F).tar.gz proyecto-sensores — hazlo DESPUÉS de limpiar y del reporte"

# Misión 6 — documentación
m6=1
if [ -f RESPALDO.md ]; then
    lineas=$(wc -l < RESPALDO.md)
    if [ "$lineas" -ge 6 ] && grep -q "tar" RESPALDO.md; then
        m6=0
    fi
fi
check 6 "Documentación (RESPALDO.md con la secuencia explicada)" $m6 \
    "escribe en RESPALDO.md cada comando usado con una línea de explicación (mínimo 6 líneas, debe incluir el tar)"

echo "=============================================="
echo " Resultado: $OK de $TOTAL misiones cumplidas"
if [ "$OK" -eq "$TOTAL" ]; then
    echo ""
    echo " Laboratorio completo. Último paso:"
    echo "   history > sesion-laboratorio.txt"
    echo " y lleva RESPALDO.md + la evidencia a tu repo bitacora-terminal (commit y push)."
else
    echo " Sigue con la primera misión marcada [--]."
fi
echo "=============================================="
