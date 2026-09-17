# Muestras del stream

Salidas capturadas del colector de la Fase 1. Sirven para alimentar al dashboard de Java **sin** un colector vivo (y sin Linux): la misma idea que el "swap" del proyecto — el consumidor no sabe si la línea viene de `/proc` o de un archivo.

| Archivo | Qué tiene |
|---------|-----------|
| `muestra-2026-09-17.txt` | 20 segundos de un sistema normal con un pico de memoria en el medio (`stress-ng`). Todas las líneas cumplen el contrato. |
| `muestra-rota.txt` | 7 líneas, de las cuales 4 **rompen** el contrato: falta un campo, no es JSON, `mem_used_kb` mayor que el total, y `mem_total_kb` en 0. Sirve para ver qué hace cada versión del dashboard con datos inválidos. |

Capturar una muestra propia (tarea de la clase 4):

```bash
./collector.sh | head -20 > samples/muestra-$(date +%F).txt
```
