package com.hometalk.onepass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class HomeController {

    @GetMapping({ "/home"})
    public String home(Model model) {
        // 로그인 한 사용자는 대시보드로 리다이렉트

        // 시드 데이터 (관련 데이터 모델에 공유 - 추후)
        return "home";
    }
}


