package br.com.gpc.domain;

import br.com.gpc.enums.StatusVotoEnum;
import br.com.gpc.enums.converter.StatusVotoConverter;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_voto")
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_voto")
    @SequenceGenerator(name = "sq_voto", sequenceName = "sq_voto", allocationSize = 1)
    private Long id;

    @Convert(converter = StatusVotoConverter.class)
    @Column(name = "voto", length = 3, nullable = false)
    private StatusVotoEnum voto;

    @ManyToOne
    @JoinColumn(name = "id_pauta", nullable = false)
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
