package br.com.gpc.service.impl;

import br.com.gpc.domain.Pauta;
import br.com.gpc.dto.PautaDTO;
import br.com.gpc.exceptions.NegocioException;
import br.com.gpc.mapper.PautaMapper;
import br.com.gpc.repository.PautaRepository;
import br.com.gpc.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PautaServiceImpl implements PautaService {

    private Logger logger = LoggerFactory.getLogger(PautaServiceImpl.class);
    private final PautaRepository pautaRepository;
    private final PautaMapper pautaMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public PautaDTO cadastrarPauta(PautaDTO pautaDTO) throws NegocioException{
        this.logger.info("Method cadastrarPauta.");
        Optional<Pauta> opPauta = this.pautaRepository.buscarPautaPorTema(pautaDTO.getTema());
        if(opPauta.isPresent()) {
            throw new NegocioException("Já existe uma pauta com esse tema.");
        }
        return this.pautaMapper.toDto(this.pautaRepository.save(this.pautaMapper.toEntity(pautaDTO)));
    }
}
