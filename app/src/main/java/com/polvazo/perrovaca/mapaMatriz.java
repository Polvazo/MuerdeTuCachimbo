package com.polvazo.perrovaca;

public class mapaMatriz {

    int[][] matriz;
    int cordX, cordY;
    int tamanio;
    nodo puntoInicio, puntoMeta;
    lista abierta;
    lista cerrados;
    lista camino;

    ///////CONSTRUCTOR//////////////////////////////////////////////////////////
    public mapaMatriz(int tamanio) {
        abierta = new lista("abierta");
        cerrados = new lista("cerrada");
        camino = new lista("camino");

        this.tamanio = tamanio;
        matriz = new int[tamanio][tamanio];
        inicializaMatriz();

        puntoInicio = null;
        puntoMeta = null;

    }

    ///////METODO PARA ESTABLECER LAS PAREDES EL EL LABERINTO///////////////////
    public void setParedes(int x, int y) {
        this.matriz[x][y] = 1;
    }

    //////METODO PARA IMPRIMIR LA MATRIZ////////////////////////////////////////
    public void imprimeMatriz() {
        System.out.println(" ");
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                System.out.print(" " + matriz[i][j]);
            }
            System.out.println(" ");
        }
    }

    ///////METODO PARA ESTABLECER EL INICIO Y LA META///////////////////////////
    public int setReferencias(int x, int y) {
        if (puntoInicio == null) {
            puntoInicio = new nodo(x, y);
            matriz[x][y] = 3;
            return 0;
        } else if (puntoMeta == null) {
            puntoMeta = new nodo(x, y);
            matriz[x][y] = 3;
            return 0;
        } else {
            return 1;
        }
    }

    ///////METODO QUE INICIALIZA LOS VALORES DE LA MATRIZ///////////////////////
    public void inicializaMatriz() {
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    //////METODO QUE EMPIEZA EL ALGORITMO Y REGRESA EL CAMINO FINAL/////////////
    public lista getCamino() {
        iniciaAlgoritmo(puntoInicio);

        nodo extra2 = camino.primero;
        ;
        lista nueva = new lista("nueva");

        while (extra2 != null) {
            extra2.siguiente = null;
            nueva.agregar(extra2);
            extra2 = extra2.padre;
        }
        nueva.eliminar(puntoMeta);

        return nueva;
    }///////////////////////////////////////////////////////////////////////////

    ///////METODO QUE REALIZA EL ALGORITMO (EN FORMA GENERAL)///////////////////
    public void iniciaAlgoritmo(nodo inicial) {
        abierta.agregar(inicial);
        nodo actual = abierta.eliminaCostoMenor();


        while (!estaEnAbiertos(puntoMeta)) {
            nodo extra = actual;
            extra.siguiente = null;
            cerrados.agregar(extra);

            //si el actual es igual al destino
            if (((actual.cordX == puntoMeta.cordX) && (actual.cordY == puntoMeta.cordY)))//|| abierta.esVacia() )
                break;//terminamos

            else {
                //obtengo adyacentes
                int x = 0, y = 0, w = 0, w2 = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        //verificamos que no sea el de referencia
                        if (i == 1 && j == 1) {
                            continue;
                        }
                        //valores de i
                        if (i == 0)
                            w = -1;
                        else if (i == 1)
                            w = 0;
                        else if (i == 2)
                            w = 1;

                        //valores de j
                        if (j == 0)
                            w2 = -1;
                        else if (j == 1)
                            w2 = 0;
                        else if (j == 2)
                            w2 = 1;

                        //asignamos los x y y correspondientes
                        x = actual.cordX + w;
                        y = actual.cordY + w2;

                        //los asignamos a un nodo el cual es el adyacente
                        nodo adyacente = new nodo(x, y);
                        // si no es transitable o si estan el la lista cerrada
                        if ((!((x >= 0) && (y >= 0) && (x < tamanio) && (y < tamanio) && (matriz[x][y] != 1))) || estaEnCerrados(adyacente)) {
                            continue;//lo ignoro
                        }

                        //consideramos los casos para avance diagonal donde rosa
                        nodo extra3;
                        if ((adyacente.cordX != actual.cordX) && (adyacente.cordY != actual.cordY)) {
                            ////QUITANDO EL CONTINUE Y DESCOMENTANDO LO DE ABAJO ACEPTAMOS CAMINOS DIAGONALES///////////////////////////
                            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            continue;
                            /*
                            if( !(adyacente.cordX-1 < 0) ){// -1 0
                                extra3 = new nodo( adyacente.cordX-1, adyacente.cordY );
                                if( ( matriz[adyacente.cordX-1][adyacente.cordY] == 1 ) && distancia( extra3, actual ) ==10 ){
                                    continue;
                                }
                            }
                            if( !(adyacente.cordX+1 >= tamanio) ){// +1 0
                                extra3 = new nodo( adyacente.cordX+1, adyacente.cordY );
                                if( ( matriz[adyacente.cordX+1][adyacente.cordY] == 1 ) && distancia( extra3, actual ) ==10 ){
                                    continue;
                                }
                            }

                            if( !(adyacente.cordY+1 >= tamanio) ){// 0 +1
                                extra3 = new nodo( adyacente.cordX, adyacente.cordY+1 );
                                if( ( matriz[adyacente.cordX][adyacente.cordY+1] == 1 ) && distancia( extra3, actual ) ==10 ){
                                    continue;
                                }
                            }

                            if( !(adyacente.cordY-1 < 0) ){// 0 -1
                                extra3 = new nodo( adyacente.cordX, adyacente.cordY-1 );
                                if( ( matriz[adyacente.cordX][adyacente.cordY-1] == 1 ) && distancia( extra3, actual ) ==10 ){
                                    continue;
                                }
                            }
                             */
                        }

                        //comprobamos que no este en la abierta ni en la cerrada
                        if (!estaEnAbiertos(adyacente) && !estaEnCerrados(adyacente)) {
                            adyacente = setValores(actual, adyacente);
                            adyacente.padre = actual;
                            abierta.agregar(adyacente);
                        } else if (estaEnAbiertos(adyacente)) {
                            if (setValores(actual, adyacente).costoF < actual.costoF) {
                                adyacente = setValores(actual, adyacente);
                                adyacente.padre = actual;
                            }
                        } else if (!estaEnAbiertos(adyacente)) {
                            if (estaEnCerrados(adyacente))
                                ;//lo ignoramos
                        }
                    }//fin del primer for
                }//fin del segundo for

            }//fin del else

            actual = abierta.eliminaCostoMenor();

            //preguntamos si ya encontro el camino para mostrarlo
            if ((actual.cordX == puntoMeta.cordX) && (actual.cordY == puntoMeta.cordY)) {
                nodo extra2 = actual;

                while (extra2 != null) {
                    extra2.siguiente = null;
                    camino.agregar(extra2);
                    extra2 = extra2.padre;
                }
            }
        }//fin del for
    }

    ///////METODO QUE RECOBE UN NODO ACTUAL Y UNO ADYACENTE Y LE PONE///////////
    ///////SUS VALORES RESPECTIVOS DE F,G Y H Y LO REGRESA//////////////////////
    public nodo setValores(nodo actual, nodo adyacente) {
        //si es el mismo
        if ((actual.cordX == adyacente.cordX) && (actual.cordY == adyacente.cordY))
            return actual;
        else if ((actual.cordX == adyacente.cordX) || (actual.cordY == adyacente.cordY))
            adyacente.costoG = 10;
        else
            adyacente.costoG = 14;

        adyacente.costoH = distancia(adyacente, puntoMeta);
        adyacente.costoF = adyacente.costoG + adyacente.costoH;

        return adyacente;
    }

    //////METODO QUE CALCULA LA DISTANCIA MANHATTAN/////////////////////////////
    public int distancia(nodo a, nodo b) {
        int distance = Math.abs(b.cordX - a.cordX) + Math.abs(b.cordY - a.cordY);
        return distance * 10;
    }

    ///////METODO QUE VERIFICA SI UN NODO ESTA EN LA LISTA DE CERRADOS//////////
    ///////Y REGRESA UN VERDADERO O FALSO SEGUN SEA EL CASO/////////////////////
    public boolean estaEnCerrados(nodo nodin) {
        nodo aux = cerrados.primero;

        //la lista esta vacia
        if (aux == null)
            return false;

        while (aux != null) {
            if ((nodin.cordX == aux.cordX) && (nodin.cordY == aux.cordY))
                return true;

            aux = aux.siguiente;
        }
        return nodin.visitado;
    }

    ///////METODO QUE VERIFICA SI UN NODO ESTA EN LA LISTA DE ABIERTOS//////////
    ///////Y REGRESA UN VERDADERO O FALSO SEGUN SEA EL CASO/////////////////////
    public boolean estaEnAbiertos(nodo nodin) {
        if (!abierta.esVacia())
            return false;
        nodo aux = abierta.primero;

        //la lista esta vacia
        if (aux == null)
            return false;

        while (aux != null) {
            if ((nodin.cordX == aux.cordX) && (nodin.cordY == aux.cordY))
                return true;

            aux = aux.siguiente;
        }
        return false;

    }
}

