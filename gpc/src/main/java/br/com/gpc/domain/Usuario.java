package br.com.gpc.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuario", schema = "gpc")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
    @SequenceGenerator(name = "gpc.sq_usuario", sequenceName = "sq_usuario", allocationSize = 1)
    private Long id;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private Set<Pauta> pautas;

}
