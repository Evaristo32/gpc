package br.com.gpc.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorInfoDTO {
    private String mensagem;

    public ErrorInfoDTO( Exception ex){
        this.mensagem = ex.getMessage();
    }

}
