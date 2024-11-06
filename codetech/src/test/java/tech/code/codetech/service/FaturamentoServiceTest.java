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
import tech.code.codetech.model.Faturamento;
import tech.code.codetech.repository.FaturamentoRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class FaturamentoServiceTest {

    @InjectMocks
    private FaturamentoService faturamentoService;

    @Mock
    private FaturamentoRepository faturamentoRepository;

    FaturamentoServiceTest(){

    }

    @BeforeEach
    void setUp(){

    }

    @DisplayName("Salvar Faturamento deve retornar faturamento salvo")
    @Test
    void salvarFaturamento(){
        Faturamento faturamento = Mockito.mock(Faturamento.class);
        Mockito.when(this.faturamentoRepository.save(ArgumentMatchers.any(Faturamento.class))).thenReturn(faturamento);
        Assertions.assertEquals(faturamento, this.faturamentoService.save(faturamento));
    }

    @DisplayName("Atualizar Faturamento deve retornar o faturamento atualizado")
    @Test
    void atualizarFaturamento(){
        Faturamento faturamento = Mockito.mock(Faturamento.class);
        Mockito.when(this.faturamentoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Mockito.when(this.faturamentoRepository.save(ArgumentMatchers.any(Faturamento.class))).thenReturn(faturamento);
        Assertions.assertEquals(faturamento, this.faturamentoService.update(1, faturamento));
    }

    @DisplayName("Atualizar Faturamento deve retornar null se faturamento não existir")
    @Test
    void atualizarFaturamentoNaoExistente(){
        Mockito.when(this.faturamentoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertNull(this.faturamentoService.update(1, Mockito.mock(Faturamento.class)));
    }

    @DisplayName("Listar deve retornar uma lista de faturamentos")
    @Test
    void listarCheio(){
        List<Faturamento> faturamentos = List.of(Mockito.mock(Faturamento.class));
        Mockito.when(this.faturamentoRepository.findAll()).thenReturn(faturamentos);
        Assertions.assertEquals(faturamentos, this.faturamentoService.findAll());
    }

    @DisplayName("Listar deve retornar uma lista vazia, caso não tenha faturamentos cadastradas no banco")
    @Test
    void listarVazio(){
        List<Faturamento> faturamentos = List.of();
        Mockito.when(this.faturamentoRepository.findAll()).thenReturn(faturamentos);
        Assertions.assertEquals(faturamentos, this.faturamentoService.findAll());
    }

    @DisplayName("Faturamento Por ID deve retornar um faturamento")
    @Test
    void porId(){
        Faturamento faturamento = Mockito.mock(Faturamento.class);
        Mockito.when(this.faturamentoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(faturamento));
        Assertions.assertEquals(faturamento, this.faturamentoService.findById(1));
    }

    @DisplayName("Faturamento Por ID deve retornar null caso o faturamento não seja encontrado")
    @Test
    void porIdNaoEncontrado(){
        Mockito.when(this.faturamentoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Assertions.assertNull(this.faturamentoService.findById(1));
    }

    @DisplayName("Deletar Faturamento deve retornar true se o faturamento for deletado")
    @Test
    void deletarFaturamento(){
        Mockito.when(this.faturamentoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Assertions.assertTrue(this.faturamentoService.delete(1));
    }

    @DisplayName("Deletar Faturamento deve retornar false se o faturamento for deletado")
    @Test
    void deletarFaturamentoNaoExistente(){
        Mockito.when(this.faturamentoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertFalse(this.faturamentoService.delete(1));
    }
}