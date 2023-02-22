package com.samsamoo.zzalu.domain.notice.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class NoticeTypeConverter  implements AttributeConverter<NoticeType,String> {
    @Override
    public String convertToDatabaseColumn(NoticeType type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public NoticeType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(NoticeType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}