package kr.ac.kopo.psjjj.bookmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class WelcomeController {
    @GetMapping("/home")
    public String requestMethod() {
        return "welcome";
    }
}
