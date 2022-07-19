package estructuras;

public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object e, NodoVert sigVert, NodoAdy ady){
        this.elem = e;
        this.sigVertice = sigVert;
        this.primerAdy = ady;
    }

    public Object getElem(){
        return elem;
    }

    public NodoVert getSigVertice(){
        return sigVertice;
    }

    public NodoAdy getPrimerAdy(){
        return primerAdy;
    }

    public void setElem(Object e){
        this.elem = e;
    }

    public void setSigVertice(NodoVert sigVert){
        sigVertice = sigVert;
    }

    public void setPrimerAdy(NodoAdy ady){
        primerAdy = ady;
    }

    public String toString(){
        String sigVert = "-", ady = "-";
        
        if (this.sigVertice != null){
            sigVert = sigVert + this.sigVertice.getElem();
        }
        if (this.primerAdy != null){
            ady = ady + this.primerAdy.getVertice().getElem();
        }
        return "["+this.elem+", SV: "+sigVert+", PA: "+ady;
    }
}
    

