package hu.parlament.votesummary;



import hu.parlament.enums.VoteSummaryResult;
import hu.parlament.enums.VoteValue;
import hu.parlament.validation.ValidationBuilder;
import hu.parlament.vote.Vote;
import hu.parlament.vote.VoteRequest;
import hu.parlament.vote.VoteService;
import hu.parlament.vote.rest.response.VoteValueResponse;
import hu.parlament.votesummary.rest.response.VoteSummariesResponse;
import hu.parlament.votesummary.rest.response.VoteSummaryFinalIdResponse;
import hu.parlament.votes.rest.request.VotesRequest;
import hu.parlament.votesummary.rest.response.VoteSummaryResponse;
import hu.parlament.votesummary.rest.response.VoteSummaryResultResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VoteSummaryService {

    private static final ZoneId BUDAPEST = ZoneId.of("Europe/Budapest");

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

    public ResponseEntity<VoteSummaryResultResponse> findVoteSummaryResult(String summaryId) {
        ValidationBuilder.of().failIf(() -> summaryId == null, "VoteSummary id is required").validate();
        ValidationBuilder.of().failIf(() -> summaryId != null && summaryId.isBlank(), "VoteSummary id must not be blank").validate();

        return ResponseEntity.ok(evaluateResult(summaryId));
    }

    public VoteSummaryResultResponse evaluateResult(String summaryId) {
        VoteSummary summary = getVoteSummaryByFinalId(summaryId);
        Integer id = summary.getId();

        return new VoteSummaryResultResponse(
                calculateVoteResult(summary),
                getLastVoteCountOrDefault(id,200),
                countByIdAndVoteValue(id, VoteValue.i),
                countByIdAndVoteValue(id, VoteValue.n),
                countByIdAndVoteValue(id, VoteValue.t)
        );
    }

    public VoteSummaryResult calculateVoteResult(VoteSummary summary) {
        if(summary == null || summary.getVotes() == null || summary.getVotes().isEmpty()) {
            return null;
        }

        Integer summaryId = summary.getId();
        int allVotesNumbers = summary.getVotes().size();
        Integer iVotesNumbers = countByIdAndVoteValue(summaryId, VoteValue.i);
        Integer lastVoteCountOrHalfMax = getLastVoteCountOrDefault(summaryId,100);

        return switch (summary.getVotingType()) {
            case j -> VoteSummaryResult.F;
            case e -> iVotesNumbers > allVotesNumbers/2 ? VoteSummaryResult.F : VoteSummaryResult.U;
            case m -> iVotesNumbers > lastVoteCountOrHalfMax ? VoteSummaryResult.F : VoteSummaryResult.U;
        };
    }

    public ResponseEntity<VoteSummariesResponse> findByDay(LocalDate date) {
        if(date == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Instant start = date.atStartOfDay(BUDAPEST).toInstant();
        Instant end = date.plusDays(1L).atStartOfDay(BUDAPEST).toInstant();

        List<VoteSummaryResponse> list = mapper.toResponses(repository.findByCreatedAtBetween(start, end));
        return ResponseEntity.ok(new VoteSummariesResponse(list));
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

    @NonNull
    private Integer getLastVoteCountOrDefault(Integer summaryId, Integer defaultCount) {
        return getLastNotEqualId(summaryId).map(VoteSummary::getVotes).map(List::size).orElse(defaultCount);
    }

    @Transactional(readOnly = true)
    private Integer countByIdAndVoteValue(Integer summaryId, VoteValue voteValue) {
        return repository.countByIdAndVotes_VoteValue(summaryId, voteValue);
    }

    @Transactional(readOnly = true)
    private Optional<VoteSummary> getLastNotEqualId(Integer summaryId) {
        if (summaryId == null) {
            return Optional.empty();
        }
        return repository.findFirstByIdNotOrderByCreatedAtDesc(summaryId);
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
        return listGetter.apply(entity).stream().map(nameGetter).filter(Objects::nonNull).filter(name -> !seen.add(name))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private VoteSummary getVoteSummaryByFinalId(String finalId) {
        Integer id = VoteSummaryFinalIdResponse.getId(finalId);
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "VoteSummary not found"));
    }
}
