package hu.parlament.vote;


import hu.parlament.enums.VoteValue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository repository;

    @Transactional(readOnly = true)
    public Optional<Vote> findByVoterNameAndVoteValue(@Nullable String voterName, @Nullable VoteValue voteValue){
        return repository.findFirstByVoterNameAndVoteValue(voterName, voteValue);
    }
}
