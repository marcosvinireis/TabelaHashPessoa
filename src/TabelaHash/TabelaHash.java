package TabelaHash;

import dados.Pessoa;
import listaEncadeada.ListaEncadeada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public ListaEncadeada<Pessoa> lerDeArquivo(String caminho) {
        ListaEncadeada<Pessoa> dados = new ListaEncadeada<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(caminho));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length == 2) {
                    Pessoa pessoa = new Pessoa(partes[0], partes[1]);
                    dados.adicionar(pessoa);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dados;
    }

    public void inserirDeArquivo(String path){
        ListaEncadeada<Pessoa> dados = this.lerDeArquivo(path);
        for (int i = 0; i < dados.getTamanho(); i++) {
            Pessoa novoDado = dados.busca(i);
            this.inserir(novoDado.getId(), novoDado);
        }
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
