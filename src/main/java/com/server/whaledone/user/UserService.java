package com.server.whaledone.user;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.config.security.auth.CustomUserDetails;
import com.server.whaledone.config.security.jwt.JwtTokenProvider;
import com.server.whaledone.family.FamilyRepository;
import com.server.whaledone.family.entity.Family;
import com.server.whaledone.user.dto.request.*;
import com.server.whaledone.user.dto.response.SignInResponseDto;
import com.server.whaledone.user.dto.response.SignUpResponseDto;
import com.server.whaledone.user.dto.response.UserInfoResponseDto;
import com.server.whaledone.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public SignUpResponseDto signUp(SignUpRequestDto dto) {
        if (userRepository.findByEmailAndStatus(dto.getEmail(), Status.ACTIVE).isPresent()) {
            throw new CustomException(CustomExceptionStatus.USER_EXISTS_EMAIL);
        }

        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(dto.toEntity());
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto(savedUser);
        signUpResponseDto.setJwtToken(jwtTokenProvider.createToken(dto.getEmail(), savedUser.getRoleType()));
        return signUpResponseDto;
    }

    // 로그인
    public SignInResponseDto signIn(SignInRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(dto.getEmail(), Status.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.USER_NOT_EXISTS_EMAIL));

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(CustomExceptionStatus.USER_NOT_MATCHES_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoleType());

        SignInResponseDto result = SignInResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .userId(user.getId())
                .jwtToken(token)
                .build();

        return result;
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
        user.changeUserInfo(dto);
        family.changeName(dto.getFamilyName());
    }
}
