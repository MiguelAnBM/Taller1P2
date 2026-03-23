
package servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException; // Para capturar excepciones sobre las fechas
import java.util.Scanner;

import modelo.personas.Paciente;
import modelo.hospital.Hospital;

/*
    Clase que almacena los distintos menús del sistema y gestionarlos
*/
public class LogicaYMenus {
    
    // Son private y final porque nadie debe modificarlos y son constantes
    private static final Scanner sc = new Scanner(System.in);
    private static final Hospital hospital = new Hospital("Nombre del hospital", "Direccion");
    
    // — Menús —
    
    // ══════════════════════════════════════════════
    //  MENÚ PRINCIPAL
    // ══════════════════════════════════════════════ 
    public static void menuPrincipal(){
        int opcion;
        System.out.println("""
                           ==================================
                            SISTEMA DE GESTION HOSPITALARIA
                           ==================================
                            1. Gestion de Pacientes
                            2. Gestion de Personal
                            3. Gestion de Citas
                            4. Reportes
                            0. Salir""");
        imprimirSeparador();
        opcion = verificarEntero(" Seleccione una opcion");
        imprimirSeparador();
        switch (opcion) {
            case 1 -> menuGestionDePacientes();
            case 2 -> menuGestionDePersonal();
            case 3 -> menuGestionDeCitas();
            case 4 -> menuDeReportes();
            case 0 -> {
                System.out.println("\n Cerrando el programa...\n");
                sc.close();
                System.exit(0);
            }
            default -> imprimirError("Opcion invalida. Intente de nuevo.");
        }
    }
     
    // ══════════════════════════════════════════════
    //  MENÚ GESTIÓN DE PACIENTES
    // ══════════════════════════════════════════════ 
    private static void menuGestionDePacientes(){
        int opcion;
        do {            
            System.out.println("""
                           ==================================
                               MENU GESTION DE PACIENTES
                           ==================================
                            1. Registrar paciente
                            2. Registrar alergia
                            3. Consultar historial
                            4. Buscar Paciente por ID   
                            5. Listar pacientes
                            0. Volver al menu principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opcion");
            imprimirSeparador();

            switch (opcion) {
                    case 1 -> registrarPaciente();
                    case 2 -> registrarAlergia();
//                    case 3 -> consultarHistorial();
//                    case 4 -> buscarPacientePorId();
//                    case 5 -> listarPacientes();
                    case 0 -> System.out.println(" Volviendo al menu principal...\n");
                    default -> imprimirError("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
    
    private static void registrarPaciente() {
        System.out.println("""
                           ==================================
                                   REGISTRAR PACIENTE
                           ==================================""");
        try {
            
            String id = verificarTexto("Ingrese el ID");
            String nombre = verificarTexto("Ingrese el Nombre");
            String apellido = verificarTexto("Ingrese el Apellido");
            LocalDate fecha = verificarFecha("Ingrese la Fecha nac. (YYYY-MM-DD)");
            String email = verificarTexto("Ingrese el Email");
            String historiaClinicaId = verificarTexto("Ingrese la Id de la HistoriaClinica");
            String grupo = verificarTexto("Ingrese el Grupo sanguineo");

            Paciente paciente = new Paciente(id, nombre, apellido, fecha, email, historiaClinicaId, grupo);

            hospital.registrarPaciente(paciente);
            System.out.println("Paciente " + paciente.getNombreCompleto() + " registrado correctamente.");
            
            // DEBUGGER PARA SABER SI SE GUARDÓ BIEN LA INFORMACIÓN
            System.out.println();
            for (Paciente p : hospital.getPacientes()) {
                System.out.println("ID: " + p.getId() + "\n" +
                                   "Nombre: " + p.getNombre() + "\n" +
                                   "Apellido: " + p.getApellido() + "\n" +
                                   "FechaNac: " + p.getFechaNacimiento() + "\n" +
                                   "Email: " + p.getEmail() + "\n" +
                                   "HCID: " + p.getHistoriaClinicaId() + "\n" +
                                   "RH: " + p.getGrupoSanguineo());
                System.out.println();
            }
            
        } catch (IllegalArgumentException error) {
            imprimirError(error.getMessage());
        }
    }
    
    private static void registrarAlergia() {
        if (hospital.getPacientes().isEmpty() || hospital.getPacientes() == null) {
            throw new IllegalArgumentException("No existen pacientes registrados.");
        }
        
        System.out.println("""
                           ==================================
                                   REGISTRAR ALERGIA
                           ==================================""");   
        
        String pacienteABuscar = verificarTexto("Ingrese el numero Id del paciente");
        Paciente pacienteLocalizado = hospital.buscarPaciente(pacienteABuscar);
        if (pacienteLocalizado == null) { 
            throw new IllegalArgumentException("Paciente no registrado en el sistema."); 
        }
     
        String alergia = verificarTexto("Ingrese la alergia");
        try {
            pacienteLocalizado.agregarAlergia(alergia);
            System.out.println("Alergia registrada correctamente.");
        } catch (IllegalArgumentException error) {
            imprimirError(error.getMessage());
        }
    }
    
    // ══════════════════════════════════════════════
    //  MENÚ GESTIÓN DE PERSONAL
    // ══════════════════════════════════════════════ 
    private static void menuGestionDePersonal() {
        int opcion;
        do {
            System.out.println("""
                           ==================================
                               MENU GESTION DE PERSONAL
                           ==================================
                            1. Contratar medico
                            2. Contratar cirujano
                            3. Contratar enfermero
                            4. Listar empleados
                            0. Volver al menú principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opción");
            sc.nextLine();
            imprimirSeparador();

            switch (opcion) {
//                case 1 -> contratarMedico();
//                case 2 -> contratarCirujano();
//                case 3 -> contratarEnfermero();
//                case 4 -> listarEmpleados();
//                case 0 -> System.out.println(" Volviendo al menu principal...\n");
//                default -> imprimirError("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
    
    // ══════════════════════════════════════════════
    //  MENÚ GESTIÓN DE CITAS
    // ══════════════════════════════════════════════ 
    private static void menuGestionDeCitas() {
        int opcion;
        do {
            System.out.println("""
                           ==================================
                                 MENU GESTION DE CITAS
                           ==================================
                            1. Agendar cita
                            2. Completar cita
                            3. Cancelar cita
                            4. Listar citas
                            0. Volver al menú principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opción");
            imprimirSeparador();

            switch (opcion) {
//                case 1 -> agendarCita();
//                case 2 -> completarCita();
//                case 3 -> cancelarCita();
//                case 4 -> listarCitas();
//                case 0 -> System.out.println(" Volviendo al menu principal...\n");
//                default -> imprimirError("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
    
    // ══════════════════════════════════════════════
    //  MENÚ DE REPORTES
    // ══════════════════════════════════════════════
    private static void menuDeReportes() {
        int opcion;
        do {
            System.out.println("""
                           ==================================
                                    MENU DE REPORTES
                           ==================================
                            1. Nomina total
                            2. Resumen del hospital
                            3. Salarios por empleado
                            0. Volver al menú principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opcion");
            imprimirSeparador();

            switch (opcion) {
//                case 1 -> reporteNomina();
//                case 2 -> reporteHospital();
//                case 3 -> reporteSalarios();
//                case 0 -> System.out.println(" Volviendo al menu principal...\n");
//                default -> imprimirError("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
    
    // — Métodos del negocio —  
    
    // — Lectura segura de tipos (Para evitar que el programa colapse) —
    private static String verificarTexto(String mensaje) {
        while (true){
            System.out.print(" " + mensaje + ": ");
            String textoVerificado = sc.nextLine().trim();
            if (textoVerificado.isBlank()) {
                imprimirError("El campo no puede estar vacio. Intente de nuevo.");
                continue;
            }
            return textoVerificado;
        }
    }
    
    private static int verificarEntero(String mensaje) {
        while (true) {
            System.out.print(" " + mensaje + ": ");
            String enteroVerificado = sc.nextLine().trim();
            if (enteroVerificado.isBlank()) {
                imprimirError("El campo no puede estar vacio. Intente de nuevo.");
                continue;
            }
            try {
                return Integer.parseInt(enteroVerificado);
            } catch (NumberFormatException ex) {
                imprimirError("Valor entero inválido. Intente de nuevo.");
            }
        }
    }
    
    private static double verificarDouble(String mensaje) {
        while (true) {
            System.out.print("  " + mensaje + ": ");
            String valor = sc.nextLine().trim();
            if (valor.isBlank()) {
                imprimirError("El campo no puede estar vacio. Intente de nuevo.");
                continue;
            }
            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException ex) {
                imprimirError("Valor numerico invalido. Intente de nuevo.");
            }
        }
    }
    
    private static LocalDate verificarFecha(String mensaje) {
        while (true) {
            System.out.print(" " + mensaje + ": ");
            String valor = sc.nextLine().trim();
            if (valor.isBlank()) {
                imprimirError("El campo no puede estar vacio. Intente de nuevo.");
                continue;
            }
            try {
                return LocalDate.parse(valor);
            } catch (DateTimeParseException ex) {
                imprimirError("Formato invalido. Use YYYY-MM-DD. Ejemplo: 1990-03-15");
            }
        }
    }
    
    private static LocalDateTime verificarFechaHora(String mensaje) {
        while (true) {
            System.out.print(" " + mensaje + " (YYYY-MM-DDTHH:MM): ");
            String fechaHoraVerificada = sc.nextLine().trim();
            if (fechaHoraVerificada.isBlank()) {
                imprimirError("El campo no puede estar vacio. Intente de nuevo.");
                continue;
            }
            try {
                return LocalDateTime.parse(fechaHoraVerificada);
            } catch (DateTimeParseException ex) {
                imprimirError("Formato invalido. Use YYYY-MM-DDTHH:MM. Ejemplo: 2025-06-15T10:30");
            }
        }
    }
    
    // // — Métodos para decorar —
    public static void imprimirSeparador(){
        System.out.println("----------------------------------");
    }
    
    public static void imprimirError(String mensaje){
        System.err.println(mensaje);
    }
    
    
}
