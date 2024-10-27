package tech.code.codetech.strategy.escritor;

import tech.code.codetech.model.Produto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProdutoEscritor {

    public static void escreverArquivo(List<Produto> produtos) {

        //Exportar nome do arquivo com data e hora atual
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH-mm");
        String dataHoraFormatada = dataHoraAtual.format(formatter);

        String nomeArquivo = "produtos " + dataHoraFormatada + ".csv";

        try {
            OutputStream outputStream = new FileOutputStream("produtos.csv");
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            escritor.write("id; nome; descricao; unidadeMedida; preco; quantidade; categoria");

            for (Produto produtoDaVez : produtos) {
                escritor.newLine();
                escritor.write(String.format("%d; %s; %s; %s; %.2f; %d; %s",
                        produtoDaVez.getId(),
                        produtoDaVez.getNome(),
                        produtoDaVez.getDescricao(),
                        produtoDaVez.getUnidadeMedida(),
                        produtoDaVez.getPreco(),
                        produtoDaVez.getQuantidade(),
                        produtoDaVez.getCategoria().getNome()
                ));
            }

            escritor.close();
            outputStream.close();

        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo CSV");
        }
    }

}
