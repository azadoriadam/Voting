package hu.parlament.vote;

import hu.parlament.enums.VoteValue;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Vote {

    @Column(name = "voter_name", nullable = false, comment = "Szavazó neve")
    private String voterName;

    @Column(name = "vote_value", nullable = false, length = 1, comment = "Szavazás típus")
    @Enumerated(EnumType.STRING)
    private VoteValue voteValue;

}
