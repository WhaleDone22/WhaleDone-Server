package com.server.whaledone.reaction.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.dto.request.ChangeReactionRequestDto;
import com.server.whaledone.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reaction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reactionId")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private ContentType type;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User author;

    @JoinColumn(name = "postId")
    @ManyToOne
    private Posts post;

    @Builder
    public Reaction(String content, ContentType type) {
        this.content = content;
        this.type = type;
        this.status = Status.ACTIVE;
    }

    public void assignAuthor(User author) {
        this.author = author;
    }

    public void belongTo(Posts post) {
        this.post = post;
        post.getReactions().add(this);
    }

    public void changeReaction(ChangeReactionRequestDto dto) {
        this.content = dto.getContent();
        this.type = dto.getType();
    }

    public void deleteReaction() {
        this.status = Status.DELETED;
    }
}
