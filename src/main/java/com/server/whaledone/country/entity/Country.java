package com.server.whaledone.country.entity;

import com.server.whaledone.config.Entity.BaseTimeEntity;
import com.server.whaledone.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Country extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countryId")
    private Long id;

    // 나라 이름
    private String countryName;

    // 나라 코드
    private String countryCode;

    //위도
    private Double latitude;

    // 경도
    private Double longitude;

    // 한국과 시차
    private String timeDiff;

    @OneToMany(mappedBy = "country")
    private List<User> userList;

    public void addUser(User user) {
        this.userList.add(user);
    }
}
