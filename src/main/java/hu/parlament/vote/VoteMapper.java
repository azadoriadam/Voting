package hu.parlament.vote;

import hu.parlament.vote.rest.response.VoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface VoteMapper {

    @Mapping(target = "voterName", source = "kepviselo")
    @Mapping(target = "voteValue", source = "szavazat")
    Vote toEntity(VoteRequest request);

    @Mapping(target = "kepviselo", source = "voterName")
    @Mapping(target = "szavazat", source = "voteValue")
    VoteResponse toResponse(Vote entity);

}
