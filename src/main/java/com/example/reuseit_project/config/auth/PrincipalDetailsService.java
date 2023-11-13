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


// 시큐리티 설정에서 loginProcessingUrl("/login"); 에서 이 클래스가 발동
// UserDetailsService 인터페이스를 구현하는 클래스가 필요한 이유는 Spring Security에서 사용자 정보를 가져오는 방법(username을 통해서)을 사용자 정의하고, 인증 프로세스에 사용자 정보를 제공하기 위함
// == 그래서 자연스럽게 사용자 이름은 절대 중복되서는 안됨.
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