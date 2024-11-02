package tech.code.codetech.fixture;


import tech.code.codetech.model.Categoria;

public class CategoriaFixture {
    public static Categoria buildCategoria(){
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Batoque");
        return categoria;
    }
}
