package br.com.gpc.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_pauta", schema = "gpc")
public class Pauta  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gpc.sq_pauta")
    @SequenceGenerator(name = "gpc.sq_pauta", sequenceName = "gpc.sq_pauta", allocationSize = 1)
    private Long id;

    @Column(name = "tema", nullable = false, length = 100)
    private String tema;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;


}
