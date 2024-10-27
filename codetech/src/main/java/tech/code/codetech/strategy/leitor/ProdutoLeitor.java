package tech.code.codetech.strategy.leitor;

import org.springframework.cglib.core.Local;
import tech.code.codetech.model.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProdutoLeitor {

    public static List<Produto> lerProdutos() {

        List<Produto> novosProdutos = new ArrayList<>();

        try (
                InputStream inputStream = new FileInputStream("produtos.csv");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                Scanner leitor = new Scanner(bufferedReader)
        ) {
            leitor.useLocale(Locale.forLanguageTag("pt-BR"));
            leitor.useDelimiter("[;\\n]");
            leitor.nextLine();

            while (leitor.hasNextLine()) {
                try {
                    Integer id = leitor.nextInt();
                    String nome = leitor.next();
                    String categoria = leitor.next();
                    Integer pontuacao = leitor.nextInt();
                    Double preco = leitor.nextDouble();
                    Integer quantidadeEstoque = leitor.nextInt();

                    Produto produto = new Produto(id, nome, categoria, pontuacao, preco, quantidadeEstoque);
                    novosProdutos.add(produto);
                } catch (Exception e) {
                    System.out.println("Erro ao ler linha: " + e.getMessage());
                    leitor.nextLine();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo!");
        }

        return novosProdutos;
    }

    public static void main(String[] args) {
        List<Produto> produtosLidos = lerProdutos();

        System.out.println("Exibindo lista de produtos lidos:");
        for (Produto produto : produtosLidos) {
            System.out.println(produto);
        }
    }
}
