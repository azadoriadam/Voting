package hu.parlament.votesummary;

import hu.parlament.enums.VoteSummaryResult;
import hu.parlament.enums.VoteValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteSummaryEvaluate {

    private final VoteSummaryRepository repository;

    public VoteSummaryResult calculateVoteResult(VoteSummary summary) {
        if (summary == null || summary.getVotes() == null || summary.getVotes().isEmpty()) {
            return null;
        }

        Integer summaryId = summary.getId();
        int allVotesNumbers = summary.getVotes().size();
        Integer iVotesNumbers = repository.countByIdAndVotes_VoteValue(summaryId, VoteValue.i);
        Integer lastVoteCountOrHalfMax = repository.findFirstByIdNotOrderByCreatedAtDesc(summaryId)
                .map(VoteSummary::getVotes).map(List::size).orElse(100);

        return switch (summary.getVotingType()) {
            case j -> VoteSummaryResult.F;
            case e -> iVotesNumbers > allVotesNumbers / 2 ? VoteSummaryResult.F : VoteSummaryResult.U;
            case m -> iVotesNumbers > lastVoteCountOrHalfMax ? VoteSummaryResult.F : VoteSummaryResult.U;
        };
    }

}
