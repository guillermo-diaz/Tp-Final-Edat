package estructuras;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;

    public NodoAdy(NodoVert n, NodoAdy ady, int etiqueta){
        this.vertice = n;
        this.sigAdyacente = ady;
        this.etiqueta = etiqueta;
    }

    public NodoVert getVertice(){
        return vertice;
    }

    public NodoAdy getSigAdyacente(){
        return sigAdyacente;
    }

    public int getEtiqueta(){
        return etiqueta;
    }

    public void setVertice(NodoVert vertice){
        this.vertice = vertice;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente){
        this.sigAdyacente = sigAdyacente;
    }

    public void setEtiqueta(int etiq){
        this.etiqueta = etiqueta;
    }
}

