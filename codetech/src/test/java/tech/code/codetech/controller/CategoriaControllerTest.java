package tech.code.codetech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tech.code.codetech.api.controller.CategoriaController;
import tech.code.codetech.config.CustomWithMockUser;
import tech.code.codetech.fixture.CategoriaFixture;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.service.CategoriaService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith({SpringExtension.class})
@WebMvcTest(controllers = {CategoriaController.class})
class CategoriaControllerTest {

    @MockBean
    private CategoriaService categoriaService;

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

    @CustomWithMockUser //por em todos
    @DisplayName("Atualizar deve retornar a categoria atualizada e o status 200 (OK)")
    @Test
    void atualizarCategoria() throws Exception {
        Categoria categoria = Mockito.mock(Categoria.class);
        Mockito.when(this.categoriaService.update(Mockito.eq(1), Mockito.any(Categoria.class))).thenReturn(categoria);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categorias/", 1)
                        .with(csrf()) //somente em  put e post
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").isNotEmpty());
    }

}