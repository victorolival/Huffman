/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cas.tj;

/**
 *
 * @author Fabio
 */
public class Pilha {

    private static final int tamanhoPilha = 500;
    
    private int itens[];
    
    private int topo;
    
    public Pilha () {
        itens = new int[tamanhoPilha];
        topo = -1;
    }
    
    public void push (int valor) {
        if ( isFull ()) {
            System.out.println("stack overflow");
            return;
        }            
        topo +=1;
        int indice = topo;
        itens[indice]= valor;
    }
    
    public boolean isEmpty () {
        if (topo < 0)
            return true;
        return false;
    }
    
    public boolean isFull () {
        if ( (topo+1) >= tamanhoPilha) {
            return true;
        }
        return false;
    }
    
    public int peek () {
        return itens[topo];  
    }
    
    public int pop () {
        if (isEmpty()) {
            System.out.println("stack underflow");
            return 0;
        }
        int indice = topo;
        topo = topo -1;
        return itens[indice];
    }
    
    public int[] fotografiaPilha () {
        int [] result = new int [topo+1];
        for (int i =0; i < result.length; i+=1) {
            result[i]=itens[i];
        }
        return result;
    }
    
    
    
}
