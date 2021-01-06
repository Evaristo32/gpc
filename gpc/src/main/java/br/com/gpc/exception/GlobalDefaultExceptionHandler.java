package br.com.gpc.exception;


import br.com.gpc.dto.ErrorInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NegocioException.class)
    @ResponseBody
    ErrorInfoDTO
    handleBadRequest(Exception ex) {
        return new ErrorInfoDTO(ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody ErrorInfoDTO
    handleInternoSeverError(Exception ex) {
        return new ErrorInfoDTO(ex);
    }

}
