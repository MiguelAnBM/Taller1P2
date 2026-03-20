package modelo.abstractas;

import java.time.LocalDate;
import java.time.Period;

abstract class Empleado extends Persona {

    private String legajo;
    private LocalDate fechaContratacion;
    private double salarioBase;
    private boolean activo;

    // Constructor base
    public Empleado(String id, String nombre, String apellido, LocalDate fechaNacimiento,
            String email, String legajo, LocalDate fechaContratacion, double salarioBase) {

        super(id, nombre, apellido, fechaNacimiento, email);
        setLegajo(legajo);
        setFechaContratacion(fechaContratacion);
        setSalarioBase(salarioBase);
        this.activo = true;
    }
    
    // — Getters —
    public String    getLegajo()            { return legajo; }
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public double    getSalarioBase()       { return salarioBase; }
    public boolean   isActivo()             { return activo; }

    // — Setters con validación —
    public void setLegajo(String legajo) {
        if (legajo == null || legajo.isBlank())
            throw new IllegalArgumentException("El legajo no puede estar vacío.");
        this.legajo = legajo.trim();
    }

    public void setFechaContratacion(LocalDate fecha) {
        if (fecha == null)
            throw new IllegalArgumentException("La fecha de contratación no puede ser nula.");
        if (fecha.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de contratación no puede ser futura: " + fecha);
        if (getFechaNacimiento() != null && fecha.isBefore(getFechaNacimiento().plusYears(18)))
            throw new IllegalArgumentException("El empleado debe tener mínimo 18 años al contratarse.");
        this.fechaContratacion = fecha;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase <= 0)
            throw new IllegalArgumentException("El salario base debe ser > 0. Recibido: " + salarioBase);
        this.salarioBase = salarioBase;
    }

    public void setActivo(boolean activo) { this.activo = activo; }

    // — Otros métodos —
    
    // calcularEdad() implementado aquí para todos los Empleados
    @Override
    public int calcularEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    // Retorna los años transcurridos desde la fecha de contratación
    public int antiguedad() {
        return Period.between(fechaContratacion, LocalDate.now()).getYears();
    }

    // Método abstracto
    public abstract double calcularSalario();
}
