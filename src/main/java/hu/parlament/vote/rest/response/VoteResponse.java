package hu.parlament.vote.rest.response;

import hu.parlament.enums.VoteValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Egy képviselő neve és szavazata")
public record VoteResponse(

        @Schema(description = "A képviselő neve", example = "Kiss Béla",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String kepviselo,

        @Schema(description = "A szavazat: i = igen, n = nem, t = tartózkodik",
                example = "i", allowableValues = {"i", "n", "t"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        VoteValue szavazat
){ }
