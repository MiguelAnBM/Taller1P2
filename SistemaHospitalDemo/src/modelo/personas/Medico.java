package modelo.personas;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections; // Para copias defensivas
import java.util.ArrayList;

import modelo.abstractas.Empleado;
import modelo.hospital.Especialidad;

public class Medico extends Empleado {

    private String numeroLicencia;
    private Especialidad especialidad; // Asociación con Especialidad
    private List<Paciente> pacientesAsignados; // Asociación con Paciente
    private int citasAtendidas;

    // Constructor
    public Medico(String id, String nombre, String apellido,
            LocalDate fechaNacimiento, String email,
            String legajo, LocalDate fechaContratacion, double salarioBase,
            String numeroLicencia, Especialidad especialidad) {

        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        setNumeroLicencia(numeroLicencia);
        setEspecialidad(especialidad);
        this.pacientesAsignados = new ArrayList<>();
        this.citasAtendidas = 0; // Inicia por defecto en cero obviamente
        
    }

    // — Getters —
    public String getNumeroLicencia() { return numeroLicencia; }

    public Especialidad getEspecialidad() { return especialidad; }

    public List<Paciente> getPacientesAsignados() { return Collections.unmodifiableList(pacientesAsignados); } // Copia defensiva más segura

    public int getCitasAtendidas() { return citasAtendidas; }

    // — Setters con validación —
    public void setNumeroLicencia(String numeroLicencia) {
        if (numeroLicencia == null || numeroLicencia.isBlank()) {
            throw new IllegalArgumentException("El numero de licencia no puede estar vacio.");
        }
        this.numeroLicencia = numeroLicencia.trim();
    }

    public void setEspecialidad(Especialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad no puede ser nula.");
        }
        this.especialidad = especialidad;
    }
    
    // — Otros métodos —
    public void atenderPaciente(Paciente paciente){
        if (paciente == null){
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        if (!pacientesAsignados.contains(paciente)){
            pacientesAsignados.add(paciente);
            citasAtendidas++;
            System.out.println("Dr. " + getNombreCompleto()
                + " atendio a " + paciente.getNombreCompleto()
                + ". Total de citas: " + citasAtendidas);
        }
    }

    // — Métodos abstractos Heredados —
    
    /*
      calcularSalario() para Médico:
      Salario base + 5% por año de antigüedad + 1% por cada 10 citas atendidas.
    */
    @Override
    public double calcularSalario() {
        double bonusAntiguedad = getSalarioBase() * 0.05 * antiguedad();
        double bonusCitas = getSalarioBase() * 0.01 * (citasAtendidas / 10);
        return getSalarioBase() + bonusAntiguedad + bonusCitas;
    }

    @Override
    public String obtenerTipo() { return "Medico"; } 
}
