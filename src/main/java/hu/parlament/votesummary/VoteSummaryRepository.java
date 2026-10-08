package hu.parlament.votesummary;

import hu.parlament.enums.VoteValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VoteSummaryRepository extends JpaRepository<VoteSummary, Integer> {

    Integer countByIdAndVotes_VoteValue(@NonNull Integer id, @NonNull VoteValue voteValue);

    Optional<VoteSummary> findFirstByIdNotOrderByIdDesc(@NonNull Integer id);


}