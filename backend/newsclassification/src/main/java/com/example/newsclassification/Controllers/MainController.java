package com.example.newsclassification.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello, World!";
    }


    @GetMapping("/api/v1")
    public String sayHelloV1() {
        return "Hello, Api!";
    }
}
