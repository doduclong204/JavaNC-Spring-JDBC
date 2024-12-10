package com.javaweb.api.web;

import com.javaweb.model.request.UserRequest;
import com.javaweb.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "newAPIOfWeb")
@RequiredArgsConstructor
public class UserAPI {
    private final IUserService userService;

    @PostMapping("/api/users")
    public void addUser(@RequestBody UserRequest request) {
        userService.add(request);
    }
}
