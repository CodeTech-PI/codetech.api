package tech.code.codetech.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NaoEncontradoException extends RuntimeException{

    public NaoEncontradoException() {
        super();
    }

    public NaoEncontradoException(String message) {
        super(String.format("%s não encontrado", message));
    }


}
