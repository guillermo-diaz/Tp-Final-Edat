package sistema;

import estructuras.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


import util.TecladoIn;
import java.util.StringTokenizer;

public class TrenesSA {
    public static void main(String[] args) {
        GrafoEtiquetado mapa = new GrafoEtiquetado();
        Diccionario estaciones = new Diccionario();
        Diccionario trenes = new Diccionario();
        HashMap<String, Lista> mapeo = new HashMap<String, Lista>(); //mapeo de lineas con lista de estaciones
        int opcion;
       
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

					break;
				case 4:
					
					break;
				case 5:

					break;
				case 6:
					
					break;
				case 7:

					break;
				case 8:

					break;
				case 9:
					
					break;
				case 10:
                    System.out.println("Fin del programa");
					break;
				default:
                    System.out.println("Opcion Incorrecta.");
					break;
			}
		}while(opcion != 10);
        System.out.println("Grafo: \n"+ mapa.toString());
        System.out.println("-----------------------");
        System.out.println("Dicc Estaciones: \n"+estaciones.toString());
        System.out.println("-------------------------");
        System.out.println("Dicc Trenes: \n"+trenes.toString());
        System.out.println("-------------------------");
  
        System.out.println("mapeo "+mapeo.get("Linea 2"));
        
        
        
    }

    public static int menu() {
        System.out.println("-------------------------------- Menu --------------------------------");
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
        System.out.println("---------------------------------------------------------------------- \n");

        System.out.println("Seleccione una opción ");
        int opcion = TecladoIn.readInt();
        return opcion;
    }

    public static int opcionesAbm(){
        int opcion;
        System.out.println("1. Agregar");
        System.out.println("2. Eliminar");
        System.out.println("3. Modificar");
        System.out.println("4. Salir");
        System.out.println("---------------------");
        System.out.println("Ingrese una opción: ");
        opcion = TecladoIn.readInt();
        return opcion;
    }

    public static void ABMTrenes(Diccionario trenes, HashMap<String, Lista> mapeo){
        int opcion;
        do {
            System.out.println("---------------------------- ABM TRENES ----------------------------");
            System.out.println("Operaciones Sobre trenes:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    agregarTren(trenes, mapeo);
                    break;
                case 2: 
                    eliminarTren(trenes, mapeo);
                    break;
                case 3: 
                    modificarTren(trenes, mapeo);
                    break;
                case 4: 
                    System.out.println("Saliendo de ABM trenes...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
    }

    public static void modificarTren(Diccionario trenes, HashMap<String, Lista> mapeo){
        if (!trenes.esVacio()){
            System.out.println("Ingrese el nombre del tren que va a modificar");
            String nombre = TecladoIn.readLine();
            if (trenes.existeClave(nombre)){
                    Tren tren = (Tren) trenes.obtenerInformacion(nombre);
                    System.out.println("Operaciones: ");
                    System.out.println("1- Cambiar el tipo Propulsion:");
                    System.out.println("2- Cambiar la cantidad de vagones pasajeros");
                    System.out.println("3- Cambiar la cantidad de vagones de carga");
                    System.out.println("4- Cambiar la linea del tren");
                    System.out.println("5- volver");
                    System.out.println("--------------------------------------------");
                    System.out.println("Ingrese una opcion:");
                    int opcion = TecladoIn.readInt();
                    switch (opcion){
                        case 1:
                            String propulsion = pedirTipoPropulsion();
                            tren.setCombustible(propulsion);
                            break;
                        case 2: 
                            int cantVagonesPasajeros = pedirVagonesPasajeros();
                            tren.setCantVagonesPasajeros(cantVagonesPasajeros);
                            break;
                        case 3: 
                            int cantVagonesCarga = pedirVagonesCarga();
                            tren.setCantVagonesCarga(cantVagonesCarga);
                            break;
                        case 4:
                            String linea = pedirLinea(mapeo);
                            tren.setLinea(linea);
                            break;
                        case 5:
                            System.out.println("Volviendo");
                            break;
                        default: 
                            System.out.println("Opcion incorrecta");
                            break;
                    }
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
            System.out.println("Ingrese el nombre del tren que quiere eliminar");
            String nombre = TecladoIn.readLine();
            if (trenes.existeClave(nombre)){
                trenes.eliminar(nombre);
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
    }

    public static String pedirLinea(HashMap<String, Lista> mapeo){
        String linea = "";
        boolean flag = false;    

        do {
            System.out.println("Ingrese la linea");
            System.out.println("- Lineas disponibles del sistema: "+mapeo.keySet());
            System.out.println("- Escriba 'LIBRE' si desea asignarlo como libre");
            linea = TecladoIn.readLine();
            if (linea.equalsIgnoreCase("LIBRE") || mapeo.get(linea) != null){ //si selecciono libre o alguna linea salgo
                flag = true;
            } else {
                System.out.println("La linea ingresada no existe, seleccione otra linea o asignelo como libre");
            }
        } while (!flag);
        return linea;
    }

    public static int pedirVagonesCarga(){
        int vagones;
        boolean flag = false;

        do {
            System.out.println("Ingrese la cnatidad de vagones de carga");
            vagones = TecladoIn.readInt();

            if (vagones < 0 || vagones > 500){ //error, no existen
                System.out.println("Por favor ingrese un numero coherente");
            } else {
                flag = true;
            }
        } while (!flag);
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
        return vagones;
    }

    public static int pedirId(Diccionario trenes){
        int id;
        boolean flag = false;

        do {
            System.out.println("Ingrese el id");
            id = TecladoIn.readInt();
            if (trenes.existeClave(id)){
                System.out.println("Dicho id ya existe, ingrese otro");
            } else {
                flag = true;
            }

        } while (!flag);
        return id;
    }

    public static String pedirTipoPropulsion(){
        String propulsion = "";
        boolean flag = false;

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
            }
        } while (!flag);
        return propulsion;
    }

    public static void ABMEstaciones(Diccionario estaciones, GrafoEtiquetado mapa, HashMap<String, Lista> mapeo){
        int opcion;
        do {
            System.out.println("---------------------------- ABM Estaciones ----------------------------");
            System.out.println("Operaciones Sobre Estaciones:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    agregarEstacion(estaciones, mapa);
                    break;
                case 2: 
                    
                    break;
                case 3: 

                    break;
                case 4: 
                    System.out.println("Saliendo de ABM Estaciones...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
    }

    public static void eliminarEstacion(Diccionario estaciones, GrafoEtiquetado mapa){
        if (estaciones.esVacio()){
            System.out.println("No hay estaciones para eliminar");
        } else {
            System.out.println("Ingrese el nombre de la estacion que va a eliminar");
            String nombre = TecladoIn.readLine();
            if (estaciones.existeClave(nombre)){
                
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
            if (calle.isEmpty()){
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
            if (ciudad.isEmpty()){
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
            if (cp.isEmpty()){
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
            if (estaciones.existeClave(nombre)){
                System.out.println("Dicho nombre ya existe, ingrese otro");
            } else if (nombre.isEmpty()){
                System.out.println("ERROR nombre vacio");
            } else{
                flag = true;
            }
        } while (!flag);
        return nombre;
    }

    public static void ABMLineas(Diccionario trenes, HashMap<String, Lista> lineas){
        int opcion;
        do {
            System.out.println("---------------------------- ABM Lineas ----------------------------");
            System.out.println("Operaciones Sobre trenes:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    
                    break;
                case 2: 
                    break;
                case 3: 

                    break;
                case 4: 
                    System.out.println("Saliendo de ABM trenes...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
    }
    
    public static void ABMRieles(GrafoEtiquetado mapa){
        int opcion;
        do {
            System.out.println("---------------------------- ABM Rieles ----------------------------");
            System.out.println("Operaciones Sobre Rieles:");
            opcion = opcionesAbm();

            switch(opcion){
                case 1:  
                    
                    break;
                case 2: 
                    break;
                case 3: 

                    break;
                case 4: 
                    System.out.println("Saliendo de ABM Rieles...");
                    break;
                default: 
                    System.out.println("Opcion incorrecta"); 
                    break;
            }
        } while (opcion != 4);
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
            System.out.println("Carga inicial completada \n ");
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
                estaciones.insertar(nombre , est);
                grafoMapa.insertarVertice(nombre);
                break;
            case "T": 
                //Formato de Tren: (id; tipoPropulsion; cantVagonesPasaj; cantVagonesCarg; linea)
                int id = Integer.parseInt(parte.nextToken());
                String tipoPropulsion = parte.nextToken();
                int cantVagonesPasajeros = Integer.parseInt(parte.nextToken());
                int cantVagonesCarga = Integer.parseInt(parte.nextToken());
                String lineaTren = parte.nextToken();
                Tren tren = new Tren(id, tipoPropulsion, cantVagonesPasajeros, cantVagonesCarga, lineaTren);
                trenes.insertar(id, tren);
                
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
                    mapeo.put(nombreLinea, listaEstaciones);
                }

                
                
                break;
            case "R": 
                String est1 = parte.nextToken();
                String est2 = parte.nextToken();
                int etiqueta = Integer.parseInt(parte.nextToken());
                grafoMapa.insertarArco(est1, est2, etiqueta);
                break;
        }
        
    }

    
}
