package br.com.gpc.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pauta")
public class Pauta  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_pauta")
    @SequenceGenerator(name = "sq_pauta", sequenceName = "sq_pauta", allocationSize = 1)
    private Long id;

    @Column(name = "tema", nullable = false, length = 100)
    private String tema;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pauta_usuario",
            joinColumns = {
                    @JoinColumn(name = "id_pauta", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "usuario_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Usuario> usuarios;

    @Column(name = "data_hora_votacao")
    private LocalDateTime dataHoraVotacao;

    @Column(name = "resultado_enviado")
    private Boolean resultadoEnviado;
}
