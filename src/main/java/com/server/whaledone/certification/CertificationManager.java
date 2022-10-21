package com.server.whaledone.certification;

import com.server.whaledone.certification.entity.*;
import com.server.whaledone.certification.redis.SmsCode;
import com.server.whaledone.certification.repository.SmsCodeRedisRepository;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.WeakHashMap;

@Component
@RequiredArgsConstructor
public class CertificationManager {

    private final int SMS_CERTIFICATION_CODE_LENGTH = 5;
    private final int GROUP_INVITATION_CODE_LENGTH = 6;

    private final long SMS_CERTIFICATION_CODE_EXPIRATION_TIME = 3 * 60 * 1000L; // 1초 * 60(초) * 3(분) = 3분
    private final long GROUP_INVITATION_CODE_EXPIRATION_TIME = 48 * 60 * 60 * 1000L; // 1초 * 60(초) * 60(분) * 48(시간) = 48시간

    private WeakHashMap<String, CustomCodeInfo> codeRepository = new WeakHashMap<>();
    private final SmsCodeRedisRepository smsCodeRedisRepository;
    // SMS -> key : 인증코드, value : 전화번호 & expiredAt
    // Invitation -> key : 인증코드, value : familyId & expiredAt

    public SmsCodeDto createSmsCode(String phoneNumber) {
        validateDuplicatePhoneNumberRequest(phoneNumber);
        String smsCode = randomGenerator(SMS_CERTIFICATION_CODE_LENGTH);

        smsCodeRedisRepository.save(SmsCode.of(phoneNumber, smsCode));

        return SmsCodeDto.of(smsCode, phoneNumber, SMS_CERTIFICATION_CODE_EXPIRATION_TIME);
    }

    public CustomCodeDto createInvitationCode(Long familyId) {
        String invitationCode = randomGenerator(GROUP_INVITATION_CODE_LENGTH);
        Date expiredAt = new Date(System.currentTimeMillis() + GROUP_INVITATION_CODE_EXPIRATION_TIME);

        InvitationCodeInfo metaData = InvitationCodeInfo.builder()
                .expiredAt(expiredAt)
                .familyId(familyId)
                .build();

        codeRepository.put((invitationCode), metaData);

        return CustomCodeDto.builder()
                .code(invitationCode)
                .info(metaData)
                .build();
    }

    public void validateSmsCode(String code, String phoneNumber) {
        SmsCode smsCode = smsCodeRedisRepository.findById(phoneNumber)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.CODE_INVALID_REQUEST));

        if (!smsCode.getCode().equals(code)) {
            throw new CustomException(CustomExceptionStatus.CODE_INVALID_REQUEST);
        }
        smsCodeRedisRepository.deleteById(phoneNumber);
    }

    public boolean validateInvitationCode(String code) {
        return true;
    }

    public CustomCodeInfo getCodeInfo(String code) {
        return codeRepository.get(code);
    }

    public void deleteCodeInfo(String phoneNumber) {
        smsCodeRedisRepository.deleteById(phoneNumber);
    }

    // 유일한 코드 생성
    public String randomGenerator(int countOfNumber) {
        Random rnd = new Random();
        StringBuilder code = new StringBuilder();

        do{
            code.delete(0, code.length());
            for (int i = 0; i < countOfNumber; i++) {
                code.append((rnd.nextInt(10)));
            }
        } while(validateDuplicationForCode(code.toString()));
        // 중복이면 true를 리턴 => while문을 타게 된다.

        return code.toString();
    }

    private boolean validateDuplicationForCode(String code) {
        return codeRepository.containsKey(code);
    }

    private void validateDuplicatePhoneNumberRequest(String phoneNumber) {
        if (smsCodeRedisRepository.existsById(phoneNumber)) {
            throw new CustomException(CustomExceptionStatus.SMS_DUPLICATE_REQUEST);
        }
    }
}
