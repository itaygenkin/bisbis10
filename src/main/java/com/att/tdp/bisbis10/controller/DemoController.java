package com.att.tdp.bisbis10.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class DemoController {


    @PostMapping("/testApp")
    ResponseEntity<Boolean> testApp() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/")
    public String getPage(){
        return "Welcome";
    }

}
