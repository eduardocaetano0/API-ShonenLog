package br.com.shonenlog.controller;

import br.com.shonenlog.mapper.UserMapper;
import br.com.shonenlog.request.UserRequest;
import br.com.shonenlog.response.UserResponse;
import br.com.shonenlog.service.UserService;
import lombok.RequiredArgsConstructor;

import br.com.shonenlog.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shonenLog/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register (@RequestBody UserRequest userRequest) {
        User savedUser = userService.save(UserMapper.toUser(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));
    }
}
