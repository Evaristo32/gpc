package br.com.gpc.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_resultado_votacao", schema = "gpc")
public class ResultaVotacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gpc.sq_resultado_votacao")
    @SequenceGenerator(name = "gpc.sq_resultado_votacao", sequenceName = "gpc.sq_resultado_votacao", allocationSize = 1)
    private Long id;

    @Column(name = "voto_favoravel", nullable = false, length = 20)
    private Integer votosFavoraveis;

    @Column(name = "voto_oposto", nullable = false, length = 20)
    private Integer votosOpostos;

    @Column(name = "total_voto", nullable = false, length = 20)
    private Integer totalDeVotos;

    @Column(name = "mensagem", nullable = false, length = 100)
    private String mensagem;

    @OneToOne
    @JoinColumn(name = "id_pauta", nullable = false)
    private Pauta pauta;

}
