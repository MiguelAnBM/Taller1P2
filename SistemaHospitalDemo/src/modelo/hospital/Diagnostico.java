
package modelo.hospital;

import java.time.LocalDate;

import modelo.personas.Medico;

public class Diagnostico {
    
    private String id;
    private String descripcion;
    private String receta;
    private LocalDate fecha;
    private Medico medico; // Asociación con Medico
    
    // Constructor
    public Diagnostico(String id, String descripcion, String receta,
                        LocalDate fecha, Medico medico){
        
        setId(id);
        setDescripcion(descripcion);
        setReceta(receta);
        setFecha(fecha);
        setMedico(medico);
        
    }
    
    // — Getters —
    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public String getReceta() { return receta; }
    public LocalDate getFecha() { return fecha; }
    public Medico getMedico() { return medico; }
    
    // — Setters con validación —
    public void setId(String id){
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID del diagnóstico no puede estar vacío.");
        this.id = id.trim();
    }
    
    public void setDescripcion(String descripcion){
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        this.descripcion = descripcion.trim();
    }
    
    public void setReceta(String receta){
        this.receta = (receta == null || receta.isBlank()) ? "Sin receta" : receta.trim();
    }
    
    public void setFecha(LocalDate fecha){
        if (fecha == null)
            throw new IllegalArgumentException("La fecha del diagnóstico no puede ser nula.");
        if (fecha.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha no puede ser futura: " + fecha);
        this.fecha = fecha;
    }
    
    public void setMedico(Medico medico){
        if (medico == null)
            throw new IllegalArgumentException("El médico no puede ser nulo.");
        this.medico = medico;
    }
    
    // — Otros Métodos —
    @Override
    public String toString() {
        return "Diag.[" + id + "] " + descripcion + " | " + fecha;
    }
}
