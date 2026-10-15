#!/bin/bash
# Script: crear_log_web.sh
# Crea el log de acceso simulado del servidor web del hospital.

echo "Creando archivo de log del servidor web..."

cat > access.log << 'LOG'
2026-10-20 08:01:12 192.168.1.20 GET /index.html 200 12
2026-10-20 08:01:15 192.168.1.20 POST /api/login 200 48
2026-10-20 08:02:03 192.168.1.20 GET /api/patients 200 95
2026-10-20 08:05:41 203.0.113.9 POST /api/login 401 31
2026-10-20 08:05:43 203.0.113.9 POST /api/login 401 29
2026-10-20 08:05:45 203.0.113.9 POST /api/login 401 30
2026-10-20 08:07:10 10.0.0.7 GET /index.html 200 11
2026-10-20 08:07:14 10.0.0.7 POST /api/login 401 33
2026-10-20 08:07:30 10.0.0.7 POST /api/login 200 51
2026-10-20 08:08:02 10.0.0.7 GET /api/appointments 200 120
2026-10-20 08:10:55 203.0.113.9 GET /admin 401 8
2026-10-20 08:11:01 203.0.113.9 GET /admin 401 7
2026-10-20 08:12:20 192.168.1.21 GET /index.html 200 13
2026-10-20 08:12:25 192.168.1.21 POST /api/login 200 47
2026-10-20 08:13:40 192.168.1.21 GET /api/patients 200 101
2026-10-20 08:15:00 192.168.1.20 GET /api/patients 500 2003
2026-10-20 08:15:09 192.168.1.20 GET /api/patients 200 98
2026-10-20 08:20:33 203.0.113.9 POST /api/login 401 28
2026-10-20 08:20:35 203.0.113.9 POST /api/login 401 30
2026-10-20 08:20:37 203.0.113.9 POST /api/login 401 29
2026-10-20 08:22:18 172.16.0.5 GET /favicon.ico 404 3
2026-10-20 08:25:47 192.168.1.22 POST /api/login 200 50
2026-10-20 08:26:02 192.168.1.22 GET /api/appointments 200 115
2026-10-20 08:30:12 10.0.0.7 GET /api/patients 200 99
2026-10-20 08:31:44 192.168.1.20 POST /api/appointments 201 140
2026-10-20 08:33:05 192.168.1.23 GET /index.html 200 12
2026-10-20 08:35:51 203.0.113.9 GET /admin 401 9
2026-10-20 08:40:16 192.168.1.20 GET /api/patients 200 97
2026-10-20 08:42:08 10.0.0.8 GET /api/patients 503 5000
LOG

echo "Archivo access.log creado con $(wc -l < access.log) entradas"
echo ""
echo "=== VISTA PREVIA DEL LOG ==="
head -5 access.log
echo "..."
echo ""
echo "Formato: FECHA HORA IP METODO RUTA ESTADO_HTTP MILISEGUNDOS"
