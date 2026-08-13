# Examen Diagnóstico — Programación II (SIS-113)

## Instrucciones

Este diagnóstico tiene dos propósitos:
1. **Para ti:** conocer tu punto de partida real al entrar a Programación II.
2. **Para mí:** adaptar el ritmo del curso al grupo (cuánto repasamos de Programación I, cuánto tiempo dedicamos a terminal, etc.).

**Esto NO tiene nota.** No hay respuestas correctas o incorrectas en la parte personal. En la parte técnica responde con honestidad: si no sabes algo, escribe **"No sé"**. Eso me sirve muchísimo más que una respuesta inventada o copiada.

**Importante:** resuelve este diagnóstico **sin usar IA, sin buscar en internet y sin revisar tus apuntes.** Si lo respondes con ayuda, el curso queda mal calibrado y el perjudicado eres tú. La Parte 5 sí pregunta explícitamente por tu uso de IA — ahí puedes ser totalmente franco, no hay castigo.

**Tiempo estimado:** 40-50 minutos.

---

## PARTE 1: Sobre ti

### 1.1 Información general

1. **Nombre completo:**

2. **¿Qué semestre estás cursando y cuándo aprobaste Programación I?**

3. **¿Cuántas materias de programación has llevado en total (incluyendo Programación I)?**

4. **¿Qué computadora usarás para el curso?**
   - [ ] Laptop propia
   - [ ] PC de escritorio en casa
   - [ ] Laboratorio de la universidad / prestada
   - [ ] Otra:

   *Sistema operativo:*  [ ] Windows   [ ] macOS   [ ] Linux   [ ] No sé

5. **¿Tienes conexión estable a internet en casa?**
   - [ ] Sí, sin problemas
   - [ ] Sí, pero es lenta o intermitente
   - [ ] No, dependo del internet de la universidad


6. **¿Tienes cuenta de GitHub?**
   - [ ] No sé qué es GitHub
   - [ ] Sé qué es pero no tengo cuenta
   - [ ] Sí → Usuario:

7. **¿Cuál es tu nivel de inglés?**
   - [ ] Básico — entiendo palabras sueltas, uso traductor constantemente
   - [ ] Intermedio — puedo leer documentación con esfuerzo
   - [ ] Intermedio-alto — leo documentación técnica sin problemas
   - [ ] Avanzado — podría seguir una clase entera en inglés

---

### 1.2 Motivación y expectativas

9. **¿Por qué elegiste Ingeniería de Sistemas / Software?**

10. **¿Qué área te llama más la atención hoy?** (marca todas las que apliquen)
    - [ ] Desarrollo web
    - [ ] Aplicaciones móviles
    - [ ] Videojuegos
    - [ ] Datos / Inteligencia Artificial
    - [ ] Redes / Infraestructura / Ciberseguridad
    - [ ] Todavía no lo sé
    - [ ] Otra:

11. **En una escala del 1 al 10, ¿qué tan cómodo te sientes programando hoy?**

    `1 [  ] 2 [  ] 3 [  ] 4 [  ] 5 [  ] 6 [  ] 7 [  ] 8 [  ] 9 [  ] 10 [  ]`

    *¿Por qué elegiste ese número?*

12. **¿Qué fue lo más difícil de Programación I para ti?**

13. **¿Qué te preocupa más de Programación II?**

---

### 1.3 Tu programa más desafiante

*Piensa en el programa más difícil que hayas escrito hasta ahora (de Programación I, de un curso propio o por hobby).*

14. **Descríbelo:**

    a) **¿Qué hacía el programa?**

    b) **¿En qué lenguaje lo escribiste?**

    c) **¿Cuál fue la parte más difícil?**

    d) **¿Cómo lograste resolverla?** *(sé específico: ¿preguntaste, buscaste, probaste, usaste IA?)*

    e) **¿Qué aprendiste de esa experiencia?**

    f) **Si lo tuvieras que hacer de nuevo, ¿qué harías diferente?**

---

## PARTE 2: Autoevaluación técnica

*Marca tu nivel honestamente. Esto NO afecta tu nota.*

| Tema / Herramienta | Nunca lo usé | Lo vi pero no recuerdo | Puedo usarlo con ayuda | Lo domino |
|---|---|---|---|---|
| Variables y tipos de datos | | | | |
| Condicionales (`if` / `switch`) | | | | |
| Ciclos (`for`, `while`) | | | | |
| Arreglos / vectores | | | | |
| Matrices (arreglos 2D) | | | | |
| Funciones / métodos con parámetros | | | | |
| Recursividad | | | | |
| Cadenas de texto (strings) y su manipulación | | | | |
| Lectura y escritura de archivos | | | | |
| Manejo de errores (`try` / `catch`) | | | | |
| Clases y objetos | | | | |
| Encapsulamiento (`private`, `public`, propiedades) | | | | |
| Herencia | | | | |
| Polimorfismo | | | | |
| Concurrencia / hilos | | | | |
| Lenguaje C# | | | | |
| Visual Studio / VS Code | | | | |
| Terminal o consola de comandos | | | | |
| Linux | | | | |
| Shell script (`.sh`) | | | | |
| Git (commit, push) | | | | |
| GitHub (repositorios, clonar) | | | | |
| Depurador (debugger, breakpoints) | | | | |
| Asistentes de IA para programar | | | | |

15. **¿En qué lenguaje(s) programaste en Programación I?**
    - [ ] C
    - [ ] C++
    - [ ] Java
    - [ ] Python
    - [ ] C#
    - [ ] Otro:

---

## PARTE 3: Preguntas técnicas — fundamentos

*Responde lo mejor que puedas. Si no sabes, escribe "No sé". Los ejemplos están en C#, pero puedes responder pensando en el lenguaje que aprendiste.*

### 3.1 Lógica y estructuras básicas

**16. ¿Cuál es la diferencia entre un ciclo `for` y un ciclo `while`? ¿Cuándo usarías cada uno?**

**17. ¿Qué imprime este código y por qué?**

```csharp
int suma = 0;
for (int i = 1; i <= 5; i++)
{
    if (i % 2 == 0)
        continue;
    suma += i;
}
Console.WriteLine(suma);
```

**18. Escribe una función que reciba un arreglo de enteros y devuelva el valor más grande.** *(Pseudocódigo o cualquier lenguaje.)*

**19. Este código tiene un error. ¿Cuál es y cómo lo arreglarías?**

```csharp
int[] numeros = new int[5];
for (int i = 0; i <= 5; i++)
{
    numeros[i] = i * 2;
}
```

**20. ¿Qué diferencia hay entre pasar un parámetro por valor y por referencia?**

---

### 3.2 Primeros conceptos de objetos

*Si nunca viste esto, escribe "No sé" con confianza — es justamente lo que vamos a construir en el curso.*

**21. En tus propias palabras: ¿qué es una clase y qué es un objeto? ¿En qué se diferencian?**

**22. Observa este código:**

```csharp
class Estudiante
{
    public string Nombre;
    public double Nota;
}
```

a) ¿Cómo crearías un objeto `Estudiante` con nombre "Ana" y nota 85?

b) ¿Qué problema podría traer que `Nota` sea `public`?

**23. Si tuvieras que representar un "Vehículo" en un programa, ¿qué datos (atributos) y qué acciones (métodos) le pondrías? Escribe al menos 3 de cada uno.**

**24. ¿Has escuchado los términos "encapsulamiento", "herencia" o "polimorfismo"? Explica con tus palabras el que mejor entiendas (o escribe "No sé").**

---

### 3.3 Terminal y sistema operativo

**25. ¿Qué es la terminal (o consola de comandos) y para qué crees que sirve?**

**26. ¿Sabes qué hacen estos comandos? Escribe lo que creas, o "No sé".**
- `ls` (o `dir`):
- `cd`:
- `pwd`:
- `mkdir`:
- `rm`:
- `cat`:

**27. ¿Cuál es la diferencia entre una ruta absoluta (`/home/ana/tarea.txt`) y una ruta relativa (`tarea.txt`)?**

**28. ¿Has usado alguna vez Linux? ¿En qué contexto?**

---

### 3.4 Archivos, versiones y colaboración

**29. Si trabajas en un proyecto durante varias semanas, ¿cómo guardas las versiones de tu código hoy?** *(sé honesto: `tarea_final_v2_BUENA.zip` es una respuesta válida)*

**30. ¿Qué es Git y qué problema resuelve? Si no sabes, ¿cómo crees que hacen los equipos de programadores para trabajar sobre el mismo código sin pisarse?**

**31. ¿Has trabajado en equipo en un proyecto de programación? ¿Cómo se repartieron el trabajo y qué salió mal?**

---

### 3.5 Depuración y resolución de problemas

**32. Tu programa compila pero da un resultado incorrecto. ¿Cuáles son los primeros 3 pasos que darías para encontrar el error?**

**33. Estás atascado 40 minutos en un error que no entiendes. ¿Qué haces?** *(describe tu proceso real, en orden)*

**34. ¿Qué es un "breakpoint" y para qué sirve? Si no lo has usado, escribe "No sé".**

---

## PARTE 4: Pregunta abierta

**35. Explícale a un familiar que no sabe nada de tecnología qué es un programa de computadora y cómo hace la máquina para "entenderlo" y ejecutarlo.**

*Escribe 2-4 párrafos. No uses jerga técnica sin explicarla.*

---

## PARTE 5: Uso de Inteligencia Artificial

*Esta sección es clave para el curso y **no tiene ninguna consecuencia negativa**. Necesito saber de dónde partimos para definir en qué momentos la IA estará permitida, restringida o prohibida. Responde con total franqueza.*

**36. ¿Usas alguna herramienta de IA para programar o estudiar?** (marca todas las que uses)
- [ ] No uso ninguna
- [ ] ChatGPT
- [ ] Claude
- [ ] Gemini
- [ ] GitHub Copilot
- [ ] Otra:

**37. ¿Con qué frecuencia usaste IA en Programación I?**
- [ ] Nunca
- [ ] Rara vez, para dudas puntuales
- [ ] Frecuentemente, para entender conceptos
- [ ] Frecuentemente, para escribir partes del código
- [ ] Casi siempre, para resolver los ejercicios completos

**38. ¿Para qué la usaste principalmente?** (marca todas las que apliquen)
- [ ] Explicarme un concepto que no entendí en clase
- [ ] Encontrar errores en mi código
- [ ] Escribir código desde cero
- [ ] Traducir o entender documentación en inglés
- [ ] Hacer la tarea completa
- [ ] No la usé

**39. Cuando la IA te da código, ¿qué haces normalmente?**
- [ ] Lo copio y pruebo si funciona
- [ ] Lo leo línea por línea hasta entenderlo
- [ ] Lo reescribo con mis propias palabras
- [ ] Le pido que me explique cada parte
- [ ] Depende del tiempo que tenga

**40. Sé honesto: ¿podrías explicar, línea por línea y sin ayuda, el último código que la IA escribió para ti?**
- [ ] Sí, completamente
- [ ] En parte
- [ ] No
- [ ] Nunca he usado IA para generar código

**41. ¿Crees que usar IA te ayudó o te perjudicó al aprender a programar? ¿Por qué?**

**42. ¿Has usado alguna vez una herramienta de IA desde la terminal (un "agente" de línea de comandos, no un chat en el navegador)?**
- [ ] No sabía que eso existía
- [ ] Sé que existe pero no la he usado
- [ ] Sí → ¿cuál?

---

## PARTE 6: Reflexión final

**43. Después de responder este diagnóstico, ¿qué temas sientes que necesitas reforzar más?**

**44. ¿Hay algo del curso que te gustaría que veamos con más profundidad o más despacio?**

**45. ¿Cómo aprendes mejor?**
- [ ] Practicando yo mismo con guía
- [ ] Leyendo material y luego aplicándolo
- [ ] Trabajando en equipo
- [ ] Otro:

**46. ¿Algo más que quieras que sepa sobre ti, tu situación o tus expectativas?**

---

## Entrega

- **Formato:** documento digital (Markdown, Word o PDF)
- **Fecha límite:** [Antes de la Semana 1 — primera sesión]
- **Enviar a:** [Email / Plataforma]

---

*Este diagnóstico es confidencial, no lleva nota y solo será usado para ajustar el curso a las necesidades reales del grupo.*
