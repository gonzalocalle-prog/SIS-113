# EC1 — Solución de referencia (solo docente)

Esta carpeta está en `.gitignore`: no se publica en el repositorio del curso.

## Ejercicio 1

Los cuatro archivos esperados, con la salida arriba y el comando comentado abajo, están en [reportes/](reportes/). Generados y verificados en WSL (Ubuntu, GNU coreutils):

| Archivo | Contenido |
|---|---|
| `ips.txt` | 9 203.0.113.9 · 7 192.168.1.20 · 5 10.0.0.7 · 3 192.168.1.21 · 2 192.168.1.22 · 1 192.168.1.23 · 1 172.16.0.5 · 1 10.0.0.8 |
| `no_autorizados.txt` | `Total de accesos no autorizados: 10` |
| `top3_rutas.txt` | 11 /api/login · 7 /api/patients · 4 /index.html |
| `ip_sospechosa.txt` | 9 203.0.113.9 |

El log está construido para que no haya empates en el top 3 de rutas ni en las cinco primeras IPs. Las IPs con 1 acceso sí empatan: su orden puede variar entre macOS y Linux, y no debe penalizarse.

Puntaje: 8 pts `ips.txt`, 6 pts `no_autorizados.txt`, 8 pts `top3_rutas.txt`, 8 pts `ip_sospechosa.txt`. Sin el comando comentado, la tarea no suma. Otras tuberías válidas cuentan si reproducen la salida en vivo: por ejemplo `grep -c ' 401 ' access.log` en lugar de `grep | wc -l`, o `awk '{print $3}'` en lugar de `cut`.

Error frecuente esperado: `grep -c 401` sin espacios. Da el mismo 10 con este log, pero conviene preguntar en la defensa por qué `' 401 '` es más seguro.

## Ejercicio 2

```bash
cp ../EC1/HospitalTest.java . && javac -d out *.java && java -cp out HospitalTest
# Result: 62/62 checks passed
```

El test sirve para verificar, pero la nota del Ejercicio 2 se pone leyendo el código: `Patient` 14, `Doctor` 12, `Appointment` 10, `HospitalSystem` 10, reglas comunes 4. Una validación que el test no ve pero que está en la clase equivocada pierde puntos.

Verificado también contra una versión rota a propósito (atributo `public`, sin guardia de edad, sin tope de 20 pacientes): el test marca 58/62 y nombra cada falla.

## Preguntas para la defensa

1. ¿Por qué `Patient` no tiene `getDiagnosis()` sin parámetros? ¿Qué pasaría si lo tuviera?
2. `Appointment.perform` llama a `doctor.diagnose`, que llama a `patient.assignDiagnosis`. ¿Quién decide si el médico está autorizado, y por qué ahí y no en `HospitalSystem`?
3. Suspende la licencia de un médico después de agendar una cita. ¿Qué clase impide la consulta? Muéstralo.
4. ¿Por qué `changeStatus` no viola la regla de "no setters"? (Respuesta esperada: el nombre expresa una operación del negocio, aunque hoy sea simple, y el día que tenga reglas se agregan ahí sin romper a nadie.)
5. En `ips.txt`: ¿por qué hace falta un `sort` antes de `uniq -c` y otro `sort -rn` después? Quita el primero en vivo y explica qué cambia.

## Cambios en vivo sugeridos (5 minutos cada uno)

- Un paciente inactivo no puede recibir diagnóstico. ¿Dónde va el `if`? (Una línea en `Patient.assignDiagnosis`.)
- Agregar `cancel()` a `Appointment`: una cita cancelada no se puede realizar ni volver a cancelar.
- Agregar `reportes/errores_servidor.txt` con el total de respuestas `5xx` y su comando comentado. Esperado: 2.
