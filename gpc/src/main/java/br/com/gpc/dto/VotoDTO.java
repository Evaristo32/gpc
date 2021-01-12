package br.com.gpc.dto;

import br.com.gpc.enums.StatusVotoEnum;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    private Long id;

    @NotNull(message = "O campo voto é de preenchimento obrigatório!")
    private StatusVotoEnum voto;

    @NotNull(message = "O campo pauta é de preenchimento obrigatório!")
    private PautaDTO pauta;

    @NotNull(message = "O campo usuário é de preenchimento obrigatório!")
    private UsuarioDTO usuario;
}

