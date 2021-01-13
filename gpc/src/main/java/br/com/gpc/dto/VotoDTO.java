package br.com.gpc.dto;

import br.com.gpc.enums.StatusVotoEnum;
import br.com.gpc.util.constant.MensagensBeanValidationUtil;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    private Long id;

    @NotNull(message = MensagensBeanValidationUtil.VOTO_OBRIGATORIO)
    private StatusVotoEnum voto;

    @NotNull(message = MensagensBeanValidationUtil.PAUTA_OBRIGATORIO)
    private PautaDTO pauta;

    @NotNull(message = MensagensBeanValidationUtil.USUARIO_OBRIGATORIO)
    private UsuarioDTO usuario;
}

