package estructuras;
/**
 *
 * @author Guille
 */
public class Lista {
    private Nodo cabecera;
    private int longitud;
    
    //constructor
    public Lista (){
        this.cabecera = null;
        this.longitud = 0;
    }
    
    public int longitud(){
        //retorna la longitud de la lista
        return this.longitud;
    }

    public boolean esVacia(){
        //verifica si la lista esta vacia
        return this.cabecera == null;
    }

    public boolean insertar(Object elem, int pos){
        //Inserta un elemento en la lista en una pos ingresada
        boolean verif;
        
        if (pos < 1 || pos > this.longitud()+1){ //error si esta fuera del rango
            verif = false;
        } else {
            if (pos == 1){ //caso especial: inserta en la primera pos
                this.cabecera = new Nodo(elem, this.cabecera); 
            } else { //avanzo hasta el elem de la pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos -1){
                    aux = aux.getEnlace();
                    i++;
                }
                //crea el nodo nuevo y lo enlaza
                Nodo nuevo = new Nodo(elem, aux.getEnlace()); 
                aux.setEnlace(nuevo);
            }
            //la longitud se actualiza y retornamos true
            this.longitud++; 
            verif = true;
        }
        return verif;
    }

    public boolean eliminar(int pos){
        //elimina el elemento de la pos ingresada
        boolean verif = true;
        
        if (pos > 0 && pos <= this.longitud()) {  
            Nodo aux = this.cabecera; 
            if (pos == 1){ //eliminar primera posicion
                this.cabecera = aux.getEnlace();
            } else { //avanzo hasta el elem de la pos-1
                int i = 2; //tiene al menos 2 elementos
                
                while (i <= pos-1){
                    aux = aux.getEnlace();
                    i++;
                }
                if (i + 1 == pos){ //caso especial eliminar ult posición
                    aux.setEnlace(null);
                } else { //posicion intermedia
                    aux.setEnlace((aux.getEnlace()).getEnlace()); //cambio el enlace del nodo de pos-1 con el enlace del nodo a eliminar
                }  
                
                
            }
            this.longitud--; //disminuimos la longitud
        } else { //reporta error si esta fuera del rango
            verif = false;
        }
        return verif;
    }  
    
    public Object recuperar(int pos){
    //devuelve el elemento de la posicion ingresada por parametro
        Object elem;
        
        if (pos > 0 && pos <= this.longitud()){ 
            int i = 1;
            Nodo aux = this.cabecera;
            while (i < pos){
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElem(); //guardo el elem 
        } else { //devuelve null si esta fuera del rango
            elem = null;
        }

        return elem;
    }

    public int localizar(Object elem){
        //Devuelve la posicion de la primera ocurrencia del elemento ingresado
        int pos = localizarR(this.cabecera, elem, 1);
        return pos;
    }

    private int localizarR(Nodo n1, Object elem, int pos){
        /*Modulo recursivo de lozalizar
        n1: nodo por el cual nos vamos a ir moviendo
        elem: elemento a buscar
        pos: posicion del nodo en el que estamos parados
        */
        int ret;
        
        if (n1 == null){ //caso base 1: no encuentra el elemento, retorna -1
            ret = -1;
        } else {
            if (n1.getElem() == elem){ //caso base 2: elemento encontrado, retorna pos
                ret = pos;
            } else { //paso recursivo: nos movemos al siguiente enlace y aumentamos la pos
                ret = localizarR(n1.getEnlace(), elem, pos+1);
            }
        }
        
        return ret;
    }
    
    public void vaciar(){
        //Quita todos los elementos de la lista
        this.cabecera = null;
        this.longitud = 0;
    } 
    
    public String toString(){
        //Crea y devuelve un string con todos los elementos de la lista
        String cad;
        
        if (this.esVacia()){ //error pila vacia
            cad = "Lista Vacia";
        } else {
            cad = "[ ";
            Nodo aux = this.cabecera;
            while (aux != null){
                cad = cad + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null){
                    cad = cad + ", ";
                }
            }
            cad = cad + " ]";
        }
        return cad;
    }
   
    public Lista clone(){
        //crea un clon de la pila actual
        Lista clon = new Lista();
        
        if (!this.esVacia()){ //tiene al menos 1 elemento
           Nodo aux = this.cabecera;
           clon.cabecera = new Nodo(aux.getElem(),null); //actualizo la cabecera
           clon.longitud++;
           cloneRecursivo(clon, clon.cabecera, aux.getEnlace());
        }
        return clon;
    }

    private void cloneRecursivo(Lista clon, Nodo puntero, Nodo n1){
        if (n1 != null) {
            Nodo nuevo = new Nodo(n1.getElem(), null);
            clon.longitud++;
            puntero.setEnlace(nuevo);
            cloneRecursivo(clon, puntero.getEnlace(), n1.getEnlace());
        }
    }


    //Adicionales tp/simulacro
    public void invertir(){
        //modulo que invierte la lista actual (sin crear nuevos nodos)
        if (this.cabecera != null){
            invertirAux(this.cabecera);
        }
    }

    private Nodo invertirAux(Nodo n){
        /*modulo aux que invierte nodos enlazados por referencia
        A la ida de la recursion llega al ultimo nodo y lo pone como cabecera y lo retorna
        a la vuelta de la recursion lo enlaza al nodo anterior y elimina el enlace doble que queda
        precondicion: Debe haber al menos 1 elemento
        */
        Nodo ret;

        if (n.getEnlace() != null){ //si n no es el ultimo elemento
            ret = invertirAux(n.getEnlace());
            ret.setEnlace(n); //como ret pasa a ser el anterior de n, seteo el enlace
            n.setEnlace(null); //borro en enlace de n (para evitar que queden doble enlazados)
            ret = ret.getEnlace(); //me muevo al sig nodo para seguir enganchando elementos
        } else { 
            //caso base: llegamos al ultimo nodo, actualizamos cabecera y la retornamos
            this.cabecera = n;
            ret = n;
        }
        return ret;
    }

    public void eliminarApariciones(Object elem){
        //Elimina las aparaciones del elem ingresado

        /*Lo primero que tengo que hacer es buscar una base(nodo) distinto del elem 
        para poder enganchar los siguientes*/
        if (this.cabecera != null){
            Nodo n1 = this.cabecera;
            while (n1.getElem().equals(elem)){
                n1 = n1.getEnlace();
                this.longitud--;
            }
            this.cabecera = n1;
            Nodo aux = n1; //nos paramos en el primer nodo distinto del elem ingresado 

            while(aux.getEnlace() != null){
                if(aux.getEnlace().getElem().equals(elem)){
                    n1 = aux.getEnlace().getEnlace();                   
                    aux.setEnlace(n1); //actualizamos enlace de cabecera
                    this.longitud--;
                }else{
                    aux = aux.getEnlace();
                }
            }
        }       
    }

    public void eliminarApariciones2(Object elem){
        if (this.cabecera != null){
            Nodo aux = this.cabecera, aux2; 
            while (aux.getElem().equals(elem)){
                aux = aux.getEnlace();
                this.longitud--;
            }

            if (aux != null){
                //armo punteros (1 adelantado)
                this.cabecera = aux;
                aux2 = aux.getEnlace(); 
                while (aux2 != null){
                    if (aux2.getElem().equals(elem)){
                       aux2 = aux2.getEnlace();  
                       aux.setEnlace(aux2);
                       this.longitud--;                   
                    } else {
                        aux = aux.getEnlace();
                        aux2 = aux2.getEnlace();
                    }
                }
            }            
        }         
    }

    public Lista obtenerMultiplos(int num){
        Lista list = new Lista();
        obtenerMultiplosAux(list, this.cabecera, num, 1);
        return list;
    }

    private void obtenerMultiplosAux(Lista l1, Nodo n, int num, int pos){
        if (n != null){
            if (pos % num == 0){  //si la posicion es multiplo del num ingresado
                if (l1.longitud+1 == 1){ //caso especial de insertar en pos 1 (como long es 0 (lista vacia) le sumo 1)
                    l1.cabecera = new Nodo(n.getElem(), null);
                } else { //insertar en la ultima pos
                    Nodo aux = l1.cabecera;
                    int i = 1;
                    while (i < l1.longitud){ //avanzo hasta el ult elem
                        aux = aux.getEnlace();
                        i++;
                    }
                    Nodo nuevo = new Nodo(n.getElem(), null); //como siempre inserto en la posicion longitud+1 el enlace es siempre null
                    aux.setEnlace(nuevo);
                }
                l1.longitud++; //incremento la longitud de la lista
            } 
            obtenerMultiplosAux(l1, n.getEnlace(), num, pos+1); //si no es multiplo busco en la sig posicion
        } 
    }
    public void agregarElem(Object elem, int x){
        if (x > 0){ //no hay saltos negativos ni de 0
            int pos, limite;
            Nodo next, aux; 
            aux = this.cabecera;  //guardo la cab en un auxiliar
            limite = this.longitud; //guardo el tamaño original para encontrar los multiplos sin que aumente la long
            
            this.cabecera = new Nodo(elem, this.cabecera); //guardo prim pos
            this.longitud++;

            for(pos = 1; pos <= limite; pos++){  
                if (pos % x == 0){
                    next = new Nodo(elem, aux.getEnlace()); //creo nuevo nodo con elem y lo enlazo al siguiente de aux
                    aux.setEnlace(next);
                    this.longitud++;
                    aux = aux.getEnlace(); //voy al nodo de elem
                } 
                aux = aux.getEnlace(); //voy al siguiente enlace
            }

        }
    }
    
}
