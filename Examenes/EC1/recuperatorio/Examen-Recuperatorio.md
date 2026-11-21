# Evaluación Continua 1 — Recuperatorio

**SIS-113 Programación II** · Presencial, en tu propio entorno (WSL o macOS) · 90 minutos

---

## Reglas

- Se puede consultar `--help` y la documentación oficial de Java.
- **IA:** si la usas, pega el prompt en `recuperatorio/BITACORA-IA.md`. Cualquier línea de tu entrega puede ser pedida para **explicarla en voz alta**. Si no la puedes explicar, no cuenta.
- Al terminar, el docente puede pedirte un **cambio en vivo** sobre tu propio código.

## Preparación

```bash
mkdir -p ~/recuperatorio/hospital && cd ~/recuperatorio/hospital
# copiar aquí HospitalTest.java (lo entrega el docente)
```

---

# Sistema de Gestión de Hospital (Java)

**Contexto**

Un hospital necesita un sistema para gestionar pacientes, médicos y citas médicas. El sistema debe controlar estrictamente el acceso a información sensible y garantizar que las operaciones médicas sigan protocolos específicos.

**Cómo responde el sistema a un error**

| Situación | Qué hace |
|---|---|
| Un **constructor** recibe datos inválidos | lanza `IllegalArgumentException`: el objeto inválido no llega a existir |
| `HospitalSystem` no puede **registrar** algo | devuelve `null` y no lo cuenta |
| Una **operación** no se puede hacer (asignar, diagnosticar, agendar, consultar) | devuelve `false` y no cambia nada |

**Reglas comunes a todas las clases (10 pts)**

- Todos los atributos son `private`. Los que no cambian después del constructor, además `final`.
- **No hay setters** (`setX`). El estado cambia solo con métodos que tienen nombre de negocio: `changeStatus`, `suspendLicense`, `assignPatient`.
- Ningún método público devuelve una lista o un arreglo interno.
- Las colecciones pueden ser `ArrayList` o arreglos con contador, como prefieras.
- El código va en inglés: nombres de clases, métodos y variables.
- Cada regla vive **dentro de la clase dueña del dato**: la edad se valida en `Patient`, el límite de 20 pacientes en `Doctor`. No en el `main` ni en `HospitalSystem`.

Las firmas de abajo son **obligatorias**: el archivo de prueba las usa tal cual.

## Clase `Patient` (20 pts)

- **Datos privados:** número de historia clínica, nombre, edad, diagnóstico, estado activo/inactivo.
- **Constructor:** `Patient(String recordNumber, String name, int age)`. El paciente nace **activo** y sin diagnóstico.
- **Validaciones encapsuladas:**
  - Historia clínica y nombre no pueden estar vacíos.
  - La edad debe estar entre 0 y 120 años.
  - El diagnóstico solo puede ser **escrito o leído** por personal autorizado: un médico con licencia vigente que tiene asignado a ese paciente.
- **Métodos públicos:**
  - `String getRecordNumber()`, `String getName()`, `int getAge()`, `boolean isActive()`
  - `boolean assignDiagnosis(Doctor doctor, String diagnosis)`: `false` si el médico no está autorizado o el diagnóstico está vacío.
  - `String getDiagnosis(Doctor requester)`: `null` si quien pregunta no está autorizado.
  - `void changeStatus(boolean active)`
  - `String basicInfo()`: historia clínica, nombre, edad y estado. **Nunca el diagnóstico.**

> No existe `getDiagnosis()` sin parámetros. Piensa por qué.

## Clase `Doctor` (20 pts)

- **Datos privados:** ID, nombre, especialidad, estado de licencia (vigente/suspendida), pacientes asignados (**máximo 20**).
- **Constructor:** `Doctor(String id, String name, String specialty)`. El médico nace con licencia **vigente**.
- **Validaciones encapsuladas:**
  - ID, nombre y especialidad no pueden estar vacíos.
  - Solo un médico con licencia vigente puede atender pacientes: recibirlos o diagnosticarlos.
  - No puede pasar de 20 pacientes asignados.
  - No acepta pacientes inactivos ni el mismo paciente dos veces.
- **Métodos públicos:**
  - `String getId()`, `String getName()`, `String getSpecialty()`, `boolean hasActiveLicense()`, `int getPatientCount()`
  - `boolean assignPatient(Patient patient)`
  - `boolean isTreating(Patient patient)`
  - `boolean diagnosePatient(Patient patient, String diagnosis)`
  - `void suspendLicense()`, `void reactivateLicense()`

## Clase `HospitalSystem` (20 pts)

- **Funcionalidades:** gestionar todo el flujo de pacientes, médicos y citas. Guarda sus colecciones en privado.
- **Operaciones críticas:**
  - `Patient registerPatient(String recordNumber, String name, int age)`: crea y registra al paciente. Devuelve `null` si los datos son inválidos o la historia clínica ya existe.
  - `Doctor registerDoctor(String id, String name, String specialty)`: igual, con el ID del médico.
  - `boolean scheduleAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime)`: programa una cita.
    - Médico y paciente tienen que estar **registrados en este hospital**.
    - Un paciente inactivo no puede tener nuevas citas, y un médico con la licencia suspendida tampoco.
    - La fecha es obligatoria y no puede estar en el pasado.
    - Si el médico todavía no atiende al paciente, se lo asigna. Si la asignación falla, por ejemplo porque ya tiene 20 pacientes, no hay cita.
  - `boolean performConsultation(Doctor doctor, Patient patient, String diagnosis)`: realiza la consulta de una cita **pendiente** entre ese médico y ese paciente. El médico diagnostica y la cita queda completada. Devuelve `false` si no hay cita pendiente, si el paciente está inactivo en ese momento o si el médico no puede diagnosticar. Una cita **no se realiza dos veces**.
  - `int getPatientCount()`, `int getDoctorCount()`, `int getAppointmentCount()`, `int getPendingAppointmentCount()`

> La forma de guardar las citas es libre: puedes crear una clase `Appointment` o resolverlo de otra manera. El test no la usa, pero el docente sí lee el diseño.

## Test

El archivo [HospitalTest.java](HospitalTest.java) **no se modifica**. Va en la misma carpeta que tus clases:

```bash
javac -d out *.java
java -cp out HospitalTest
```

- Si no compila, alguna firma no coincide con el enunciado.
- Las secciones 1 a 4 son los cuatro casos del ejercicio original: creación y validaciones, operaciones médicas, seguridad y escenarios límite. La 5 prueba las consultas y la 6 revisa la encapsulación **con los metadatos del compilador**: un atributo `public`, un setter o una lista devuelta se detectan solos.
- Cada línea imprime `PASS` o `FAIL`, y la última es tu puntaje: `Result: N/M checks passed`.
- El test es una herramienta para que verifiques tu trabajo. **La nota se pone sobre tu código**: el docente lee cada clase y revisa que las reglas estén donde corresponden.

---

## Entrega

```
~/recuperatorio/
├── hospital/        Patient.java · Doctor.java · HospitalSystem.java · (Appointment.java) · HospitalTest.java
└── BITACORA-IA.md   solo si usaste IA
```

Commit y push a tu repositorio antes del cierre, o comprimido con `tar -czf recuperatorio-<apellido>.tar.gz recuperatorio/`.

---

## Criterios de evaluación

| Parte | Qué se evalúa | Puntos |
|---|---|---|
| **Código** | Validaciones en la clase correcta, atributos privados, sin setters, sin colecciones expuestas, firmas del enunciado. `Patient` 20 · `Doctor` 20 · `HospitalSystem` 20 · reglas comunes 10 | 70 |
| **Defensa** | Explicación línea por línea a pedido y cambio en vivo sin ayuda | 30 |
| | **Total** | **100** |

Una regla que el test no detecta pero que está mal ubicada en el código (por ejemplo, validar la edad en `HospitalSystem` en lugar de en `Patient`) pierde puntos igual.

Si se usó IA y la defensa no se sostiene, la parte correspondiente no cuenta.
