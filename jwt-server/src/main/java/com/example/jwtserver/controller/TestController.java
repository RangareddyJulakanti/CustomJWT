package com.example.jwtserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("@groupServiceTemplate.prodOrNonProd(authentication)")
    @GetMapping("/")
    public String m1(){
        return "Hi";
    }

}
