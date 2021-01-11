package br.com.gpc.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum StatusVotoEnum {

    SIM("SIM"),
    NAO("NÂO");

    private final String descricao;

    public static StatusVotoEnum buscarSituacaoVoto(String descricao) {
        return Stream.of(StatusVotoEnum.values())
                .filter(stv -> stv.getDescricao().equals(descricao))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
