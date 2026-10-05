package hu.parlament.enums.producertype;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProcedureTypeConverter implements AttributeConverter<ProcedureType, String> {

    @Override
    public String convertToDatabaseColumn(ProcedureType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public ProcedureType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ProcedureType.fromCode(dbData);
    }
}