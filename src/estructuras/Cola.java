package estructuras;
/**
 *
 * @author Guille
 */
public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    public Cola(){
        frente = null;
        fin = null;
    }
    
    public boolean poner(Object elem){
        Nodo nuevo = new Nodo(elem, null);
        if (this.esVacia()){ //caso especial: si esta vacia engancho el frente y fin al mismo nodo
            this.frente = nuevo;
            this.fin = nuevo;
        } else { //caso general: cambio el enlace del fin y lo actualizo al nuevo nodo
            (this.fin).setEnlace(nuevo);
            this.fin = nuevo;
        }
        return true;
    }
    public boolean sacar(){
        boolean verif;
        
        if (this.esVacia()){
            verif = false; //cola vacia reporta error
        } else {
            //al menos hay un elemento
            this.frente = (this.frente).getEnlace();
            if (this.frente == null){ //si la cola quedo vacia actualizo fin
                this.fin = null;
            }
            verif = true;
        }
        return verif;
    }
    public boolean esVacia(){
        return this.frente == null;
    }
    public Object obtenerFrente(){
        Object elem;
        if (this.esVacia()){
            elem = null;
        } else {
            elem = (this.frente).getElem();
        }
        return elem;
    }
    
    public void vaciar(){
        this.frente = null;
        this.fin = null;
    }
    public String toString(){
        String cad = "";
        
        if (this.esVacia()){
            cad = "Cola Vacia";
        } else {
            Nodo aux = this.frente;
            cad = "|";
            while (aux != null){
                cad = cad + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null){
                    cad = cad + ", ";
                }
            }
            cad = cad + "|";
        }
        return cad;
    } 
    public Cola clone() {
        Cola clon = new Cola();
        
        if (!this.esVacia()){
           Nodo aux1 = this.frente;
           clon.frente = new Nodo(aux1.getElem(), null); //engancha el primer elemento
           aux1 = aux1.getEnlace();
           Nodo aux2 = clon.frente;
           while (aux1 != null){ 
              aux2.setEnlace(new Nodo(aux1.getElem(), null));
              aux2 = aux2.getEnlace();
              aux1 = aux1.getEnlace();
           } 
           clon.fin = aux2;
        }
        return clon;   
    }
}
