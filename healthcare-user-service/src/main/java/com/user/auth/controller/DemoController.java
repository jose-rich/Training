package com.user.auth.controller;

import com.user.auth.entity.UserEntity;
import com.user.auth.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    @GetMapping()
    public ResponseEntity<String> sayHello() {
        UserEntity user = AuthUtils.getCurrentUser();
        System.out.println("======================" + user);
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
