package com.server.whaledone.posts;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.posts.dto.SavePostsRequestDto;
import com.server.whaledone.posts.dto.response.PostsMapToDateResponseDto;
import com.server.whaledone.posts.dto.response.PostsResponseDto;
import com.server.whaledone.posts.entity.Posts;
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

        return new PostsMapToDateResponseDto(getPostsMapGroupingByDate(allPosts));
    }

    public PostsMapToDateResponseDto getMyPosts(CustomUserDetails userDetails) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        return new PostsMapToDateResponseDto(getPostsMapGroupingByDate(user.getPosts()));
    }

    @Transactional
    public void deletePosts(CustomUserDetails userDetails, Long postId) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Posts posts = postsRepository.findByIdAndStatus(postId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.POSTS_NOT_EXISTS));

        if (user.getId() != posts.getAuthor().getId()) {
            throw new CustomException(CustomExceptionStatus.POSTS_INVALID_DELETE_REQUEST);
        }
        posts.deletePost();
    }

    private Map<LocalDate, List<PostsResponseDto>> getPostsMapGroupingByDate(List<Posts> allPosts) {
        return allPosts.stream()
                .filter(posts -> posts.getStatus() == Status.ACTIVE)
                .sorted(Comparator.comparing(Posts::getCreatedAt).reversed())
                .map(PostsResponseDto::new)
                .collect(Collectors
                        .groupingBy(PostsResponseDto::getCreatedDate));
    }
}
