package com.server.whaledone.config.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    // Common
    INVALID_INPUT_VALUE(true, "COM001", "유효하지 않은 입력입니다."),

    /**
     * User
     */
    USER_NOT_EXISTS(true, "U001", "존재하지 않는 사용자입니다."),
    // Email
    USER_EXISTS_EMAIL(true,"UE001","이미 가입된 이메일이에요."),
    USER_INVALID_EMAIL(true, "UE002", "올바른 이메일 주소가 아니에요"),
    USER_NOT_EXISTS_EMAIL(true, "UE003", "존재하는 이메일이 없어요."),

    // Nickname
    USER_EXISTS_NICKNAME(true, "UN001", "이미 가입된 닉네임이에요."),
    USER_INVALID_NICKNAME(true, "UN002", "한글/영문/숫자/특수문자(*,._-+!?)까지 입력 가능해요."),

    // Password
    USER_INVALID_PASSWORD(true, "UP001", "영문 대문자/소문자/숫자 3가지를 포함해서 입력하세요."),
    USER_NOT_MATCHES_PASSWORD(true, "UP002", "일치하지 않는 비밀번호입니다."),

    // Code
    CODE_EXPIRED_DATE(true, "C001", "만료된 초대 코드입니다."),
    CODE_INVALID_REQUEST(true, "C002", "잘못된 초대 코드입니다."),

    //Group
    GROUP_NOT_EXISTS(true, "G001", "해당 가족채널이 존재하지 않습니다."),
    GROUP_CODE_NOT_EXISTS(true, "G002", "일치하는 초대 코드가 존재하지 않습니다."),

    // Posts
    POSTS_NOT_EXISTS(true, "P001", "해당 게시글은 존재하지 않습니다."),
    POSTS_INVALID_REQUEST(true, "P002", "글 작성자가 아닙니다."),

    // Reaction
    REACTION_NOT_EXISTS(true, "R001", "해당 리액션은 존재하지 않습니다."),
    REACTION_INVALID_REQUEST(true, "R002", "리액션 작성자가 아닙니다."),

    // Country
    COUNTRY_NOT_EXISTS(true, "CO001", "해당 국가는 미지원하는 국가입니다."),

    // Question
    QUESTION_NOT_EXISTS_IN_CATEGORY(true, "Q001", "해당 카테고리의 질문이 존재하지 않습니다.");

    private final boolean responseStatus;
    private final String code;
    private final String message;


}
