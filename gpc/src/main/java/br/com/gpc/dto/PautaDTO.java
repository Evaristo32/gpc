package br.com.gpc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
public class PautaDTO {

    private Long id;
    private String tema;
    private String descricao;

    @Tolerate
    public PautaDTO() {
        super();
    }
}
