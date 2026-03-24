
package servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException; // Para capturar excepciones sobre las fechas
import java.time.format.DateTimeFormatter; // Para formatear fechaHora
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.hospital.CitaMedica;
import modelo.hospital.Especialidad;
import modelo.personas.Paciente;
import modelo.hospital.Hospital;
import modelo.personas.Cirujano;
import modelo.personas.Enfermero;
import modelo.personas.Medico;
import modelo.abstractas.Empleado;
import modelo.enums.EstadoCita;
import modelo.enums.Turno;
import modelo.hospital.Diagnostico;

/*
    Clase que almacena los distintos menús del sistema y su gestión
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
        while (true) {            
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
            
            try {
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
            } catch (IllegalArgumentException error) {
                imprimirError(error.getMessage());
            }
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
                            6. Realizar cirugia
                            0. Volver al menu principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opcion");
            imprimirSeparador();
            
            try {
                switch (opcion) {
                    case 1 -> registrarPaciente();
                    case 2 -> registrarAlergia();
                    case 3 -> consultarHistorial();
                    case 4 -> buscarPacientePorId();
                    case 5 -> listarPacientes();
                    case 6 -> realizarCirugia();
                    case 0 -> {
                        System.out.println(" Volviendo al menu principal...\n");
                        return;
                    }
                    default -> imprimirError("Opcion invalida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException error) {
                imprimirError(error.getMessage());
            }
            
        } while (opcion != 0);
    }
    
    private static void registrarPaciente() {
        System.out.println("""
                           ==================================
                                   REGISTRAR PACIENTE
                           ==================================""");
        try {
            
            String id;
            do {
                id = verificarTexto("Ingrese el ID");
                if (existeIdPacienteOEmpleado(id)) {
                    imprimirError("El ID ya existe. Ingrese uno diferente.");
                    continue;
                }
                break;
            } while (true);
            
            String nombre = verificarTexto("Ingrese el Nombre");
            String apellido = verificarTexto("Ingrese el Apellido");
            
            LocalDate fechaNac;
            while (true) {                
                fechaNac = verificarFecha("Ingrese la Fecha nac. (YYYY-MM-DD)");
                if (fechaNac.isAfter(LocalDate.now())) {
                    imprimirError("La fecha de nacimiento no puede ser en el futuro. Intente de nuevo.");
                    continue;
                }
                break;
            }
            
            String email;
            while (true) {                             
                email = verificarTexto("Ingrese el Email");
                if (!email.contains("a")) {
                    imprimirError("Correo invalido. Intente de nuevo.");
                    continue;
                }
                break;
            }
            
            String historiaClinicaId = verificarTexto("Ingrese la Id de la HistoriaClinica");
            String grupo = verificarTexto("Ingrese el Grupo sanguineo");

            Paciente paciente = new Paciente(id, nombre, apellido, fechaNac, email, historiaClinicaId, grupo);

            hospital.registrarPaciente(paciente);
            
        } catch (IllegalArgumentException error) {
            imprimirError(error.getMessage());
        }
    }
    
    private static void registrarAlergia() {
        if (hospital.getPacientes().isEmpty()) {
            throw new IllegalArgumentException("No existen pacientes registrados.");
        }
        
        System.out.println("""
                           ==================================
                                   REGISTRAR ALERGIA
                           ==================================""");   
        
        Paciente pacienteLocalizado = hospital.buscarPaciente(verificarTexto("Ingrese el numero Id del paciente"));
        if (pacienteLocalizado == null) {
            throw new IllegalArgumentException("Paciente no registrado en el sistema."); 
        }
     
        String alergia = verificarTexto("Ingrese la alergia");
        try {
            pacienteLocalizado.agregarAlergia(alergia);
        } catch (IllegalArgumentException error) {
            imprimirError(error.getMessage());
        }
    }
    
    private static void consultarHistorial() {
        if (hospital.getPacientes().isEmpty()) {
            throw new IllegalArgumentException("No existen pacientes registrados.");
        }
        
        System.out.println("""
                           ==================================
                              CONSULTAR HISTORIAL CLINICO 
                           ==================================""");   
        
        Paciente pacienteLocalizado = hospital.buscarPaciente(verificarTexto("Ingrese el numero Id del paciente"));
        if (pacienteLocalizado == null) {
            throw new IllegalArgumentException("Paciente no registrado en el sistema."); 
        }
        
        System.out.println(pacienteLocalizado.obtenerHistorial());
        imprimirSeparador();
    }
    
    private static void buscarPacientePorId() {
        if (hospital.getPacientes().isEmpty()) {
            throw new IllegalArgumentException("No existen pacientes registrados.");
        }
        
        System.out.println("""
                           ==================================
                                 BUSCAR PACIENTE POR ID
                           ==================================""");
        
        Paciente pacienteLocalizado = hospital.buscarPaciente(verificarTexto("Ingrese el numero Id del paciente"));
        if (pacienteLocalizado == null) {
            throw new IllegalArgumentException("Paciente no registrado en el sistema."); 
        }
        imprimirSeparador();
        System.out.println(pacienteLocalizado);
        imprimirSeparador();
    }
    
    private static void listarPacientes() {
        if (hospital.getPacientes().isEmpty()) {
            throw new IllegalArgumentException("No existen pacientes registrados.");
        }
        
        System.out.println("""
                           ==================================
                                   LISTA DE PACIENTES
                           ==================================""");
        
        for (Paciente paciente : hospital.getPacientes()) {
            System.out.println(paciente);
            imprimirSeparador();
        }
    }
    
    private static void realizarCirugia() {
        if (hospital.getPacientes().isEmpty() || hospital.getEmpleados().isEmpty()) {
            throw new IllegalArgumentException("No existen pacientes o empleados registrados.");
        }
        
        
        System.out.println("""
                           ==================================
                                    REALIZAR CIRUGIA
                           ==================================""");
        
        LocalDateTime fechaHora = verificarFechaHora("Ingrese Fecha/hora de la cirugia");
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No puedes agendar una cita en el pasado");
        }
        
        imprimirSeparador();
        Cirujano cirujano = seleccionarCirujano();
        if (cirujano == null) { return; }
        imprimirSeparador();
        
        Paciente paciente = hospital.buscarPaciente(verificarTexto("Ingrese el numero ID del paciente"));
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente no encontrado"); 
        }
        
        String tipoCirugia = verificarTexto("Ingrese el tipo de cirugia");
        
        imprimirSeparador();
        Enfermero enfermeroAsignado = seleccionarEnfermero();
        if (enfermeroAsignado == null) { return; }
        
        enfermeroAsignado.asistirCirugia(cirujano, paciente);
        cirujano.realizarCirugia(paciente, tipoCirugia);
        
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
                            5. Desactivar/Activar empleado
                            0. Volver al menu principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opcion");
            imprimirSeparador();
            
            try {
                switch (opcion) {
                    case 1 -> contratarMedico();
                    case 2 -> contratarCirujano();
                    case 3 -> contratarEnfermero();
                    case 4 -> listarEmpleados();
                    case 5 -> desactivarEmpleado();
                    case 0 -> {
                            System.out.println("Volviendo al menu principal...\n");
                            return;
                    }
                    default -> imprimirError("Opcion invalida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException error) {
                imprimirError(error.getMessage());
            }
        } while (opcion != 0);
    }
    
    private static void contratarMedico(){     
        System.out.println("""
                           ==================================
                                    CONTRATAR MEDICO
                           ==================================""");
        
        DatosEmpleado datos = leerDatosEmpleado();
        String numLicencia = verificarTexto("Ingrese el Numero de licencia");
        Especialidad especialidad = leerEspecialidad();

        Medico medico = new Medico(
            datos.id(), datos.nombre(), datos.apellido(),
            datos.fechaNac(), datos.email(),
            datos.legajo(), datos.fechaContratacion(),
            datos.salarioBase(), numLicencia, especialidad
        );
        
        hospital.contratarEmpleado(medico);
    }
    
    private static void contratarCirujano(){     
        System.out.println("""
                           ==================================
                                   CONTRATAR CIRUJANO
                           ==================================""");
        
        DatosEmpleado datos = leerDatosEmpleado();
        String numLicencia = verificarTexto("Ingrese el Numero de licencia");
        Especialidad especialidad = leerEspecialidad();

        String respuestaDispon;
        do {
            System.out.print(" Disponible para emergencias? (s/n): ");
            respuestaDispon = sc.nextLine().trim().toLowerCase();

            if (!respuestaDispon.equals("s") && !respuestaDispon.equals("n")) {
                System.out.println("Entrada invalida. Por favor ingrese (s) o (n).");
            }

        } while (!respuestaDispon.equals("s") && !respuestaDispon.equals("n"));

        Cirujano cirujano = new Cirujano(
            datos.id(), datos.nombre(), datos.apellido(),
            datos.fechaNac(), datos.email(),
            datos.legajo(), datos.fechaContratacion(),
            datos.salarioBase(), numLicencia, especialidad
        );

        if (respuestaDispon.equals("s")) {
            cirujano.setDisponibleEmergencias(true);
        } else if (respuestaDispon.equals("n")){
            cirujano.setDisponibleEmergencias(false);
        }

        hospital.contratarEmpleado(cirujano);
    }
    
    private static void contratarEnfermero(){     
        System.out.println("""
                           ==================================
                                  CONTRATAR ENFERMERO
                           ==================================""");
        
        DatosEmpleado datos = leerDatosEmpleado();
        Turno turno = leerTurno();
        String areaAsignada = verificarTexto("Ingrese el area de asignacion");

        Enfermero enfermero = new Enfermero(
            datos.id(), datos.nombre(), datos.apellido(),
            datos.fechaNac(), datos.email(),
            datos.legajo(), datos.fechaContratacion(),
            datos.salarioBase(), turno, areaAsignada
        );

        hospital.contratarEmpleado(enfermero);
    }
    
    private static void listarEmpleados(){
        if (hospital.getEmpleados().isEmpty()) {
            throw new IllegalArgumentException("No existen empleados registrados.");
        }
        
        System.out.println("""
                           ==================================
                                    LISTAR EMPLEADOS
                           ==================================""");
        
        for (Empleado empleado : hospital.getEmpleados()) {
            System.out.println(empleado);
            imprimirSeparador();
        }
    }
    
    private static void desactivarEmpleado() {
        if (hospital.getEmpleados().isEmpty()) {
            throw new IllegalArgumentException("No hay empleados registrados.");
        }

        System.out.println("""
                           ==================================
                              GESTIONAR ESTADO DE EMPLEADO
                           ==================================""");

        // Mostrar todos los empleados con su estado actual
        List<Empleado> lista = hospital.getEmpleados();
        
        for (int i = 0; i < lista.size(); i++) {
            Empleado empleado = lista.get(i);
            String estado = empleado.isActivo() ? "Activo" : "Inactivo";
            System.out.println(" " + (i + 1) + ". " + empleado.getNombreCompleto()
                    + " -- " + empleado.obtenerTipo()
                    + " -- " + estado);
        }
        imprimirSeparador();

        while (true) {
            int seleccion = verificarEntero("Seleccione el numero del empleado");
            if (seleccion >= 1 && seleccion <= lista.size()) {
                Empleado empleado = lista.get(seleccion - 1);
                String accion = empleado.isActivo() ? "desactivar" : "activar";

                System.out.print(" Desea " + accion + " a "
                        + empleado.getNombreCompleto() + "? (s/n): ");
                String confirmacion = sc.nextLine().trim().toLowerCase();

                if (confirmacion.equals("s")) {
                    empleado.setActivo(!empleado.isActivo());
                    String resultado = empleado.isActivo() ? "activado" : "desactivado";
                    System.out.println(empleado.getNombreCompleto()
                            + " " + resultado + " correctamente.");
                } else {
                    imprimirError("Operacion cancelada.");
                }
                return;
            } else {
                imprimirError("Seleccion invalida. Ingrese un numero entre 1 y " + lista.size());
            }
        }
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
                            4. Listar citas por paciente
                            5. Listar citas por medico
                            0. Volver al menu principal""");
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opcion");
            imprimirSeparador();
            
            try {
                switch (opcion) {
                    case 1 -> agendarCita();
                    case 2 -> completarCita();
                    case 3 -> cancelarCita();
                    case 4 -> listarCitasPorPaciente();
                    case 5 -> listarCitasPorMedico();
                    case 0 -> {
                            System.out.println(" Volviendo al menu principal...\n");
                            return;
                        }
                    default -> imprimirError("Opcion invalida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException error) {
                imprimirError(error.getMessage());
            }
        } while (opcion != 0);
    }
    
    private static void agendarCita(){
        
        // Verifica específicamente si hay médicos activos
        // e es empleado
        boolean hayMedicos = hospital.getEmpleados().stream()
                .anyMatch(e -> e instanceof Medico && e.isActivo());

        if (hospital.getPacientes().isEmpty()) {
            throw new IllegalArgumentException("No existen pacientes registrados.");
        }
        if (!hayMedicos) {
            throw new IllegalArgumentException("No existen medicos activos registrados.");
        }
        
        System.out.println("""
                           ==================================
                                     AGENDAR CITA
                           ==================================""");
        
        Paciente paciente = hospital.buscarPaciente(verificarTexto("Ingrese el ID del paciente"));
        if (paciente == null) { 
            throw new IllegalArgumentException("Paciente no encontrado"); 
        }

        LocalDateTime fechaHora = verificarFechaHora("Ingrese Fecha/hora");
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No puedes agendar una cita en el pasado");
        }

        Medico medico = seleccionarMedico(fechaHora);
        if (medico == null){ 
            throw new IllegalArgumentException("No hay medicos disponibles");
        }
        
        String idCita;
        while (true) {
            idCita = verificarTexto("Ingrese el ID de la cita");
            if (!existeIdCita(idCita)) { 
                break;
            }
            imprimirError("El ID '" + idCita + "' ya esta registrado. Ingrese uno diferente.");
        }

        String motivo = verificarTexto("Motivo");

        CitaMedica cita = hospital.agendarCita(idCita, paciente, medico, fechaHora, motivo);
        System.out.println("Cita " + cita.getId() + " agendada correctamente.");
    }
    
    private static void completarCita() {
        if (hospital.getCitas().isEmpty()) {
            throw new IllegalArgumentException ("No existen citas registradas.");
        }
        
        System.out.println("""
                           ==================================
                                    COMPLETAR CITA
                           ==================================""");
        try {
            CitaMedica cita = hospital.buscarCita(verificarTexto("Ingrese el ID de la cita"));
            if (cita == null) {
                throw new IllegalArgumentException("Cita no registrada en el sistema."); 
            }

            if (cita.getEstado() != EstadoCita.PENDIENTE) {
                throw new IllegalArgumentException("La cita no esta pendiente. Estado actual: " + cita.getEstado().getEstado()); 
            }

            String diagId = verificarTexto("Ingrese el ID del diagnostico");
            String descripcion = verificarTexto("Ingrese la Descripcion del diagnostico");
            String receta = verificarTexto("Ingrese la Receta");

            Diagnostico diagnostico = new Diagnostico(diagId, descripcion, receta, LocalDate.now(), cita.getMedico());

            cita.completar(diagnostico);
        } catch (IllegalArgumentException error) {
            imprimirError(error.getMessage());
        }
    }
    
    private static void cancelarCita() {
        if (hospital.getCitas().isEmpty()) {
            throw new IllegalArgumentException ("No existen citas registradas.");
        }
        
        System.out.println("""
                           ==================================
                                    CANCELAR CITA
                           ==================================""");
        
        CitaMedica cita = hospital.buscarCita(verificarTexto("Ingrese el ID de cita"));
        if (cita == null) {
            throw new IllegalArgumentException("Cita no encontrada"); 
        }

        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            imprimirError("La cita no esta pendiente. Estado actual: "
                    + cita.getEstado().getEstado());
            return;
        }
        cita.cancelar();
    }
    
    private static void listarCitasPorPaciente() {
        if (hospital.getPacientes().isEmpty()) {
            throw new IllegalArgumentException("No hay pacientes registrados.");
        }

        System.out.println("""
                           ==================================
                                  CITAS POR PACIENTE
                           ==================================""");

        Paciente paciente = hospital.buscarPaciente(verificarTexto("Ingrese el ID del paciente"));
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente no encontrado"); 
        }

        // Filtrar citas del paciente
        List<CitaMedica> citasPaciente = new ArrayList<>();
        for (CitaMedica cita : hospital.getCitas()) {
            if (cita.getPaciente().getId().equalsIgnoreCase(paciente.getId())) {
                citasPaciente.add(cita);
            }
        }
        
        if (citasPaciente.isEmpty()) {
            throw new IllegalArgumentException("El paciente no tiene citas registradas"); 
        }

        System.out.println(" Citas de: " + paciente.getNombreCompleto());
        imprimirSeparador();

        for (CitaMedica cita : citasPaciente) {
            System.out.println(cita);
        }
    }
    
    private static void listarCitasPorMedico() {
        if (hospital.getEmpleados().isEmpty()) {
            throw new IllegalArgumentException("No hay empleados registrados.");
        }

        System.out.println("""
                           ==================================
                                   CITAS POR MEDICO
                           ==================================""");
        
        Medico medico = hospital.buscarMedico(verificarTexto("Ingrese el id del medico"));
        if (medico == null) { 
            throw new IllegalArgumentException("Medico no encontrado");
        }

        // Filtrar citas del médico
        List<CitaMedica> citasMedico = new ArrayList<>();
        for (CitaMedica cita : hospital.getCitas()) {
            if (cita.getMedico().getId().equalsIgnoreCase(medico.getId())) {
                citasMedico.add(cita);
            }
        }
        
        if (citasMedico.isEmpty()) {
            throw new IllegalArgumentException("El medico no tiene citas registradas.");
        }
        
        System.out.println(" Citas de: Dr. " + medico.getNombreCompleto());
        imprimirSeparador();

        for (CitaMedica cita : citasMedico) {
            System.out.println(cita);
        }
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
                            2. Salarios por empleado
                            0. Volver al menu principal""");
            
            imprimirSeparador();
            opcion = verificarEntero(" Seleccione una opcion");
            imprimirSeparador();
            
            try {
                switch (opcion) {
                case 1 -> reporteNominaTotal();
                case 2 -> reporteSalariosPorEmpleado();
                case 0 -> {
                    System.out.println(" Volviendo al menu principal...\n");
                    return;
                }
                default -> imprimirError("Opcion invalida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException error) {
                imprimirError(error.getMessage());
            }        
        } while (opcion != 0);
    }
    
    private static void reporteNominaTotal(){
        if (hospital.getEmpleados().isEmpty()) {
            throw new IllegalArgumentException ("No hay empleados registrados.");
        }
        System.out.println("""
                           ==================================
                                      NOMINA TOTAL
                           ==================================""");
        
        double nominaTotal = hospital.calcularNominaTotal();
        System.out.println(Formato.mostrarUnidades(nominaTotal));
        imprimirSeparador();
    }
    
    private static void reporteSalariosPorEmpleado(){
        if (hospital.getEmpleados().isEmpty()) {
            throw new IllegalArgumentException ("No hay empleados registrados.");
        }
        System.out.println("""
                           ==================================
                                  SALARIO POR EMPLEADO
                           ==================================""");
        
        for (Empleado empleado : hospital.getEmpleados()) {
            System.out.println("  Nombre     : " + empleado.getNombreCompleto());
            System.out.println("  Tipo       : " + empleado.obtenerTipo());
            System.out.println("  Antiguedad : " + empleado.antiguedad() + " anos");
            System.out.println("  Salario    : " + Formato.mostrarUnidades(empleado.calcularSalario()));
            imprimirSeparador();
        }
    }
    
    // ══════════════════════════════════════════════
    //  OTROS MÉTODOS
    // ══════════════════════════════════════════════
    
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
                imprimirError("Valor entero invalido. Intente de nuevo.");
            }
        }
    }
    
    private static double verificarDouble(String mensaje) {
        while (true) {
            System.out.print(" " + mensaje + ": ");
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
            System.out.print(" " + mensaje + "(YYYY-MM-DD): ");
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
                // Para formatear la fecha y hora sin los segundos
                return LocalDateTime.parse(fechaHoraVerificada, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")); 
            } catch (DateTimeParseException ex) {
                imprimirError("Formato invalido. Use YYYY-MM-DDTHH:MM. Ejemplo: 2025-06-15T10:30");
            }
        }
    }
 
    // Verifica si el id de una persona ya existe, y retorna false en caso de que no
    private static boolean existeIdPacienteOEmpleado(String id) {
        for (Paciente paciente : hospital.getPacientes()) {
            if (paciente.getId().equalsIgnoreCase(id)) return true;
        }
        for (Empleado empleado : hospital.getEmpleados()) {
            if (empleado.getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
    
    // Verifica si el id de una cita ya existe, y retorna false en caso de que no
    private static boolean existeIdCita(String id) {
        for (CitaMedica cita : hospital.getCitas()) {
            if (cita.getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
    
    // Valida la disponibilidad de un médico para agendar una cita, retorna true para disponibilidad
    private static boolean medicoDisponible(Medico medico, LocalDateTime fechaHora) {
        for (CitaMedica citas : hospital.getCitas()) {
            if (citas.getMedico().getId().equals(medico.getId())
                    && citas.getFechaHora().equals(fechaHora)
                    && citas.getEstado() == EstadoCita.PENDIENTE) {
                return false;
            }
        }
        return true;
    }
    
    
    // Permite seleccionar de entre una lista de cirujanos disponibles
    private static Cirujano seleccionarCirujano() {
        
        List<Cirujano> disponibles = new ArrayList<>();
        List<Cirujano> noDisponibles = new ArrayList<>();

        for (Empleado empleado : hospital.getEmpleados()) {
            if (empleado instanceof Cirujano cirujano && cirujano.isActivo()) {
                disponibles.add(cirujano);
            }
        }

        if (disponibles.isEmpty()) {
            imprimirError("No hay cirujanos disponibles.");
            return null;
        }
        
        // Cirujanos disponibles — seleccionables
        System.out.println(" Cirujanos disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            Cirujano cirujano = disponibles.get(i);
            System.out.println(" " + (i + 1) + ". " + cirujano.getNombreCompleto()
                    + " -- " + cirujano.getEspecialidad().getNombre());
        }

        // Médicos ocupados — solo informativo, no seleccionables
        if (!noDisponibles.isEmpty()) {
            imprimirSeparador();
            System.out.println(" Cirujanos ocupados:");
            for (Cirujano cirujano : noDisponibles) {
                System.out.println(" - " + cirujano.getNombreCompleto()
                        + " -- " + cirujano.getEspecialidad().getNombre());
            }
        }

        System.out.println("  0. Cancelar"); // --> Para salir de la selección 
        imprimirSeparador();

        // Selección — solo se aceptan índices de la lista de disponibles
        while (true) {
            int seleccion = verificarEntero("Seleccione el numero del cirujano");
            if (seleccion == 0) return null; // --> Escape
            if (seleccion >= 1 && seleccion <= disponibles.size()) {
                return disponibles.get(seleccion - 1);
            }
            imprimirError("Seleccion invalida. Ingrese un numero entre 1 y " + disponibles.size());
        }
    }
    
    // Permite seleccionar de entre una lista de enfemeros disponibles
    private static Enfermero seleccionarEnfermero() {
        
        List<Enfermero> disponibles = new ArrayList<>();
        List<Enfermero> noDisponibles = new ArrayList<>();

        for (Empleado empleado : hospital.getEmpleados()) {
            if (empleado instanceof Enfermero enfermero && enfermero.isActivo()) {
                    disponibles.add(enfermero);
            }
        }

        if (disponibles.isEmpty()) {
            imprimirError("No hay enfermeros disponibles.");
            return null;
        }
        
        // Cirujanos disponibles — seleccionables
        System.out.println(" Enfermeros disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            Enfermero enfermero = disponibles.get(i);
            System.out.println(" " + (i + 1) + ". " + enfermero.getNombreCompleto());
        }

        // Médicos ocupados — solo informativo, no seleccionables
        if (!noDisponibles.isEmpty()) {
            imprimirSeparador();
            System.out.println(" Enfermeros ocupados en ese horario:");
            for (Enfermero enfermero : noDisponibles) {
                System.out.println(" - " + enfermero.getNombreCompleto());
            }
        }

        System.out.println("  0. Cancelar"); // --> Para salir de la selección 
        imprimirSeparador();

        // Selección — solo se aceptan índices de la lista de disponibles
        while (true) {
            int seleccion = verificarEntero("Seleccione el numero del enfermero");
            if (seleccion == 0) return null; // --> Escape
            if (seleccion >= 1 && seleccion <= disponibles.size()) {
                return disponibles.get(seleccion - 1);
            }
            imprimirError("Seleccion invalida. Ingrese un numero entre 1 y " + disponibles.size());
        }
    }
    
    // Permite seleccionar de entre una lista de médicos disponibles
    private static Medico seleccionarMedico(LocalDateTime fechaHora) {

        List<Medico> disponibles = new ArrayList<>();
        List<Medico> noDisponibles = new ArrayList<>();

        for (Empleado empleado : hospital.getEmpleados()) {
            if (empleado instanceof Medico medico && medico.isActivo()) {
                if (medicoDisponible(medico, fechaHora)) {
                    disponibles.add(medico);
                } else {
                    noDisponibles.add(medico);
                }
            }
        }

        if (disponibles.isEmpty()) {
            imprimirError("No hay medicos disponibles en ese horario.");
            return null;
        }

        // Médicos disponibles — seleccionables
        System.out.println(" Medicos disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            Medico medico = disponibles.get(i);
            System.out.println(" " + (i + 1) + ". " + medico.getNombreCompleto()
                    + " -- " + medico.getEspecialidad().getNombre());
        }

        // Médicos ocupados — solo informativo, no seleccionables
        if (!noDisponibles.isEmpty()) {
            imprimirSeparador();
            System.out.println(" Medicos ocupados en ese horario:");
            for (Medico medico : noDisponibles) {
                System.out.println(" - " + medico.getNombreCompleto()
                        + " -- " + medico.getEspecialidad().getNombre());
            }
        }

        System.out.println("  0. Cancelar"); // --> Para salir de la selección 
        imprimirSeparador();

        // Selección — solo se aceptan índices de la lista de disponibles
        while (true) {
            int seleccion = verificarEntero(" Seleccione el numero del medico");
            if (seleccion == 0) return null; // --> Escape
            if (seleccion >= 1 && seleccion <= disponibles.size()) {
                return disponibles.get(seleccion - 1);
            }
            imprimirError("Seleccion invalida. Ingrese un numero entre 1 y " + disponibles.size());
        }
    }
    
    // Retorno los datos de los empleados gracias al record DatosEmpleado
    private static DatosEmpleado leerDatosEmpleado() {
        String id;
        while (true) {            
            id = verificarTexto("Ingrese el ID");
            if (existeIdPacienteOEmpleado(id)) {
                imprimirError("El ID ya esta registrado. Intente otro.");
                continue;
            }
            break;
        }

        String nombre = verificarTexto("Ingrese el Nombre");
        String apellido = verificarTexto("Ingrese el Apellido");
        
        LocalDate fechaNac;
        while (true) {            
            fechaNac = verificarFecha("Ingrese la Fecha de Nacimiento");
            if (fechaNac.isAfter(LocalDate.now())) {
                imprimirError("No puede ingresar una fecha futura. Intente de nuevo.");
                continue;
            }
            break;
        }
        
        String email;
        while (true) {            
            email = verificarTexto("Ingrese el Email");
            if (!email.contains("@")) {
                imprimirError("Correo invalido. Intente de nuevo.");
                continue;
            }
            break;
        }
        
        
        String legajo = verificarTexto("Ingrese el Legajo");
        
        LocalDate fechaCont;
        while (true) {            
            fechaCont = verificarFecha("Ingrese la Fecha de contratacion");
            if (fechaCont.isAfter(LocalDate.now())) {
                imprimirError("No puede ingresar una fecha futura. Intente de nuevo.");
                continue;
            }
            break;
        }
        
        double salario = verificarDouble("Ingrese el Salario base");

        return new DatosEmpleado(id, nombre, apellido, fechaNac, email, legajo, fechaCont, salario);
    }
    
    // Retorna una nueva especialidad con los datos validados
    private static Especialidad leerEspecialidad() {
        imprimirSeparador();
        System.out.println("----- Especialidad ------");
        String codigo = verificarTexto("Ingrese el Codigo");
        String nombre = verificarTexto("Ingrese el Nombre");
        String descripcion = verificarTexto("Ingrese la Descripcion");
        double costoConsulta = verificarDouble("Ingrese el Costo por consulta");
        return new Especialidad(codigo, nombre, descripcion, costoConsulta);
    }
    
    // Retorna un turno para enfermeros
    private static Turno leerTurno(){
        while (true) {
            imprimirSeparador();
            System.out.println(" Turno: 1. Manana   2. Tarde   3. Noche");
            imprimirSeparador();
            int opcion = verificarEntero("Seleccione turno");
            switch (opcion) {
                case 1 -> { return Turno.MANANA; }
                case 2 -> { return Turno.TARDE;  }
                case 3 -> { return Turno.NOCHE;  }
                default -> imprimirError("Turno invalido. Seleccione 1, 2 o 3.");
            }
        }
    }
    
    // — Métodos para decorar —
    private static void imprimirSeparador(){
        System.out.println("----------------------------------");
    }
    
    private static void imprimirError(String mensaje){
        System.err.println(mensaje);
    }
}