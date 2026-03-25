
package modelo.personas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.hospital.Especialidad;
import servicios.Formato;


public class Cirujano extends Medico {
    
    private int cirugiasRealizadas;
    private boolean disponibleEmergencias;
    
    // Constructor
    public Cirujano(String id, String nombre, String apellido, 
            LocalDate fechaNacimiento, String email, String legajo, 
            LocalDate fechaContratacion, double salarioBase, 
            String numeroLicencia, Especialidad especialidad) {
        
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase, numeroLicencia, especialidad);
        this.cirugiasRealizadas = 0; // Por defecto obviamente cero
        this.disponibleEmergencias = true; // Por defecto se encuentra disponible
        
    }
    
    // — Getters —
    public int getCirugiasRealizadas() { return cirugiasRealizadas; }
    public boolean isDisponibleEmergencias() { return disponibleEmergencias; }
    
    // — Setters con validación —
    public void setDisponibleEmergencias(boolean disponibleEmergencias) {
        this.disponibleEmergencias = disponibleEmergencias;
    }
    
    // — Otros métodos —
    public void realizarCirugia(Paciente paciente, String tipoCirugia) {
        if (paciente == null)
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        if (tipoCirugia == null || tipoCirugia.isBlank())
            throw new IllegalArgumentException("El tipo de cirugia no puede estar vacio.");
        cirugiasRealizadas++;
        System.out.println("Dr. " + getNombreCompleto()
                + " realiza cirugia de [" + tipoCirugia + "] a " + paciente.getNombreCompleto());
    }
    
    // Le puse un bono de 200.000 por cada cirugía realizada
    public double calcularBono(){ return cirugiasRealizadas * 200000;}
    
    // — Métodos abstractos Heredados —
    /*
      calcularSalario() para Cirujano:
      Hereda la lógica de Medico (super) y le suma el bono quirúrgico y 5% por antiguedad
    */
    @Override
    public double calcularSalario() {
        return super.calcularSalario() + calcularBono();
    }

    @Override
    public String obtenerTipo() { return "Cirujano"; }
    
    @Override
    public String toString(){
        return obtenerTipo() + " | " + "(ID: " + getId() + ")" + "\n"
                         + "Nombre              : " + getNombre() + "\n"
                         + "Apellido            : " + getApellido() + "\n"
                         + "FechaNac            : " + getFechaNacimiento() + "\n"
                         + "Email               : " + getEmail() + "\n"
                         + "Legajo              : " + getLegajo() + "\n"
                         + "Num.Licencia        : " + getNumeroLicencia() + "\n"
                         + "Especialidad        : " + getEspecialidad() + " P/C"  + "\n"
                         + "FechaContratacion   : " + getFechaContratacion() + "\n"
                         + "Antiguedad          : " + antiguedad() + " anos" + "\n"
                         + "Salario base        : " + Formato.mostrarUnidades(getSalarioBase()) + "\n"
                         + "Cirugias Realizadas : " + getCirugiasRealizadas() + "\n"
                         + "Disponibilidad Em.  : " + (isDisponibleEmergencias() ? "Disponible": "No disponible");
    }
}
