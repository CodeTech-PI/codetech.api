package tech.code.codetech.strategy;

import tech.code.codetech.api.controller.generico.StatusUnidade;
import tech.code.codetech.model.Unidade;
import java.util.List;

public interface UnidadeInterface {

    Unidade save(Unidade unidade);
    List<Unidade> findAll();
    Unidade findById(Integer id);
    Unidade update(Integer id, Unidade unidade);
    boolean delete(Integer id);
    int quantidadeDeUnidades();
    List<Unidade> findAllByStatus(StatusUnidade statusUnidade);
}