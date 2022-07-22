package sistema;

public class Tren {
    private String tipoPropulsion, linea;
    private int id, cantVagonesPasajeros, cantVagonesCarga;

    public Tren(int id, String tipoPropulsion, int cantVagonesPasajeros, int cantVagonesCarga, String linea) {
        this.id = id;
        this.tipoPropulsion = tipoPropulsion;
        this.cantVagonesPasajeros = cantVagonesPasajeros;
        this.cantVagonesCarga = cantVagonesCarga;
        this.linea = linea;
    }

    public int getId() {
        return id;
    }

    public String getLinea() {
        return linea;
    }

    public String getTipoPropulsion() {
        return tipoPropulsion;
    }

    public int getCantidadVagonesPasajeros() {
        return cantVagonesPasajeros;
    }

    public void setCombustible(String tipoPropulsion) {
        this.tipoPropulsion = tipoPropulsion;
    }

    public int getCantidadVagonesCarga() {
        return cantVagonesCarga;
    }
    
    public void setCantVagonesPasajeros(int cantVagonesPasajeros) {
        this.cantVagonesPasajeros = cantVagonesPasajeros;
    }

    public void setCantVagonesCarga(int cantVagonesCarga) {
        this.cantVagonesCarga = cantVagonesCarga;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String toString() {
        return  id +": "+tipoPropulsion+", "+cantVagonesPasajeros+", "+cantVagonesCarga
                +", "+linea;
    }
}
