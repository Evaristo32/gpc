package br.com.gpc.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum StatusUsuarioEnum {

    ABLE_TO_VOTE("ABLE_TO_VOTE"),
    UNABLE_TO_VOTE("UNABLE_TO_VOTE");

    private final String descricao;

    public static StatusUsuarioEnum getTipoEmpresaEnumDescricao(String descricao) {
        return Stream.of(StatusUsuarioEnum.values())
                .filter(stu -> stu.getDescricao().equals(descricao))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
