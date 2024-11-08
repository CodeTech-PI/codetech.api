package tech.code.codetech.service;


import io.jsonwebtoken.lang.Assert;
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
import tech.code.codetech.exception.naoencontrado.ListaProdutoNaoEncontradaException;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.repository.ListaProdutoRepository;
import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class ListaProdutosServiceTest {

    @InjectMocks
    private ListaProdutoService listaProdutoService;

    @Mock
    private ListaProdutoRepository listaProdutoRepository;

    ListaProdutosServiceTest(){

    }

    @BeforeEach
    void setUp(){

    }

    @DisplayName("Salvar todos os produtos deve retornar a lista de produtos salvos")
    @Test
    void salvarTodosProdutos() {
        List<ListaProduto> produtos = List.of(Mockito.mock(ListaProduto.class));
        Mockito.when(listaProdutoRepository.saveAll(ArgumentMatchers.anyList())).thenReturn(produtos);
        Assertions.assertEquals(produtos, listaProdutoService.saveAll(produtos));
    }

    @DisplayName("Buscar produto por ID deve retornar o produto")
    @Test
    void buscarProdutoPorId() {
        ListaProduto produto = Mockito.mock(ListaProduto.class);
        Mockito.when(listaProdutoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(produto));
        Assertions.assertEquals(produto, listaProdutoService.findById(1));
    }

    @DisplayName("Buscar produto por ID deve lançar exceção se o produto não for encontrado")
    @Test
    void buscarProdutoPorIdNaoEncontrado() {
        Mockito.when(listaProdutoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ListaProdutoNaoEncontradaException.class, () -> listaProdutoService.findById(1));
    }

    @DisplayName("Buscar todos os produtos deve retornar a lista de produtos")
    @Test
    void buscarTodosProdutos() {
        List<ListaProduto> produtos = List.of(Mockito.mock(ListaProduto.class));
        Mockito.when(listaProdutoRepository.findAll()).thenReturn(produtos);
        Assertions.assertEquals(produtos, listaProdutoService.findAll());
    }

    @DisplayName("Buscar todos os produtos deve retornar uma lista vazia se naõ tiver")
    @Test
    void buscarTodosProdutosNaoExistente() {
        List<ListaProduto> produtos = List.of();
        Mockito.when(listaProdutoRepository.findAll()).thenReturn(produtos);
        Assertions.assertEquals(produtos, listaProdutoService.findAll());
    }


    @DisplayName("Atualizar produtos deve retornar a lista de produtos atualizados")
    @Test
    void atualizarProdutos() {
        List<ListaProduto> produtos = List.of(Mockito.mock(ListaProduto.class));
        Mockito.when(listaProdutoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Mockito.when(listaProdutoRepository.saveAll(ArgumentMatchers.anyList())).thenReturn(produtos);
        Assertions.assertEquals(produtos, listaProdutoService.update(produtos));
    }

    @DisplayName("Atualizar produtos deve lançar exceção se algum produto não for encontrado")
    @Test
    void atualizarProdutosNaoEncontrado() {
        List<ListaProduto> produtos = List.of(Mockito.mock(ListaProduto.class));
        Mockito.when(listaProdutoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertThrows(ListaProdutoNaoEncontradaException.class, () -> listaProdutoService.update(produtos));
    }

    @DisplayName("Deletar produto deve retornar true se o produto for deletado")
    @Test
    void deletarProduto() {
        Mockito.when(listaProdutoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Assertions.assertTrue(listaProdutoService.delete(1));
    }

    @DisplayName("Deletar produto deve retornar false se o produto não for encontrado")
    @Test
    void deletarProdutoNaoEncontrado() {
        Mockito.when(listaProdutoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertFalse(listaProdutoService.delete(1));
    }

    @DisplayName("Buscar lista de produtos pelo agendamento deve retornar a lista de produtos")
    @Test
    void buscarListaProdutosPeloAgendamento() {
        List<ListaProduto> produtos = List.of(Mockito.mock(ListaProduto.class));
        Mockito.when(listaProdutoRepository.findByAgendamentoId(ArgumentMatchers.anyInt())).thenReturn(produtos);
        Assertions.assertEquals(produtos, listaProdutoService.buscarListaProdutosPeloAgendamento(1));
    }

    @DisplayName("Deletar todos os produtos deve chamar o método de deletar todos por ID")
    @Test
    void deletarTodosProdutos() {
        List<Integer> ids = List.of(1, 2, 3);
        ListaProduto produto = Mockito.mock(ListaProduto.class);
        Mockito.when(listaProdutoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(produto));
        Mockito.doNothing().when(listaProdutoRepository).deleteAllById(ArgumentMatchers.anyList());
        listaProdutoService.deleteAll(ids);
        Mockito.verify(listaProdutoRepository).deleteAllById(ids);
    }
}