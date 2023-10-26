package com.example.reuseit_project.config.auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.reuseit_project.model.User;
import com.example.reuseit_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login"); 에서 이 클래스가 발동
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 loc되어 있는 loadUserByUsername
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(PrincipalDetailsService.class);

    // 시큐리티 session (내부 Authentication (내부 UserDetials))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //여기 매개변수에 있는 username은 로그인 폼에 있는 name 값과 일치해야함.
        log.info("username : " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        return new PrincipalDetails(user);
    }
}