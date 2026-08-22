#!/bin/bash
# Laboratorio "Respaldo automático de proyectos" — SIS-113 Clase 3
# Genera (o regenera) el proyecto de práctica proyecto-sensores/.

set -e
cd "$(dirname "$0")"

if [ -d proyecto-sensores ]; then
    echo "Ya existe proyecto-sensores/ — se borra y se regenera desde cero."
    rm -rf proyecto-sensores
fi

mkdir -p proyecto-sensores/src
mkdir -p proyecto-sensores/datos
mkdir -p proyecto-sensores/docs

# --- src/ -------------------------------------------------------------
cat > proyecto-sensores/src/analizar.sh << 'EOF'
#!/bin/bash
# Resume las lecturas registradas en datos/
DATOS="$(dirname "$0")/../datos"
echo "=== Resumen de lecturas ==="
echo "Total de lecturas : $(cat "$DATOS"/*.log | wc -l)"
echo "Lecturas OK       : $(cat "$DATOS"/*.log | grep -c OK)"
echo "Lecturas con ERROR: $(cat "$DATOS"/*.log | grep -c ERROR)"
echo "==========================="
echo "Si puedes leer esto, la Misión 3 está cumplida: el script ya ejecuta."
EOF
chmod 644 proyecto-sensores/src/analizar.sh   # sin permiso de ejecución: Misión 3

# --- datos/ -----------------------------------------------------------
generar_log() {
    archivo="$1"; sensor="$2"; dia="$3"
    for hora in 08 09 10 11 12 13 14 15 16 17; do
        temp=$((18 + RANDOM % 10)).$((RANDOM % 9))
        if [ $((RANDOM % 7)) -eq 0 ]; then
            echo "2026-08-$dia $hora:00:00 $sensor temp=-- ERROR lectura fallida" >> "$archivo"
        else
            echo "2026-08-$dia $hora:00:00 $sensor temp=$temp OK" >> "$archivo"
        fi
    done
}

generar_log proyecto-sensores/datos/sensor-01.log sensor-01 18
generar_log proyecto-sensores/datos/sensor-02.log sensor-02 19
generar_log proyecto-sensores/datos/sensor-03.log sensor-03 20

# Garantiza que exista al menos una línea ERROR (Misión 4)
echo "2026-08-20 18:00:00 sensor-03 temp=-- ERROR lectura fallida" >> proyecto-sensores/datos/sensor-03.log

# --- docs/ ------------------------------------------------------------
cat > proyecto-sensores/docs/NOTAS.md << 'EOF'
# Notas del proyecto

Lecturas de temperatura de 3 sensores de laboratorio.
Formato de cada línea: fecha hora sensor temp=<valor> <estado>
EOF

# --- basura para la Misión 2 -----------------------------------------
echo "temporal" > proyecto-sensores/datos/cache-viejo.tmp
echo "temporal" > proyecto-sensores/src/prueba.tmp
echo "respaldo de editor" > "proyecto-sensores/docs/NOTAS.md~"

echo ""
echo "Listo: proyecto-sensores/ creado."
echo "Empieza por la Misión 1 del README.md y revisa tu avance con: bash verificar.sh"
