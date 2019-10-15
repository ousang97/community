package com.wwtt.community.controller;

import com.wwtt.community.mapper.UserMapper;
import com.wwtt.community.modal.User;
import com.wwtt.community.pojo.AccessTokenPojo;
import com.wwtt.community.pojo.GithubUser;
import com.wwtt.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenPojo accessTokenPojo = new AccessTokenPojo();
        accessTokenPojo.setCode(code);
        accessTokenPojo.setState(state);
        accessTokenPojo.setRedirect_uri(redirectUri);
        accessTokenPojo.setClient_id(clientId);
        accessTokenPojo.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenPojo);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGetModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登录成功，写cookie和session


            return "redirect:/";
        } else {
            return "redirect:/";
            //登录失败重新登录
        }

    }
}
