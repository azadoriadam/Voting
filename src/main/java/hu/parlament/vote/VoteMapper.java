package hu.parlament.vote;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface VoteMapper {

    VoteDTO toDTO(Vote entity);

    Vote toEntity(VoteDTO dto);

}
