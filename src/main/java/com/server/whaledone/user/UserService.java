package com.server.whaledone.user;

import com.server.whaledone.user.dto.request.SignUpRequestDto;
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

    public String signUp(SignUpRequestDto dto) {

        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(dto.toEntity());
//        dto.setJwtToken(savedUser.get);
        return new SignUpResponseDto(savedUser).toString();
    }

}
