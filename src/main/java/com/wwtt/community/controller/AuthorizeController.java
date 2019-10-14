package com.wwtt.community.controller;

import com.wwtt.community.pojo.AccessTokenPojo;
import com.wwtt.community.pojo.GithubUser;
import com.wwtt.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        AccessTokenPojo accessTokenPojo = new AccessTokenPojo();
        accessTokenPojo.setCode(code);
        accessTokenPojo.setState(state);
        accessTokenPojo.setRedirect_uri(redirectUri);
        accessTokenPojo.setClient_id(clientId);
        accessTokenPojo.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenPojo);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
