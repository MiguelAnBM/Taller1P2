
package servicios;

import java.time.LocalDate;

/*
    Creé una clase record para poder leer datos de empleados de forma más rápida
    y segura sin tener que validar constantemente los datos
*/

public record DatosEmpleado(
    String    id,
    String    nombre,
    String    apellido,
    LocalDate fechaNac,
    String    email,
    String    legajo,
    LocalDate fechaContratacion,
    double    salarioBase
){}
