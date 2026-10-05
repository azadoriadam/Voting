package hu.parlament.votesummary;

import hu.parlament.enums.producertype.ProcedureType;
import hu.parlament.enums.producertype.ProcedureTypeConverter;
import hu.parlament.enums.votingtype.VotingType;
import hu.parlament.enums.votingtype.VotingTypeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@NamedEntityGraph(
        name = VoteSummary.WITH_VOTES,
        attributeNodes = @NamedAttributeNode("votes"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteSummary {

    public static final String WITH_VOTES = "VoteSummary.withVotes";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, comment = "id")
    private Integer id;

    @Column(name = "vote_date", comment = "Időpont")
    private LocalDateTime voteDate;

    @Column(name = "subject", comment = "Tárgy")
    private String subject;

    @Column(name = "voting_type", nullable = false, length = 1, comment = "Szavazás típus")
    @Convert(converter = VotingTypeConverter.class)
    private VotingType votingType;

    @Column(name = "procedure_type", nullable = false, length = 1, comment = "Eljárás típus")
    @Convert(converter = ProcedureTypeConverter.class)
    private ProcedureType procedureType;

    @Column(comment = "Elnök neve")
    private String president;

    @Singular
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "vote_summary_vote", joinColumns = @JoinColumn(name = "vote_summary_id"))
    private List<Vote> votes;

    @Embeddable
    public record Vote(

        @Column(name = "voter_name", nullable = false, comment = "Szavazó neve")
        String name,

        @Column(name = "vote_type", nullable = false, length = 1, comment = "Szavazat")
        VotingType voteType

    ) { }
}