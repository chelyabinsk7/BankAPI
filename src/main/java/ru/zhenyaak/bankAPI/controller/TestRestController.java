package ru.zhenyaak.bankAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zhenyaak.bankAPI.entity.Test;
import ru.zhenyaak.bankAPI.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestRestController {

    @Autowired
    private TestService testService;

    @GetMapping("/list")
    public List<Test> showTest() {
        return testService.getTest();
    }
}
