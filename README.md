# taller1P2
evaluación de conocimientos hasta el corte 2
# 🏥 Sistema de Gestión Hospitalaria (SGH)

&gt; **Asignatura:** Programación Orientada a Objetos  
&gt; **Tema:** Fundamentos de POO, UML y Metodologías Ágiles

---

## 📋 Objetivo de Aprendizaje

Desarrollar una aplicación Java que integre los siguientes conceptos de POO:
- Estructura y organización de clases
- Constructores y sobrecarga
- Encapsulamiento (Getters y Setters)
- Análisis de requisitos
- Diagramas UML (Clases y Casos de Uso)
- Relaciones entre clases
- Herencia
- Polimorfismo
- Clases Abstractas

---

## 🎯 Descripción del Problema

Un hospital necesita digitalizar su sistema de gestión. El sistema debe permitir:

**Gestión de Pacientes:**
- Registrar pacientes con datos personales, historial clínico y grupo sanguíneo
- Registrar alergias y contraindicaciones
- Consultar historial de citas médicas

**Gestión de Personal:**
- Contratar diferentes tipos de empleados: médicos generales, cirujanos y enfermeros
- Asignar especialidades médicas con costos de consulta variables
- Gestionar turnos de trabajo (mañana, tarde, noche)
- Calcular salarios considerando bonificaciones por antigüedad, especialidad y turno

**Gestión de Citas:**
- Agendar citas médicas validando disponibilidad de médicos
- Calcular costos automáticamente según especialidad y tipo de atención
- Registrar diagnósticos y recetas médicas
- Cambiar estados de citas (pendiente, confirmada, en atención, completada, cancelada)

---

## 📖 Historias de Usuario (Product Backlog)

| ID | Historia | Prioridad | Story Points |
|:---|:---|:---:|:---:|
| **HU-01** | Como **recepcionista**, quiero registrar pacientes para mantener un historial médico digital | Alta | 5 |


---

## 🏗️ Requisitos de Diseño UML

### 1. Diagrama de Casos de Uso
**Actores identificados:**
- Recepcionista
- Médico
- Administrador
- Paciente

### 2. Diagrama de Clases
**Jerarquía de herencia obligatoria:**



**Relaciones entre clases a modelar:**
- Composición/Aggregación entre Hospital y sus entidades
- Asociación entre CitaMedica, Paciente y Medico
- Dependencia con enums (EstadoCita, Turno)

**Atributos y métodos mínimos requeridos:**

| Clase | Atributos clave | Métodos especiales |
|:---|:---|:---|
| `Persona` (abstracta) | id, nombre, apellido, fechaNacimiento, email | `calcularEdad()` (abstracto), `obtenerTipo()` (abstracto) |
| `Empleado` (abstracta) | legajo, fechaContratacion, salarioBase, activo | `calcularSalario()` (abstracto), `antiguedad()` |
| `Paciente` | historiaClinicaId, grupoSanguineo, alergias[], citas[] | `agregarAlergia()`, `obtenerHistorial()` |
| `Medico` | numeroLicencia, especialidad, pacientesAsignados[], citasAtendidas | `atenderPaciente()`, `calcularSalario()` |
| `Cirujano` | cirugiasRealizadas, disponibleEmergencias | `realizarCirugia()`, `calcularBono()` |
| `Enfermero` | turno, areaAsignada, pacientesACargo | `asistirCirugia()`, `calcularSalario()` |
| `Hospital` | nombre, direccion, empleados[], pacientes[], citas[] | `contratarEmpleado()`, `agendarCita()`, `calcularNominaTotal()` |
| `CitaMedica` | id, paciente, medico, fechaHora, motivo, estado, costo, diagnostico | `calcularCosto()`, `completar()`, `cancelar()` |
| `Especialidad` | codigo, nombre, descripcion, costoConsulta | getters/setters |
| `Diagnostico` | id, descripcion, receta, fecha, medico | getters/setters |

---

## 🔧 Requisitos Técnicos

### Conceptos POO Obligatorios

| Concepto | Requisito específico |
|:---|:---|
| **Clases Abstractas** | Mínimo 2: `Persona` y `Empleado`. Deben contener al menos un método abstracto cada una |
| **Herencia** | Jerarquía de 3 niveles: `Cirujano` hereda de `Medico`, que hereda de `Empleado`, que hereda de `Persona` |
| **Polimorfismo** | Sobrescritura de métodos: `calcularSalario()` debe comportarse diferente en `Medico`, `Cirujano` y `Enfermero`. `obtenerTipo()` debe retornar identificación específica por clase |
| **Constructores** | Uso obligatorio de `super()` en todas las clases hijas. Sobrecarga permitida |
| **Getters y Setters** | Todos los atributos privados. Getters de colecciones deben retornar copias defensivas |
| **Encapsulamiento** | Validaciones básicas en setters (ej: salario > 0, fechas válidas) |

### Restricciones Tecnológicas

| Aspecto | Requisito |
|:---|:---|
| Lenguaje | Java 17 o superior |
| Manejo de fechas | `java.time.LocalDate`, `java.time.LocalDateTime` (prohibido `java.util.Date`) |
| Colecciones | `java.util.List`, `java.util.ArrayList` (no arrays estáticos) |
| Estructura | Organización en paquetes obligatoria |

### Estructura de Paquetes Requerida



| Paquete | Contenido esperado |
|:---|:---|
| `modelo.abstractas` | `Persona.java`, `Empleado.java` |
| `modelo.personas` | `Paciente.java`, `Medico.java`, `Cirujano.java`, `Enfermero.java` |
| `modelo.hospital` | `Hospital.java`, `CitaMedica.java`, `Diagnostico.java`, `Especialidad.java` |
| `modelo.enums` | `EstadoCita.java`, `Turno.java` |
| `servicios` | *(Opcional)* Clases de utilidad o lógica de negocio adicional |
| `principal` | `SistemaHospitalDemo.java` o clase con método `main` |

---

## 📁 Entregables

### Obligatorios
1. **Código fuente Java** organizado en la estructura de paquetes indicada
2. **Diagrama de Casos de Uso** (formato: imagen PNG/JPG o archivo .puml)
3. **Diagrama de Clases** (formato: imagen PNG/JPG o archivo .puml)
4. **Historias de Usuario** documentadas en formato tabla (puede incluirse en este README)

### Opcionales
- Documentación JavaDoc en clases públicas
- Video corto (2 min máximo) demostrando la ejecución

### Prohibido
- Frameworks externos (Spring, Hibernate, etc.)
- Librerías de reducción de código (Lombok)
- Uso de `var` (explicitar tipos para claridad)

---

## ✅ Lista de Verificación Pre-Entrega

Antes de entregar, verifica:

- [ ] Las clases `Persona` y `Empleado` son abstractas y tienen métodos abstractos
- [ ] Todas las clases hijas implementan los métodos abstractos de sus padres
- [ ] Se usa `super()` en constructores de todas las clases que heredan
- [ ] Los getters de colecciones retornan `new ArrayList<>(lista)` (copia defensiva)
- [ ] Existe al menos una demostración de polimorfismo en la clase principal (ej: recorrer lista heterogénea de empleados y llamar `calcularSalario()`)
- [ ] Los diagramas UML coinciden con el código implementado
- [ ] El código compila y ejecuta sin errores

---

## 💡 Pistas de Implementación (No son respuestas)

1. **¿Cómo demostrar polimorfismo?** Crea una `List<Empleado>` que contenga instancias de `Medico`, `Cirujano` y `Enfermero`. Recórrela llamando a `calcularSalario()` y observa los resultados diferentes.

2. **¿Cómo manejar las relaciones?** Un `Hospital` "tiene" empleados y pacientes (composición). Una `CitaMedica` "conoce" a un `Paciente` y un `Medico` (asociación).

3. **¿Dónde validar?** Los setters son el lugar ideal para validar que un salario no sea negativo, o que una fecha no sea futura.

4. **¿Enums o constantes?** Usa `enum` para estados fijos como `EstadoCita` o `Turno`, permite `switch` más limpios y type-safety.

---

## 📚 Recursos Permitidos

- [Documentación oficial de Java](https://docs.oracle.com/en/java/)
- [PlantUML Guide](https://plantuml.com/)
- [Apuntes de clase y material del curso](https://java-learn-test.netlify.app/)

---

## 🚀 Instrucciones de Entrega

1. Crea un repositorio privado en GitHub haciendo fork al actual 
2. Sube el código en la estructura de carpetas indicada
3. Incluye los diagramas en una carpeta `/docs`
4. Agrega este README.md completo
5. Comparte el enlace al repositorio antes de la fecha límite

---

**Fecha límite de entrega:** proxima clase  
**Modalidad:** Individual o parejas (máximo 2 personas)

---

*Buena suerte con tu implementación. Recuerda: el objetivo es demostrar comprensión de los conceptos de POO, no solo "hacer que funcione".*
