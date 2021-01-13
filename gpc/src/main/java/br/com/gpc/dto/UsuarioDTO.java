package br.com.gpc.dto;

import br.com.gpc.util.constant.MensagensBeanValidationUtil;
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

    @Size(min = 11,max = 11, message = MensagensBeanValidationUtil.CPF_TAMANHO)
    @NotEmpty(message = MensagensBeanValidationUtil.CPF_OBRIGATORIO)
    private String cpf;
}
