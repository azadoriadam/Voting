package hu.parlament.votesummary;

import hu.parlament.vote.VoteMapper;
import hu.parlament.votes.rest.request.VotesRequest;
import hu.parlament.votesummary.rest.response.VoteSummaryResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = { VoteMapper.class, VoteSummaryEvaluate.class })
public interface VoteSummaryMapper {

    @Mapping(target = "voteDate", source = "idopont")
    @Mapping(target = "subject", source = "targy")
    @Mapping(target = "votingType", source = "tipus")
    @Mapping(target = "procedureType", source = "eljaras")
    @Mapping(target = "president", source = "elnok")
    @Mapping(target = "votes", source = "szavazatok")
    VoteSummary toEntity(VotesRequest votesRequest);

    @Mapping(target = "idopont", source = "voteDate")
    @Mapping(target = "targy", source = "subject")
    @Mapping(target = "tipus", source = "votingType")
    @Mapping(target = "eljaras", source = "procedureType")
    @Mapping(target = "elnok", source = "president")
    @Mapping(target = "szavazatok", source = "votes")
    @Mapping(target = "kepviselokSzama", expression = "java(summary.getVotes().size())")
    @Mapping(target = "eredmeny", source = "summary")
    VoteSummaryResponse toResponse(VoteSummary summary);

    List<VoteSummaryResponse> toResponses(List<VoteSummary> voteSummaries);
}
