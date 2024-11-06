package tech.code.codetech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.code.codetech.model.Produto;
import tech.code.codetech.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    public ProdutoServiceTest() {
    }

    @BeforeEach
    void setIp(){

    }

    @DisplayName("Salvar Produto deve retornar o Produto salvo (CREATED)")
    @Test
    void salvarProduto (){
        Produto produto = Mockito.mock(Produto.class);
        Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(produto);
        Assertions.assertEquals(produto, this.produtoService.save(produto));
    }

    @DisplayName("Atualizar Produto deve retornar o produto atualizado (OK)")
    @Test
    void atualizarProduto(){
        Produto produto = Mockito.mock(Produto.class);
        Mockito.when(produtoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Mockito.when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(produto);
        Assertions.assertEquals(produto, this.produtoService.update(1, produto));
    }

    @DisplayName("Atualizar Produto deve retornar null se o Produto não existir")
    @Test
    void atualizarProdutoNaoExistente(){
        Mockito.when(produtoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertNull(produtoService.update(1, Mockito.mock(Produto.class)));
    }

    @DisplayName("Listar deve retornar uma lista de produtos")
    @Test
    void listarProdutos(){
        List<Produto> produtos = List.of(Mockito.mock(Produto.class));
        Mockito.when(this.produtoRepository.findAll()).thenReturn(produtos);
        Assertions.assertEquals(produtos, this.produtoService.findAll());
    }

    @DisplayName("Listar vazio deve retornar uma lista vazia, caso não tenha produtos cadastrados no banco de dados")
    @Test
    void listarVazio(){
        List<Produto>  produtos = List.of();
        Mockito.when(this.produtoRepository.findAll()).thenReturn(produtos);
        Assertions.assertEquals(produtos, this.produtoService.findAll());
    }

    @DisplayName("Produto Por ID deve retornar um produto específico")
    @Test
    void porId(){
        Produto produto = Mockito.mock(Produto.class);
        Mockito.when(this.produtoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(produto));
        Assertions.assertEquals(produto, this.produtoService.findById(1));
    }

    @DisplayName("Produto Por ID deve retornar null caso Produto não seja encontrado")
    @Test
    void porIdNaoEncontrado(){
        Mockito.when(this.produtoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Assertions.assertNull(this.produtoService.findById(1));
    }

    @DisplayName("Deletar Produto deve retornar true se Produto for deletado")
    @Test
    void deletarProduto(){
        Mockito.when(this.produtoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Assertions.assertTrue(this.produtoService.delete(1));
    }

    @DisplayName("Deletar Produto deve retornar false")
    @Test
    void deletarProdutoNaoExistente(){
        Mockito.when(this.produtoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertFalse(this.produtoService.delete(1));
    }

}
