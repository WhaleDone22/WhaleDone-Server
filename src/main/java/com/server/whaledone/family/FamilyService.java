package com.server.whaledone.family;

import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.dto.response.CreateFamilyResponseDto;
import com.server.whaledone.family.entity.Family;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;

    private long CODE_EXPIRATION_TIME = 48 * 60 * 60 * 1000L; // 1초 * 60(초) * 60(분) * 48(시간) = 48시간

    @Transactional
    public CreateFamilyResponseDto createFamily(CustomUserDetails userDetails) {
        // 그룹 코드 생성
        String invitationCode = "temp";
        Date date = new Date(System.currentTimeMillis() + CODE_EXPIRATION_TIME);
        long remainTime = date.getTime() - new Date().getTime();

        // 새로운 그룹 생성
        Family newFamily = Family.builder()
                .invitationCode(invitationCode)
                .build();
        familyRepository.save(newFamily);

        newFamily.addMember(userDetails.getUser());

        return CreateFamilyResponseDto.builder()
                .invitationCode(invitationCode)
                .remainingValidTime(String.valueOf(remainTime))
                .build();
    }
}
