package TabelaHash;

import dados.Pessoa;
import listaEncadeada.ListaEncadeada;

public class TabelaHash {

    private ListaEncadeada<Pessoa> [] tabela;
    private int tamanho;


    private final String ELEMENTO_NAO_EXISTE = "Esse elemento não existe";
    private final String ELEMENTO_CADASTRADO = "Esse elemento já foi cadastrado";
    private boolean posicaoVazia(int pos){return this.tabela[pos] == null;}


    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new ListaEncadeada[tamanho];
        for (int i = 0; i < tamanho; i++) this.tabela[i] = null;
    }

    private int funcaoHash(String chave){
        long somaHash = 0;
        for (int i = 0; i < chave.length(); i++) {
            char caractere = chave.charAt(i);
            int valorAscii = (int) caractere;
            somaHash += valorAscii*Math.pow(3, chave.length()-i);
        }
        return (int) somaHash % this.tamanho;
    }

    public void inserir(String chave, Pessoa valor){
        int pos = funcaoHash(chave);

        if (posicaoVazia(pos)){
            ListaEncadeada<Pessoa> bucket = new ListaEncadeada<>();
            bucket.adicionar(valor);
            this.tabela[pos] = bucket;
        } else {
            if (this.tabela[pos].contains(valor)){
                System.out.println("Item já cadastrado");
                return;
            }
            this.tabela[pos].adicionar(valor);
        }
    }


}
