package hu.parlament.votesummary;

import hu.parlament.common.DefaultEntity;
import hu.parlament.enums.ProcedureType;
import hu.parlament.enums.VotingType;
import hu.parlament.vote.Vote;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@ToString
@NamedEntityGraph(
    name = VoteSummary.WITH_VOTES,
    attributeNodes = @NamedAttributeNode("votes"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteSummary extends DefaultEntity {

    public static final String WITH_VOTES = "VoteSummary.withVotes";

    @Column(name = "vote_date", comment = "Időpont")
    private LocalDateTime voteDate;

    @Column(name = "subject", comment = "Tárgy")
    private String subject;

    @Column(name = "voting_type", nullable = false, length = 1, comment = "Szavazás típus")
    @Enumerated(EnumType.STRING)
    private VotingType votingType;

    @Column(name = "procedure_type", nullable = false, length = 1, comment = "Eljárás típus")
    @Enumerated(EnumType.STRING)
    private ProcedureType procedureType;

    @Column(name = "president", comment = "Elnök neve")
    private String president;

    @Singular
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_summary_id", nullable = false)
    @ToString.Exclude
    private List<Vote> votes;

}