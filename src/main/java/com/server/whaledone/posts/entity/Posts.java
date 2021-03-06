package com.server.whaledone.posts.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.posts.dto.UpdatePostsRequestDto;
import com.server.whaledone.reaction.entity.Reaction;
import com.server.whaledone.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private ContentType type;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Reaction> reactions = new ArrayList<>();

    @Builder
    public Posts(String title, String content, ContentType type) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = Status.ACTIVE;
    }

    public void assignAuthor(User author) {
        this.author = author;
    }

    public void deletePost() {
        this.status = Status.DELETED;
    }

    public void changePosts(UpdatePostsRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.type = dto.getType();
    }
}
