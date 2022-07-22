package sistema;

import estructuras.*;
import java.io.*;
import java.util.HashMap;
import util.TecladoIn;
import java.util.StringTokenizer;

public class TrenesSA {
    public static void main(String[] args) {
        GrafoEtiquetado mapa = new GrafoEtiquetado();
        Diccionario estaciones = new Diccionario();
        Diccionario trenes = new Diccionario();
        HashMap<Linea, Estacion> mapeo = new HashMap<Linea, Estacion>();
        int opcion;

        do {
			opcion = menu();
			switch (opcion) {
				case 1:
                    cargarDatos(mapa, estaciones, trenes, mapeo);
					break;
				case 2:

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
        
    }

    public static void cargarDatos(GrafoEtiquetado mapa, Diccionario estaciones, Diccionario trenes, HashMap lineas){

        try {
            BufferedReader archivo = new BufferedReader(new FileReader("src\\datos.txt"));
            String linea = archivo.readLine(); 

            while (linea != null){
                analizarLinea(linea, mapa, estaciones, trenes, lineas);
                linea = archivo.readLine();
            }
            System.out.println("-------------------------------------------------------");
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
        
    }

    public static void analizarLinea(String linea, GrafoEtiquetado mapa, Diccionario estaciones, Diccionario trenes, HashMap lineas){
        StringTokenizer parte = new StringTokenizer(linea, ";");

        switch (parte.nextToken()){
            case "E": 
                //poner un formato
                break;
            case "T": 

                break;
            case "L": 

                break;
            case "R": 

                break;
        }
    }

    public static int menu() {
        System.out.println("-------------------------------- Menu --------------------------------");
        System.out.println("1. Carga inicial del sistema:");
        System.out.println("2. ABM de trenes");
        System.out.println("3. ABM de estaciones");
        System.out.println("4. ABM de rieles");
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
}
