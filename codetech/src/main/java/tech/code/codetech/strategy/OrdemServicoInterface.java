package tech.code.codetech.strategy;

import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.model.OrdemServico;

import java.util.List;

public interface OrdemServicoInterface {

   OrdemServico save(OrdemServico ordemServico);

   OrdemServico findById(Integer id);

   OrdemServico update(Integer id, OrdemServico ordemServico);

   boolean delete(Integer id);

   List<OrdemServico> findAll();

    OrdemServicoLucroDto executarOrdemServico(OrdemServico model);

    OrdemServicoLucroDto atualizarOrdemServico(OrdemServico model, Integer id);

    List<OrdemServicoLucroDto> buscarTodos();
    OrdemServicoLucroDto buscarPorId(Integer id);
}