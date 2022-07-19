package estructuras;

public class NodoAVL{
    private Comparable elem;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;

    public NodoAVL(Comparable e, NodoAVL izq, NodoAVL der){
        elem = e;
        izquierdo = izq;
        derecho = der; 
        this.recalcularAltura();
    }

    public NodoAVL(Comparable e){
        elem = e;
        izquierdo = null;
        derecho = null;
        altura = 0;
    }

    public int getAltura(){
        return altura;
    }

    public Comparable getElem(){
        return elem;
    }
    
    public NodoAVL getIzquierdo(){
        return izquierdo;
    }

    public NodoAVL getDerecho(){
        return derecho;
    }

    public void recalcularAltura(){
        int altIzq, altDer;
        altIzq = -1;
        altDer = -1;

        if (this.izquierdo != null){ //Si tiene HI calculo su altura
            altIzq = (this.izquierdo).altura;
        } 
        if (this.derecho != null){ //Si tiene HD calculo su altura
            altDer = (this.derecho).altura;
        }
        
        this.altura = Math.max(altIzq, altDer) + 1;
    }

    public void setElem(Comparable elem){
        this.elem = elem;
    }
    public void setIzquierdo(NodoAVL izquierdo){
        this.izquierdo = izquierdo;
    }
    public void setDerecho(NodoAVL derecho){
        this.derecho = derecho;
    }

    /* testing
    public String toString(){
        String izq, der;
        izq = "-";
        der = "-";

        if (izq != null){
            izq = izquierdo.getElem().toString();
        }
        if (der != null){
            der = derecho.getElem().toString();
        }

        return "["+elem+", "+izq+" , "+der+"]";
    }
    */
}
