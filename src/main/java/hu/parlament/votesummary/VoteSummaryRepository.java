package hu.parlament.votesummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteSummaryRepository extends JpaRepository<VoteSummary, Integer> {

}