package br.com.gpc.dto;

import br.com.gpc.domain.Usuario;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    private Long id;
    private String tema;
    private String descricao;
    private Set<Usuario> usuarios;
}
