package com.server.whaledone.family;

import com.server.whaledone.family.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<Family> findByInvitationCode(String invitationCode);
}
