/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cas.tj;

/**
 *
 * @author fabio.tpereiro
 */
public class Node {
    
    private Integer freq;
    private char caracter;
    private Node esq;
    private Node dir;
    
    public Node () {
        freq = null;
        esq = null;
        dir = null;
     }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }   

    public Node getEsq() {
        return esq;
    }

    public void setEsq(Node esq) {
        this.esq = esq;
    }

    public Node getDir() {
        return dir;
    }

    public void setDir(Node dir) {
        this.dir = dir;
    }
    
    public boolean ehFolha () {
        return dir == null && esq == null;
    }
    
    
            
    
}
