package br.com.gpc.mapper;

import br.com.gpc.domain.Voto;
import br.com.gpc.dto.VotoDTO;
import org.mapstruct.Mapper;

@Mapper
public interface VotoMapper extends EntityMapper<VotoDTO, Voto> {
}
