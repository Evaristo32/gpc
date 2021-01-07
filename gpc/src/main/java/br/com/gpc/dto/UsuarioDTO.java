package br.com.gpc.dto;

import br.com.gpc.domain.Pauta;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String cpf;
    private Set<Pauta> pautas;
}
