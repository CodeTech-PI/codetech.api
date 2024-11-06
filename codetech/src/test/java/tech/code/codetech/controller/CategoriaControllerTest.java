package tech.code.codetech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tech.code.codetech.api.controller.CategoriaController;
import tech.code.codetech.config.CustomWithMockUser;
import tech.code.codetech.fixture.CategoriaFixture;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.repository.CategoriaRepository;
import tech.code.codetech.service.CategoriaService;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoriaControllerTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @CustomWithMockUser
    @DisplayName("Criar deve retornar a categoria criada e o status 201 (CREATED)")
    @Test
    void criarCategoria() throws Exception {
        Categoria categoria = CategoriaFixture.buildCategoria();
        Mockito.when(this.categoriaService.save(Mockito.any(Categoria.class))).thenReturn(categoria);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").isNotEmpty());
    }

    @DisplayName("Atualizar Categoria deve retornar a categoria atualizada")
    @Test
    void atualizarCategoria(){
        Categoria categoria = CategoriaFixture.buildCategoria();
        Mockito.when(this.categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Mockito.when(this.categoriaRepository.save(ArgumentMatchers.any(Categoria.class))).thenReturn(categoria);
        Assertions.assertEquals(categoria, this.categoriaService.update(1, categoria));
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
    @DisplayName("Deletar deve retornar true se a categoria for deletada")
    @Test
    void deletarCategoria(){
        Mockito.when(categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(true);
        Assertions.assertTrue(categoriaService.delete(1));
    }
    @DisplayName("Deletar deve retornar true se a categoria for deletada")
    @Test
    void deletarCategoriaNaoExistente(){
        Mockito.when(categoriaRepository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
        Assertions.assertFalse(categoriaService.delete(1));
    }

}
