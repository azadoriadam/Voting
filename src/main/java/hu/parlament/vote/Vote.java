package hu.parlament.vote;

import hu.parlament.common.DefaultEntity;
import hu.parlament.enums.VoteValue;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vote extends DefaultEntity {

    @Column(name = "voter_name", nullable = false, comment = "Szavazó neve")
    private String voterName;

    @Column(name = "vote_value", nullable = false, length = 1, comment = "Szavazás típus")
    @Enumerated(EnumType.STRING)
    private VoteValue voteValue;

}
