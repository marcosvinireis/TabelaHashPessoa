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

    protected int funcaoHash(String chave) {
        int hash = 0;
        int multiplicador = 31;
        for (int i = 0; i < chave.length(); i++) {
            hash = multiplicador * hash + chave.charAt(i);
        }
        hash = Math.abs(hash);
        return hash % this.tamanho;
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
                System.out.println(ELEMENTO_CADASTRADO);
                return;
            }
            this.tabela[pos].adicionar(valor);
        }
    }

    public void remover(String chave){
        int pos = this.funcaoHash(chave);

        if (posicaoVazia(pos)) {
            System.out.println(ELEMENTO_NAO_EXISTE);
            return;
        }else {
            Pessoa elemento = this.tabela[pos].busca(pos);
            if (this.tabela[pos].contains(elemento)){
                int posLista = this.tabela[pos].buscaPorElemento(elemento);
                this.tabela[pos].remover(posLista);
            }else System.out.println(ELEMENTO_NAO_EXISTE);
        }
    }

    public void buscar(String chave){
        int pos = this.funcaoHash(chave);
        if(posicaoVazia(pos)){
            System.out.println(ELEMENTO_NAO_EXISTE);
        }else{
            ListaEncadeada<Pessoa> bucket = this.tabela[pos];
            boolean busca =false;
            for(int i=0;i< bucket.getTamanho();i++){
                Pessoa pessoa= bucket.busca(i);
                if(pessoa.getId().equals(chave)){
                    System.out.println("Elemento encontrado :"+ pessoa.toString());
                    busca=true;
                    break;
                }
            }
            if (!busca) {
                System.out.println(ELEMENTO_NAO_EXISTE);
            }
        }
    }

    public void imprimir(){
        System.out.println("Tabela Hash: ");
        for (int i=0;i<this.tamanho;i++){
            if(tabela[i]== null){
                System.out.println("NULL");
            }else{
                System.out.print("Posicao " + i + ": ");
                ListaEncadeada<Pessoa> bucket = this.tabela[i];
                for (int j = 0; j < bucket.getTamanho(); j++) {
                    Pessoa pessoa = bucket.busca(j);
                    System.out.print(pessoa.getId() + " - " + pessoa.getNome() + " . "); // Imprime chave e valor
                }
                System.out.println();

            }
        }
    }

}
