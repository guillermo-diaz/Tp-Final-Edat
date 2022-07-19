package estructuras;
/**
 * 
 * @author Guille
 */


public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL(){
        //constructor
        this.raiz = null;
    }

    public NodoAVL rotarIzquierda(NodoAVL r){
        NodoAVL h, temp;
        h =r.getDerecho();
        temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    public NodoAVL rotarDerecha(NodoAVL r){
        NodoAVL h, temp;

        h = r.getIzquierdo();
        temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);

        r.recalcularAltura();
        h.recalcularAltura();

        return h;
    }

    public boolean insertar(Comparable elem){
        //inserta un elem conservando el orden de el arbol 
        boolean verif = true;

        if (this.raiz == null){
            this.raiz = new NodoAVL(elem, null, null);
        } else {
            verif = insertarAux(this.raiz, elem, null);
        }
        return verif;
    }

    private boolean insertarAux(NodoAVL n, Comparable elem, NodoAVL padreAux){
        //metodo aux que inserta un elem en un arbol no vacio, devuelve false si el elem ya existe
        boolean flag = true;

        if (n != null){
            NodoAVL izq = n.getIzquierdo(), der = n.getDerecho(); 

            if (elem.compareTo(n.getElem()) == 0){ 
                flag = false; //error el elem ya existe en el arbol
                
            } else if (elem.compareTo(n.getElem()) > 0) { //si el elem es mayor que la raiz voy al HD
                if (der == null){ //si n no tiene HD inserto
                    n.setDerecho(new NodoAVL(elem, null, null));
                } else { //paso recursivo con subarbol der
                    flag = insertarAux(der, elem, n);
                }
            } else { //si el elem es menor que la raiz voy al HI
                if (izq == null){ //si n no tiene HI inserto
                    n.setIzquierdo(new NodoAVL(elem, null, null));
                } else { //Paso recursivo con subarbol izq
                    flag = insertarAux(izq, elem, n);
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

    public boolean eliminar(Comparable elem){
        //Recibe el elemento que se desea eliminar y se procede a removerlo del árbol
        return eliminarAux(this.raiz, elem, null);
    }
 
    private boolean eliminarAux(NodoAVL n, Comparable elem, NodoAVL padre){
        //elimina un elemento del arbol conservando el orden y balance del mismo
        boolean flag = false;

        if (n != null){
            NodoAVL izq, der;
            //hijos de nodo actual
            izq = n.getIzquierdo();
            der = n.getDerecho();
            Comparable aux = n.getElem(); //elem de nodo actual

            if (elem.compareTo(aux) == 0){ //elem encontrado
                flag = true; 
                
                //CASOS
                if (izq != null && der != null){ //Si el elem a eliminar tiene 2 hijos
                    Comparable candidato = menorEnSubarbolDer(der); //busco candidato a reemplazar a su padre
                    eliminarAux(der, candidato, n); //elimino candidato 
                    n.setElem(candidato); //reemplazo nodo actual con candidato
                    
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

    private void casoHoja(NodoAVL padre, Comparable aux){
        //Caso del metodo eliminar: eliminar el nodo que es hoja. 
        if (padre == null){ //caso especial arbol de 1 elem
            this.raiz = null;
        } else {
            if (aux.compareTo(padre.getElem()) < 0){ //si el hijo es menor cambio el HI
                padre.setIzquierdo(null);
            } else { //si el hijo es mayor cambio el HD
                padre.setDerecho(null);
            }
        }
    }

    private void caso1Hijo(NodoAVL izq, NodoAVL der, NodoAVL padre, Comparable aux){
        //Caso del metodo eliminar: eliminar el nodo que tiene solo 1 hijo
        if (padre != null){ 
            
            if (der == null){ //si solo tiene HI
                if (aux.compareTo(padre.getElem()) < 0){ // si aux es menor a su padre lo coloco como HI
                    padre.setIzquierdo(izq);
                } else { //aux es mayor q su padre, lo coloco como HD
                    padre.setDerecho(izq);
                }
            } else { //si solo tiene HD
                if (aux.compareTo(padre.getElem()) < 0){  //si aux es menor a su padre lo como como HI
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

    private Comparable menorEnSubarbolDer(NodoAVL n){
        //metodo aux que busca el mayor elem de un subarbol
        Comparable ret; 
        if (n.getIzquierdo() == null){
            ret = n.getElem();
        } else {
            ret = menorEnSubarbolDer(n.getIzquierdo());
        }
        return ret;
    }

    private int balance(NodoAVL n){
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
    
    
    private void balancear(int balance, NodoAVL n, NodoAVL padreAux){
        /*Metodo aux que balancea el nodo (n) con estos 4 casos
        balance: variable con el balance de n
        padreAux: es el padre de n, usaado para setear a su hijo desbalanceado una vez termine el proceso
        precondicion: n no es vacio y balance es 2 o -2
        */
        NodoAVL aux;

        if (balance < -1){ //si esta torcido a der
            int balanceHDer = balance(n.getDerecho());

            if (balanceHDer <= 0){ //si el HD esta torcido a la der
                n = rotarIzquierda(n); //lo tuerzo a la izq

                if (padreAux == null){ //caso especial el nodo a balancear es raiz
                    this.raiz = n;
                } else {
                    //seteo uno de los hijos de PadreAux
                    if (n.getElem().compareTo(padreAux.getElem()) > 0){
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
                    if (n.getElem().compareTo(padreAux.getElem()) > 0){
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

    public Lista listar(){ 
        //devuelve una lista ordenada con los elementos que se encuentran almacenados en él.
        Lista ls = new Lista();
        listarAux(this.raiz, ls);
        return ls;
    }

    private void listarAux(NodoAVL n, Lista ls){
        //inserta los elem del arbol de menor a mayor, recorre HD primero para mejorar einsertar en 1|
        if (n != null){ 
            listarAux(n.getDerecho(), ls);
            ls.insertar(n.getElem(), 1);
            listarAux(n.getIzquierdo(), ls);
        }
    }

    public Lista listarRango(Comparable ext1, Comparable ext2){
        //devuelve una lista ordenada con los elementos que se encuentran en el intervalo [elemMinimo, elemMaximo].
        Lista ls = new Lista();
        listarRangoAux(this.raiz, ext1, ext2, ls);
        return ls;
    }

    private void listarRangoAux(NodoAVL n, Comparable ext1, Comparable ext2, Lista ls){
        //inserta los elem > ext1 y < ext2. Hago recorrido en inorden inverso (der, raiz, izq) para inserta en 1
        if (n != null){
            Comparable aux = n.getElem(); //elem de la raiz

            if (aux.compareTo(ext2) < 0){ //si aux < ext2 me muevo al subarbol der
                listarRangoAux(n.getDerecho(), ext1, ext2, ls); 
            } 
            if (aux.compareTo(ext1) >= 0 && aux.compareTo(ext2) <= 0) {
                ls.insertar(aux, 1);
            }
            if (aux.compareTo(ext1) > 0){ //si aux > ext2 me muevo al subarbol izq
                listarRangoAux(n.getIzquierdo(), ext1, ext2, ls);
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

    public boolean pertenece(Comparable elem){
        // Devuelve verdadero si el elem está en el árbol y falso en caso contrario
        return perteneceAux(this.raiz, elem);
    }

    private boolean perteneceAux(NodoAVL n, Comparable elem){
        //metodo aux que verifica si un elemento esta en el arbol
        boolean flag; 

        if (n != null){
            if (elem.compareTo(n.getElem()) == 0){ //si es igual encontramos elem
                flag = true;
            } else if (elem.compareTo(n.getElem()) < 0) { //Si elem es menor a la raiz busco en subArbol izq
                flag = perteneceAux(n.getIzquierdo(), elem);
            } else { //Si elem es mayor a la raiz busco en subArbol der
                flag = perteneceAux(n.getDerecho(), elem);
                
            }
        } else {
            flag = false;
        }

        return flag;
    }

    public Comparable minimoElem(){
        //recorre la rama correspondiente y devuelve el elemento más pequeño almacenado en el árbol.
        return minimoAux(this.raiz);
    }

    private Comparable minimoAux(NodoAVL n){
        //busca el menor elem en el arbol
        Comparable ret; 

        if (n != null){
            if (n.getIzquierdo() == null){ //si es hoja ret el elem 
                ret = n.getElem();
            } else {
                ret = minimoAux(n.getIzquierdo()); //busco en su HI 
            }
        } else {
            ret = null;
        }
        

        return ret;
    }

    public Comparable maximoElem(){ 
        // recorre la rama correspondiente y devuelve el elemento más pequeño almacenado en el árbol
        return maximoAux(this.raiz);
    }

    private Comparable maximoAux(NodoAVL n){
        //busca el mayor elem en el arbol buscando en los subarboles der recursivamente
        Comparable ret; 

        if (n != null){ //si no es vacio
            if (n.getDerecho() == null){ //si es hoja ret el elem 
                ret = n.getElem();
            } else {
                ret = minimoAux(n.getDerecho()); //busco en su HI 
            }
        } else {
            ret = null;
        }
        
        return ret;
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

    private String toStringAux(NodoAVL n){
        String cad = "";

        if (n != null){
            //cad = cad +"("+ n.getElem() + ") Alt:"+n.getAltura()+" ->  "; 
            cad = cad +"("+ n.getElem() + ") ->  ";
            NodoAVL izq, der;
            izq = n.getIzquierdo(); 
            der = n.getDerecho();
            
            if (izq != null){ //si no es nulo imprimo el elem izq
                cad = cad + "HI: " + izq.getElem() + "    ";
            } else {
                cad = cad + "HI: -    ";
            }
            if (der != null){ //si no es nulo imprimo el elem der
                cad = cad + "HD: " + der.getElem() + "\n";
            } else {
                cad = cad + "HD: -\n";
            }
            cad = cad + toStringAux(izq); //voy al hijo izq para seguir imprimiendo
            cad = cad + toStringAux(der); //voy al hijo der para seguir imprimiendo
        }
        return cad;
    }

    public ArbolAVL clone(){
        //crea un clon del arbol actual
        ArbolAVL clon = new ArbolAVL();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoAVL cloneAux(NodoAVL n){
        //metodo aux que copia los nodos y enlaces del arbol
        NodoAVL ret;

        if (n != null){
            ret = new NodoAVL(n.getElem(), cloneAux(n.getIzquierdo()), cloneAux(n.getDerecho()));
        } else {
            ret = null;
        }

        return ret;
    }
}
