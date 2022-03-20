package com.server.whaledone.family.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.config.Entity.Status;
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
public class Family extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "familyId")
    private Long id;

    private String familyName;

    @Enumerated(EnumType.STRING)
    private Status status;

    // 추후에 code entity로 변경
    // private String invitationCode;

    @OneToMany(mappedBy = "family")
    private List<User> users = new ArrayList<>();
    // 1:N (그룹 : 회원)

    public void addMember(User user) {
        user.setFamily(this);
        users.add(user);
    } // 연관관계 편의 메서드

    public void changeName(String familyName) {
        this.familyName = familyName.strip();
    }

    @Builder
    Family(String familyName) {
        this.familyName = "웨일던, 칭찬하는 가족";
        this.status = Status.ACTIVE;
    }
}
