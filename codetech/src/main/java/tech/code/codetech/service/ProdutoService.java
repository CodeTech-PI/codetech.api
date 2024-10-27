package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.model.Produto;
import tech.code.codetech.repository.ProdutoRepository;
import tech.code.codetech.strategy.CategoriaInterface;
import tech.code.codetech.strategy.ListaProdutoInterface;
import tech.code.codetech.strategy.ProdutoInterface;
import tech.code.codetech.strategy.escritor.ProdutoEscritor;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService implements ProdutoInterface {

    @Autowired
    private ProdutoRepository productRepository;

    @Autowired
    private ListaProdutoInterface listaProdutoService;

    @Autowired
    private CategoriaInterface categoriaInterface;

    @Override
    public <E extends Comparable<E>> List<E> ordenar(List<E> lista) {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(j).compareTo(lista.get(i)) < 0) {
                    E aux = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, aux);
                }
            }
        }
        return lista;
    }

    @Transactional // Garante integridade dos dados (rollback)
    public Produto save(Produto produto) {
        // Integer categoriaId
        // produto.setCategoria(categoriaInterface.findById(categoriaId));
        return productRepository.save(produto);
    }

    public List<Produto> findAll() { // Encontra tudo
        List<Produto> produtos = productRepository.findAll();
        return ordenar(produtos);
    }

    public Produto findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Produto update(Integer id, Produto product) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public boolean delete(Integer id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public void darBaixaEstoque(Integer idAgendamento) {
        List<ListaProduto> listaProdutos = listaProdutoService.buscarListaProdutosPeloAgendamento(idAgendamento);

        for (ListaProduto listaProduto : listaProdutos) {
            Produto produto = listaProduto.getProduto();
            produto.setQuantidade(produto.getQuantidade() - listaProduto.getQuantidadeProdutos());
        }
    }

    public BigDecimal precoTotal(int contador) {
        List<Produto> produtos = productRepository.findAll();

        if (contador >= produtos.size()) {
            return BigDecimal.valueOf(0.0);
        }

        BigDecimal valor = produtos.get(contador).getPreco();
        return valor.add(precoTotal(contador + 1));

//        return valor + precoTotal(contador + 1);
    }

    public void exportarProdutosParaCsv() {
        List<Produto> produtos = productRepository.findAll();
        ProdutoEscritor.escreverArquivo(produtos);
    }

}
