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
import tech.code.codetech.exception.naoencontrado.OrdemServicoNaoEncontradaException;
import tech.code.codetech.exception.naoencontrado.base.NaoEncontradaException;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.repository.OrdemServicoRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class OrdemServicoServiceTest {

    @InjectMocks
    private OrdemServicoService ordemServicoService;

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    OrdemServicoServiceTest(){

    }

    @BeforeEach
    void setUp(){

    }

    @DisplayName("Salvar Ordem de Serviço deve retornar a OS salva")
    @Test
    void salvarOrdemServico(){
        OrdemServico ordemServico = Mockito.mock(OrdemServico.class);
        Mockito.when(this.ordemServicoRepository.save(ArgumentMatchers.any(OrdemServico.class))).thenReturn(ordemServico);
        Assertions.assertEquals(ordemServico, this.ordemServicoService.save(ordemServico));
    }

    @DisplayName("Atualizar Ordem de Serviço deve retornar a OS atualizada")
    @Test
    void atualizarOrdemServico(){
        OrdemServico ordemServico = Mockito.mock(OrdemServico.class);
        Mockito.when(this.ordemServicoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(this.ordemServicoRepository.save(ArgumentMatchers.any(OrdemServico.class))).thenReturn(ordemServico);
        Assertions.assertEquals(ordemServico, this.ordemServicoService.update(1, ordemServico));
    }

    @DisplayName("Atualizar Ordem de Serviço deve retornar (Ordem De Serviço Não Encontrada Exception) se a OS não existir")
    @Test
    void atualizarOrdemServicoNaoExistente(){
        Mockito.when(this.ordemServicoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
           this.ordemServicoService.update(1, Mockito.mock(OrdemServico.class));
        });
    }

    @DisplayName("Listar deve retornar uma Lista de Ordens de Serviço")
    @Test
    void listarCheio(){
        List<OrdemServico> ordensServicos = List.of(Mockito.mock(OrdemServico.class));
        Mockito.when(this.ordemServicoRepository.findAll()).thenReturn(ordensServicos);
        Assertions.assertEquals(ordensServicos, this.ordemServicoService.findAll());

    }

    @DisplayName("Listar deve retornar uma lista vazia, caso não tenha ordens de serviço cadastradas no banco de dados")
    @Test
    void listarVazio(){
        List<OrdemServico> ordensServico = List.of();
        Mockito.when(this.ordemServicoRepository.findAll()).thenReturn(ordensServico);
        Assertions.assertEquals(ordensServico, this.ordemServicoService.findAll());
    }

    @DisplayName("Ordem de Serviço Por ID deve retornar uma OS")
    @Test
    void porId(){
        OrdemServico ordemServico = Mockito.mock(OrdemServico.class);
        Mockito.when(this.ordemServicoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            this.ordemServicoService.findById(1);
        });
    }

    @DisplayName("Ordem de Serviço Por ID deve retornar (Ordem de Serviço Não Encontrada Exception) caso a OS não seja encontrada")
    @Test
    void porIdNaoEncontrada(){
        Mockito.when(this.ordemServicoRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(OrdemServicoNaoEncontradaException.class, () ->{
           this.ordemServicoService.findById(1);
        });
    }

    @DisplayName("Deletar Ordem de Serviço deve retornar true se a OS for deletada")
    @Test
    void deletarOrdemServico(){
        Mockito.when(this.ordemServicoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Assertions.assertTrue(this.ordemServicoService.delete(1));
    }

    @DisplayName("Deletar Ordem de Serviço deve retornar false se a OS não for deletada")
    @Test
    void deletarOrdemServicoNaoExistente(){
        Mockito.when(this.ordemServicoRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertFalse(this.ordemServicoService.delete(1));
    }

}
