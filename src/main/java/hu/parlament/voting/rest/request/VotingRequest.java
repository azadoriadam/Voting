package hu.parlament.voting.rest.request;

import hu.parlament.enums.ProcedureType;
import hu.parlament.enums.VotingType;
import hu.parlament.vote.VoteDTO;

import java.time.LocalDateTime;
import java.util.List;

public record VotingRequest(
        LocalDateTime idopont,
        String targy,
        VotingType tipus,
        ProcedureType eljaras,
        String elnok,
        List<VoteDTO> szavazatok
        ) { }
