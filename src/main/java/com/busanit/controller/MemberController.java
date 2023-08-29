package com.busanit.controller;

import com.busanit.domain.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/memberEx")
public class MemberController {

    @GetMapping("/ex1")
    public String memberExample(Model model){
        List<MemberDTO> memberList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            MemberDTO mDto = new MemberDTO();
            mDto.setUserId("idnumber : "+i);
            mDto.setUserPw("pw : "+i+"***");
            mDto.setUserName("name is : "+ i + "???");
            mDto.setUserEmail("email area");
            mDto.setUserRegDate(LocalDateTime.now());

            memberList.add(mDto);
        }
        model.addAttribute("memberList",memberList);
        return "memberEx/memberEx1";
    }
}
