package com.server.whaledone.family;

import com.server.whaledone.certification.CertificationManager;
import com.server.whaledone.certification.entity.CustomCodeDto;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.dto.request.UpdateFamilyNameRequestDto;
import com.server.whaledone.family.dto.request.ValidateInvitationCodeRequestDto;
import com.server.whaledone.family.dto.response.CreateFamilyResponseDto;
import com.server.whaledone.family.dto.response.UsersInFamilyResponseDto;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.user.UserRepository;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final CertificationManager certificationManager;

    @Transactional
    public CreateFamilyResponseDto createFamily(CustomUserDetails userDetails) {
        // 새로운 그룹 생성
        Family newFamily = Family.builder()
                .build();

        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        newFamily.addMember(user);
        Family savedFamily = familyRepository.save(newFamily);

        // 가족 코드 생성 & 메모리 저장소에 저장
        CustomCodeDto invitationCodeDto = certificationManager.createInvitationCode(savedFamily.getId());
        long remainTime = invitationCodeDto.getInfo().getExpiredTime().getTime() - new Date().getTime();

        remainTime /= 1000;

        long second = remainTime % 60;
        long minute = remainTime / 60;
        long hour = minute / 60;
        minute %= 60;

        return CreateFamilyResponseDto.builder()
                .invitationCode(invitationCodeDto.getCode())
                .remainingValidTimeHour(Long.toString(hour))
                .remainingValidTimeMinute(Long.toString(minute))
                .remainingValidTimeSecond(Long.toString(second))
                .build();
    }

    @Transactional
    public void updateFamilyName(Long familyId, UpdateFamilyNameRequestDto dto) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));
        family.changeName(dto.getUpdateName());
    }

    @Transactional
    public void validateInvitationCode(CustomUserDetails userDetails, ValidateInvitationCodeRequestDto dto) {
//        Family family = familyRepository.findByInvitationCode(dto.getInvitationCode())
//                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_CODE_NOT_EXISTS));

        // family.getInvitationCode 후 유효시간 체크하기 -> 지났으면 오류
//
//        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
//                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
//
//        family.addMember(user);
    }

    @Transactional
    public List<UsersInFamilyResponseDto> getUsersInFamily(Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));

        return family.getUsers()
                .stream()
                .map(UsersInFamilyResponseDto::new)
                .collect(Collectors.toList());
    }
}
