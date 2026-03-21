
package modelo.hospital;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; // Para copias defensivas

import modelo.abstractas.Empleado;
import modelo.personas.Medico;
import modelo.personas.Paciente;


public class Hospital {
    
    private String nombre;
    private String direccion;
    private List<Empleado> empleados; // Agregación con Empleado
    private List<Paciente> pacientes; // Agregación con Pacientes
    private List<CitaMedica> citas; // Composición con Citas
    
    // Constructor
    public Hospital (String nombre, String direccion){
        setNombre(nombre);
        setDireccion(direccion);
        this.empleados = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }    
    
    // — Getters —
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public List<Empleado> getEmpleados() { return Collections.unmodifiableList(empleados); } // Copia defensiva más segura
    public List<Paciente> getPacientes() { return Collections.unmodifiableList(pacientes); } // Copia defensiva más segura
    public List<CitaMedica> getCitas() { return Collections.unmodifiableList(citas); } // Copia defensiva más segura
    
    // — Setters —
    public void setNombre(String nombre){
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre del hospital no puede estar vacío.");
        this.nombre = nombre.trim();
    }
    
    public void setDireccion(String direccion){
        if (direccion == null || direccion.isBlank())
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        this.direccion = direccion.trim();
    }
    
    // — Otros Métodos —
    public void contratarEmpleado(Empleado empleado){
        if (empleado == null){
            throw new IllegalArgumentException("El empleado no puede ser nulo.");
        }
        if (!empleado.isActivo()){
            throw new IllegalArgumentException("No se puede contratar un empleado inactivo.");
        }
        empleados.add(empleado);
        System.out.println("Contratado: " + empleado.getNombreCompleto()
                + " (" + empleado.obtenerTipo() + ")");
    }
    
    /*
        COMPOSICIÓN: el Hospital crea la CitaMedica.
        Establece la ASOCIACIÓN bidireccional CitaMedica ↔ Paciente.
    */
    public CitaMedica agendarCita(String id, Paciente paciente, Medico medico,
                                   LocalDateTime fechaHora, String motivo) {
        if (!empleados.contains(medico))
            throw new IllegalArgumentException(
                    "El medico " + medico.getNombreCompleto() + " no esta contratado en este hospital.");
        if (!pacientes.contains(paciente))
            throw new IllegalArgumentException(
                    "El paciente " + paciente.getNombreCompleto() + " no esta registrado en este hospital.");

        CitaMedica cita = new CitaMedica(id, paciente, medico, fechaHora, motivo);
        citas.add(cita);             // Composición: la cita vive dentro del hospital
        paciente.agregarCita(cita);  // Asociación bidireccional
        System.out.println("Cita agendada: " + cita);
        return cita;
    }
    
    public double calcularNominaTotal(){
        double total = 0;
        for (Empleado e : empleados) {
            if (e.isActivo()) {
                total += e.calcularSalario(); // --> Polimorfismo
            }
        }
        return total;
    }
    
    // toString() para mostrar los datos completos del hospital
    @Override
    public String toString() {
        return "Hospital " + nombre + " | " + direccion + "\n"
                + "Empleados: " + empleados.size()
                + "Pacientes: " + pacientes.size()
                + "Citas: " + citas.size();
    }
    
}
