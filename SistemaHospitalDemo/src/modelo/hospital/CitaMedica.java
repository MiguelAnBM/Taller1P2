
package modelo.hospital;

import java.time.LocalDateTime;

import modelo.enums.EstadoCita;
import modelo.personas.Medico;
import modelo.personas.Paciente;
import servicios.Formato;

public class CitaMedica {
    
    private String id;
    private Paciente paciente; // Asociación con Paciente
    private Medico medico; // Asociación con Medico
    private LocalDateTime fechaHora;
    private String motivo;
    private EstadoCita estado; // Dependencia con enum EstadoCita
    private double costo;
    private Diagnostico diagnostico; // Asociación con Diagnostico
    
    // Constructor
    public CitaMedica(String id, Paciente paciente, Medico medico,
            LocalDateTime fechaHora, String motivo){
        
        setId(id);
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        setMotivo(motivo);
        this.estado = EstadoCita.PENDIENTE; // Por defecto es PENDIENTE
        this.costo = 0; // Por defecto es cero
        this.diagnostico = null; // Por defecto se encuentra null
        
    }
    
    // — Getters —
    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public EstadoCita getEstado() { return estado; }
    public double getCosto() { return costo; }
    public Diagnostico getDiagnostico() { return diagnostico; }
    
     // — Setters con validación —
    
    public void setId(String id){
        if (id == null || id.isBlank()){
            throw new IllegalArgumentException("El ID de cita no puede estar vacio.");
        }
        this.id = id.trim();
    }
    
    public void setPaciente(Paciente paciente){
        if (paciente == null){
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        this.paciente = paciente;
    }
    
    public void setMedico(Medico medico){
        if (medico == null){
            throw new IllegalArgumentException("El medico no puede ser nulo.");
        }
        this.medico = medico;
    }
    
    public void setFechaHora(LocalDateTime fechaHora){
        if (fechaHora == null){
            throw new IllegalArgumentException("La fecha/hora no puede ser nula.");
        }
        this.fechaHora = fechaHora;
    }
    
    public void setMotivo(String motivo){
        if (motivo == null || motivo.isBlank()){
            throw new IllegalArgumentException("El motivo no puede estar vacio.");
        }
        this.motivo = motivo.trim();
    }
    
    /*  
        No creé setEstado(), setCosto() ni setDiagnostico() porque 
        son datos que deben ser manipulados únicamente en completar() para
        mayor seguridad y encapsulamiento
    */ 
    
    // — Otros métodos —
    
    // Costo base de la especialidad + 10% si ya tiene diagnóstico. 
    public double calcularCosto() {
        double base = medico.getEspecialidad().getCostoConsulta();
        costo = (diagnostico != null) ? base * 1.10 : base;
        return costo;
    }
    
    public void completar(Diagnostico diagnostico) {
        if (estado != EstadoCita.PENDIENTE) {
            System.err.println("Cita [" + id + "] no puede completarse. Estado: "
                    + estado.getEstado());
            return;
        }
        if (diagnostico == null){
            throw new IllegalArgumentException("Se requiere un diagnostico para completar la cita.");
        }
        this.diagnostico = diagnostico;
        this.estado = EstadoCita.COMPLETADA;
        calcularCosto();
        medico.atenderPaciente(paciente); // Asociación con Médico
        System.out.println("Cita [" + id + "] completada. Costo: $" + Formato.mostrarUnidades(costo));
    }
    
    public void cancelar() {
        if (estado != EstadoCita.PENDIENTE) {
            System.err.println("Cita [" + id + "] no puede cancelarse. Estado: "
                    + estado.getEstado());
            return;
        }
        estado = EstadoCita.CANCELADA;
        System.out.println("Cita [" + id + "] cancelada. --> " + estado.getDescripcion());
    }
    
    // toString() para presentar más bonito los datos :b
    @Override
    public String toString() {
        return "Cita[" + id + "] "
             + "Paciente     :" + paciente.getNombreCompleto() + "\n"
             + "Doctor       :" + medico.getNombreCompleto() + "\n"
             + "Fecha y Hora :" + fechaHora + "\n"
             + "Estado       :" + estado.getEstado() + "\n"
             + "Costo:       "  + ((estado == EstadoCita.COMPLETADA) ? costo : "Por definir");
    }
}
