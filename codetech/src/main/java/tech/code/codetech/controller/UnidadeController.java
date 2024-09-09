package tech.code.codetech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tech.code.codetech.model.Unidade;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    public Unidade buscarUnidadePorCep(String cep){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/"+cep+"/json/";
        return restTemplate.getForObject(url, Unidade.class);
    }

    public void ordenarPorLogradouro(Unidade[] unidades){
        for (int i = 0; i < unidades.length - 1 ; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < unidades.length; j++) {
                String logradouroAtual = unidades[j].getLogradouro();
                String logradouroMenor = unidades[indiceMenor].getLogradouro();

                if(logradouroAtual != null && (logradouroMenor == null || logradouroAtual.compareTo(logradouroMenor) < 0)){
                    indiceMenor = j;
                }
            }
            if(indiceMenor != i){
                Unidade unidadeTemporaria = unidades[i];
                unidades[i] = unidades[indiceMenor];
                unidades[indiceMenor] = unidadeTemporaria;
            }
        }
    }

    public Unidade[] buscarEordenarUnidadesPorLogradouro(String[] ceps){
           Unidade[] unidades = new Unidade[ceps.length];
        for (int i = 0; i < ceps.length; i++) {
            unidades[i] = buscarUnidadePorCep(ceps[i]);
        }
        ordenarPorLogradouro(unidades);
        return unidades;
    }

    public static void main(String[] args){
        UnidadeController controller = new UnidadeController();
        String[] ceps = {"01001-000", "01002-000", "01003-000", "01234-000", "00000-001"};
        Unidade[] unidades = controller.buscarEordenarUnidadesPorLogradouro(ceps);
        for (Unidade unidade : unidades) {
            if(unidade != null && unidade.getLogradouro() != null){
                System.out.println(unidade.getLogradouro());
            }else{
                System.out.println("Unidade não encontrada");
            }
        }
    }
}