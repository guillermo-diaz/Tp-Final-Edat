/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;
/**
 *
 * @author Guille
 */
public class Nodo {
    private Object elem;
    private Nodo enlace;
    
    //constructor
    public Nodo(Object elem, Nodo enlace){
        this.elem = elem;
        this.enlace = enlace;
    }
    //obeservadores
    public Object getElem() {
        return elem;
    }
    public Nodo getEnlace(){
        return enlace;
    }
    
    //modificadoras
    public void setElem(Object elem) {
        this.elem = elem;
    }
    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

    /*testing
    public String toString(){
        String el, enl;

        if (this.elem == null){
            el = "-";
        } else {
            el = this.elem.toString();
        }
        if (this.enlace == null){
            enl = ".";
        } else {
            enl = this.enlace.getElem().toString();
        }
        return "[ "+el+", "+enl+" ]";
    }
    */
}

