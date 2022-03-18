package com.server.whaledone.reaction;

import com.server.whaledone.reaction.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}
