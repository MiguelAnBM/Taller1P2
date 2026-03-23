
package modelo.personas;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; // Para copias defensivas

import modelo.abstractas.Empleado;
import modelo.enums.Turno;

public class Enfermero extends Empleado {
    
    private Turno turno; // Dependencia con Turno
    private String areaAsignada;
    private List<Paciente> pacientesACargo; // Asociación con Paciente
  
    // Constructor
    public Enfermero(String id, String nombre, String apellido, 
            LocalDate fechaNacimiento, String email, String legajo, 
            LocalDate fechaContratacion, double salarioBase, Turno turno,
            String areaAsignada) {
        
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        setTurno(turno);
        setAreaAsignada(areaAsignada);
        this.pacientesACargo = new ArrayList<>();
    }
    
    // — Getters —
    public Turno getTurno(){ return turno; }
    public String getAreaAsignada(){ return areaAsignada; }
    public List<Paciente> getPacientesACargo(){ return Collections.unmodifiableList(pacientesACargo); } // Copia defensiva más segura
    
    // — Setters con validación —
    public void setTurno(Turno turno){
        if (turno == null)
            throw new IllegalArgumentException("El turno no puede ser nulo. Use el enum Turno.");
        this.turno = turno;
    }
    
    public void setAreaAsignada(String areaAsignada){
        if (areaAsignada == null || areaAsignada.isBlank())
            throw new IllegalArgumentException("El area asignada no puede estar vacia.");
        this.areaAsignada = areaAsignada.trim();
    }
    
    // — Otros métodos —
    public void asistirCirugia(Cirujano cirujano, Paciente paciente){
        if (cirujano == null || paciente == null){
            throw new IllegalArgumentException("Cirujano y paciente son obligatorios.");
        }
        System.out.println("Enf. " + getNombreCompleto()
                + " asiste al Dr. " + cirujano.getNombreCompleto()
                + " en la cirugia de " + paciente.getNombreCompleto());
    }
    
    // — Métodos abstractos Heredados —
    
    /*
       calcularSalario() para Enfermero:
       El recargo se obtiene directamente del enum Turno.
    */
    @Override
    public double calcularSalario() {
        double recargo = getSalarioBase() * turno.getRecargoSalarial();
        return getSalarioBase() + recargo;
    }

    @Override
    public String obtenerTipo() { return "Enfermero";}
    
}
