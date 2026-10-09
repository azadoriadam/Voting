package hu.parlament.votesummary.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Szavazások listája")
public record VoteSummariesResponse(
        @Schema(description = "A leadott szavazások", requiredMode = Schema.RequiredMode.REQUIRED)
        List<VoteSummaryResponse> szavazasok)
{ }
