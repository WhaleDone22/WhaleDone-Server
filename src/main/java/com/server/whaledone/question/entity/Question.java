package com.server.whaledone.question.entity;

import com.server.whaledone.config.Entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Enumerated(EnumType.STRING)
    private Status status;

}
