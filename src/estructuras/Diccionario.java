package estructuras;
/**
 * 
 * @author Guille
 */
//diccionario con avl
public class Diccionario {
    private NodoAVLDicc raiz;

    public Diccionario(){
        //constructor
        this.raiz = null;
    }

    public NodoAVLDicc rotarIzquierda(NodoAVLDicc r){
        NodoAVLDicc h, temp;
        h =r.getDerecho();
        temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    public NodoAVLDicc rotarDerecha(NodoAVLDicc r){
        NodoAVLDicc h, temp;

        h = r.getIzquierdo();
        temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    public boolean insertar(Comparable clave, Object dato){
        //inserta un elem conservando el orden de el arbol 
        boolean verif = true;

        if (this.raiz == null){
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
        } else {
            verif = insertarAux(this.raiz, clave, dato, null);
        }
        return verif;
    }

    private boolean insertarAux(NodoAVLDicc n, Comparable elem, Object dato, NodoAVLDicc padreAux){
        //metodo aux que inserta un elem en un arbol no vacio, devuelve false si el elem ya existe
        boolean flag = true;

        if (n != null){
            NodoAVLDicc izq = n.getIzquierdo(), der = n.getDerecho(); 

            if (elem.compareTo(n.getClave()) == 0){ 
                flag = false; //error el elem ya existe en el arbol
                
            } else if (elem.compareTo(n.getClave()) > 0) { //si el elem es mayor que la raiz voy al HD
                if (der == null){ //si n no tiene HD inserto
                    n.setDerecho(new NodoAVLDicc(elem, dato, null, null));
                } else { //paso recursivo con subarbol der
                    flag = insertarAux(der, elem, dato, n);
                }
            } else { //si el elem es menor que la raiz voy al HI
                if (izq == null){ //si n no tiene HI inserto
                    n.setIzquierdo(new NodoAVLDicc(elem, dato, null, null));
                } else { //Paso recursivo con subarbol izq
                    flag = insertarAux(izq, elem, dato, n);
                }
            }

            if (flag){ //si fue insertado verifico si hay desbalance
                n.recalcularAltura();
                int balance = balance(n); //veo el balance de n

                if (balance < -1 || balance > 1){ //si esta desbalanceado
                    balancear(balance, n, padreAux);
                    n.recalcularAltura();
                } 
            }                     
        }   
        return flag;
    }

    public boolean eliminar(Comparable clave){
        //Recibe el elemento que se desea eliminar y se procede a removerlo del árbol
        return eliminarAux(this.raiz, clave, null);
    }
 
    private boolean eliminarAux(NodoAVLDicc n, Comparable elem, NodoAVLDicc padre){
        //elimina un elemento del arbol conservando el orden y balance del mismo
        boolean flag = false;

        if (n != null){
            NodoAVLDicc izq, der;
            //hijos de nodo actual
            izq = n.getIzquierdo();
            der = n.getDerecho();
            Comparable aux = n.getClave(); //elem de nodo actual

            if (elem.compareTo(aux) == 0){ //elem encontrado
                flag = true; 
                
                //CASOS
                if (izq != null && der != null){ //Si el elem a eliminar tiene 2 hijos
                    NodoAVLDicc candidato = menorEnSubarbolDer(der); //busco candidato a reemplazar a su padre
                    Comparable claveCand = candidato.getClave();
                    Object datoCand = candidato.getDato();
                    eliminarAux(der, claveCand, n); //elimino candidato 

                    //reemplazo nodo actual con los datos de candidato
                    n.setClave(claveCand); 
                    n.setDato(datoCand);
                    
                } else if (izq == null && der == null){ //Si el elem a eliminar es hoja
                    casoHoja(padre, aux);

                } else { //Si el elem a eliminar tiene solo 1 hijo
                    caso1Hijo(izq, der, padre, aux);
                }

            } else if (elem.compareTo(aux) < 0) { //si es menor busco a la izq
                flag = eliminarAux(n.getIzquierdo(), elem, n);
            } else { //si es mayor busco a la der
                flag = eliminarAux(n.getDerecho(), elem, n);  
            }

            if (flag){ //si se logro eliminar verifico balance de nodo actual
                n.recalcularAltura();
                int balance = balance(n); //veo el balance de n
                if (balance < -1 || balance > 1){ //si esta desbalanceado
                    balancear(balance, n, padre);
                    n.recalcularAltura();
                } 
            } 

        }
        return flag;
    }

    private void casoHoja(NodoAVLDicc padre, Comparable aux){
        //Caso del metodo eliminar: eliminar el nodo que es hoja. 
        if (padre == null){ //caso especial arbol de 1 elem
            this.raiz = null;
        } else {
            if (aux.compareTo(padre.getClave()) < 0){ //si el hijo es menor cambio el HI
                padre.setIzquierdo(null);
            } else { //si el hijo es mayor cambio el HD
                padre.setDerecho(null);
            }
        }
    }

    private void caso1Hijo(NodoAVLDicc izq, NodoAVLDicc der, NodoAVLDicc padre, Comparable aux){
        //Caso del metodo eliminar: eliminar el nodo que tiene solo 1 hijo
        if (padre != null){ 
            
            if (der == null){ //si solo tiene HI
                if (aux.compareTo(padre.getClave()) < 0){ // si aux es menor a su padre lo coloco como HI
                    padre.setIzquierdo(izq);
                } else { //aux es mayor q su padre, lo coloco como HD
                    padre.setDerecho(izq);
                }
            } else { //si solo tiene HD
                if (aux.compareTo(padre.getClave()) < 0){  //si aux es menor a su padre lo como como HI
                    padre.setIzquierdo(der);
                } else {
                    padre.setDerecho(der);
                }
            }
        } else { //caso especial: si el elem es raiz lo reemplazo por su hijo
            if (izq == null){
                this.raiz = izq;
            } else {
                this.raiz = der;
            }
        }
    }

    private NodoAVLDicc menorEnSubarbolDer(NodoAVLDicc n){
        //metodo aux que busca el mayor elem de un subarbol
        //PC: n no debe ser vacio
        NodoAVLDicc ret; 
        if (n.getIzquierdo() == null){
            ret = n;
        } else {
            ret = menorEnSubarbolDer(n.getIzquierdo());
        }
        return ret;
    }

    private int balance(NodoAVLDicc n){
        //modulo que calcula el balance de un nodoAVL
        int izq = -1, der = -1; //altura de null es -1
    
        if (n.getIzquierdo() != null){
            izq = n.getIzquierdo().getAltura();
        } 
        if (n.getDerecho() != null){
            der = n.getDerecho().getAltura();
        }

        return izq - der; 
    }
    
    
    private void balancear(int balance, NodoAVLDicc n, NodoAVLDicc padreAux){
        /*Metodo aux que balancea el nodo (n) con estos 4 casos
        balance: variable con el balance de n
        padreAux: es el padre de n, usaado para setear a su hijo desbalanceado una vez termine el proceso
        precondicion: n no es vacio y balance es 2 o -2
        */
        NodoAVLDicc aux;

        if (balance < -1){ //si esta torcido a der
            int balanceHDer = balance(n.getDerecho());

            if (balanceHDer <= 0){ //si el HD esta torcido a la der
                n = rotarIzquierda(n); //lo tuerzo a la izq

                if (padreAux == null){ //caso especial el nodo a balancear es raiz
                    this.raiz = n;
                } else {
                    //seteo uno de los hijos de PadreAux
                    if (n.getClave().compareTo(padreAux.getClave()) > 0){
                        padreAux.setDerecho(n);
                    } else {
                        padreAux.setIzquierdo(n);
                    }
                    padreAux.recalcularAltura();
                }
                
            } else { //el HD esta torcido a la izq
                aux = rotarDerecha(n.getDerecho()); //lo tuerzo al mismo lado q el padre
                n.setDerecho(aux);

                balancear(balance, n, padreAux); //reutilizo el metodo para balancear al padre(n)
            }
        } else { //si esta torcido a izq
            int balanceHIzq = balance(n.getIzquierdo());
            if (balanceHIzq >= 0){ //si HI esta torcido a la izq
                n = rotarDerecha(n);
 
                if (padreAux == null){
                    this.raiz = n;
                } else {
                    //seteo uno de los hijos de PadreAux
                    if (n.getClave().compareTo(padreAux.getClave()) > 0){
                        padreAux.setDerecho(n);
                    } else {
                        padreAux.setIzquierdo(n);
                    }
                    padreAux.recalcularAltura();
                }
                
            } else { //Si HI esta torcido a la der
                aux = rotarIzquierda(n.getIzquierdo()); //lo tuerzo al mismo lado q el padre
                n.setIzquierdo(aux); 

                balancear(balance, n, padreAux); //reutilizo el metodo para balancear al padre(n)
            }
            
        }
    }

    public boolean esVacio(){
        //verifica si el arbol está vacio
        return this.raiz == null;
    }

    public void vaciar(){
        //vacia el arbol
        this.raiz = null;
    }

    public boolean existeClave(Comparable elem){
        // Devuelve verdadero si el elem está en el árbol y falso en caso contrario
        return existeClaveAux(this.raiz, elem);
    }

    private boolean existeClaveAux(NodoAVLDicc n, Comparable elem){
        //metodo aux que verifica si un elemento esta en el arbol
        boolean flag; 

        if (n != null){
            if (elem.compareTo(n.getClave()) == 0){ //si es igual encontramos elem
                flag = true;

            } else if (elem.compareTo(n.getClave()) < 0) { //Si elem es menor a la raiz busco en subArbol izq
                flag = existeClaveAux(n.getIzquierdo(), elem);
            } else { //Si elem es mayor a la raiz busco en subArbol der
                flag = existeClaveAux(n.getDerecho(), elem);
                
            }
        } else {
            flag = false;
        }

        return flag;
    }

    public Object obtenerInformacion(Comparable clave){
        return obtenerInformacionAux(this.raiz, clave);
    }

    private Object obtenerInformacionAux(NodoAVLDicc n, Comparable elem){
        //metodo aux que verifica si un elemento esta en el arbol
        Object ret;

        if (n != null){
            if (elem.compareTo(n.getClave()) == 0){ //si es igual encontramos elem
                
                ret = n.getDato();
                System.out.println("elem "+n.getDato());
            } else if (elem.compareTo(n.getClave()) < 0) { //Si elem es menor a la raiz busco en subArbol izq
                ret = obtenerInformacionAux(n.getIzquierdo(), elem);
            } else { //Si elem es mayor a la raiz busco en subArbol der
                ret = obtenerInformacionAux(n.getDerecho(), elem);
                
            }
        } else {
            ret = null;
        }

        return ret;
    }

    public Lista listarClaves(){
        // devuelve una lista ordenada con las claves de los elementos del arbol
        Lista ls = new Lista();
        listarClavesAux(this.raiz, ls);
        return ls;
    }

    private void listarClavesAux(NodoAVLDicc n, Lista ls){
        //Lista las claves del arbol en orden. Recorre el arbol en inorden inverso para insertar en pos 1
        if (n != null){
            listarClavesAux(n.getDerecho(), ls);
            ls.insertar(n.getClave(), 1);
            listarClavesAux(n.getIzquierdo(), ls);
            
        }
    }

    public Lista listarDatos(){
        //devuelve una lista ordenada con la info de los elementos del arbol
        Lista ls = new Lista();
        listarDatosAux(this.raiz, ls);
        return ls;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista ls){
        //Lista los datos del arbol en orden. Recorre el arbol en inorden inverso para insertar en pos 1
        if (n != null){
            listarClavesAux(n.getDerecho(), ls);
            ls.insertar(n.getDato(), 1);
            listarClavesAux(n.getIzquierdo(), ls);
        }
        
    }

    public String toString(){
        //devuelve un String con la informacion del arbol actual
        String cad; 

        if (this.raiz == null){
            cad = "Arbol Vacio";
        } else {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoAVLDicc n){
        String cad = "";

        if (n != null){
            cad = cad +"("+ n.getClave() + ") Alt:"+n.getAltura()+" ->  "; 
            //cad = cad +"("+ n.getClave() + ") ->  ";
            NodoAVLDicc izq, der;
            izq = n.getIzquierdo(); 
            der = n.getDerecho();
            
            if (izq != null){ //si no es nulo imprimo el elem izq
                cad = cad + "HI: " + izq.getClave() + "    ";
            } else {
                cad = cad + "HI: -    ";
            }
            if (der != null){ //si no es nulo imprimo el elem der
                cad = cad + "HD: " + der.getClave()+ "\n";
            } else {
                cad = cad + "HD: -\n";
            }
            cad = cad + toStringAux(izq); //voy al hijo izq para seguir imprimiendo
            cad = cad + toStringAux(der); //voy al hijo der para seguir imprimiendo
        }
        return cad;
    }

    public Diccionario clone(){
        //crea un clon del arbol actual
        Diccionario clon = new Diccionario();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVLDicc cloneAux(NodoAVLDicc n){
        //metodo aux que copia los nodos y enlaces del arbol
        NodoAVLDicc ret;

        if (n != null){
            ret = new NodoAVLDicc(n.getClave(), n.getDato(), cloneAux(n.getIzquierdo()), cloneAux(n.getDerecho()));
        } else {
            ret = null;
        }

        return ret;
    }
}
