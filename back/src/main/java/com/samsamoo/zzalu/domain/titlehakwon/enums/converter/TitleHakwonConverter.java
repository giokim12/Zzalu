package com.samsamoo.zzalu.domain.titlehakwon.enums.converter;

import com.samsamoo.zzalu.domain.titlehakwon.enums.TitleHakwonState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;
@Converter
public class TitleHakwonConverter  implements AttributeConverter<TitleHakwonState,String> {
    @Override
    public String convertToDatabaseColumn(TitleHakwonState state) {

        if (state == null) {

            return null;
        }
        return state.getCode();
    }

    @Override
    public TitleHakwonState convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(TitleHakwonState.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
