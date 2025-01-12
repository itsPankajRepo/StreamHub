package com.stream.hub.userMgmt.attributeConvertor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.Objects;

@Converter
public class ListToStringConvertor implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return strings.isEmpty() ? null : String.join(",", strings);

    }

    @Override
    public List<String> convertToEntityAttribute(String string) {

        return Objects.isNull(string) ? List.of() : List.of(string.split(","));
    }
}
