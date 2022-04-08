package com.server.whaledone.posts;

import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.posts.dto.ReactionCountDto;
import com.server.whaledone.posts.dto.SavePostsRequestDto;
import com.server.whaledone.posts.dto.UpdatePostsRequestDto;
import com.server.whaledone.posts.dto.response.PostsMapToDateResponseDto;
import com.server.whaledone.posts.dto.response.PostsResponseDto;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.entity.Reaction;
import com.server.whaledone.user.UserRepository;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    public void savePosts(CustomUserDetails userDetails, SavePostsRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        Posts newPost = dto.toEntity();
        user.upLoadPosts(newPost);

        postsRepository.save(newPost);
    }

    public PostsMapToDateResponseDto getFamilyPosts(CustomUserDetails userDetails) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        Family family = user.getFamily();

        List<Posts> allPosts = new ArrayList<>();
        family.getUsers()
                .forEach(user1 -> allPosts.addAll(user1.getPosts()));

        return new PostsMapToDateResponseDto(getPostsDtoMapGroupingByDate(allPosts));
    }

    public PostsMapToDateResponseDto getMyPosts(CustomUserDetails userDetails) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        return new PostsMapToDateResponseDto(getPostsDtoMapGroupingByDate(user.getPosts()));
    }

    @Transactional
    public void deletePosts(CustomUserDetails userDetails, Long postId) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Posts posts = postsRepository.findByIdAndStatus(postId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.POSTS_NOT_EXISTS));

        if (user.getId() != posts.getAuthor().getId()) {
            throw new CustomException(CustomExceptionStatus.POSTS_INVALID_REQUEST);
        }
        posts.deletePost();
    }

    @Transactional
    public void updatePosts(CustomUserDetails userDetails, Long postId, UpdatePostsRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Posts posts = postsRepository.findByIdAndStatus(postId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.POSTS_NOT_EXISTS));

        if (user.getId() != posts.getAuthor().getId()) {
            throw new CustomException(CustomExceptionStatus.POSTS_INVALID_REQUEST);
        }
        posts.changePosts(dto);
    }


    private Map<LocalDate, List<PostsResponseDto>> getPostsDtoMapGroupingByDate(List<Posts> allPosts) {
        List<PostsResponseDto> result = new ArrayList<>();
        for (Posts posts : allPosts) {
            if (posts.getStatus() == Status.ACTIVE) {
                List<ReactionCountDto> reactionCountListByType = getReactionCountListByType(posts.getReactions());
                PostsResponseDto postsResponseDto = new PostsResponseDto(posts);
                postsResponseDto.setReactionCounts(reactionCountListByType);
                result.add(postsResponseDto);
            }
        }

        TreeMap<LocalDate, List<PostsResponseDto>> collect = result.stream()
                .collect(Collectors.groupingBy(PostsResponseDto::getCreatedDate, TreeMap::new, Collectors.toList()));

        for (Map.Entry<LocalDate, List<PostsResponseDto>> localDateListEntry : collect.entrySet()) {
            localDateListEntry.getValue().sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        }

        TreeMap<LocalDate, List<PostsResponseDto>> reverseResult = new TreeMap<>(Collections.reverseOrder());
        reverseResult.putAll(collect);
        return reverseResult;
    }

    private List<ReactionCountDto> getReactionCountListByType(List<Reaction> reactions) {
        List<ReactionCountDto> result = new ArrayList<>();

        Map<ContentType, Long> contentTypeCountMap = new HashMap<>();

        for (ContentType c : ContentType.values()) {
            contentTypeCountMap.put(c, 0L);
        }

        for (Reaction reaction : reactions) {
            if(reaction.getStatus() == Status.ACTIVE) {
                contentTypeCountMap.put(reaction.getType(), contentTypeCountMap.get(reaction.getType()) + 1);
            }
        }

        for (Map.Entry<ContentType, Long> entry : contentTypeCountMap.entrySet()) {
            result.add(ReactionCountDto.builder()
                    .type(entry.getKey())
                    .count(entry.getValue())
                    .build());
        }
        return result;
    }
}
