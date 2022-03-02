package com.server.whaledone.user.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.config.Entity.Status;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    // 추후에 국가 클래스로 변경
    private String nation;

    private String email;

    private String password;

    private String phoneNumber;

    private String profileImgUrl;

    // 서비스 알람 수신 여부
    private Boolean alarmStatus;

    // default ROLE_USER
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 계정 상태
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public User(String nation, String phoneNumber, Boolean alarmStatus, String email, String nickName, String password) {
        this.nation = nation;
        this.phoneNumber = phoneNumber;
        this.alarmStatus = alarmStatus;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.roleType = RoleType.ROLE_USER;
        this.status = Status.ACTIVE;
    }
}
