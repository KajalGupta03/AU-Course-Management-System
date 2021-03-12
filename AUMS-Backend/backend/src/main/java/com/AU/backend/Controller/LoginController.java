package com.AU.backend.Controller;

import com.AU.backend.Service.LoginService;
import com.AU.backend.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/googleLogin")
    @CrossOrigin("*")
    public ResponseEntity<User> loginByGoogle (@RequestBody User user) throws Exception {
        System.out.println("hello");
        return new ResponseEntity<>(loginService.loginByGoogle(user), HttpStatus.OK);
    }
}