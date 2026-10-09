package hu.parlament.votesummary.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hu.parlament.enums.ProcedureType;
import hu.parlament.enums.VoteSummaryResult;
import hu.parlament.enums.VotingType;
import hu.parlament.vote.rest.response.VoteResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Egy szavazás adatai")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VoteSummaryResponse(

        @Schema(description = "A szavazás időpontja", example = "2026-10-08T10:30:00",
                requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime idopont,

        @Schema(description = "A szavazás tárgya", example = "A 2027. évi költségvetés elfogadása",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String targy,

        @Schema(description = "A szavazás típusa: j = jelenlét, e = egyszerű többség, m = minősített többség",
                example = "e", allowableValues = {"j", "e", "m"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        VotingType tipus,

        @Schema(description = "Az eljárás típusa: n = normál, s = sürgősségi, k = kivételes, e = szabályzattól eltérő",
                example = "n", allowableValues = {"n", "s", "k", "e"},
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        ProcedureType eljaras,

        @Schema(description = "Az elnök neve", example = "Nagy Katalin",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String elnok,

        @Schema(description = "A szavazás eredménye: F = elfogadva, U = elutasítva (jelenléti szavazásnál nincs)",
                example = "F", allowableValues = {"F", "U"},
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        VoteSummaryResult eredmeny,

        @Schema(description = "A szavazáson részt vevő képviselők száma", example = "2", minimum = "0",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer kepviselokSzama,

        @Schema(description = "A leadott szavazatok", requiredMode = Schema.RequiredMode.REQUIRED)
        List<VoteResponse> szavazatok)
{ }
