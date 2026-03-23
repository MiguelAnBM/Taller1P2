
package modelo.enums;

public enum EstadoCita {
    PENDIENTE  ("Pendiente",   "La cita aun no ha sido atendida"),
    COMPLETADA ("Completada",  "La cita fue atendida con diagnostico"),
    CANCELADA  ("Cancelada",   "La cita fue cancelada antes de realizarse");

    private final String estado;
    private final String descripcion;

    // Constructor
    EstadoCita(String estado, String descripcion) {
        this.estado = estado;
        this.descripcion = descripcion;
    }
    
    // — Getters —
    public String getEstado() { return estado; }
    public String getDescripcion() { return descripcion; }
    
    // — Otros métodos —
    @Override
    public String toString() { return estado; }
}
