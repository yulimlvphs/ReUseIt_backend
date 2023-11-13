package com.example.reuseit_project.config.oauth;
import com.example.reuseit_project.config.auth.PrincipalDetails;
import com.example.reuseit_project.config.oauth.provider.FacebookUserInfo;
import com.example.reuseit_project.config.oauth.provider.GoogleUserInfo;
import com.example.reuseit_project.config.oauth.provider.NaverUserInfo;
import com.example.reuseit_project.config.oauth.provider.OAuth2UserInfo;
import com.example.reuseit_project.model.User;
import com.example.reuseit_project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(PrincipalOauth2UserService.class);

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //OAuth2 로그인 요청을 처리하고, OAuth2 공급자(예: 구글)에서 사용자 정보를 가져오는 역할을 수행하는 함수이다.
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다. (@AuthenticationPrincipal 어노테이션은 Spring Security에서 사용되며, 현재 인증된 사용자의 Principal(주체, 즉 현재 로그인한 사용자) 객체를 가져올 때 사용)
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("getClientRegistration : "+userRequest.getClientRegistration());
        log.info("getAccessToken : "+userRequest.getAccessToken().getTokenValue());

        // DefaultOAuth2UserService를 통해 사용자 정보를 로드하고 OAuth2User 객체를 반환
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글로그인 버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code 리턴(OAuth-Client 라이브러리가 code를 받아줌) -> AccessToken 요청
        // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아줌.
        log.info("getAttributes : "+oAuth2User.getAttributes());

        // 여기 아래있는 코드들은 OAuth2UserInfo 인터페이스를 통해서 google인지 facebook를 구별하여 처리
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("--- 구글 로그인 요청 ---");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("--- 페이스북 로그인 요청 ---");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("--- 네이버 로그인 요청 ---");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("우리는 구글, 페이스북, 네이버만 지원합니다.");
        }

        //아래에 있는 6개의 항목은 entitiy 클래스에 있는 필드들임
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("TemporaryPasswordKey");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userentity = userRepository.findByUsername(username);
        if(userentity == null) {
            System.out.println("OAuth 로그인이 최초입니다. ");
            userentity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userentity);
        } else{
            System.out.println("로그인을 이미 진행한 적이 있습니다. 당신은 회원가입이 되어 있습니다.");
        }
        return new PrincipalDetails(userentity, oAuth2User.getAttributes());
    }
}
