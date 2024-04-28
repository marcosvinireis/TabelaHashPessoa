import dados.Pessoa;
import TabelaHash.TabelaHash;

public class Teste {
    public static void main(String[] args){
        //testando git
        System.out.println("Iniciando o programa...");
        TabelaHash tabela = new TabelaHash(5);
        String caminho = "src/dados/entradaDados.txt";

        tabela.inserirDeArquivo(caminho);

        tabela.buscar("OAA233I5");
        tabela.buscar("DEF456Y7");

        Pessoa p = new Pessoa("XYZ123L9", "Carlos");
        tabela.inserir("XYZ123L9", p);

        tabela.buscar("AAA999Z1");

        tabela.imprimir();



    }
}
