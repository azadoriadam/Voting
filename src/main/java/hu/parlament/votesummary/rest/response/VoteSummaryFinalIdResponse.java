package hu.parlament.votesummary.rest.response;

public record VoteSummaryFinalIdResponse(String szavazasId) {

    private static final String PREFIX = "OJ";

    public VoteSummaryFinalIdResponse(Integer id) {
        this(id == null ? null : String.format("%s%02d", PREFIX, id));
    }
}