package tech.code.codetech.exception.naoencontrado.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NaoEncontradaException extends RuntimeException{

    public NaoEncontradaException(String message){
        super(String.format("Não foi possível encontrar a entidade: %s", message));
    }



}
