package br.com.gpc.mapper;

import br.com.gpc.domain.Pauta;
import br.com.gpc.dto.PautaDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {UsuarioMapper.class})
public interface PautaMapper extends EntityMapper<PautaDTO, Pauta> {
}