package br.com.gpc.enums.converter;


import br.com.gpc.enums.StatusVotoEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class StatusVotoConverter implements AttributeConverter<StatusVotoEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusVotoEnum attribute) {
        return attribute.getDescricao();
    }

    @Override
    public StatusVotoEnum convertToEntityAttribute(String dbData) {
        if (Objects.nonNull(dbData) && dbData.trim().isEmpty())
            return null;
        return StatusVotoEnum.buscarSituacaoVoto(dbData);
    }

}
