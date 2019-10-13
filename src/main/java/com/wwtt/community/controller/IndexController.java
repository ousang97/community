package com.wwtt.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Cread by wwtt
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){return "index";}
}
