# Evaluación Continua 1

**SIS-113 Programación II** · Presencial, en tu propio entorno (WSL o macOS) · 2 horas

---

## Reglas

- Se puede consultar `man`, `--help` y la documentación oficial de Java.
- **IA:** si la usas, pega el prompt en `examen/BITACORA-IA.md`. Cualquier línea de tu entrega puede ser pedida para **explicarla en voz alta**. Si no la puedes explicar, no cuenta.
- Al terminar, el docente puede pedirte un **cambio en vivo** sobre tu propio código.

## Preparación

```bash
mkdir -p ~/examen && cd ~/examen
# copiar aquí crear_log_web.sh y HospitalTest.java (los entrega el docente)
chmod u+x crear_log_web.sh
./crear_log_web.sh
```

---

# Ejercicio 1: Analizador del Log del Servidor Web

**Escenario:** El sistema del hospital tiene un servidor web en Java: el *backend*. Cada petición que hace un navegador queda registrada en una línea de `access.log`. El equipo de seguridad sospecha que alguien intenta entrar por fuerza bruta y te pide un reporte.

El script `crear_log_web.sh` crea el log. Cada línea tiene este formato:

```
FECHA HORA IP METODO RUTA ESTADO_HTTP MILISEGUNDOS
2026-10-20 08:05:41 203.0.113.9 POST /api/login 401 31
```

Recordatorio: `200` = OK, `201` = creado, `401` = no autorizado (credenciales incorrectas), `404` = no existe, `500`/`503` = error del servidor.

### Formato de cada archivo de reporte

Cada archivo de `reportes/` tiene **la salida** del comando y, **debajo**, el comando que usaste, comentado con `#`. Sin el comando, la tarea no cuenta aunque la salida sea correcta.

```
<salida del comando>
# <el comando que usaste, completo, con sus tuberías>
```

La salida se guarda con redirección (`>`). La línea del comando se agrega después, con `nano` o con `echo '# ...' >> archivo`.

### Tareas (30 puntos)

1. Crear el directorio `reportes/`.

2. **(8 pts)** Listar las IPs que hicieron peticiones y cuántas veces, de mayor a menor, usando `cut`, `sort` y `uniq -c`.
   - Archivo: `reportes/ips.txt`
     ```
           9 203.0.113.9
           7 192.168.1.20
           5 10.0.0.7
           3 192.168.1.21
           2 192.168.1.22
           1 ...
     # <comando usado>
     ```

3. **(6 pts)** Contar los accesos no autorizados (estado `401`) usando `grep` y `wc`.
   - Archivo: `reportes/no_autorizados.txt`
     ```
     Total de accesos no autorizados: 10
     # <comando usado>
     ```

4. **(8 pts)** Identificar las 3 rutas más pedidas usando `cut`, `sort`, `uniq -c` y `head`.
   - Archivo: `reportes/top3_rutas.txt`
     ```
          11 /api/login
           7 /api/patients
           4 /index.html
     # <comando usado>
     ```

5. **(8 pts)** Encontrar la IP con más accesos no autorizados. Es una sola tubería que combina lo de las tareas 2 y 3.
   - Archivo: `reportes/ip_sospechosa.txt`
     ```
           9 203.0.113.9
     # <comando usado>
     ```

6. Al terminar, `reportes/` contiene los 4 archivos: `ips.txt`, `no_autorizados.txt`, `top3_rutas.txt` e `ip_sospechosa.txt`.

**Verificación:**

```bash
ls reportes/
cat reportes/*.txt      # cada archivo: salida arriba, comando comentado abajo
```

Para cada tarea, el docente puede pedirte que vuelvas a ejecutar tu comando en vivo y que la salida coincida.

ref: [cut command](https://labex.io/es/tutorials/linux-linux-cut-command-with-practical-examples-422626)

---

# Ejercicio 2: Sistema de Gestión de Hospital (Java) · 50 puntos

**Contexto**

Ese mismo hospital necesita el núcleo de su backend: gestionar pacientes, médicos y citas. La información médica es sensible y las operaciones siguen protocolos estrictos. Como en Sentinel, **el estado inválido no debe poder existir**: cada regla vive dentro de la clase dueña del dato, no en el `main`.

**Reglas comunes a todas las clases (4 pts)**

- Todos los atributos son `private`. Los que no cambian después del constructor, además `final`.
- **No hay setters** (`setX`). El estado cambia solo con métodos que tienen nombre de negocio: `changeStatus`, `suspendLicense`, `perform`.
- Ningún método público devuelve un arreglo interno.
- Las colecciones se guardan en **arreglos con capacidad fija y un contador**, como `History` en Sentinel.
- El código va en inglés: nombres de clases, métodos, variables y mensajes.

Las firmas de abajo son **obligatorias**: el archivo de prueba las usa tal cual.

### Clase `Patient` (14 pts)

- **Datos privados:** número de historia clínica, nombre, edad, diagnóstico, estado activo/inactivo.
- **Constructor:** `Patient(String recordNumber, String name, int age)`. El paciente nace **activo** y sin diagnóstico.
- **Validaciones encapsuladas:**
  - Historia clínica y nombre no pueden estar vacíos.
  - La edad debe estar entre 0 y 120.
  - El diagnóstico solo lo puede **escribir o leer** un médico con licencia vigente que atiende a ese paciente.
- **Métodos públicos:**
  - `String getRecordNumber()`, `String getName()`, `int getAge()`, `boolean isActive()`
  - `void changeStatus(boolean active)`
  - `void assignDiagnosis(Doctor doctor, String diagnosis)`
  - `String getDiagnosis(Doctor requester)`
  - `String basicInfo()`: historia clínica, nombre, edad y estado. **Nunca el diagnóstico.**

> No existe `getDiagnosis()` sin parámetros. Piensa por qué.

### Clase `Doctor` (12 pts)

- **Datos privados:** ID, nombre, especialidad, licencia vigente/suspendida, arreglo de pacientes asignados (**máximo 20**).
- **Constructor:** `Doctor(String id, String name, String specialty)`. El médico nace con licencia **vigente**.
- **Validaciones encapsuladas:**
  - ID, nombre y especialidad no pueden estar vacíos.
  - Solo un médico con licencia vigente puede recibir pacientes o diagnosticar.
  - No puede pasar de 20 pacientes.
  - No acepta pacientes inactivos ni el mismo paciente dos veces.
- **Métodos públicos:**
  - `String getId()`, `String getName()`, `String getSpecialty()`, `boolean hasActiveLicense()`, `int getPatientCount()`
  - `void assignPatient(Patient patient)`
  - `boolean isTreating(Patient patient)`
  - `void diagnose(Patient patient, String diagnosis)`
  - `void suspendLicense()`, `void reactivateLicense()`

### Clase `Appointment` (10 pts)

Una cita. Como `Snapshot` en Sentinel: **nace válida**.

- **Datos privados:** número, paciente, médico, fecha y hora (`String`, por ejemplo `"2026-10-21 09:00"`), completada sí/no.
- **Constructor:** `Appointment(int number, Patient patient, Doctor doctor, String dateTime)`
- **Validaciones encapsuladas:**
  - Número positivo, fecha no vacía.
  - El paciente está activo, el médico tiene licencia vigente y **atiende** a ese paciente.
- **Métodos públicos:**
  - `int getNumber()`, `Patient getPatient()`, `Doctor getDoctor()`, `String getDateTime()`, `boolean isCompleted()`
  - `void perform(String diagnosis)`: realiza la consulta. El médico diagnostica al paciente y la cita queda completada. Una cita **no se realiza dos veces**, y no se realiza si en ese momento el paciente está inactivo o el médico tiene la licencia suspendida.

> La cita solo pasa a "completada" a través de `perform`. No hay otra puerta.

### Clase `HospitalSystem` (10 pts)

- **Funcionalidades:** gestionar todo el flujo de pacientes, médicos y citas. Guarda sus tres arreglos en privado.
- **Métodos públicos:**
  - `void registerPatient(Patient patient)`: rechaza una historia clínica repetida.
  - `void registerDoctor(Doctor doctor)`: rechaza un ID repetido.
  - `Patient findPatient(String recordNumber)`, `Doctor findDoctor(String id)`: si no existe, rechaza.
  - `Appointment scheduleAppointment(String recordNumber, String doctorId, String dateTime)`: si el médico todavía no atiende al paciente, se lo asigna. Las citas se numeran 1, 2, 3…
  - `void performConsultation(int appointmentNumber, String diagnosis)`: rechaza un número de cita que no existe.
  - `int getPatientCount()`, `int getDoctorCount()`, `int getAppointmentCount()`, `int getPendingAppointmentCount()`

### Test

El archivo [HospitalTest.java](HospitalTest.java) **no se modifica**. Va en la misma carpeta que tus cuatro clases:

```bash
javac -d out *.java
java -cp out HospitalTest
```

- Si no compila, alguna firma no coincide con el enunciado.
- Cada línea imprime `PASS` o `FAIL`, y la última es tu puntaje: `Result: N/62 checks passed`.
- La sección 5 revisa la encapsulación **con los metadatos del compilador**: un atributo `public`, un setter o un arreglo devuelto se detectan solos.
- El test es una herramienta para que verifiques tu trabajo. **La nota se pone sobre tu código**: el docente lee cada clase y revisa que las reglas estén donde corresponden.

---

## Entrega

```
~/examen/
├── access.log
├── reportes/            ips.txt · no_autorizados.txt · top3_rutas.txt · ip_sospechosa.txt  (salida + comando comentado)
├── hospital/            Patient.java · Doctor.java · Appointment.java · HospitalSystem.java · HospitalTest.java
└── BITACORA-IA.md       solo si usaste IA
```

Commit y push a tu repositorio antes del cierre, o comprimido con `tar -czf examen-<apellido>.tar.gz examen/`.

---

## Criterios de evaluación

| Parte | Qué se evalúa | Puntos |
|---|---|---|
| **Ejercicio 1** | Cada archivo con la salida correcta **y** el comando usado comentado debajo. Tareas 2, 4 y 5: 8 pts cada una · tarea 3: 6 pts | 30 |
| **Ejercicio 2** | El código de cada clase: validaciones en el lugar correcto, atributos privados, sin setters, sin arreglos expuestos, firmas del enunciado. `Patient` 14 · `Doctor` 12 · `Appointment` 10 · `HospitalSystem` 10 · reglas comunes 4 | 50 |
| **Defensa** | Explicación línea por línea a pedido y cambio en vivo sin ayuda | 20 |
| | **Total** | **100** |

En el Ejercicio 1, una salida sin su comando no suma. En el Ejercicio 2, una regla que el test no detecta pero que está mal ubicada en el código (por ejemplo, validar la edad en `HospitalSystem` en lugar de en `Patient`) pierde puntos igual.

Si se usó IA y la defensa no se sostiene, la parte correspondiente no cuenta.
