package sistema;
import estructuras.*;
import java.io.*;
import java.util.HashMap;
import util.TecladoIn;
import java.util.StringTokenizer;

public class TrenesSA {
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        GrafoEtiquetado mapa = new GrafoEtiquetado();
        Diccionario estaciones = new Diccionario();
        Diccionario trenes = new Diccionario();
        HashMap<String, Lista> mapeo = new HashMap<String, Lista>(); //mapeo de lineas con lista de estaciones
        int opcion;

        System.out.println();
        crearLog();
        do {
			opcion = menu();
			switch (opcion) {
				case 1:
                    cargarDatos(mapa, estaciones, trenes, mapeo);
					break;
				case 2:
                    ABMTrenes(trenes, mapeo);
					break;
				case 3:
                    ABMEstaciones(estaciones, mapa);
					break;
				case 4:
					ABMLineas(estaciones, mapeo, trenes);
					break;
				case 5:
                    ABMRieles(estaciones, mapa);
					break;
				case 6:
					consultasTrenes(trenes, estaciones, mapeo);
					break;
				case 7:
                    consultasEstaciones(estaciones);
					break;
				case 8:
                    consultasViajes(estaciones, mapa);
					break;
				case 9:
					mostrarSistema(mapa, estaciones, trenes, mapeo);
					break;
				case 10:
                    System.out.println("Fin del programa");
					break;
				default:
                    System.out.println("Opcion Incorrecta.");
					break;
			}
		}while(opcion != 10); 
      
    }

    public static int menu() {
        System.out.println("--------------------------------- Menu ---------------------------------");
        System.out.println("1. Carga inicial del sistema:");
        System.out.println("2. ABM de trenes");
        System.out.println("3. ABM de estaciones");
        System.out.println("4. ABM de lineas");
        System.out.println("5. ABM de la red de rieles");
        System.out.println("6. Consultas sobre trenes");
        System.out.println("7. Consultas sobre estaciones");
        System.out.println("8. Consultas sobre viajes: Dada una estacion A y una estacion B");
        System.out.println("9. Mostrar Sistema");
        System.out.println("10. Salir");
        System.out.println("------------------------------------------------------------------------ \n");

        System.out.println("Seleccione una opción ");
        int opcion = TecladoIn.readInt();
        System.out.println();
        return opcion;
    }

    public static int opcionesAbm(){
        int opcion;
        System.out.println(VERDE+"1. Agregar"+RESET);
        System.out.println(ROJO+"2. Eliminar"+RESET);
        System.out.println(AZUL+"3. Modificar"+RESET);
        System.out.println(PURPLE+"4. Salir"+RESET);
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("Ingrese una opción: ");
        opcion = TecladoIn.readInt();
        System.out.println();
        return opcion;
    }

    public static void ABMTrenes(Diccionario trenes, HashMap<String, Lista> mapeo){
        int opcion; 
        do {
            System.out.println("------------------------------ ABM Trenes ------------------------------");
            System.out.println("Operaciones Sobre trenes:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    System.out.println(VERDE+"AGREGAR TREN\n"+RESET);
                    agregarTren(trenes, mapeo);
                    break;
                case 2: 
                    System.out.println(ROJO+"ELIMINAR TREN\n"+RESET);
                    eliminarTren(trenes, mapeo);
                    break;
                case 3: 
                    System.out.println(AZUL+"MODIFICAR TREN\n"+RESET);
                    modificarTren(trenes, mapeo);
                    break;
                case 4: 
                    System.out.println(PURPLE+"Saliendo de ABM trenes... \n"+RESET);
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }

        } while (opcion != 4);
    }

    public static void modificarTren(Diccionario trenes, HashMap<String, Lista> mapeo){
        if (!trenes.esVacio()){
            System.out.println("Ingrese el id del tren que va a modificar");
            int id = TecladoIn.readInt();
            if (trenes.existeClave(id)){
                Tren tren = (Tren) trenes.obtenerInformacion(id);
                int opcion;
                do{
                    System.out.println("Operaciones: ");
                    System.out.println("1- Cambiar el tipo Propulsion:");
                    System.out.println("2- Cambiar la cantidad de vagones pasajeros");
                    System.out.println("3- Cambiar la cantidad de vagones de carga");
                    System.out.println("4- Cambiar la linea del tren");
                    System.out.println("5- Mostrar estado del tren");
                    System.out.println("6- Volver");
                    System.out.println("--------------------------------------------");
                    System.out.println("Ingrese una opcion:");
                    opcion = TecladoIn.readInt();
                    switch (opcion){
                        case 1:
                            String propulsion = pedirTipoPropulsion();
                            tren.setCombustible(propulsion);
                            escribir("SE MODIFICO EL TREN "+id);
                            break;
                        case 2: 
                            int cantVagonesPasajeros = pedirVagonesPasajeros();
                            tren.setCantVagonesPasajeros(cantVagonesPasajeros);
                            escribir("SE MODIFICO EL TREN "+id);
                            break;
                        case 3: 
                            int cantVagonesCarga = pedirVagonesCarga();
                            tren.setCantVagonesCarga(cantVagonesCarga);
                            escribir("SE MODIFICO EL TREN "+id);
                            break;
                        case 4:
                            String linea = pedirLinea(mapeo);
                            tren.setLinea(linea);
                            escribir("SE MODIFICO EL TREN "+id);
                            break;
                        case 5:
                            System.out.println("Tren Actual: ");
                            System.out.println(tren.toString());
                            break;
                        case 6:
                            System.out.println("Volviendo");
                            break;
                        default: 
                            System.out.println("Opcion incorrecta");
                            break;
                    }
                } while (opcion != 6);
                
            } else {
                System.out.println("El tren ingresado no existe");
            } 
        } else {
            System.out.println("No hay trenes disponibles para modificar");
        }
             
        
    }

    public static void eliminarTren(Diccionario trenes, HashMap<String, Lista> mapeo){
        if (trenes.esVacio()){
            System.out.println("No existen trenes que eliminar");
        } else {
            System.out.println("Ingrese el codigo del tren que quiere eliminar");
            int id = TecladoIn.readInt();
            if (trenes.eliminar(id)){ //lo intento eliminar
                escribir("SE ELIMINO EL TREN: "+id);
            } else {
                System.out.println("El tren que seleccionó no existe");
            }
        }
    }

    public static void agregarTren(Diccionario trenes, HashMap<String, Lista> mapeo){
        int id = pedirId(trenes);
        String tipoPropulsion = pedirTipoPropulsion();
        int cantVagonesPasajeros = pedirVagonesPasajeros();
        int cantVagonesCarga = pedirVagonesCarga();
        String linea = pedirLinea(mapeo);

        Tren tren = new Tren(id, tipoPropulsion, cantVagonesPasajeros, cantVagonesCarga, linea);
        trenes.insertar(id, tren);
        escribir("SE AGREGO EL TREN "+id);
    }

    public static String pedirLinea(HashMap<String, Lista> mapeo){
        String linea;
        boolean flag = false;    

        do {
            System.out.println("Ingrese la linea");
            System.out.println("- Lineas disponibles del sistema: "+mapeo.keySet());
            System.out.println("- Escriba 'LIBRE' si desea asignarlo como libre");
            linea = TecladoIn.readLine();
            linea = linea.trim();
            if (linea.equalsIgnoreCase("LIBRE") || mapeo.containsKey(linea)){ //si selecciono libre o alguna linea salgo
                flag = true;
            } else {
                System.out.println("La linea ingresada no existe, seleccione otra linea o asignelo como libre");
            }
        } while (!flag);
        System.out.println();
        return linea;
    }

    public static int pedirVagonesCarga(){
        int vagones;
        boolean flag = false;

        do {
            System.out.println("Ingrese la cantidad de vagones de carga");
            vagones = TecladoIn.readInt();

            if (vagones < 0 || vagones > 500){ //error, no existen
                System.out.println("Por favor ingrese un numero coherente");
            } else {
                flag = true;
            }
        } while (!flag);
        System.out.println();
        return vagones;
    }

    public static int pedirVagonesPasajeros(){
        int vagones;
        boolean flag = false;

        do {
            System.out.println("Ingrese la cantidad de vagones de pasajeros");
            vagones = TecladoIn.readInt();

            if (vagones < 0 || vagones > 500){ //error, no existen
                System.out.println("Por favor ingrese un numero coherente");
            } else {
                flag = true;
            }
        } while (!flag);
        System.out.println();
        return vagones;
    }

    public static int pedirId(Diccionario trenes){
        int id;
        boolean flag = false;

        do {
            System.out.println("Ingrese el id");
            id = TecladoIn.readInt();
            if (id < 0){
                System.out.println("ERROR: no puede ingresar un id negativo");
            } else if (trenes.existeClave(id)){
                System.out.println("Dicho id ya existe, ingrese otro");
            } else {
                flag = true;
            }

        } while (!flag);
        System.out.println();
        return id;
    }

    public static String pedirTipoPropulsion(){
        String propulsion = "";
        boolean flag = true;

        do {
            System.out.println("Ingrese el tipo de propulsion: ");
            System.out.println("1- Electricidad");
            System.out.println("2- Diesel");
            System.out.println("3- Fuel oil");
            System.out.println("4- Gasolina");
            System.out.println("5- Hibrido");
            int opcion = TecladoIn.readInt();
            switch (opcion){
                case 1: 
                    propulsion = "Electricidad";
                    break;
                case 2:
                    propulsion = "Diesel";
                    break;
                case 3: 
                    propulsion = "Fuel oil";
                    break;
                case 4: 
                    propulsion = "Gasolina";
                    break;
                case 5: 
                    propulsion = "Hibrido";
                    break;
                default:
                    System.out.println("Opcion incorrecta");
                    flag = false;
            }
        } while (!flag);
        System.out.println();
        return propulsion;
    }

    public static void ABMEstaciones(Diccionario estaciones, GrafoEtiquetado mapa){
        int opcion;
        do {
            System.out.println("---------------------------- ABM Estaciones ----------------------------");
            System.out.println("Operaciones Sobre Estaciones:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    System.out.println(VERDE+"AGREGAR ESTACION\n"+RESET);
                    agregarEstacion(estaciones, mapa);
                    break;
                case 2: 
                    System.out.println(ROJO+"ELIMINAR ESTACION\n"+RESET);
                    eliminarEstacion(estaciones, mapa);
                    break;
                case 3: 
                    System.out.println(AZUL+"MODIFICAR ESTACION\n"+RESET);
                    modificarEstacion(estaciones, mapa);
                    break;
                case 4: 
                    System.out.println(PURPLE+"Saliendo de ABM estaciones... \n"+RESET);
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
    }

    public static void modificarEstacion(Diccionario estaciones, GrafoEtiquetado mapa){
        
        if (!estaciones.esVacio()){
            System.out.println("Ingrese el nombre de la estacion que va a modificar:");
            String nombre = TecladoIn.readLine();
            if (estaciones.existeClave(nombre)){
                Estacion est = (Estacion) estaciones.obtenerInformacion(nombre);
                int opcion;
                do{
                    System.out.println("Operaciones: ");
                    System.out.println("1- Cambiar la cantidad de vias:");
                    System.out.println("2- Cambiar la cantidad de plataformas");
                    System.out.println("3- Mostrar estado de la estacion");
                    System.out.println("4- Volver");
                    System.out.println("--------------------------------------------");
                    System.out.println("Ingrese una opcion:");
                    opcion = TecladoIn.readInt();
                    switch (opcion){
                        case 1:
                            int cantVias = pedirCantVias();
                            est.setVias(cantVias);
                            escribir("SE MODIFICO LA ESTACION: '"+nombre+ "' CAMBIANDO LA CANTIDAD DE VIAS");
                            break;
                        case 2: 
                            int cantPlataf = pedirCantPlataf();
                            est.setPlataformas(cantPlataf);
                            escribir("SE MODIFICO LA ESTACION: '"+nombre+"' CAMBIANDO LA CANTIDAD DE PLATAFORMAS");
                            break;
                        case 3: 
                            System.out.println("Estacion Actual: ");
                            System.out.println(est.toString());
                            break;
                        case 4:
                            System.out.println("Volviendo...");
                            break;
                        default: 
                            System.out.println("Opcion incorrecta");
                            break;
                    }
                } while (opcion != 4);
            } else {
                System.out.println("La estacion ingresada no existe");
            }
        } else {
            System.out.println("No existen estaciones disponibles para modificar");
        }
    }

    public static void eliminarEstacion(Diccionario estaciones, GrafoEtiquetado mapa){
        if (estaciones.esVacio()){
            System.out.println("No hay estaciones para eliminar");
        } else {
            System.out.println("Ingrese el nombre de la estacion que va a eliminar");
            String nombre = TecladoIn.readLine();
            if (estaciones.eliminar(nombre)){ //lo elimino del
                mapa.eliminarVertice(nombre); //si se elimino la estacion, lo saco del mapa
                escribir("SE ELIMINO LA ESTACION "+nombre);
            } else {
                System.out.println("Dicha estacion no existe");
            }
            
        }
    }

    public static void agregarEstacion(Diccionario estaciones, GrafoEtiquetado mapa){
        String nombre = pedirNombreEstacion(estaciones);
        String calle = pedirCalle();
        int num = pedirNumeroEstacion();
        String ciu = pedirCiudad();
        String cp = pedirCP();
        int cantV = pedirCantVias();
        int cantPlataf = pedirCantPlataf();
        
        Estacion nuevaEstacion = new Estacion(nombre, calle, num, ciu, cp, cantV, cantPlataf);
        estaciones.insertar(nombre, nuevaEstacion);
        mapa.insertarVertice(nombre);
        escribir("SE AGREGO LA ESTACION: "+nombre);
    }

    public static int pedirCantPlataf(){
        int cantPlataf;
        boolean flag = false;

        do{
            System.out.println("Ingrese la cantidad de plataformas");
            cantPlataf = TecladoIn.readInt();
            if (cantPlataf < 0){
                System.out.println("Error cantidad no valida");
            } else {
                flag = true;
            }
        } while (!flag);
        return cantPlataf;
    }

    public static int pedirCantVias(){
        int cantVias;
        boolean flag = false;

        do{
            System.out.println("Ingrese la cantidad de vias");
            cantVias = TecladoIn.readInt();
            if (cantVias < 0){
                System.out.println("Error cantidad no valida");
            } else {
                flag = true;
            }
        } while (!flag);
        return cantVias;
    }

    public static int pedirNumeroEstacion(){
        int num;
        boolean flag = false;

        do{
            System.out.println("Ingrese el numero de la estacion");
            num = TecladoIn.readInt();
            if (num < 0){
                System.out.println("Error numero no valido");
            } else {
                flag = true;
            }
        } while (!flag);
        return num;
    }

    public static String pedirCalle(){
        String calle;
        boolean flag = false;

        do{
            System.out.println("Ingrese una calle");
            calle = TecladoIn.readLine();
            if (calle.trim().isEmpty()){
                System.out.println("Error calle vacia");
            } else {
                flag = true;
            }
        } while (!flag);
        return calle;
    }

    public static String pedirCiudad(){
        String ciudad;
        boolean flag = false;

        do{
            System.out.println("Ingrese una ciudad");
            ciudad = TecladoIn.readLine();
            if (ciudad.trim().isEmpty()){
                System.out.println("Error ciudad vacia");
            } else {
                flag = true;
            }
        } while (!flag);
        return ciudad;
    }

    public static String pedirCP(){
        String cp;
        boolean flag = false;

        do{
            System.out.println("Ingrese un codigo postal");
            cp = TecladoIn.readLine();
            if (cp.trim().isEmpty()){
                System.out.println("Error codigo postal vacio");
            } else {
                flag = true;
            }
        } while (!flag);
        return cp;
    }

    public static String pedirNombreEstacion(Diccionario estaciones){
        String nombre;
        boolean flag = false;

        do {
            System.out.println("Ingrese el nombre de la estacion");
            nombre = TecladoIn.readLine();
            if (nombre.trim().isEmpty()){
                System.out.println("ERROR nombre vacio");
            } else if (estaciones.existeClave(nombre)){
                System.out.println("Dicho nombre ya existe, ingrese otro");
            } else{
                flag = true;
            }
        } while (!flag);
        return nombre;
    }

    public static void ABMLineas(Diccionario estaciones, HashMap<String, Lista> lineas, Diccionario trenes){
        int opcion;
        do {
            System.out.println("------------------------------ ABM Lineas ------------------------------");
            System.out.println("Operaciones Sobre trenes:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    System.out.println(VERDE+"AGREGAR LINEA\n"+RESET);
                    agregarLinea(estaciones, lineas);
                    break;
                case 2: 
                    System.out.println(ROJO+"ELIMINAR LINEA\n"+RESET);
                    eliminarLinea(lineas, trenes);
                    break;
                case 3: 
                    System.out.println(AZUL+"MODIFICAR LINEA\n"+RESET);
                    modificarLinea(estaciones, lineas);
                    break;
                case 4: 
                    System.out.println(PURPLE+"Saliendo de ABM Linea... \n"+RESET);
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
    }
    

    public static void modificarLinea(Diccionario estaciones, HashMap<String, Lista> lineas){
        if (!lineas.isEmpty()){
            if (estaciones.esVacio()){
                System.out.println("No existen datos para modificar");
            } else {
                System.out.println("Ingrese el nombre de la linea que va a modificar");
                String nombre = TecladoIn.readLine();
                nombre = nombre.trim();
                if(lineas.containsKey(nombre)){
                    Lista listaEstaciones = lineas.get(nombre);
                    int opcion;

                    do {
                        System.out.println("Operaciones: ");
                        System.out.println("1- Agregar una estacion a la linea:");
                        System.out.println("2- Eliminar una estacion de la linea");
                        System.out.println("3- Mostrar estado de la linea");
                        System.out.println("4- Volver");
                        System.out.println("--------------------------------------------");
                        System.out.println("Ingrese una opcion:");
                        opcion = TecladoIn.readInt();
                        switch (opcion){
                            case 1:
                                agregarEstacionDeLinea(estaciones, nombre, listaEstaciones);                         
                                break;
                            case 2: 
                                eliminarEstacionDeLinea(estaciones, nombre, listaEstaciones);
                                break;
                            case 3: 
                                System.out.println("Lineas: \n"+lineas.toString());
                                break;
                            case 4:
                                System.out.println("Volviendo...");
                                break;
                            default: 
                                System.out.println("Opcion incorrecta");
                                break;
                        }
                    } while (opcion != 4);

                } else {
                    System.out.println("Dicha linea no existe");
                }
            }
        } else {
            System.out.println("No hay lineas disponibles para modificar");
        }
    }

    public static void eliminarEstacionDeLinea(Diccionario estaciones, String nombreLinea, Lista listaEstaciones){
        boolean flag = false;
        do {
            System.out.println("Estaciones disponibles en el sistema: "+estaciones.listarClaves());
            System.out.println("Ingrese el nombre de la estacion que va a eliminar");
            String nombre = TecladoIn.readLine();
            nombre = nombre.trim();
            if (estaciones.existeClave(nombre)){
                Estacion est = (Estacion) estaciones.obtenerInformacion(nombre);
                int pos = listaEstaciones.localizar(est); 
                if (pos < 0){
                    System.out.println("Dicha estacion no existe en la linea");
                } else {
                    listaEstaciones.eliminar(pos);
                    flag = true;
                    escribir("SE MODIFICO LA LINEA '"+nombreLinea+"' ELIMINANDOLE LA ESTACION: "+nombre);
                }
            } else {
                System.out.println("ERROR: dicha estacion no existe");
            } 
        } while (!flag);
    }

    public static void agregarEstacionDeLinea(Diccionario estaciones, String nombreLinea, Lista listaEstaciones){
        boolean flag = false;
        do {
            System.out.println("Estaciones Disponibles en el sistema: "+estaciones.listarClaves());
            System.out.println("Ingrese el nombre de la estacion que va a agregar");
            String nombre = TecladoIn.readLine();
            nombre = nombre.trim();
            if (estaciones.existeClave(nombre)){
                Estacion est = (Estacion) estaciones.obtenerInformacion(nombre);
                if (listaEstaciones.localizar(est) < 0){
                    listaEstaciones.insertar(est, 1);// si no esta en la lista, lo agrego
                    escribir("SE MODIFICO LA LINEA '"+nombreLinea+"' AGREGANDOLE LA ESTACION: "+nombre);
                    flag = true;
                } else {
                    System.out.println("Error: La estacion ya está cargada en la linea, ingrese otra");
                }
            } else {
                System.out.println("Error la estacion no existe, ingrese una correcta");
            }
        } while (!flag);
    }

    public static void eliminarLinea(HashMap<String, Lista> lineas, Diccionario trenes){
        if (lineas.isEmpty()){
            System.out.println("Error: No hay lineas disponibles para eliminar");
        } else {
            System.out.println("Ingrese el nombre");
            String nombreLinea = TecladoIn.readLine();
            nombreLinea = nombreLinea.trim();
            if (lineas.containsKey(nombreLinea)){
                lineas.remove(nombreLinea);
                eliminarLineaDeTrenes(nombreLinea, trenes); //elimino la linea de los trenes que esten en ella
                escribir("SE ELIMINO LA LINEA "+nombreLinea);
                
            } else {
                System.out.println("Dicha linea no existe");
            }
        }
    }

    public static void eliminarLineaDeTrenes(String nombre, Diccionario trenes){
        //Cambia a 'LIBRE' los trenes que tengan como linea a 'nombre'
        Lista listaTrenes = trenes.listarDatos(); //lista de todos los trenes
        int pos, tam = listaTrenes.longitud();
        Tren tren;

        for (pos = 1; pos <= tam; pos++){
            tren = (Tren) listaTrenes.recuperar(pos);
            if (tren.getLinea().equals(nombre)){ //si tiene asignado esta linea
                tren.setLinea("LIBRE");
            }
        } 
    }

    public static void agregarLinea(Diccionario estaciones, HashMap<String, Lista> lineas){
        if (estaciones.esVacio()){
            System.out.println("No hay estaciones en el sistema, no se pueden agregar lineas");
        } else {
            String nombreLinea = pedirNombreLinea(lineas);
            Lista listaEstaciones = new Lista();
            String nombreEstacion;
            String salir;

            System.out.println("Carga de estaciones a la linea:");
            do { 
                System.out.println("Estaciones disponibles para asignar a la linea");
                System.out.println(estaciones.listarClaves());
                System.out.println("Ingrese el nombre de alguna de las estaciones");
                nombreEstacion = TecladoIn.readLine();
                nombreEstacion = nombreEstacion.trim(); //para evitar espacios de mas

                if (estaciones.existeClave(nombreEstacion)){ 
                    Estacion est = (Estacion) estaciones.obtenerInformacion(nombreEstacion);
                    if (listaEstaciones.localizar(est) < 0){ //si la estacion no está registrada
                        listaEstaciones.insertar(est, 1);
                    } else {
                        System.out.println("La linea ya tiene asignada dicha estacion, pruebe con otra");
                    }
                } else {
                    System.out.println("Dicha estacion no existe, pruebe con otra");
                }

                System.out.println("Quiere agregar otra estacion? Ingrese SI/NO");
                salir = TecladoIn.readLine();
                salir = salir.trim();
            } while (salir.equalsIgnoreCase("NO"));

            System.out.println("Carga de estaciones terminada");

            if (listaEstaciones.esVacia()){
                System.out.println("Hubo un error: No se puede crear la linea si no pasa por ninguna estacion");
            } else {
                lineas.put(nombreLinea, listaEstaciones);
                escribir("SE AGREGO LA LINEA "+nombreLinea);
            }
        }
        
    }

    public static String pedirNombreLinea(HashMap<String, Lista> lineas){
        String nombre;
        boolean flag = false;

        do {
            System.out.println("Ingrese el nombre de la linea");
            nombre = TecladoIn.readLine().trim();
            if (nombre.isEmpty() ){
                System.out.println("ERROR nombre vacio"); 
            } else if (lineas.containsKey(nombre)){
                System.out.println("Dicho nombre ya existe, ingrese otro");
            } else{
                flag = true;
            }
        } while (!flag);
        return nombre;
    }
    
    public static void ABMRieles(Diccionario estaciones, GrafoEtiquetado mapa){
        int opcion;
        do {
            System.out.println("------------------------------ ABM Rieles ------------------------------");
            System.out.println("Operaciones Sobre Rieles:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    System.out.println(VERDE+"AGREGAR RIEL\n"+RESET);
                    agregarRiel(mapa, estaciones);
                    break;
                case 2: 
                    System.out.println(ROJO+"ELIMINAR RIEL\n"+RESET);
                    eliminarRiel(mapa, estaciones);
                    break;
                case 3: 
                    System.out.println(AZUL+"MODIFICAR RIEL\n"+RESET);
                    System.out.println("Dichos rieles no se pueden modificar");
                    //ver si se puede modificar algo
                    break;
                case 4: 
                    System.out.println(PURPLE+"Saliendo de ABM rieles... \n"+RESET);
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
    }

    public static void eliminarRiel(GrafoEtiquetado mapa, Diccionario estaciones){
        System.out.println("Ingrese el nombre de la primera estacion del riel");
        String nombre1 = TecladoIn.readLine();
        System.out.println("Ingrese el nombre de la segunda estacion del riel");
        String nombre2 = TecladoIn.readLine();
        nombre1 = nombre1.trim();
        nombre2 = nombre2.trim();
        if (estaciones.existeClave(nombre1) && estaciones.existeClave(nombre2)){
            if (mapa.eliminarArco(nombre1, nombre2)){  //lo intento eliminar y verifico con el metodo si se pudo
                escribir("SE ELIMINO EL RIEL ENTRE LAS ESTACIONES '"+nombre1+"' y '"+nombre2+"'");
            } else {
                System.out.println("No existe un riel entre estas 2 estaciones");
            }
        } else {
            System.out.println("ERROR: alguna o ambas de las estaciones ingresadas no existen");
        }
    }   

    public static void agregarRiel(GrafoEtiquetado mapa, Diccionario estaciones){
        
        System.out.println("Ingrese el nombre de la primera estacion del riel");
        String nombre1 = TecladoIn.readLine();
        System.out.println("Ingrese el nombre de la segunda estacion del riel");
        String nombre2 = TecladoIn.readLine();
        nombre1 = nombre1.trim();
        nombre2 = nombre2.trim();
        if (estaciones.existeClave(nombre1) && estaciones.existeClave(nombre2)){
            System.out.println("Ingrese la distancia en km entre las estaciones");
            int distancia = TecladoIn.readInt();
            if (distancia < 0){
                System.out.println("ERROR: Ingrese una distancia coherente");
            } else {
                if (mapa.insertarArco(nombre1, nombre2, distancia)){ //lo intento insertar y verifico si se pudo
                    escribir("SE AGREGO UN RIEL ENTRE LAS ESTACIONES '"+nombre1+"' y '"+nombre2+"'");
                } else {
                    System.out.println("Error: Ya existe un riel entre las estaciones");
                }
            }
            
        } else {
            System.out.println("ERROR: alguna o ambas de las estaciones ingresadas no existen");
        }
        
    }

    public static void consultasTrenes(Diccionario trenes, Diccionario estaciones, HashMap<String, Lista> lineas){
        int opcion;

        do{
            System.out.println("--------------------------- Consultas Trenes ---------------------------");
            System.out.println("1- Mostrar información sobre un tren");
            System.out.println("2- Consultar linea de un tren y que ciudades visitaria");
            System.out.println("3- Volver");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            opcion = TecladoIn.readInt();
            System.out.println();
            switch(opcion){
                case 1:
                    mostrarInfoTren(trenes);
                    break;
                case 2: 
                    consultaLineaYCiudades(trenes, lineas);
                    break;
                case 3: 
                    System.out.println("Volviendo...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta");
                    break;
            }
        } while (opcion != 3);
        System.out.println();
    }

    public static void consultaLineaYCiudades(Diccionario trenes, HashMap<String, Lista> lineas){
        if (trenes.esVacio()){
            System.out.println("No hay trenes disponibles para dicha consulta");
        } else {
            System.out.println("Ingrese el codigo del tren");
            int id = TecladoIn.readInt();
            if (trenes.existeClave(id)){
                Tren tren = (Tren) trenes.obtenerInformacion(id);
                String lineaTren = tren.getLinea();
                System.out.println("El tren "+id+" esta asignado a la linea: "+lineaTren);
                if (!lineaTren.equals("LIBRE")){
                    System.out.println("Lista de ciudades que recorre: ");
                    Lista listaEstaciones = lineas.get(lineaTren);
                    int i , tam = listaEstaciones.longitud();

                    for (i = 1; i <= tam; i++){
                        Estacion est = (Estacion) listaEstaciones.recuperar(i);
                        System.out.println("- "+est.getCiudad());
                    }
                    
                }
            } else {
                System.out.println("Error: el tren no existe");
            }
        }
    }

    public static void mostrarInfoTren(Diccionario trenes){
        if (trenes.esVacio()){
            System.out.println("No hay trenes disponibles para mostrar");
        } else {
            System.out.println("Ingrese el codigo del tren");
            int id = TecladoIn.readInt();
            if (trenes.existeClave(id)){
                System.out.println("Tren: "+trenes.obtenerInformacion(id).toString());
            } else {
                System.out.println("ERROR: el tren no existe");
            }
        }
    }

    public static void consultasEstaciones(Diccionario estaciones){
        int opcion;
        do{ 
            System.out.println("------------------------- Consultas Estaciones -------------------------");
            System.out.println("1- Mostrar informacion sobre una estacion");
            System.out.println("2- Dada un nombre, mostrar todas las estaciones que comienzan con dicho nombre");
            System.out.println("3- Volver");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            opcion = TecladoIn.readInt();
            System.out.println();
            switch(opcion){
                case 1:
                    mostrarInfoEstacion(estaciones);
                    break;
                case 2: 
                    estacionesConNombre(estaciones);
                    break;
                case 3: 
                    System.out.println("Volviendo...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta");
                    break;
            }
            System.out.println();
        } while (opcion != 3);
    }

    public static void estacionesConNombre(Diccionario estaciones){
        if (estaciones.esVacio()){
            System.out.println("No hay estaciones disponibles para dicha consulta");
        } else {
            System.out.println("Ingrese el nombre con el que empiezan las estaciones que quiere ver");
            String nombreInicio = TecladoIn.readLine().trim();
            System.out.println("Lista de estaciones que empiezan con '"+nombreInicio+"': "+estaciones.listarPorRango(nombreInicio, nombreInicio +"ZZZZZZZZZZZZZZZZZZZZZ"));
        }
    }
    

    public static void mostrarInfoEstacion(Diccionario estaciones){
        if (estaciones.esVacio()){
            System.out.println("No hay estaciones disponibles para mostrar");
        } else {
            System.out.println("Estaciones Disponibles "+estaciones.listarClaves());
            System.out.println("Ingrese el nombre de la estacion");
            String nombre = TecladoIn.readLine();
            nombre = nombre.trim();
            if (estaciones.existeClave(nombre)){
                Estacion est = (Estacion) estaciones.obtenerInformacion(nombre);
                System.out.println(est.mostrarDatos());
            } else {
                System.out.println("La estacion no existe");
            }
        }
    }

    public static void consultasViajes(Diccionario estaciones, GrafoEtiquetado mapa){
        int opcion;
        do{ 
            System.out.println("--------------------------- Consultas Viajes ---------------------------");
            System.out.println("1- Obtener el camino que llegue de una estacion A a una estacion B que pase por menos estaciones");
            System.out.println("2- Obtener el camino que llegue de una estacion A a una estacion B de menor distancia en kilómetros");
            System.out.println("3- Volver");
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Ingrese una opcion");
            opcion = TecladoIn.readInt();
            System.out.println();
            switch(opcion){
                case 1:
                    consultaCaminoMenor(estaciones, mapa);
                    break;
                case 2: 
                    consultaCaminoMenorKm(estaciones, mapa);
                    break;
                case 3: 
                    System.out.println("Volviendo...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta");
                    break;
            }
            System.out.println();
        } while (opcion != 3);
    }

    public static void consultaCaminoMenorKm(Diccionario estaciones, GrafoEtiquetado mapa){
        if (!mapa.esVacio()){
            System.out.println("Estaciones disponibles: "+estaciones.listarClaves());
            System.out.println("Ingrese el nombre de estacion A");
            String nombre1 = TecladoIn.readLine();
            System.out.println("Ingrese el nombre de la estacion B");
            String nombre2 = TecladoIn.readLine();
            nombre1 = nombre1.trim();
            nombre2 = nombre2.trim();
            if (estaciones.existeClave(nombre1) && estaciones.existeClave(nombre2)){
                Lista camino = mapa.caminoMenosPeso(nombre1, nombre2);
                if (camino.esVacia()){
                    System.out.println("No existe un camino entre las 2 estaciones");
                } else {
                    System.out.println("Camino menor distancia en km: "+camino.toString());
                }
            } else {
                System.out.println("ERROR: alguna o ambas de las estaciones ingresadas no existen");
            }
        } else {
            System.out.println("ERROR no hay datos en el mapa");
        }
        
    }

    public static void consultaCaminoMenor(Diccionario estaciones, GrafoEtiquetado mapa){
        if (!mapa.esVacio()){
            System.out.println("Estaciones disponibles: "+estaciones.listarClaves());
            System.out.println("Ingrese el nombre de estacion A");
            String nombre1 = TecladoIn.readLine();
            System.out.println("Ingrese el nombre de la estacion B");
            String nombre2 = TecladoIn.readLine();
            nombre1 = nombre1.trim();
            nombre2 = nombre2.trim();
            if (estaciones.existeClave(nombre1) && estaciones.existeClave(nombre2)){
                Lista camino = mapa.caminoMasCorto(nombre1, nombre2);
                if (camino.esVacia()){
                    System.out.println("No existe un camino entre los 2 nodos");
                } else {
                    System.out.println("Camino de menor estaciones: "+camino.toString());
                }
            } else {
                System.out.println("ERROR: alguna o ambas de las estaciones ingresadas no existen");
            }
        } else {
            System.out.println("ERROR no hay datos en el mapa");
        }
        
    }

    public static void mostrarSistema(GrafoEtiquetado grafoMapa, Diccionario estaciones, Diccionario trenes, HashMap<String, Lista> mapeo){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("|                                MAPA                                  |");
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println(grafoMapa.toString());

        System.out.println("------------------------------------------------------------------------");
        System.out.println("|                             ESTACIONES                               |");
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println(estaciones.toString());

        System.out.println("------------------------------------------------------------------------");
        System.out.println("|                               TRENES                                 |");
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println(trenes.toString());

        System.out.println("------------------------------------------------------------------------");
        System.out.println("|                               LINEAS                                 |");
        System.out.println("------------------------------------------------------------------------\n");
        System.out.println(mapeo.toString());
        System.out.println();
    }

    public static void cargarDatos(GrafoEtiquetado mapa, Diccionario estaciones, Diccionario trenes, HashMap<String, Lista> lineas){

        try {
            BufferedReader archivo = new BufferedReader(new FileReader("src\\datos.txt"));
            String linea = archivo.readLine(); 

            while (linea != null){
                
                analizarLinea(linea, mapa, estaciones, trenes, lineas);
                linea = archivo.readLine();
            }
            archivo.close();
            escribir("Carga inicial completada ");
            System.out.println();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
        
    }

    public static void analizarLinea(String linea, GrafoEtiquetado grafoMapa, Diccionario estaciones, Diccionario trenes, HashMap<String, Lista> mapeo){
        StringTokenizer parte = new StringTokenizer(linea, ";");

        switch (parte.nextToken()){
            case "E": 
                //Formato de Estacion: (nombre; calle; numero; ciudad; CP ; cantVias; cantPlataformas)
                String nombre = parte.nextToken();
                String calle = parte.nextToken();
                int numero = Integer.parseInt(parte.nextToken());
                String ciudad = parte.nextToken();
                String cp = parte.nextToken();
                int cantVias = Integer.parseInt(parte.nextToken());
                int cantPlataf = Integer.parseInt(parte.nextToken());
                Estacion est = new Estacion(nombre, calle, numero, ciudad, cp, cantVias, cantPlataf);
                if (estaciones.insertar(nombre , est) && grafoMapa.insertarVertice(nombre)){
                    escribir("ESTACION CARGADA: "+nombre);
                }
                break;
            case "T": 
                //Formato de Tren: (id; tipoPropulsion; cantVagonesPasaj; cantVagonesCarg; linea)
                int id = Integer.parseInt(parte.nextToken());
                String tipoPropulsion = parte.nextToken().trim();
                int cantVagonesPasajeros = Integer.parseInt(parte.nextToken());
                int cantVagonesCarga = Integer.parseInt(parte.nextToken());
                String lineaTren = parte.nextToken();
                Tren tren = new Tren(id, tipoPropulsion, cantVagonesPasajeros, cantVagonesCarga, lineaTren);
                if (trenes.insertar(id, tren)){
                    escribir("TREN CARGADO: "+id);
                }

                break;
            case "L": 
                //Formato de Linea: (nombre de la línea; y nombre de las estaciones por las que pasa)
                String nombreLinea = parte.nextToken();
                
                
                if (!mapeo.containsKey(nombreLinea)){ //si la linea no existe
                    Lista listaEstaciones = new Lista();
                    
                    while (parte.hasMoreTokens()){ //mientras tenga estaciones relacionadas
                        String aux = parte.nextToken();
                        if (estaciones.existeClave(aux)){ //si la estacion existe
                            Estacion estacion =  (Estacion) estaciones.obtenerInformacion(aux);
                            listaEstaciones.insertar(estacion, 1);
                        }   
                    }

                    if (!listaEstaciones.esVacia()){ 
                        mapeo.put(nombreLinea, listaEstaciones);
                        escribir("LINEA CARGADA: "+nombreLinea);
                    } else { //si una linea no tiene estaciones, entonces no deberia existir
                        listaEstaciones = null;
                    }
                }  
                break;
            case "R": 
                //Formato de Riel: (nombre de la estacion 1; nombre de la estacion 2; distancia entre las 2 estaciones)
                String est1 = parte.nextToken();
                String est2 = parte.nextToken();
                int etiqueta = Integer.parseInt(parte.nextToken());
                if (grafoMapa.insertarArco(est1, est2, etiqueta)){
                    escribir("RIEL CARGADO: "+est1+" - "+est2);
                }
                break;
        }
    }

    public static void crearLog(){
        try {
            PrintWriter log = new PrintWriter(new File("src\\log.txt"));
        } catch (IOException e) {
            System.out.println("Error al crear el archivo LOG:" + e);
        }
    }
    
	public static void escribir(String entrada) {
		FileWriter fichero = null;
        PrintWriter pw = null;

        System.out.println(entrada);

        try{
            fichero = new FileWriter("src\\log.txt", true);
            pw = new PrintWriter(fichero);

            pw.println(entrada);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (fichero != null)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
    

    
}
