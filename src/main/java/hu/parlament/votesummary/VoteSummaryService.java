package hu.parlament.votesummary;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VoteSummaryService {

    private final VoteSummaryRepository voteSummaryRepository;

    @Transactional(readOnly = true)
    public VoteSummary getByVoteId(Integer voteId) throws ResponseStatusException {
        return voteSummaryRepository.findById(voteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vote summary not found for vote: " + voteId));
    }

}
