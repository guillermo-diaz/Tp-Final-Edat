package sistema;

public class Estacion implements Comparable{
    private final String NOMBRE, calle, ciudad, codigoPostal;
    private final int numero;
    private int cantVias, cantPlataformas;

    public Estacion(String n, String cal, int num, String ciu, String cp, int cantV, int cantPlataf) {
        this.NOMBRE = n;
        this.calle = cal;
        this.numero = num;
        this.ciudad = ciu;
        this.codigoPostal = cp;
        this.cantVias = cantV;
        this.cantPlataformas = cantPlataf;
    }

    public String getNombre(){
        return this.NOMBRE;
    }

    public String getCalle(){
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public int getCantidadVias() {
        return cantVias;
    }

    public int getCantidadPlataformas() {
        return cantPlataformas;
    }

    public void setVias(int cantidadVias) {
        this.cantVias = cantidadVias;
    }

    public void setPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }

    public String mostrarDatos(){
        return " '"+NOMBRE+"', Direccion: " +calle+ " "+numero+", "+ciudad+", CP: "+codigoPostal+
        ", CantVias: "+cantVias+", cantPlataformas: "+cantPlataformas+" ";
    }

    public String toString() {
        return " '"+NOMBRE+"' ";
    } 

    public boolean equals(Estacion otro){
        return NOMBRE.equals(otro.NOMBRE);
    }
    
    @Override
    public int compareTo(Object o) {
        Estacion otraEstacion = (Estacion) o;
        return this.NOMBRE.compareTo(otraEstacion.NOMBRE);
    }
}
