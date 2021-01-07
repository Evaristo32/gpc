package br.com.gpc.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "gpc",name = "pauta_usuario",
            joinColumns = {
                    @JoinColumn(name = "id_pauta", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "usuario_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Usuario> usuarios;

}
