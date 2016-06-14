/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cas.tj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 *
 * @author Fabio
 */
public class Arquivo {


private  long writeBits;
private  long writeBytes;
private  String toWriteByte;
private  byte toWriteBits;

private  byte readBits;
private  byte readBitsArray[] = new byte[8];

private OutputStream outfile;

private File out;

private InputStream infile;
private File in;


private Arquivo () {
    
}

private static Arquivo instance = null;

public static Arquivo getInstance () {
    if (instance == null)
        instance = new Arquivo();
    return instance;
}
//*******************************************
//** funcoes de escrita em arquivo binario **
//*******************************************

//abre o arquivo binário para escrita
//é necessário passar a tabela de frequencia preenchida
public void abreArquivoBinarioEscrita (String nomeArquivo, int[] freqTable) throws Exception {
         out = new File(nomeArquivo);      
         if (out.exists()) {
             System.out.println("arquivo com o nome "+nomeArquivo+" já existe");
             System.exit(1);
         }
      
        int totalBytesRead = 0;
        outfile = new BufferedOutputStream(new FileOutputStream(out));
    
        writeBits = 0;
        writeBytes = 0;

        toWriteByte = "";
        toWriteBits = 0;
        outfile.write(longToBytes(writeBits));
        
        int i=0;
        int freqMaiorZero = 0;
        //ver quantas frequencias serão gravadas
        for (i=0; i< 256; i+=1) {
            if (freqTable[i]>0)
                freqMaiorZero+=1;
        }
        outfile.write(intToBytes(freqMaiorZero));
        //grava char e valor de frequencia
        for (i=0; i< 256; i+=1) {
            if (freqTable[i]>0) {
                byte c[] = new byte[1];
                c[0] = (byte) i;
                outfile.write(c);
                outfile.write(intToBytes(freqTable[i]));  
            }
        }
}

//escreve um bit no arquivo binario (valor deve ser 0 ou 1)
public void escreveBit (int bitToWrite) throws Exception {
    if (bitToWrite < 0 && bitToWrite > 1) {
           System.out.println("br.com.cas.tj.Arquivo.escreveBit() - bitToWrite deve ser 0 ou 1");
           System.exit(1);
    }
    toWriteByte += Integer.toString(bitToWrite);
    //toWriteBits +=1; //contador do byte atual
    writeBits+=1; //contador de todos os bits já gravados
    //está na hora de gravar o byte
    
    if (toWriteByte.length() == 8 ) {       
        int i = Integer.parseInt(toWriteByte,2);       
        outfile.write(i);
        writeBytes+=1; //contador de total de bytes gravados
        toWriteByte = "";
        
    }
    
}

//fecha o arquivo binario
public void fechaArquivoBinarioEscrita () throws Exception {
    if (toWriteByte.length() != 0) {
            //grava o ultimo byte
            while (toWriteByte.length()<8)
            toWriteByte+="0";         
            int i = Integer.parseInt(toWriteByte,2);
            outfile.write(i);
    }
    
    outfile.close();
    //volta no inicio do arquivo e grava o numero de bits e bytes
    RandomAccessFile file = new RandomAccessFile(out.getAbsoluteFile(), "rw");
    file.seek(0);
    //guardando valor para o contador de bits
    file.write(longToBytes(writeBits));
    file.close();
    
}


//*******************************************
//** funcoes de leitura em arquivo binario **
//*******************************************

//abre arquivo binario para leitura. retorna o numero de bits para serem lidos
public long abreArquivoBinarioLeitura (String nomeArquivo) throws Exception {
    in = new File(nomeArquivo);   
    if (!in.exists()) {
        System.out.println("arquivo binario compactado não foi encontrado ("+nomeArquivo+")");
        System.exit(1);
    }
    infile = new BufferedInputStream(new FileInputStream(in));
    readBits = 0;
    byte[] c = new byte[Long.SIZE/Byte.SIZE];
    infile.read(c,0,Long.SIZE/Byte.SIZE);
    writeBits = bytesToLong(c);
    return writeBits;
}


//le tabela de frequencia. 
public int[] leTabelaFrequencia () throws Exception {
    int freqTable[] = new int[256];
    int freqMaiorZero = 0;
    int i;
    byte c[] = new byte[Integer.SIZE/Byte.SIZE];
    infile.read (c,0,Integer.SIZE/Byte.SIZE);
    freqMaiorZero = bytesToInt(c);
    
    //grava char e valor de frequencia
    for (i=0; i< freqMaiorZero; i+=1) {
              c = new byte[1];
              infile.read(c, 0, 1);
              byte letra = c[0];
              c = new byte[Integer.SIZE/Byte.SIZE];
              infile.read (c,0,Integer.SIZE/Byte.SIZE);
              freqTable[letra] = bytesToInt(c);
    }
    return freqTable;
}

//retorna o proximo bit (0 ou 1)
public int leBit () throws Exception {
    if (readBits == 0) { //precisa ler mais um byte
        byte[] c = new byte[1];
        infile.read(c, 0, 1);
        //converte para binário
        int i;
        String bitR = Integer.toBinaryString(c[0]);
        while (bitR.length()<8)
            bitR = "0"+bitR;
        int contador = 0;
        for (i = bitR.length()-8; i < bitR.length(); i+=1) {
            readBitsArray[contador++] = (byte) Integer.parseInt(""+bitR.charAt(i));
        }
        readBits = 8;
    }
    return readBitsArray [ 8- (readBits--)]; 
}

//fecha arqiovo binario de leitura
public void fechaArquivoBinarioLeitura () throws Exception {
   infile.close();
}



//*****************************************
//** funcoes de leitura de arquivo texto **
//*****************************************


//abre arquivo de texto
public void abreArquivoTextoLeitura (String nomeArquivo) throws Exception {
    in = new File(nomeArquivo);   
    if (!in.exists()) {
        System.out.println("arquivo texto a ser compactado não foi encontrado ("+nomeArquivo+")");
        System.exit(1);
    }
    infile = new BufferedInputStream(new FileInputStream(in));
}

//le um caracter do arquivo e posiciona ponteiro para o proximo
public char leCaracterArquivoTexto  () throws Exception {
    return (char) infile.read();
}

//fecha arquivo de texto
public void fechaArquivoTextoLeitura () throws Exception {
    infile.close();
}

//*****************************************
//** funcoes de escrita em arquivo texto **
//*****************************************

//abre o arquivo texto para escrita
public void abreArquivoTextoEscrita (String nomeArquivo) throws Exception {
    out = new File(nomeArquivo);      
    if (out.exists()) {
        System.out.println("arquivo com o nome "+nomeArquivo+" já existe");
        System.exit(1);
    }


   outfile = new BufferedOutputStream(new FileOutputStream(out));
    
}

//escreve um caracter
public void escreveCaracter (char caracter) throws Exception {
    outfile.write(caracter);
}

//fechar arquivo de texto modo escrita
public void fechaArquivoTextoEscrita () throws Exception {
    outfile.close();
}


/****************************
 * Funções utilitárias
 ***************************/

public byte[] longToBytes(long x) {
    ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
    buffer.putLong(x);
    return buffer.array();
}

public long bytesToLong(byte[] bytes) {
    ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
    buffer.put(bytes);
    buffer.flip();//need flip 
    return buffer.getLong();
}

public byte[] intToBytes(int x) {
    ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE/Byte.SIZE);
    buffer.putInt(x);   
    return buffer.array();
}

public int  bytesToInt (byte[] bytes) {
    ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE/Byte.SIZE);
    buffer.put(bytes);
    buffer.flip();//need flip 
    return buffer.getInt();
}

}


