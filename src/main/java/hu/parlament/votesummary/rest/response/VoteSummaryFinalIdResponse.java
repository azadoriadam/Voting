package hu.parlament.votesummary.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Egy mentett szavazás id")
public record VoteSummaryFinalIdResponse(String szavazasId) {

    private static final String PREFIX = "OJ";

    public VoteSummaryFinalIdResponse(Integer id) {
        this(id == null ? null : String.format("%s%02d", PREFIX, id));
    }

    public static Integer getId(String szavazasId) {
        return szavazasId == null ? null : Integer.valueOf(szavazasId.substring(PREFIX.length()));
    }
}