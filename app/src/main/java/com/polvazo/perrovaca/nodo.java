package com.polvazo.perrovaca;

/**
 * Created by USUARIO on 02/10/2017.
 */

public class nodo {

    nodo padre;
    nodo hijo;
    nodo siguiente;
    boolean visitado;
    int cordX, cordY;
    int costoF, costoG, costoH;


    //Constructor Nodo
    public nodo(int x, int y) {
        this.cordX = x;
        this.cordY = y;
        visitado = false;
        padre = null;
    }

    //Metodo para asignar al nodo Padre
    public void asignarPadre(nodo padre) {
        this.padre = padre;
    }
}

