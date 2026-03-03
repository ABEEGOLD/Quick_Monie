package com.semicolon.africa.beework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String getGreet(){
        return "index.html";
    }
    @GetMapping("better")
    public String getBetter(){
        return "Coder Girl";
    }

}
