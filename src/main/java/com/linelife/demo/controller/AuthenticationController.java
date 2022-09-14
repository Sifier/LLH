package com.linelife.demo.controller;

import com.linelife.demo.dto.AuthenticationRequestDto;
import com.linelife.demo.dto.RegisterRequestDto;
import com.linelife.demo.exception.TokenRefreshException;
import com.linelife.demo.model.RefreshToken;
import com.linelife.demo.model.Status;
import com.linelife.demo.model.User;

import com.linelife.demo.payload.request.TokenRefreshRequest;
import com.linelife.demo.payload.response.MessageResponse;
import com.linelife.demo.payload.response.TokenRefreshResponse;
import com.linelife.demo.security.jwt.JwtUser;
import com.linelife.demo.security.jwt.JwtUtils;
import com.linelife.demo.service.ParametersUserService;
import com.linelife.demo.service.PersonalUserService;
import com.linelife.demo.service.UserService;
import com.linelife.demo.service.impl.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;


@RestController
@RequestMapping(value = "api/auth/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService tokenService;
    private final ParametersUserService parametersUserService;
    private final PersonalUserService personalUserService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils, RefreshTokenService tokenService, ParametersUserService parametersUserService, PersonalUserService personalUserService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.tokenService = tokenService;
        this.parametersUserService = parametersUserService;
        this.personalUserService = personalUserService;
    }


    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(jwtUser);
        RefreshToken refreshToken = tokenService.createRefreshToken(jwtUser.getId());
        Map<Object, Object> response = new HashMap<>();
        response.put("id", jwtUser.getId());
        response.put("username", requestDto.getUsername());
        response.put("access_token", jwt);
        response.put("refresh_token", refreshToken.getToken());
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "register")
    public ResponseEntity register(@RequestBody RegisterRequestDto requestDto){
        if(userService.existsByUsername(requestDto.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if(userService.existsByEmail(requestDto.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        User u = new User();
        u.setPassword(requestDto.getPassword());
        u.setEmail(requestDto.getEmail());
        u.setUsername(requestDto.getUsername());
        u.setStatus(Status.ACTIVE);
        User us = userService.saveUser(u);
        parametersUserService.add(us.getId());
        personalUserService.add(us.getId());
        String jwt = jwtUtils.generateTokenFromUsername(us.getUsername());
        RefreshToken refreshToken = tokenService.createRefreshToken(us.getId());
        Map<Object, Object> response = new HashMap<>();
        response.put("id", us.getId());
        response.put("username", us.getUsername());
        response.put("access_token", jwt);
        response.put("refresh_token", refreshToken.getToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "refreshtoken")
    public ResponseEntity refreshtoken(@RequestBody TokenRefreshRequest request){

        String requestRefreshToken = request.getRefreshToken();
        return tokenService.findByToken(requestRefreshToken)
                .map(tokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    RefreshToken refreshToken = tokenService.createRefreshToken(user.getId());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, refreshToken.getToken()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

}
