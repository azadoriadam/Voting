package hu.parlament.vote;

import hu.parlament.enums.VoteValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findFirstByVoterNameAndVoteValue(@Nullable String voterName, @Nullable VoteValue voteValue);

}