package hu.parlament.voting.rest.resource;

import hu.parlament.votesummary.VoteSummaryService;
import hu.parlament.votesummary.rest.response.VoteSummaryFinalIdResponse;
import hu.parlament.voting.rest.request.VotingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/szavazasok")
@RequiredArgsConstructor
public class VotingController {

    private final VoteSummaryService voteSummaryService;

    @PostMapping(path = "/szavazas" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteSummaryFinalIdResponse> vote(@RequestBody VotingRequest votingRequest) {
        voteSummaryService.validate(votingRequest);
        return voteSummaryService.handleVoting(votingRequest);
    }
}
