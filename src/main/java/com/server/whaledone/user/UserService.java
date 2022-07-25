package com.server.whaledone.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.whaledone.certification.CertificationManager;
import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.config.security.jwt.JwtTokenProvider;
import com.server.whaledone.country.CountryRepository;
import com.server.whaledone.country.entity.Country;
import com.server.whaledone.family.FamilyRepository;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.mail.MailProvider;
import com.server.whaledone.mail.MailRequestDto;
import com.server.whaledone.sms.SmsService;
import com.server.whaledone.sms.dto.SendSmsRequestDto;
import com.server.whaledone.sms.dto.ValidateSmsCodeRequestDto;
import com.server.whaledone.user.dto.request.*;
import com.server.whaledone.user.dto.response.ResetPasswordResponseDto;
import com.server.whaledone.user.dto.response.SignInResponseDto;
import com.server.whaledone.user.dto.response.SignUpResponseDto;
import com.server.whaledone.user.dto.response.UserInfoResponseDto;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final CountryRepository countryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CertificationManager certificationManager;
    private final SmsService smsService;

    // 회원가입
    public SignUpResponseDto signUp(SignUpRequestDto dto) {
        if (userRepository.findByEmailAndStatus(dto.getEmail(), Status.ACTIVE).isPresent()) {
            throw new CustomException(CustomExceptionStatus.USER_EXISTS_EMAIL);
        }
        Country country = countryRepository.findByCountryCode(dto.getCountryCode())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COUNTRY_NOT_EXISTS));

        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(dto.toEntity(country));
        country.addUser(savedUser); // 해당 나라에 속하는 유저 저장용 (편의 메서드 아님)
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto(savedUser);
        signUpResponseDto.setJwtToken(jwtTokenProvider.createToken(dto.getEmail(), savedUser.getRoleType()));
        return signUpResponseDto;
    }

    // 로그인
    public SignInResponseDto signIn(SignInRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(dto.getEmail(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS_EMAIL));

        boolean hasFamily = false;
        Long familyId = -1L;

        if (user.getFamily() != null) {
            hasFamily = true;
            familyId = user.getFamily().getId();
        }

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(CustomExceptionStatus.USER_NOT_MATCHES_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoleType());

        return SignInResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .userId(user.getId())
                .jwtToken(token)
                .hasFamily(hasFamily)
                .familyId(familyId)
                .build();
    }

    public UserInfoResponseDto getUserInfo(CustomUserDetails userDetails) {
        return new UserInfoResponseDto(userDetails.getUser());
    }

    public void getEmailValidationStatus(EmailValidRequestDto email) {
        if (userRepository.findByEmailAndStatus(email.getEmail(), Status.ACTIVE).isPresent()) {
            throw new CustomException(CustomExceptionStatus.USER_EXISTS_EMAIL);
        }
    }
    public void getNicknameValidationStatus(NicknameValidRequestDto nickName) {
        if (userRepository.findByNickNameAndStatus(nickName.getNickName(), Status.ACTIVE).isPresent()) {
            throw new CustomException(CustomExceptionStatus.USER_EXISTS_NICKNAME);
        }
    }

    @Transactional
    public void deleteUserAccount(CustomUserDetails userDetails) {
        User activeUser = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        activeUser.deleteAccount();
    }

    @Transactional
    public void updateProfileImg(CustomUserDetails userDetails, UpdateProfileImgRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        user.changeProfileImg(dto.getProfileImgUrl());
    }

    @Transactional
    public void updateUserInfo(CustomUserDetails userDetails, UpdateUserInfoRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        Family family = familyRepository.findById(user.getFamily().getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.GROUP_NOT_EXISTS));
        Country country = countryRepository.findByCountryCode(dto.getCountryCode())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COUNTRY_NOT_EXISTS));
        user.changeUserInfo(dto);
        user.changeCountry(country);
        family.changeName(dto.getFamilyName());
    }

    @Transactional
    public void reIssuePassword(SendSmsRequestDto dto) throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        // 비밀번호 재발급을 요청하는 유저의 이메일을 받아서, 유저 정보를 조회한다.
        User user = userRepository.findByPhoneNumberAndStatus(dto.getRecipientPhoneNumber(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        // 임시 비밀번호 생성 후 암호화해서 저장
        String tempPassword = certificationManager.randomGenerator(8);
        user.resetPassword(bCryptPasswordEncoder.encode(tempPassword));

        smsService.sendPWSms(dto, tempPassword);
    }

    @Transactional
    public void resetPassword(CustomUserDetails userDetails, ResetPasswordRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(userDetails.getEmail(), userDetails.getStatus())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));

        // 변경할 비밀번호를 받아서 reset
        user.resetPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
    }

    @Transactional
    public ResetPasswordResponseDto validatePasswordCode(ValidateSmsCodeRequestDto dto) {
        if (!certificationManager.validateSmsCode(dto.getSmsCode(), dto.getPhoneNumber())) {
            throw new CustomException(CustomExceptionStatus.CODE_EXPIRED_DATE);
        }
        // 현재 해당 코드가 있는지, 해당 코드의 시간이 유효한지, 해당 코드와 번호가 일치하는지 검증
        certificationManager.deleteCodeInfo(dto.getSmsCode());

        // 비밀번호 재발급을 요청하는 유저의 전화번호를 받아서, 유저 정보를 조회한다.
        User user = userRepository.findByPhoneNumberAndStatus(dto.getPhoneNumber(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS));
        // 완료시 메모리에서 제거
        String tempPassword = certificationManager.randomGenerator(8);
        user.resetPassword(bCryptPasswordEncoder.encode(tempPassword));

        return ResetPasswordResponseDto.builder()
                .tempPassword(tempPassword)
                .build();
    }
}
