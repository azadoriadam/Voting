package hu.parlament.vote;

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
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, comment = "id")
    private Integer id;

    @Column(name = "voter_name", nullable = false, comment = "Szavazó neve")
    private String voterName;

    @Column(name = "vote_value", nullable = false, length = 1, comment = "Szavazás típus")
    @Enumerated(EnumType.STRING)
    private VoteValue voteValue;

}
