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
public class Main {
    
    public static void main (String [] args) {
        try {
            if (args.length < 3) {
                System.out.println("Compactador / Descompactador de Huffman");
                System.out.println("");
                System.out.println("Uso:");
                System.out.println("");
                System.out.println("Compactar: java -jar huffman.jar -c arquivoTexto.txt arquivoBinario.huf");
                System.out.println("Descompactar: java -jar huffman.jar -d arquivoBinario.huf arquivoTexto.txt");
                System.exit(1);
            }
            HuffmanExemplo huf = new HuffmanExemplo ();
            //Huffman huf = new Huffman ();
            if (args[0].equals("-c")) {
                huf.compactar(args[1], args[2]);
            }
            if (args[0].equals("-d")) {
                huf.descompactar(args[1], args[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    
    }
    
    
    
}
