package hu.parlament.vote;

import hu.parlament.enums.VoteValue;

public record VoteDTO(
    String voterName,
    VoteValue voteValue
) { }
