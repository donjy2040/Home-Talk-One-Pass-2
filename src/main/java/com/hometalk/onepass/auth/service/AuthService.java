package com.hometalk.onepass.auth.service;

import com.hometalk.onepass.auth.dto.SignUpDTO;
import com.hometalk.onepass.auth.entity.LocalAccount;
import com.hometalk.onepass.auth.entity.User;
import com.hometalk.onepass.auth.repository.LocalAccountRepository;
import com.hometalk.onepass.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LocalAccountRepository localAccountRepository;

    @Transactional
    public void signUp(SignUpDTO dto) {
        // 1. User (부모) 생성
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhoneNumber())
                // DB 에러가 'users' 테이블의 'login_id' 때문이라면 여기에 넣어줘야 합니다.
                // 만약 User 엔티티에 loginId 필드가 없다면 추가해야 합니다.
                .build();

        User savedUser = userRepository.save(user);

        // 2. LocalAccount (자식) 생성
        LocalAccount localAccount = LocalAccount.builder()
                .user(savedUser) // @MapsId로 연결된 User 객체
                .loginId(dto.getLoginId()) // 또는 dto.getLoginId()를 사용
                .passwordHash(dto.getPassword()) // 평문 저장 (테스트용)
                .build();

        localAccountRepository.save(localAccount);
    }
}