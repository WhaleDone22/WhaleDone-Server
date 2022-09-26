package com.server.whaledone.family;

import com.server.whaledone.certification.CertificationManager;
import com.server.whaledone.certification.entity.CustomCodeDto;
import com.server.whaledone.certification.entity.InvitationCodeInfo;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.family.dto.request.UpdateFamilyNameRequestDto;
import com.server.whaledone.family.dto.request.ValidateInvitationCodeRequestDto;
import com.server.whaledone.family.dto.response.*;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.posts.entity.Posts;
import com.server.whaledone.reaction.entity.Reaction;
import com.server.whaledone.user.UserRepository;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        savedFamily.setInvitationCode(invitationCodeDto.getCode());

        return CreateFamilyResponseDto.builder()
                .invitationCode(invitationCodeDto.getCode())
                .remainingValidTimeHour(invitationCodeDto.getHour())
                .remainingValidTimeMinute(invitationCodeDto.getMinute())
                .remainingValidTimeSecond(invitationCodeDto.getSecond())
                .familyId(savedFamily.getId())
                .build();
    }

    @Transactional
    public void updateFamilyName(Long familyId, UpdateFamilyNameRequestDto dto) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));
        family.changeName(dto.getUpdateName());
    }

    @Transactional
    public ValidateInvitationCodeResponseDto validateInvitationCode(CustomUserDetails userDetails, ValidateInvitationCodeRequestDto dto) {
        if (!certificationManager.validateInvitationCode(dto.getInvitationCode())) {
            certificationManager.deleteCodeInfo(dto.getInvitationCode()); // 유효 기간이 지났으므로 지워준다.
            throw new CustomException(CustomExceptionStatus.CODE_EXPIRED_DATE);
        }

        InvitationCodeInfo codeInfo = (InvitationCodeInfo) certificationManager.getCodeInfo(dto.getInvitationCode());

        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        Family family = familyRepository.findById(codeInfo.getFamilyId())
                .orElseThrow(() ->new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));

        family.addMember(user);

        return ValidateInvitationCodeResponseDto.builder()
                .familyId(family.getId())
                .build();
    }

    @Transactional
    public List<UsersInFamilyResponseDto> getUsersInfoInFamily(CustomUserDetails userDetails, Long familyId) {
        User authUser = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));
        // 호출자까지 family에 포함되어 있음.

        Map<Long, Long> communicationCountMap = getCommunicationCountMap(authUser, family);

        List<UsersInFamilyResponseDto> resultList = family.getUsers()
                .stream()
                .map(UsersInFamilyResponseDto::new)
                .collect(Collectors.toList());

        for (UsersInFamilyResponseDto usersInFamilyResponseDto : resultList) {
            usersInFamilyResponseDto.setCommunicationCount(communicationCountMap.get(usersInFamilyResponseDto.getId()));
        } // dto 순회하면서 리액션 결과값 넣어주기

        return resultList;
    }

    @Transactional
    public ReIssueInvitationCodeResponseDto reIssueInvitationCode(CustomUserDetails customUserDetails, Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));

        String invitationCode = family.getInvitationCode();

        // 삭제하고 새로 발급한다.
        certificationManager.deleteCodeInfo(invitationCode);
        CustomCodeDto dto = certificationManager.createInvitationCode(familyId);
        family.setInvitationCode(dto.getCode());
        return ReIssueInvitationCodeResponseDto.builder()
                .invitationCode(dto.getCode())
                .hour(dto.getHour())
                .minute(dto.getMinute())
                .second(dto.getSecond())
                .build();
        }

    public FamilyInfoResponseDto getFamilyInfo(CustomUserDetails customUserDetails, Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));
        // 해당 familyId가 내 가족이 맞는지?
        return FamilyInfoResponseDto.builder()
                .familyName(family.getFamilyName())
                .build();
    }

    public List<FamilyTimeDiffResponseDto> getFamilyTimeDiff(CustomUserDetails userDetails, Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));

        return family.getUsers().stream().map(FamilyTimeDiffResponseDto::new).collect(Collectors.toList());
    }

    /* return 가족 구성원 id, 나와의 소통 횟수
     * 1. 내 글에 달린 리액션 작성자를 카운팅한다. (내 글에 리액션 단 경우)
     * 2. 가족 구성원들의 글에 달린 리액션이 내가 작성한 것이면 카운팅한다. (다른 사람 글에 내가 리액션 단 경우)
     */
    private Map<Long, Long> getCommunicationCountMap(User authUser, Family family) {
        Map<Long, Long> reactionCountingMap = new HashMap<>();

        Long authId = authUser.getId();

        List<User> familyUsers = family.getUsers();

        for (User user : familyUsers) {
            reactionCountingMap.put(user.getId(), 0L);
        } // init

        for (Posts post : authUser.getPosts()) {
            for (Reaction reaction : post.getReactions()) {
                Long communicatorId = reaction.getAuthor().getId();
                reactionCountingMap.put(communicatorId, reactionCountingMap.get(communicatorId) + 1);
            } // 내 글 모두를 순회하고, 글에 달린 리액션을 모두 순회한다.
        } // 내 글에 달려있는 리액션을 쓴 사람과 소통 횟수가 늘어난다.

        for (User familyUser : familyUsers) {
            if(familyUser.getId() == authId)
                continue; // 나는 제외
            for (Posts post : familyUser.getPosts()) {
                for (Reaction reaction : post.getReactions()) {
                    Long authorId = reaction.getAuthor().getId();
                    if (authorId == authId) { // 가족 구성원이 받은 리액션 중 내가 쓴 리액션인 경우
                        reactionCountingMap.put(familyUser.getId(), reactionCountingMap.get(familyUser.getId()) + 1);
                    }
                }
            }
        }
        return reactionCountingMap;
    }
}
