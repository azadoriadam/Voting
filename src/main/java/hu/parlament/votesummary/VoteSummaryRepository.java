package hu.parlament.votesummary;

import hu.parlament.enums.VoteValue;
import hu.parlament.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteSummaryRepository extends JpaRepository<VoteSummary, Integer> {

    Optional<Vote> findFirstByVotes_VoterNameAndVotes_VoteValue(String voterName, VoteValue voteValue);
}