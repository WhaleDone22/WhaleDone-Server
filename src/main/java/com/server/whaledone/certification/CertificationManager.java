package com.server.whaledone.certification;

import com.server.whaledone.certification.entity.CustomCodeDto;
import com.server.whaledone.certification.entity.CustomCodeInfo;
import com.server.whaledone.certification.entity.InvitationCodeInfo;
import com.server.whaledone.certification.entity.SmsCertificationCodeInfo;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CertificationManager {

    private final int SMS_CERTIFICATION_CODE_LENGTH = 5;
    private final int GROUP_INVITATION_CODE_LENGTH = 6;

    private final long SMS_CERTIFICATION_CODE_EXPIRATION_TIME = 3 * 60 * 1000L; // 1초 * 60(초) * 3(분) = 3분
    private final long GROUP_INVITATION_CODE_EXPIRATION_TIME = 48 * 60 * 60 * 1000L; // 1초 * 60(초) * 60(분) * 48(시간) = 48시간

    private ConcurrentHashMap<String, CustomCodeInfo> codeRepository = new ConcurrentHashMap<>();
    // SMS -> key : 인증코드, value : 전화번호 & expiredAt
    // Invitation -> key : 인증코드, value : familyId & expiredAt

    public CustomCodeDto createSmsCode(String phoneNumber) {
        String smsCode = randomGenerator(SMS_CERTIFICATION_CODE_LENGTH);
        Date expiredAt = new Date(System.currentTimeMillis() + SMS_CERTIFICATION_CODE_EXPIRATION_TIME);

        SmsCertificationCodeInfo metaData = SmsCertificationCodeInfo.builder()
                .expiredTime(expiredAt)
                .phoneNumber(phoneNumber)
                .build();

        codeRepository.put(smsCode, metaData);

        return CustomCodeDto.builder()
                .code(smsCode)
                .info(metaData)
                .build();
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

    // 코드 유효 시간 검증 - SMS 인증 : 3분, 가족 초대 코드 : 48시간
    public boolean validateCode(String code) {
        if(!codeRepository.containsKey(code))
            throw new CustomException(CustomExceptionStatus.CODE_INVALID_REQUEST);
        return new Date().before(codeRepository.get(code).getExpiredTime());
        // 현재 시각이 코드 만료날짜 전이면 유효한 코드
    }

    public CustomCodeInfo getCodeInfo(String code) {
        return codeRepository.get(code);
    }

    public void deleteCodeInfo(String code) {
        codeRepository.remove(code);
    }

    // 유일한 코드 생성
    private String randomGenerator(int countOfNumber) {
        Random rnd = new Random();
        StringBuffer code = new StringBuffer();

        do{
            code.delete(0, code.length());
            for (int i = 0; i < countOfNumber; i++) {
                if (rnd.nextBoolean()) {
                    code.append((char) (int) (rnd.nextInt(26) + 97));
                } else {
                    code.append((rnd.nextInt(10)));
                }
            }
        } while(validateDuplicationForCode(code.toString()));
        // 중복이면 true를 리턴 => while문을 타게 된다.

        return code.toString();
    }

    private boolean validateDuplicationForCode(String code) {
        return codeRepository.containsKey(code);
    }
}
