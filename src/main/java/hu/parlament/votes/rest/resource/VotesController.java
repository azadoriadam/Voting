package hu.parlament.votes.rest.resource;

import hu.parlament.enums.VoteValue;
import hu.parlament.validation.ValidationBuilder;
import hu.parlament.vote.rest.response.VoteValueResponse;
import hu.parlament.votesummary.VoteSummaryService;
import hu.parlament.votesummary.rest.response.VoteSummaryFinalIdResponse;
import hu.parlament.votes.rest.request.VotesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Szavazások", description = "Szavazatok rögzítése és lekérdezése")
@RestController
@RequestMapping("/szavazasok")
@RequiredArgsConstructor
public class VotesController {

    private final VoteSummaryService voteSummaryService;

    @Operation(summary = "Egy képviselő szavazata",
            description = "Egy képviselő adott szavazáson leadott szavazatát adja vissza.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A szavazat megtalálva"),
            @ApiResponse(responseCode = "400", description = "Hiányzó paraméter",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Nincs ilyen szavazás, vagy a képviselő nem szavazott",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(path = "/szavazat", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteValueResponse> findVote(@RequestParam(name = "szavazas", required = false) VoteValue voteValue,
                                                      @RequestParam(name = "kepviselo", required = false) String voterName) {
        ValidationBuilder.of().failIf(() -> voterName == null || voterName.isBlank(), "'kepviselo' is required").validate();
        ValidationBuilder.of().failIf(() -> voteValue == null, "'szavazas' is required").validate();

        return voteSummaryService.findByVoterNameAndVoteValue(voterName,voteValue);
    }


    @Operation(summary = "Szavazás rögzítése",
            description = "Rögzíti a szavazást és a szavazatokat, visszaadja a szavazás azonosítóját.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A szavazás rögzítve"),
            @ApiResponse(responseCode = "500", description = "Hibás kérés, semmi nem lett rögzítve",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(path = "/szavazas",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteSummaryFinalIdResponse> voting(@RequestBody VotesRequest votesRequest) {
        voteSummaryService.validate(votesRequest);
        return voteSummaryService.handleVoting(votesRequest);
    }
}
