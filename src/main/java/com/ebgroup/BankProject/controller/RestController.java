package com.ebgroup.BankProject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/")
    public String aa(){
        return "/account\n" +
                "/customer";
    }



}
