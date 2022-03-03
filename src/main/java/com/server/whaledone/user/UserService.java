package com.server.whaledone.user;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.security.jwt.JwtTokenProvider;
import com.server.whaledone.user.dto.request.SignInRequestDto;
import com.server.whaledone.user.dto.request.SignUpRequestDto;
import com.server.whaledone.user.dto.response.SignInResponseDto;
import com.server.whaledone.user.dto.response.SignUpResponseDto;
import com.server.whaledone.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public String signUp(SignUpRequestDto dto) {

        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(dto.toEntity());
        dto.setJwtToken(jwtTokenProvider.createToken(dto.getEmail(), savedUser.getRoleType()));
        return new SignUpResponseDto(savedUser).toString();
    }

    public String signIn(SignInRequestDto dto) {
        User user = userRepository.findByEmailAndStatus(dto.getEmail(), Status.ACTIVE).orElseThrow(() -> new IllegalArgumentException("SignIn"));
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException();
        }
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoleType());

        SignInResponseDto result = SignInResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .accountId(user.getId())
                .jwtToken(token)
                .build();

        return result.toString();
    }

}
