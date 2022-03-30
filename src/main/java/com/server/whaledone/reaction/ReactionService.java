package com.server.whaledone.reaction;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.posts.PostsRepository;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.dto.request.ChangeReactionRequestDto;
import com.server.whaledone.reaction.dto.request.SaveReactionRequestDto;
import com.server.whaledone.reaction.dto.response.GetReactionAlarmsResponseDto;
import com.server.whaledone.reaction.dto.response.GetReactionsResponseDto;
import com.server.whaledone.reaction.dto.response.ReactionsMapToDateResponseDto;
import com.server.whaledone.reaction.entity.Reaction;
import com.server.whaledone.user.UserRepository;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<GetReactionsResponseDto> getReactions(CustomUserDetails userDetails, Long postId) {
        Posts posts = postsRepository.findByIdAndStatus(postId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.POSTS_NOT_EXISTS));

        return posts.getReactions().stream()
                .filter(reaction -> reaction.getStatus() == Status.ACTIVE)
                .sorted(Comparator.comparing(Reaction::getCreatedAt))
                .map(GetReactionsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeReaction(CustomUserDetails userDetails, Long postId, Long reactionId, ChangeReactionRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Reaction reaction = reactionRepository.findByIdAndStatus(reactionId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.REACTION_NOT_EXISTS));

        if (user.getId() != reaction.getAuthor().getId()) {
            throw new CustomException(CustomExceptionStatus.POSTS_INVALID_REQUEST);
        }

        reaction.changeReaction(dto);
    }

    @Transactional
    public void deleteReaction(CustomUserDetails userDetails, Long postId, Long reactionId) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Reaction reaction = reactionRepository.findByIdAndStatus(reactionId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.REACTION_NOT_EXISTS));

        if (user.getId() != reaction.getAuthor().getId()) {
            throw new CustomException(CustomExceptionStatus.POSTS_INVALID_REQUEST);
        }

        reaction.deleteReaction();
    }

    public ReactionsMapToDateResponseDto getReactionAlarms(CustomUserDetails userDetails) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        List<Reaction> allReactions = new ArrayList<>();
        user.getPosts().forEach(posts -> allReactions.addAll(posts.getReactions()));

        return new ReactionsMapToDateResponseDto(getReactionMapGroupingByDate(allReactions));
    }

    private Map<LocalDate, List<GetReactionAlarmsResponseDto>> getReactionMapGroupingByDate(List<Reaction> allReactions) {
        return allReactions.stream()
                .filter(reaction -> reaction.getStatus() == Status.ACTIVE)
                .map(GetReactionAlarmsResponseDto::new)
                .sorted(Comparator.comparing(GetReactionAlarmsResponseDto::getCreatedDate).reversed())
                .collect(Collectors.groupingBy(GetReactionAlarmsResponseDto::getCreatedDate));
    }
}
