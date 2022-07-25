package estructuras;

public class NodoAVLDicc{
    private Comparable clave;
    private Object dato;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;
    private int altura;

    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho) {
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.recalcularAltura();
    }

    public int getAltura(){
        return altura;
    }

    public Comparable getClave() {
        return clave;
    }
    
    public NodoAVLDicc getIzquierdo(){
        return izquierdo;
    }

    public NodoAVLDicc getDerecho(){
        return derecho;
    }

    public Object getDato() {
        return this.dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }
   
    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo){
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoAVLDicc derecho){
        this.derecho = derecho;
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

}
