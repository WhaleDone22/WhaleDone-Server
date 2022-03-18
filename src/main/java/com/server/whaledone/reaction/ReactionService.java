package com.server.whaledone.reaction;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.posts.PostsRepository;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.dto.request.SaveReactionRequestDto;
import com.server.whaledone.reaction.entity.Reaction;
import com.server.whaledone.user.UserRepository;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public void saveReaction(CustomUserDetails userDetails, Long postId, SaveReactionRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Posts posts = postsRepository.findByIdAndStatus(postId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.POSTS_NOT_EXISTS));

        Reaction reaction = dto.toEntity();
        reactionRepository.save(reaction);

        // 리액션과 쓴 사람을 매칭해준다.
        user.writeComment(reaction);

        // 리액션과 글과 매칭해준다
        reaction.belongTo(posts);
    }
}
