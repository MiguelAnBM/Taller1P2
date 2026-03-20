package modelo.abstractas;

import java.time.LocalDate;

public abstract class Persona {

    private String id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String email;

    // Constructor base (Recordar usar super obligatoriamente para acceder aquí)
    // Lo escribí como setters para validar automáticamente al pasar los argumentos
    public Persona(String id, String nombre, String apellido,
            LocalDate fechaNacimiento, String email) {

        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(fechaNacimiento);
        setEmail(email);

    }
    
    // — Getters —
    public String getId(){ return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getNombreCompleto() {return nombre + " " + apellido;} // --> Para facilidad futura
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getEmail() { return email; }
    
    // — Setters con validación —
    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        this.id = id.trim(); // Para evitar guardar espacios en blanco
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido.trim();
    }

    public void setFechaNacimiento(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura: " + fecha);
        }
        this.fechaNacimiento = fecha;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
        this.email = email.trim().toLowerCase();
    }

    // ── Métodos abstractos — 
    public abstract int calcularEdad();
    public abstract String obtenerTipo(); 
    
    
    // El toString de toda la vida
    @Override
    public String toString() {
        return obtenerTipo() + " | " + getNombreCompleto() + " (ID: " + id + ")";
    }

}
