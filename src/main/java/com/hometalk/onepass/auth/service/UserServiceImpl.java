package com.hometalk.onepass.auth.service;

import com.hometalk.onepass.auth.entity.LocalAccount;
import com.hometalk.onepass.auth.repository.LocalAccountRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService {

    private final LocalAccountRepository localAccountRepository;

    /**
     * 스프링 시큐리티의 인증 프로세스에서 호출되는 메서드
     * 사용자가 입력한 loginId를 가지고 DB에서 계정 정보를 찾아옵니다.
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        LocalAccount account = localAccountRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다: " + loginId));

        // username = loginId로 설정
        return User.builder()
                .username(account.getLoginId()) // ★ 여기! 숫자 id가 아닌 문자열 loginId 사용
                .password(account.getPasswordHash()) // 현재 DB에 있는 "1234"
                .roles("USER")
                .build();
    }
}