package estructuras;

public class GrafoEtiquetado {
    //Grafo normal etiquetado 
    private NodoVert inicio;

    public GrafoEtiquetado(){
        this.inicio = null;
    }

    public boolean insertarVertice(Object elem){
        boolean flag = false;
        NodoVert aux = this.ubicarVertice(elem);

        if (aux == null){
            flag = true;
            this.inicio = new NodoVert(elem, this.inicio, null);
        } 
        
        return flag;
    }

    public boolean eliminarVertice(Object elem){
        boolean flag = false;
        NodoVert aux = this.inicio, anterior = null;

        while (aux != null && !aux.getElem().equals(elem)){
            anterior = aux;
            aux = aux.getSigVertice();
        }

        if (aux != null){
            NodoAdy ady = aux.getPrimerAdy();
            while (ady != null){
                eliminarArcoAux(ady.getVertice(), elem);
                ady = ady.getSigAdyacente();
            }
            if (anterior == null){ //caso especial el nodo a eliminar esta en el inicio de la lista de nodos
                this.inicio = this.inicio.getSigVertice();
            } else {
                anterior.setSigVertice(aux.getSigVertice()); 
            }
        }
        return flag;
    }

    private boolean eliminarArcoAux(NodoVert n, Object elem){
        //elimina un arco hacia el nodo elem, retorna un boolean para verificar si fue encontrado
        NodoAdy anterior = null, aux = n.getPrimerAdy();
        boolean flag = false;

        while(!flag && aux != null){
            if (elem.equals(aux.getVertice().getElem())){
                flag = true;
            } else {
                anterior = aux;
                aux = aux.getSigAdyacente();
            }
        } 
        if (flag){
            if (anterior == null){
                n.setPrimerAdy(aux.getSigAdyacente());
            } else {
                anterior.setSigAdyacente(aux.getSigAdyacente());
            }
        }
        return flag;
    }

    private NodoVert ubicarVertice(Object buscado){
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)){
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(Object origen, Object destino, int etiqueta){
        boolean flag = false;

        NodoVert aux, auxOrig, auxDest;
        aux = this.inicio; 
        auxOrig = null;
        auxDest = null;

        while (aux != null && (auxOrig == null || auxDest == null)){ //busco a los 2 nodos
            if (aux.getElem().equals(origen)){
                auxOrig = aux;
            } 
            if (aux.getElem().equals(destino)){
                auxDest = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxOrig != null && auxDest != null){
            flag = true;
            conectarAdy(auxOrig, auxDest, etiqueta);
            conectarAdy(auxDest, auxOrig, etiqueta);
        }

        return flag;
    }

    public boolean eliminarArco(Object origen, Object destino){
        boolean flag = false; 
        NodoVert auxOrig = ubicarVertice(origen);
        
        if (auxOrig != null){ //si el origen fue encontrado, busco en sus adyacentes a el destino
            NodoVert auxDest = null;
            NodoAdy anterior = null;
            NodoAdy dest = auxOrig.getPrimerAdy();

            while (!flag && dest != null){
                if (dest.getVertice().getElem().equals(destino)){
                    flag = true;
                    auxDest = dest.getVertice();
                } else {
                    anterior = dest;
                    dest = dest.getSigAdyacente();
                }
            }

            if (flag){ //si estan conectados
                //desconecto desde el origen primero
                if (anterior == null){ //caso especial es el primer ady
                    auxOrig.setPrimerAdy(dest.getSigAdyacente());
                } else {
                    anterior.setSigAdyacente(dest.getSigAdyacente());
                }

                //desconecto desde el destino
                eliminarArcoAux(auxDest, origen);
            }
        }
        return flag;
    }

    private void conectarAdy(NodoVert n, NodoVert adyacente, int etiqueta){
        //precondicion de este metodo: no deben ser nulos
        NodoAdy nuevo = new NodoAdy(adyacente, n.getPrimerAdy(), etiqueta);
        n.setPrimerAdy(nuevo);
    }

    public boolean existeVertice(Object elem){
        boolean flag;
        if (this.ubicarVertice(elem) == null){
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean existeArco(Object origen, Object destino){
        boolean flag = false;
        NodoVert auxO = this.ubicarVertice(origen);

        if (auxO != null){
            NodoAdy ady = auxO.getPrimerAdy();
            while (ady != null){
                if (destino.equals(ady.getVertice().getElem())){
                    flag = true;
                } else {
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return flag;
    }

    public boolean existeCamino(Object origen, Object destino){
        boolean flag = false;

        NodoVert aux, auxOrig, auxDest;
        aux = this.inicio; 
        auxOrig = null;
        auxDest = null;

        while (aux != null && (auxOrig == null || auxDest == null)){ //busco a los 2 nodos
            if (aux.getElem().equals(origen)){
                auxOrig = aux;
            } 
            if (aux.getElem().equals(destino)){
                auxDest = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxOrig != null && auxDest != null){ //si ambos fueron encontrados, busco un camino
            Lista visitados = new Lista();
            flag = existeCaminoAux(auxOrig, destino, visitados);

        }
        return flag;
    }

    public boolean existeCaminoAux(NodoVert n, Object dest, Lista vis){
        boolean flag = false;

        if (n != null){
            if (n.getElem().equals(dest)){ //camino encontrado
                flag = true;
            } else {
                //si no es el camino, verifico si hay camino entre n y dest
                vis.insertar(n.getElem(), vis.longitud()+1);
                NodoAdy ady = n.getPrimerAdy();

                while (!flag && ady != null){
                    if (vis.localizar(ady.getVertice().getElem()) < 0){
                        flag = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return flag;
    }

    public Lista caminoMasCorto(Object origen, Object destino){
        Lista camMin = new Lista();

        NodoVert aux, auxOrig, auxDest;
        aux = this.inicio; 
        auxOrig = null;
        auxDest = null;

        while (aux != null && (auxOrig == null || auxDest == null)){ //busco a los 2 nodos
            if (aux.getElem().equals(origen)){
                auxOrig = aux;
            } 
            if (aux.getElem().equals(destino)){
                auxDest = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxOrig != null && auxDest != null){ //si ambos fueron encontrados, busco un camino
            Lista visitados = new Lista(), camActual = new Lista();
            camMin = caminoCortoAux(auxOrig, destino, camMin, camActual, visitados);

        }
        return camMin;
    }

    private Lista caminoCortoAux(NodoVert n, Object destino, Lista camMin, Lista camActual, Lista visitados){

        if (n != null){
            Object elem = n.getElem();

            visitados.insertar(elem, visitados.longitud()+1); //lo pongo en visitados
            camActual.insertar(elem, camActual.longitud()+1);

            if (elem.equals(destino)){ //si llego al destino
                if (camMin.esVacia() || (camMin.longitud() > camActual.longitud())){ //si encontró un camino menor, lo cambio
                    camMin = camActual.clone(); //retorno un clon para no modificar la referencia
                }
                
            } else {
                NodoAdy ady = n.getPrimerAdy();

                while (ady != null){ //paso recursivo con sus adyacentes 
                    if (visitados.localizar(ady.getVertice().getElem()) < 0){
                        camMin = caminoCortoAux(ady.getVertice(), destino, camMin, camActual, visitados); 
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            camActual.eliminar(camActual.longitud()); //ya lo visite, lo elimino del camino
            visitados.eliminar(visitados.longitud()); //lo saco de visitados ya que puede haber mas caminos que visiten este nodo
        }

        return camMin;
    }

    public Lista caminoMasLargo(Object origen, Object destino){
        Lista camMax = new Lista();

        NodoVert aux, auxOrig, auxDest;
        aux = this.inicio; 
        auxOrig = null;
        auxDest = null;

        while (aux != null && (auxOrig == null || auxDest == null)){ //busco a los 2 nodos
            if (aux.getElem().equals(origen)){
                auxOrig = aux;
            } 
            if (aux.getElem().equals(destino)){
                auxDest = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxOrig != null && auxDest != null){ //si ambos fueron encontrados, busco un camino
            Lista visitados = new Lista(), camActual = new Lista();
            camMax = caminoLargoAux(auxOrig, destino, camMax, camActual, visitados);

        }
        return camMax;
    }

    private Lista caminoLargoAux(NodoVert n, Object destino, Lista camMax, Lista camActual, Lista visitados){

        if (n != null){
            
            Object elem = n.getElem();
            System.out.println("ELEM: "+elem);
            visitados.insertar(elem, visitados.longitud()+1); //lo pongo en visitados
            camActual.insertar(elem, camActual.longitud()+1);

            if (elem.equals(destino)){ //si llego al destino

                if (camMax.longitud() < camActual.longitud()){ //si encontró un camino menor, lo cambio
                    camMax = camActual.clone(); //retorno un clon para no modificar la referencia
                }
                
            } else {
                NodoAdy ady = n.getPrimerAdy();

                while (ady != null){ //paso recursivo con sus adyacentes 
                    if (visitados.localizar(ady.getVertice().getElem()) < 0){
                        camMax = caminoLargoAux(ady.getVertice(), destino, camMax, camActual, visitados); 
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            camActual.eliminar(camActual.longitud()); //ya lo visite, lo elimino del camino
            visitados.eliminar(visitados.longitud()); //lo saco de visitados ya que puede haber mas caminos que visiten este nodo
        }

        return camMax;
    }

    public Lista caminoMenosPeso(Object origen, Object destino){
        Lista camMin = new Lista();

        NodoVert aux, auxOrig, auxDest;
        aux = this.inicio; 
        auxOrig = null;
        auxDest = null;

        while (aux != null && (auxOrig == null || auxDest == null)){ //busco a los 2 nodos
            if (aux.getElem().equals(origen)){
                auxOrig = aux;
            } 
            if (aux.getElem().equals(destino)){
                auxDest = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxOrig != null && auxDest != null){ //si ambos fueron encontrados, busco un camino
            Lista visitados = new Lista(), camActual = new Lista();
            int[] pesoMenor = new int[1];
            pesoMenor[0] = 0; //creo un arreglo para tener referencia del peso del camino
            camMin = caminoMenosPesoAux(auxOrig, destino, 0, pesoMenor, camActual, camMin, visitados);

        }
        return camMin;
    }

    private Lista caminoMenosPesoAux(NodoVert n, Object destino, int pesoAux, int[] pesoMenor, Lista camMin, Lista camActual, Lista visitados){
        
        if (n != null){
            Object elem = n.getElem();

            visitados.insertar(elem, visitados.longitud()+1); //lo pongo en visitados
            camActual.insertar(elem, camActual.longitud()+1);

            if (elem.equals(destino)){ //si llego al destino
                int peso = pesoMenor[0]; //peso del camMin
                if (peso == 0  || peso > pesoAux){ //si encontró un camino de menor peso, lo cambio
                    pesoMenor[0] = pesoAux;
                    camMin = camActual.clone();
                }
                
            } else {
                NodoAdy ady = n.getPrimerAdy();

                while (ady != null){ //paso recursivo con sus adyacentes 
                    if (visitados.localizar(ady.getVertice().getElem()) < 0){
                        int nuevo = pesoAux + ady.getEtiqueta(); //sumo el peso del arco
                        camMin = caminoMenosPesoAux(ady.getVertice(), destino, nuevo, pesoMenor, camMin, camActual, visitados);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            camActual.eliminar(camActual.longitud()); //ya lo visite, lo elimino del camino
            visitados.eliminar(visitados.longitud()); //lo saco de visitados ya que puede haber mas caminos que visiten este nodo
        }

        return camMin;
    }

    public Lista listarEnProfundidad(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        
        while (aux != null){ 
            if (visitados.localizar(aux.getElem()) < 0){ //si el vertice no fue visitado
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista visitados){
        if (n != null){
            visitados.insertar(n.getElem(), visitados.longitud()+1); //lo visita
            NodoAdy ady = n.getPrimerAdy(); 

            while (ady != null){ //visita a los adyacentes que no fueron visitados
                if (visitados.localizar(ady.getVertice().getElem()) < 0){
                    listarEnProfundidadAux(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        
        while (aux != null){ 
            if (visitados.localizar(aux.getElem()) < 0){ //si el vertice no fue visitado
                anchuraDesde(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        
        return visitados;
    }

    private void anchuraDesde(NodoVert vertInicio, Lista vis){
        NodoVert aux;
        NodoAdy ady;
        Cola q = new Cola();
        vis.insertar(vertInicio.getElem(), vis.longitud()+1);
        q.poner(vertInicio);

        while (!q.esVacia()){
            aux = (NodoVert) q.obtenerFrente();
            q.sacar();
            ady = aux.getPrimerAdy();
            
            while (ady != null){
                Object elem = ady.getVertice().getElem();
                if (vis.localizar(elem) < 0){ //si no esta en visitados
                    vis.insertar(elem, vis.longitud()+1); //lo visito y luego lo recorro por anchura
                    q.poner(ady.getVertice());
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public boolean esVacio(){
        return this.inicio == null;
    }

    public void vaciar(){
        this.inicio = null;
    }
    
    public String toString(){
        String cad;

        if (this.inicio == null){
            cad = "Grafo vacio";
        } else {
            cad = toStringAux(this.inicio);
        }
        return cad;
    }

    private String toStringAux(NodoVert n){
        /*retorna un string con el grafo
        precondicion: n no debe ser vacio
        */
        String ret = "";

        
        ret = "+ ("+n.getElem()+"): \n       ";
        NodoAdy ady = n.getPrimerAdy();

        if (ady != null){
            while (ady != null){
                ret = ret + "->  "+ady.getVertice().getElem()+": "+ady.getEtiqueta()+"\n       ";
                ady = ady.getSigAdyacente();
                    
            }
        } else {
            ret = ret + "------ \n";
        }
        
        if (n.getSigVertice() != null){
            ret = ret + "\n "+toStringAux(n.getSigVertice());
        }

        return ret;
    }
    
}

