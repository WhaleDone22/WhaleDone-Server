package com.server.whaledone.reaction;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.reaction.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByIdAndStatus(Long reactionId, Status status);
}
