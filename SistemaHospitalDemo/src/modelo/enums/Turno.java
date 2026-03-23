
package modelo.enums;

public enum Turno {
    MANANA ("Manana", 0.00, "06:00 - 14:00"),
    TARDE  ("Tarde",  0.00, "14:00 - 22:00"),
    NOCHE  ("Noche",  0.20, "22:00 - 06:00");  // 20% de recargo nocturno

    private final String turno;
    private final double recargoSalarial; // Porcentaje adicional sobre salario base
    private final String horario;
    
    // Constructor
    Turno(String turno, double recargoSalarial, String horario) {
        this.turno = turno;
        this.recargoSalarial = recargoSalarial;
        this.horario = horario;
    }
    
    // — Getters —
    public String getTurno() { return turno; }
    public double getRecargoSalarial() { return recargoSalarial; }
    public String getHorario() { return horario; }
    
    // — Otros métodos —
    // toString para retornar un mensaje más bonito :b
    @Override
    public String toString() { return turno + " (" + horario + ")"; }
}
