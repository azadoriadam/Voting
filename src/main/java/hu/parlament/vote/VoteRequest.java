package hu.parlament.vote;

import hu.parlament.enums.VoteValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Egy képviselő szavazata")
public record VoteRequest(

        @Schema(description = "A képviselő neve", example = "Kiss Béla")
        String kepviselo,

        @Schema(description = "A szavazat: i = igen, n = nem, t = tartózkodik",
                example = "i", allowableValues = {"i", "n", "t"})
        VoteValue szavazat
) { }
