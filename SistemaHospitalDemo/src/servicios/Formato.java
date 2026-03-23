
package servicios;

import java.text.DecimalFormat;

/*
    Toda esta clase es para poder llamar a DecimalFormat en todo el código,
    y la creé para mostrar las unidades en los casos donde se muestren valores
    de saldo.
*/

public class Formato {
    
    private static final DecimalFormat df = new DecimalFormat("#,###.##");
    
    // Constructor privado (Nadie lo debe instanciar)
    private Formato(){}
    
    public static String mostrarUnidades(double valor) {
        return "$" + df.format(valor);
    }

}
