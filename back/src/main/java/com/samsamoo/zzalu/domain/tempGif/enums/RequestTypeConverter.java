package com.samsamoo.zzalu.domain.tempGif.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class RequestTypeConverter  implements AttributeConverter<RequestType,String> {
    @Override
    public String convertToDatabaseColumn(RequestType type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public RequestType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(RequestType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

