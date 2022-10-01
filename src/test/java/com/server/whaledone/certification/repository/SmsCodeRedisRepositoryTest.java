package com.server.whaledone.certification.repository;

import com.server.whaledone.certification.redis.SmsCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SmsCodeRedisRepositoryTest {

    @Autowired
    private SmsCodeRedisRepository smsCodeRedisRepository;

    @AfterEach
    public void exit() {
        smsCodeRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회() {
        // given
        String testCode = "12345";
        String testPhoneNumber = "01012345678";
        SmsCode smsCode = SmsCode.of(testPhoneNumber, testCode);
        smsCodeRedisRepository.save(smsCode);

        // when
        SmsCode findSmsCode = smsCodeRedisRepository.findById(testPhoneNumber).get();

        // then
        assertAll("redis 등록 조회",
                () -> assertThat(findSmsCode.getCode()).isEqualTo(testCode),
                () -> assertThat(findSmsCode.getPhoneNumber()).isEqualTo(testPhoneNumber)
        );
    }
}
