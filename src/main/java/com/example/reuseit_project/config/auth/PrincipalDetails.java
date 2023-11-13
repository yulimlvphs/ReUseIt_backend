package com.example.reuseit_project.config.auth;
import com.example.reuseit_project.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 진행시킨다.
// 로그인이 진행 완료되면 시큐리티가 시큐리티만의 자체 session을 만들어준다. (Security ContextHolder)
// 그런데 이 세션에 접근하기 위해서는 특정 오브젝트로 접근할 수 있는데.... 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User 오브젝트타입 => UserDatails 타입 객체

//Security Session -> Authentication -> UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user;  //해당 user의 권한을 리턴하는 곳!!
    private Map<String, Object> attribute; //OAuth2 인증 시 사용자의 속성 정보를 저장하기 위해 선언된 필드

    // 일반 로그인 때 사용하는 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    //OAuth를 사용하여 로그인하는 생성자
    public PrincipalDetails(User user, Map<String, Object> attribute){
        this.user = user;
        this.attribute = attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> user.getRole());
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }
}
