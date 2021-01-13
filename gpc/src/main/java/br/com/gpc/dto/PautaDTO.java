package br.com.gpc.dto;

import br.com.gpc.util.constant.MensagensBeanValidationUtil;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    private Long id;

    @Size(max = 100, message = MensagensBeanValidationUtil.TEMA_TAMANHO_MAXIMO)
    @NotEmpty(message = MensagensBeanValidationUtil.TEMA_OBRIGATORIO)
    private String tema;

    @Size(max = 255, message = MensagensBeanValidationUtil.DESCRICAO_TAMANHO_MAXIMO)
    @NotEmpty(message = MensagensBeanValidationUtil.DESCRICAO_OBRIGATORIO)
    private String descricao;

    private Set<UsuarioDTO> usuarios;

    private LocalDateTime dataHoraVotacao;

    private Boolean resultadoEnviado;
}
