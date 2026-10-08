package hu.parlament.votesummary;



import hu.parlament.enums.VoteValue;
import hu.parlament.validation.ValidationBuilder;
import hu.parlament.vote.Vote;
import hu.parlament.vote.VoteRequest;
import hu.parlament.vote.VoteService;
import hu.parlament.vote.rest.response.VoteValueResponse;
import hu.parlament.votesummary.rest.response.VoteSummaryFinalIdResponse;
import hu.parlament.votes.rest.request.VotesRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VoteSummaryService {

    private final VoteSummaryRepository repository;

    private final VoteSummaryMapper mapper;

    private final VoteService voteService;

    public ResponseEntity<VoteSummaryFinalIdResponse> handleVoting(VotesRequest votesRequest) throws ResponseStatusException {
        VoteSummary entity = mapper.toEntity(votesRequest);
        Integer savedEntityId = create(entity).getId();
        return ResponseEntity.ok(new VoteSummaryFinalIdResponse(savedEntityId));
    }

    @Transactional
    public VoteSummary create(VoteSummary summary) {
        validate(summary);
        VoteSummary save = repository.save(summary);
        log.info("Successfully created vote summary: {}", save);
        return save;
    }

    @Transactional(readOnly = true)
    public Vote findVote(String voterName, VoteValue voteValue) {
        return voteService.findByVoterNameAndVoteValue(voterName, voteValue)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found"));
    }

    public ResponseEntity<VoteValueResponse> findByVoterNameAndVoteValue(String voterName, VoteValue voteValue) {
        ValidationBuilder.of().failIf(() -> voterName == null || voterName.isBlank(), "Voter name is required").validate();
        ValidationBuilder.of().failIf(() -> voteValue == null, "Vote value is required").validate();

        Vote vote = findVote(voterName, voteValue);
        return ResponseEntity.ok(new VoteValueResponse(vote.getVoteValue()));
    }

    public void validate(VotesRequest request) {
        ValidationBuilder.of(request)
            .failIf(s -> s.idopont() == null, "'idopont' is required")
            .failIf(s -> s.targy() == null, "'targy' is required")
            .failIf(s -> s.targy() != null && s.targy().isBlank(), "'targy' must not be blank")
            .failIf(s -> s.elnok() == null, "'elnok' is required")
            .failIf(s -> s.elnok() != null && s.elnok().isBlank(), "'elnok' must not be blank")
            .failIf(s -> s.tipus() == null, "'tipus' is required")
            .failIf(s -> !getDuplicateNames(s).isEmpty(), (s) -> "Duplicate voters: " + String.join(", ", getDuplicateNames(s)))
            .validate();
    }

    private void validate(VoteSummary summary) {
        ValidationBuilder.of(summary)
            .failIf(s -> s.getSubject() == null || s.getSubject().isBlank(), "Subject is required")
            .failIf(s -> s.getVotingType() == null, "Voting type is required")
            .failIf(s -> s.getProcedureType() == null, "Procedure type is required")
            .failIf(s -> !getDuplicateNames(s).isEmpty(), (s) -> "Duplicate voters: " + String.join(", ", getDuplicateNames(s)))
            .validate();
    }

    private Set<String> getDuplicateNames(VoteSummary summary) {
        return getDuplicateNames(summary, VoteSummary::getVotes, Vote::getVoterName);
    }

    private Set<String> getDuplicateNames(VotesRequest request) {
       return getDuplicateNames(request, VotesRequest::szavazatok, VoteRequest::kepviselo);
    }

    private <X,Z> Set<String> getDuplicateNames(X entity, Function<X,List<Z>> listGetter, Function<Z,String> nameGetter) {
        if(entity == null || listGetter.apply(entity) == null || listGetter.apply(entity).isEmpty()) {
            return new HashSet<>();
        }
        Set<String> seen = new HashSet<>();
        return listGetter.apply(entity).stream().map(nameGetter).filter(name -> !seen.add(name))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


}
