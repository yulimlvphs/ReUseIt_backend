package com.example.reuseit_project.config.oauth.provider;

// 왜 인터페이스가 존재하고, 그 인터페이스를 implement 하는 클래스들이 존재하는냐?? 그건 내 블로그 10강을 참조하도록....
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
