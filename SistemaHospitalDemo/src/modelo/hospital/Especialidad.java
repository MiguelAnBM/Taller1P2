
package modelo.hospital;
import servicios.Formato;

public class Especialidad {
    
    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoConsulta;
    
    public Especialidad(String codigo, String nombre, String descripcion,
                        double costoConsulta){
        
        setCodigo(codigo);
        setNombre(nombre);
        setDescripcion(descripcion);
        setCostoConsulta(costoConsulta);
        
    }
    
    // — Getters —
    public String getCodigo(){ return codigo; }
    public String getNombre(){ return nombre; }
    public String getDescripcion(){ return descripcion; }
    public double getCostoConsulta(){ return costoConsulta; }
   
    // — Setters con validación —
    public void setCodigo(String codigo){
        if (codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("El codigo de especialidad no puede estar vacio.");
        }
        this.codigo = codigo.trim();
    }
    
    public void setNombre(String nombre){
        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre de especialidad no puede estar vacio.");
        }
        this.nombre = nombre.trim();
    }
    
    public void setDescripcion(String descripcion){
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripcion de la especialidad no puede estar vacia.");
        }
        this.descripcion = descripcion.trim();
    }
    
    public void setCostoConsulta(double costoConsulta){
        if (costoConsulta <= 0){
            throw new IllegalArgumentException( "El costo de consulta debe ser > 0. Recibido: " + costoConsulta);
        }
        this.costoConsulta = costoConsulta;
    }
    
    
    // — Otros Métodos —
    //toString() para mostrar más bonito el objeto
    @Override
    public String toString() {
        return "[" + codigo + "] " + nombre + " - Costo: " + Formato.mostrarUnidades(costoConsulta);
    }
    
}
