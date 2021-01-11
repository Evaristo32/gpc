package br.com.gpc.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultaVotacaoDTO {
    private PautaDTO pauta;
    private Integer votosFavoraveis;
    private Integer votosOpostos;
    private Integer totalDeVotos;
    private String mensagem;
}
