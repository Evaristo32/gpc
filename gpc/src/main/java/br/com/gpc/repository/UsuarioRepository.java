package br.com.gpc.repository;

import br.com.gpc.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from  Usuario u where upper( u.cpf ) = upper(:cpf)")
    Optional<Usuario> buscarUsuarioPorCpf(@Param("cpf") String cpf);
}
