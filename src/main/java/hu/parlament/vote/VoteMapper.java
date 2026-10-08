package hu.parlament.vote;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface VoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voterName", source = "kepviselo")
    @Mapping(target = "voteValue", source = "szavazat")
    Vote toEntity(VoteRequest request);

}
