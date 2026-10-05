package hu.parlament.enums.votingtype;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VotingTypeConverter implements AttributeConverter<VotingType, String> {

    @Override
    public String convertToDatabaseColumn(VotingType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public VotingType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : VotingType.fromCode(dbData);
    }
}