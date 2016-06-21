//Classe template para implementação do algoritmo de Huffman
package br.com.cas.tj;

public class Huffman {
    
    private Node raiz;   
    
    public Huffman () {
        raiz = null;
    }
    
    public void compactar (String origem, String destino) throws Exception {
        int freq[] = new int [256];
        
        //parte 0 - abre o arquivo de texto pela primeira vez, para montar a tabela de frequencia
        Arquivo file = Arquivo.getInstance();
        

        
        //parte 1 - le o arquivo e monta a tabela de frequencia
        file.abreArquivoTextoLeitura(origem);
        byte caracter;
        while ( (caracter =(byte) file.leCaracterArquivoTexto()) != -1) {
            //soma 1 na frequencia da posicao indicada pelo valor do caracter
            //exemplo: caracter = 'a' é a mesma coisa que caracter = 97
            //freq[caracter] = freq[97]
            freq[caracter]+=1;
        }      
        //parte 2 - cria lista de nós 
        Lista nos = new Lista();
        //parte 3 - monta a arvore
        for (int i= 0; i < 256; i++){
            if(freq[i]>0){
                Node no = new Node();
                no.setFreq(freq[i]);
                no.setCaracter((char)i);
                nos.inserir(no);
            }
        }
        
        Node noaux1,noaux2 = new Node();
        noaux1 = null;
        noaux2 = null;
        int ind=0;
        noaux1.setFreq(Integer.MAX_VALUE);
        noaux2.setFreq(Integer.MAX_VALUE);
        
        while (nos.tamanho() > 1){
            for (int i=0; i< nos.tamanho(); i++){
                if(nos.get(i).getFreq() < noaux1.getFreq()){
                    noaux1 = nos.get(i);
                    ind = i;
                }
            }
            nos.remover(ind);

            for (int i=0; i< nos.tamanho(); i++){
                if(nos.get(i).getFreq() < noaux2.getFreq()){
                    noaux2 = nos.get(i);
                    ind = i;
                }
            }
            nos.remover(ind);

            Node no = new Node();
            if (noaux1.getFreq() < noaux2.getFreq()){
                no.setEsq(noaux1);
                no.setDir(noaux2);
                no.setFreq(noaux1.getFreq() + noaux2.getFreq());
            }
            else{
                no.setEsq(noaux2);
                no.setDir(noaux1);
                no.setFreq(noaux1.getFreq() + noaux2.getFreq());
            }
            nos.inserir(no);
        
        }
        //parte 4 - atualiza raiz da arvore com o no que restou na lista
        this.raiz = nos.get(0);
        //parte 5 - cria tabela de códigos 
        int codigos[][] = new int[256][];
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
        Pilha pilha = new Pilha ();
        int i = 0;
        while (i <= codigos.length){
            
        }
        
        
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
    private int[] percursoEmOrdem (Node no, Pilha pilha) {
        if (no.getEsq()!=null){
            pilha.push(0);
            if(!no.getEsq().ehFolha()){
                percursoEmOrdem(no.getEsq(),pilha);
            }
            return pilha.fotografiaPilha();
        }
        if (no.getDir()!=null){
            pilha.push(1);
            if(!no.getDir().ehFolha()){
                percursoEmOrdem(no.getDir(),pilha);
            }
            return pilha.fotografiaPilha();
        }
        return null;
    }
    
    
    
   
}
