package com.example.recapsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityTestController {

    @GetMapping("/permit-all")
    public String permitAll(){
        return "success";
    }

    @GetMapping("/connected")
    public String connected(){
        return "success";
    }

    @GetMapping("/user")
    public String roleUser(){
        return "success";
    }

    @GetMapping("/admin")
    public String roleAdmin(){
        return "success";
    }


}
