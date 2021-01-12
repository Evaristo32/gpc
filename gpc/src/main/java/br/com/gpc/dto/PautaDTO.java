package br.com.gpc.dto;

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

    @Size(max = 100, message = "O tamanho máximo do campo tema é de 100 caracteres!")
    @NotEmpty(message = "O campo tema e de preenchimento obrigatório!")
    private String tema;

    @Size(max = 255, message = "O tamanho máximo do campo descrição é de 255 caracteres!")
    @NotEmpty(message = "O campo descrição e de preenchimento obrigatório!")

    private String descricao;

    private Set<UsuarioDTO> usuarios;

    private LocalDateTime dataHoraVotacao;

    private Boolean resultadoEnviado;
}
