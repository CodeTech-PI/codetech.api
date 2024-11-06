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
import tech.code.codetech.model.Categoria;
import tech.code.codetech.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

     CategoriaServiceTest() {
    }

    @BeforeEach
    void setUp(){

    }

    @DisplayName("Salvar Categoria deve retornar a Categoria salva")
    @Test
    void salvarCategoria(){
        Categoria categoria = Mockito.mock(Categoria.class);
        Mockito.when(categoriaRepository.save(ArgumentMatchers.any(Categoria.class))).thenReturn(categoria);
        Assertions.assertEquals(categoria, this.categoriaService.save(categoria));
    }

    @DisplayName("Atualizar Categoria deve retornar a categoria atualizada")
    @Test
    void atualizarCategoria(){
        Categoria categoria = Mockito.mock(Categoria.class);
        Mockito.when(this.categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Mockito.when(this.categoriaRepository.save(ArgumentMatchers.any(Categoria.class))).thenReturn(categoria);
        Assertions.assertEquals(categoria, this.categoriaService.update(1, categoria));
    }

    @DisplayName("Atualizar Categoria deve retornar null se a categoria não existir")
    @Test
    void atualizarCategoriaNaoExistente(){
        Mockito.when(this.categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertNull(categoriaService.update(1, Mockito.mock(Categoria.class)));
    }

    @DisplayName("Listar deve retornar uma lista de categorias")
    @Test
    void listarCheio(){
         List<Categoria> categorias = List.of(Mockito.mock(Categoria.class));
         Mockito.when(this.categoriaRepository.findAll()).thenReturn(categorias);
        Assertions.assertEquals(categorias, this.categoriaService.findAll());
    }

    @DisplayName("Listar deve retornar uma lista vazia, caso não tenha categorias cadastradas no banco")
    @Test
    void listarVazio(){
         List<Categoria> categorias = List.of();
         Mockito.when(this.categoriaRepository.findAll()).thenReturn(categorias);
         Assertions.assertEquals(categorias, this.categoriaService.findAll());
    }


    @DisplayName("Categoria Por ID deve retornar uma categoria")
    @Test
    void porId(){
         Categoria categoria = Mockito.mock(Categoria.class);
         Mockito.when(this.categoriaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(categoria));
         Assertions.assertEquals(categoria, this.categoriaService.findById(1));
    }

    @DisplayName("Categoria Por ID deve retornar null caso a categoria não seja encontrada")
    @Test
    void porIdNaoEncontrado(){
         Mockito.when(this.categoriaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
         Assertions.assertNull(this.categoriaService.findById(1));
    }

    @DisplayName("Deletar Categoria deve retornar true se a categoria for deletada")
    @Test
    void deletarCategoria(){
         Mockito.when(categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
         Assertions.assertTrue(categoriaService.delete(1));
    }

    @DisplayName("Deletar Categoria deve retornar false se a categoria não for deletada")
    @Test
    void deletarCategoriaNaoExistente(){
        Mockito.when(categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertFalse(categoriaService.delete(1));
    }
}