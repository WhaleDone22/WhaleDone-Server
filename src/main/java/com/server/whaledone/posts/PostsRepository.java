package com.server.whaledone.posts;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.posts.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    Optional<Posts> findByIdAndStatus(Long id, Status status);
}
