package com.server.whaledone.posts.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.config.Entity.ContentType;
import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
