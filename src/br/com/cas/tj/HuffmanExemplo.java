//referencia para uso da classe Arquivo e implementação de partes do algoritmo de Huffman
package br.com.cas.tj;

public class HuffmanExemplo {
    
    private Node raiz;   
    
    public HuffmanExemplo () {
        raiz = null;
    }    
  
    public void compactar (String origem, String destino) throws Exception {
        int freq[] = new int [256];
        Arquivo file = Arquivo.getInstance();
        //parte 0 - abre o arquivo de texto pela primeira vez, para montar a tabela de frequencia
        file.abreArquivoTextoLeitura(origem);

        byte caracter;
        //parte 1 - le o arquivo e monta a tabela de frequencia
        while ( (caracter =(byte) file.leCaracterArquivoTexto()) != -1) {
            //soma 1 na frequencia da posicao indicada pelo valor do caracter
            //exemplo: caracter = 'a' é a mesma coisa que caracter = 97
            //freq[caracter] = freq[97]
            freq[caracter]+=1;
        }
       
        //CODIGO ILUSTRATIVO, NAO PRECISA ESTAR NA VERSAO FINAL
        //imprime tabela de frequencia, percorrendo os 256 possiveis valores de um byte/char
        for (int i=0; i< 256; i+=1) {
            //soh imprime se a frequencia for maior que zero
            if (freq[i]>0) {
                //imprime os caracteres que não são pulo de linhas
                if ( ((char)i)!='\n' && ((char)i)!='\r')
                    System.out.println("freq['"+((char)i)+"']="+freq[i]);
                //imprime o pulo de linha \n (newline)
                if ( ((char)i)=='\n' )   
                     System.out.println("freq['\\n']="+freq[i]);
                //imprime o pulo de linha \r (return)
                if ( ((char)i)=='\r' )   
                     System.out.println("freq['\\r']="+freq[i]);                
            }
        }
        
        Lista nos = new Lista();
        //parte 2 - cria lista de nós
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
        //noaux1.setFreq(Integer.MAX_VALUE);
        //noaux2.setFreq(Integer.MAX_VALUE);
        
        while (nos.tamanho() > 1){
            noaux1 = removeMenorFrequencia(nos);
            noaux2 = removeMenorFrequencia(nos);
            

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
        
        this.raiz = nos.get(0);
        this.print();
        //seu código para criar a lista de nós vai aqui
        
         //parte 3 - monta a arvore, iterando sobre a lista até ela ter tamanho 1
         //seu código de montagem da arvore na lista vai aqui
        
        //parte 4 - atualiza raiz da arvore com o no que restou na lista | this.raiz = nos.get(0);
        //pode imprimir a arvore depois de atualizar a raiz para dar uma conferida
        //this.print ();
        
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
        int codigos[][] = new int[256][];
        
        
            
        //parte 6 - preencher a tabela de códigos percorrendo a arvore, guardando o caminho em 
        //pilha e atualizando a tabela sempre que encontrar um nó folha
        //fiz um metodo auxiliar da pilha, para que seja possivel obter 
        //os valores da pilha sem ter que desempilhar e empilhar tudo denovo
        //método - int[] fotografiaPilha ()
        Pilha pilha = new Pilha ();
        
        atualizaMatriz(raiz, pilha, codigos);
        
        //seu código para obter os códigos de cada caracter vai aqui
        
        //CODIGO ILUSTRATIVO, NAO PRECISA ESTAR NA VERSAO FINAL
        //imprime tabela de código para os caracteres que tem frequencia maior que 0
        for (int i=0; i< 256; i+=1) {
            //soh imprime se a frequencia for maior que zero
            if (freq[i]>0) {
                //imprime os caracteres que não são pulo de linhas
                int[] codigo = codigos[i];
                if (codigo == null)
                    continue;
                if ( ((char)i)!='\n' && ((char)i)!='\r')
                    System.out.print("codigos['"+((char)i)+"']=");
                //imprime o pulo de linha \n (newline)
                if ( ((char)i)=='\n' )   
                     System.out.println("codigos['\\n']=");
                //imprime o pulo de linha \r (return)
                if ( ((char)i)=='\r' )   
                     System.out.println("codigos['\\r']=");  
                for (int j=0; j< codigo.length; j+=1) {
                    System.out.print(codigo[j]);
                }
                System.out.println();
            }
        }
        
        //parte 7 - abre o arquivo binario
        file.abreArquivoBinarioEscrita(destino, freq);
        
        //parte 8 - reabre o arquivo texto
        file.abreArquivoTextoLeitura(origem);
        
        //parte 9 - percorre o arquivo texto, caracter a caracter, procurando 
        //o codigo para cada caracter e gravando a sequencia de bits com 
        //a funcao file.escreveBit        
        while ( (caracter =(byte) file.leCaracterArquivoTexto()) != -1) {
            System.out.print((char)caracter);
            //obtem o código
            int [] codigo = codigos[caracter];
            if (codigo == null)
                continue;
            //escreve os bits no arquivo binario 
            for (int i=0; i< codigo.length; i+=1) {
                file.escreveBit(codigo[i]);               
            }
        }
        
        //parte 10 - fecha arquivos
        file.fechaArquivoBinarioEscrita();
        file.fechaArquivoTextoLeitura();
        
    }
    
    public void descompactar (String origem, String destino) throws Exception {
        int freq[] = new int [256];
        Arquivo file = Arquivo.getInstance();        
        //parte 0 - abre o arquivo binario e guarda o numero de bits que precisam
        //ser lidos
        long numeroDeBitsParaLer = file.abreArquivoBinarioLeitura(origem);
        
        //parte 1 - le tabela de frequencia guardada no arquivo binario
        freq = file.leTabelaFrequencia();
        
        //parte 2 - abre o arquivo de texto para escrita do documento descompactado
        file.abreArquivoTextoEscrita(destino);
        
        //parte 3 - monta a arvore de huffman a partir da tabela de frequencia (igual no compactar)
        
        
        //parte 4 - monteagem da arvore - cria lista de nós com a informacao da tabela de frequencia
        Lista nos = new Lista();
        //seu código de montagem da lista vai aqui
        for (int i= 0; i < 256; i++){
            if(freq[i]>0){
                Node no = new Node();
                no.setFreq(freq[i]);
                no.setCaracter((char)i);
                nos.inserir(no);
            }
        }
        //parte 5 - monta a arvore, iterando sobre a lista até ela ter tamanho 1
        Node noaux1,noaux2 = new Node();
        noaux1 = null;
        noaux2 = null;
        int ind=0;
        
        while (nos.tamanho() > 1){
            noaux1 = removeMenorFrequencia(nos);
            noaux2 = removeMenorFrequencia(nos);
            

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
        //seu código de montagem da arvore na lista vai aqui
        
        //parte 6 - atualiza raiz da arvore com o no que restou na lista | this.raiz = nos.get(0);
        //pode imprimir a arvore depois de atualizar a raiz para dar uma conferida
        //this.print ();
        this.raiz = nos.get(0);
        this.print();
        
        Node noatual = raiz;
        
        //parte 7 - le bit a bit o arquivo binario, percorre a arvore e grava o 
        //caracter encontrado no arquivo de texto        
        
        //exemplo da leitura bit a bit do arquivo
        for (int i=0; i < numeroDeBitsParaLer; i+=1) {            
            
            int bit = file.leBit();           
            if(bit == 0){
                noatual = noatual.getEsq();
            }
            else if(bit == 1){
                noatual = noatual.getDir();
            }
            if (noatual.ehFolha()){
                char caracter = noatual.getCaracter();
                file.escreveCaracter(caracter);
                noatual = raiz;
            }
            
        }
        
        //parte 8 fecha os arquivos
        file.fechaArquivoBinarioLeitura();
        file.fechaArquivoTextoEscrita();
    }
    
    
    
    
    //retorna o nó de menor frequencia, retirando-o da lista de nós passada como parametro
    private Node removeMenorFrequencia (Lista l) {
        int idx=-1;
        int menor = Integer.MAX_VALUE;
        for (int i =0; i < l.tamanho(); i+=1) {
            if (l.get(i).getFreq() < menor) {
                menor = l.get(i).getFreq();
                idx = i;
            }
        }            
        Node result = l.get(idx);
        l.remover(idx);
        return result;
        
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
    
    private void atualizaMatriz(Node no,Pilha pilha, int codigos[][]){
        if(!no.ehFolha()){
            if(no.getEsq() != null){
                pilha.push(0);
                atualizaMatriz(no.getEsq(),pilha,codigos);
                pilha.pop();
                
            }
            if(no.getDir() != null){
                pilha.push(1);
                atualizaMatriz(no.getDir(),pilha,codigos);
                pilha.pop();
            }
        }
        else{
            codigos[no.getCaracter()]= pilha.fotografiaPilha();
        }
    }
}
