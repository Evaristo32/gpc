package br.com.gpc.mapper;

import br.com.gpc.domain.Usuario;
import br.com.gpc.dto.UsuarioDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {
}
