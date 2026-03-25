
package modelo.hospital;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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
    public List<Empleado> getEmpleados() { return new ArrayList<>(empleados); }
    public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); }
    public List<CitaMedica> getCitas() { return new ArrayList<>(citas); }
    
    // — Setters con validación —
    public void setNombre(String nombre){
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre del hospital no puede estar vacio.");
        this.nombre = nombre.trim();
    }
    
    public void setDireccion(String direccion){
        if (direccion == null || direccion.isBlank())
            throw new IllegalArgumentException("La direccion no puede estar vacia.");
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
        AGREGACIÓN: recibe un paciente ya existente y lo registra en el hospital.
    */
    public void registrarPaciente(Paciente paciente) {
        if (paciente == null)
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        pacientes.add(paciente);
        System.out.println("\nPaciente " + paciente.getNombreCompleto() + " registrado correctamente.");
    }
    
    // Busca, valida y retorna una cita
    public CitaMedica buscarCita(String id) {
        return citas.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }
    
    // Método para buscar un paciente por su id
    public Paciente buscarPaciente(String id) {
        return pacientes.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }
    
    // Busca, valida y retorna un Médico
    public Medico buscarMedico(String id) {
        return empleados.stream()
                .filter(e -> e instanceof Medico && e.getId().equalsIgnoreCase(id))
                .map(e -> (Medico) e)
                .findFirst().orElse(null);
    }

    
    /*
        COMPOSICIÓN: el Hospital crea la CitaMedica.
        Establece la ASOCIACIÓN  CitaMedica ↔ Paciente.
    */
    public CitaMedica agendarCita(String id, Paciente paciente, Medico medico,
                                   LocalDateTime fechaHora, String motivo) {
        if (!empleados.contains(medico))
            throw new IllegalArgumentException(
                    "El medico " + medico.getNombreCompleto() + " no esta contratado en este hospital.");
        if (!medico.isActivo()) {
            throw new IllegalArgumentException(
                    "El medico " + medico.getNombreCompleto() + " no esta activo actualmente.");
        }
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
                + "Empleados: " + empleados.size() + "\n"
                + "Pacientes: " + pacientes.size() + "\n"
                + "Citas: " + citas.size();
    }
    
}
