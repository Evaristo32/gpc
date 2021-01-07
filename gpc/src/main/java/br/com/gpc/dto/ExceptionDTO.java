package br.com.gpc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDTO {

    private String mensagem;

    public ExceptionDTO( Exception ex){
        this.mensagem = ex.getMessage();
    }
}
