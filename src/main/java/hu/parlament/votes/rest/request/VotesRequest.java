package hu.parlament.votes.rest.request;

import hu.parlament.enums.ProcedureType;
import hu.parlament.enums.VotingType;

import java.time.LocalDateTime;
import java.util.List;

import hu.parlament.vote.VoteRequest;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Szavazás rögzítésének kérése")
public record VotesRequest(

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
                requiredMode = Schema.RequiredMode.REQUIRED)
        ProcedureType eljaras,

        @Schema(description = "Az elnök neve", example = "Nagy Katalin",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String elnok,

        @Schema(description = "A leadott szavazatok")
        List<VoteRequest> szavazatok

) { }
