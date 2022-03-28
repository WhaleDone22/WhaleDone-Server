package com.server.whaledone.user;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndStatus(String email, Status status);

    Optional<User> findByNickNameAndStatus(String nickName, Status status);

    Optional<User> findByPhoneNumberAndStatus(String phoneNumber, Status status);
}
