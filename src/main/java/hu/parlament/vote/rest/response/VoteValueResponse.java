package hu.parlament.vote.rest.response;

import hu.parlament.enums.VoteValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Egy képviselő szavazata")
public record VoteValueResponse(VoteValue szavazat) {
}
