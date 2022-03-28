package com.server.whaledone.user.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.country.entity.Country;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.entity.Reaction;
import com.server.whaledone.user.dto.request.UpdateUserInfoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String nickName;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    private String email;

    private String password;

    private String phoneNumber;

    private String profileImgUrl;

    @ManyToOne
    @JoinColumn(name = "familyId")
    private Family family;

    @OneToMany(mappedBy = "author")
    private List<Posts> posts = new ArrayList<>();

    private String alarmTime;

    // 서비스 알람 수신 여부
    private Boolean alarmStatus;

    // default ROLE_USER
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 계정 상태
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "author")
    private List<Reaction> reactions = new ArrayList<>();

    @Builder
    public User(Country country, String phoneNumber, Boolean alarmStatus, String email, String nickName, String password) {
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.alarmStatus = alarmStatus;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.roleType = RoleType.ROLE_USER;
        this.status = Status.ACTIVE;
        this.profileImgUrl = "https://whaledone-static-contents.s3.ap-northeast-2.amazonaws.com/defaultImage.png";
    }

    public void deleteAccount() {
        this.status = Status.DELETED;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void changeProfileImg(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void changeUserInfo(UpdateUserInfoRequestDto dto) {
        this.phoneNumber = dto.getPhoneNumber();
        this.alarmTime = dto.getAlarmTime();
        this.alarmStatus = dto.getAlarmStatus();
    }

    public void changeCountry(Country country) {
        this.country = country;
        country.getUserList().add(this);
    }

    public void upLoadPosts(Posts posts) {
        posts.assignAuthor(this);
        this.posts.add(posts);
    }

    public void writeComment(Reaction reaction) {
        reaction.assignAuthor(this);
        this.reactions.add(reaction);
    }

    public void resetPassword(String password) {
        this.password = password;
    }
}
