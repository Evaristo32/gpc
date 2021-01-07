package br.com.gpc.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @Size(min = 11,max = 11, message = "O campo campo cpf deve ser preenchido com 11 caracteres!")
    @NotEmpty(message = "O campo cpf é de preenchimento obrigatório!")
    private String cpf;
}
