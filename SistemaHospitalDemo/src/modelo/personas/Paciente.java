
package modelo.personas;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; // Para copias defensivas
import modelo.abstractas.Persona;

public class Paciente extends Persona {
    
    private String historiaClinicaId;
    private String grupoSanguineo;
    private List<String> alergias;
    private List<CitaMedica> citas; // Aún no he establecido la relación de asociación con CitaMedica
    
    // Constructor base
    public Paciente(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                    String historiaClinidaId, String grupoSanguineo) {
        
        super(id, nombre, apellido, fechaNacimiento, email);
        setHistoriaClinicaId(historiaClinicaId);
        setGrupoSanguineo(grupoSanguineo);
        this.alergias = new ArrayList<>();
        this.citas = new ArrayList<>();
    }
    
    // Sobrecarga para cuando grupo sanguíneo no esté determinado aún
    public Paciente(String id, String nombre, String apellido,
                    LocalDate fechaNacimiento, String email,
                    String historiaClinicaId) {
        this(id, nombre, apellido, fechaNacimiento, email,
             historiaClinicaId, "No determinado");
    }
    
    // — Getters —
    public String getHistoriaClinicaId() { return historiaClinicaId; }
    public String getGrupoSanguineo() { return grupoSanguineo; }
    public List<String> getAlergias() { return Collections.unmodifiableList(alergias); } // Copia defensiva más segura
    public List<CitaMedica> getCitas() { return Collections.unmodifiableList(citas); } // Copia defensiva más segura
    
    
    // — Setters con validación —
    public void setHistoriaClinicaId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID de historia clínica no puede estar vacío.");
        this.historiaClinicaId = id.trim();
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        if (grupoSanguineo == null || grupoSanguineo.isBlank())
            throw new IllegalArgumentException("El grupo sanguíneo no puede estar vacío.");
        this.grupoSanguineo = grupoSanguineo.trim();
    }
    
    // — Métodos del negocio — 
    public void agregarAlergia(String alergia) {
        if (alergia == null || alergia.isBlank())
            throw new IllegalArgumentException("La alergia no puede estar vacía.");
        String a = alergia.trim();
        if (alergias.contains(a))
            System.err.println("Alergia ya registrada: " + a);
        else {
            alergias.add(a);
            System.out.println("Alergia registrada para " + getNombreCompleto() + ": " + a);
        }
    }

    // Asocia una cita al paciente (Se debe llamar internamente por Hospital).
    void agregarCita(String cita) {
        if (cita != null) { citas.add(cita); }
    }

    public String obtenerHistorial() {
        /* 
            Usé StringBuilder porque al concatenar con " + " Java crea un nuevo
            objeto en memoria, entonces ajá StringBuilder es más óptimo porque
            funciona como un HashMap y al final con un toString me devuelve todo
            formateado
        */
        StringBuilder sb = new StringBuilder();
        sb.append("══ Historial Clínico: ").append(getNombreCompleto()).append(" ══\n");
        sb.append("HC ID    : ").append(historiaClinicaId).append("\n");
        sb.append("Sangre   : ").append(grupoSanguineo).append("\n");
        sb.append("Edad     : ").append(calcularEdad()).append(" años\n");
        sb.append("Alergias : ").append(alergias.isEmpty() ? "Ninguna" : String.join(", ", alergias)).append("\n");
        sb.append("Citas    : ").append(citas.size()).append("\n");
        citas.forEach(c -> sb.append("  --> ").append(c).append("\n"));
        return sb.toString();
    }
    
    // — Métodos abstractos —
    @Override
    public int calcularEdad() { return Period.between(getFechaNacimiento(), LocalDate.now()).getYears(); }

    @Override
    public String obtenerTipo() { return "Paciente"; } // --> Polimorfismo :D
    
}
