package tech.code.codetech.strategy;

import tech.code.codetech.model.OrdemServico;

public interface OrdemServicoInterface {

   OrdemServico save(OrdemServico ordemServico);

   OrdemServico findById(Integer id);

   OrdemServico update(Integer id, OrdemServico ordemServico);

   boolean delete(Integer id);

}