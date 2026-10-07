package hu.parlament.votesummary;

import hu.parlament.votesummary.rest.response.VoteSummaryFinalIdResponse;
import hu.parlament.voting.rest.request.VotingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface VoteSummaryMapper {

    VoteSummaryFinalIdResponse toResponse(VoteSummary entity);

    VoteSummary toEntity(VoteSummaryFinalIdResponse response);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voteDate", source = "idopont")
    @Mapping(target = "subject", source = "targy")
    @Mapping(target = "votingType", source = "tipus")
    @Mapping(target = "procedureType", source = "eljaras")
    @Mapping(target = "president", source = "elnok")
    @Mapping(target = "votes", source = "szavazatok")
    VoteSummary toEntity(VotingRequest votingRequest);
}
