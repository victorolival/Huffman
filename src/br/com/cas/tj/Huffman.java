//Classe template para implementação do algoritmo de Huffman
package br.com.cas.tj;

public class Huffman {
    
    private Node raiz;   
    
    public Huffman () {
        raiz = null;
    }
    
    public void compactar (String origem, String destino) throws Exception {
       
        //parte 0 - abre o arquivo de texto pela primeira vez, para montar a tabela de frequencia
       
        //parte 1 - le o arquivo e monta a tabela de frequencia
                
        //parte 2 - cria lista de nós 
       
        //parte 3 - monta a arvore
       
        //parte 4 - atualiza raiz da arvore com o no que restou na lista
       
        //parte 5 - cria tabela de códigos 
        //SUGESTÃO - usar uma matriz de inteiros
        //sabemos que tem até 256 tipos diferente de caracteres na nossa tabela de 
        //frequencia, mas ainda nao sabemos o tamanho dos códigos.
        //por exemplo:
        //a = 97... se o código para o caracter 'a' for 01001, então
        //codigos['a'] = codigos[97] = new int[5];
        //codigos['a'][0] = 0;
        //codigos['a'][1] = 1;
        //codigos['a'][2] = 0;
        //codigos['a'][3] = 0;
        //codigos['a'][4] = 1;
       
        
        //parte 6 - preencher a tabela de códigos percorrendo a arvore, guardando o caminho em 
        //pilha e atualizando a tabela sempre que encontrar um nó folha
        //fiz um metodo auxiliar da pilha, para que seja possivel obter 
        //os valores da pilha sem ter que desempilhar e empilhar tudo denovo
        //método - int[] fotografiaPilha ()        
              
        //parte 7 - abre o arquivo binario
               
        //parte 8 - reabre o arquivo texto
               
        //parte 9 - percorre o arquivo texto, caracter a caracter, procurando 
        //o codigo para cada caracter e gravando a sequencia de bits com 
        //a funcao file.escreveBit        
       
        //parte 10 - fecha arquivos       
        
    }  
    
    public void descompactar (String origem, String destino) throws Exception {
             
        //parte 0 - abre o arquivo binario e guarda o numero de bits que precisam
        //ser lidos
                
        //parte 1 - le tabela de frequencia guardada no arquivo binario
                
        //parte 2 - abre o arquivo de texto para escrita do documento descompactado
                
        //parte 3 - monta a arvore de huffman a partir da tabela de frequencia (igual no compactar)
        
        //parte 4 - monteagem da arvore - cria lista de nós com a informacao da tabela de frequencia
        
        //parte 5 - monta a arvore, iterando sobre a lista até ela ter tamanho 1
        
        //parte 6 - atualiza raiz da arvore com o no que restou na lista
        
        //parte 7 - le bit a bit o arquivo binario, percorre a arvore e grava o 
        //caracter encontrado no arquivo de texto        
                
        //parte 8 fecha os arquivos
    }
    
    //imprime a arvore
   public void print () {
        printAux(raiz, 0);
    }
    
   //auxilia na impressao da arvore
    private void printAux (Node no, Integer nivel) {
        
        for (int i=0; i< nivel; i++) {
            System.out.print("\t");
        }
        if (no == null) {
            System.out.println("\\- NULO");
            return;
        }
        else {
            if (no.ehFolha())
                System.out.println("\\- " + no.getFreq()+"-("+no.getCaracter()+")");        
            else
                System.out.println("\\- " + no.getFreq());        
        }
        printAux(no.getEsq(), nivel+1);
        printAux(no.getDir(), nivel+1);
    }
    
    
    
    
   
}
