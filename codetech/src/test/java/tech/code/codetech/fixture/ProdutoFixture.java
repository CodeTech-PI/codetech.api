package tech.code.codetech.fixture;

import tech.code.codetech.model.Produto;

import java.math.BigDecimal;

public class ProdutoFixture {
    public static Produto buildProduto(){
        Produto produto = new Produto();
        produto.setId(1);
        produto.setQuantidade(10);
        produto.setNome("Batoque");
        produto.setDescricao("Batoque de plastico");
        produto.setUnidadeMedida("Unidade");
        produto.setPreco(BigDecimal.valueOf(10.0));
        return produto;
    }
}
