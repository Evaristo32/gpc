package br.com.gpc.mapper;

import br.com.gpc.domain.ResultaVotacao;
import br.com.gpc.dto.ResultaVotacaoDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {PautaMapper.class})
public interface ResultadoVotacaoMapper extends EntityMapper<ResultaVotacaoDTO, ResultaVotacao> {
}