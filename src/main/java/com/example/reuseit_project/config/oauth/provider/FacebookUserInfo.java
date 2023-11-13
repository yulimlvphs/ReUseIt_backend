package com.example.reuseit_project.config.oauth.provider;
import java.util.Map;

public class FacebookUserInfo implements OAuth2UserInfo{
    private Map<String, Object> attributes;

    public FacebookUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "facebook"; // 원래 Provider이라는 필드에는 로르인하는 주체, 대상이 오는 거임.
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
