package com.example.campingweb;


import com.example.campingweb.Model.CampingAdmin;
import com.example.campingweb.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @GetMapping(path = "")
    public String getRegister(){
        return "register";
    }

    @PostMapping(path = "/adduser")
    public String postRegister(User user, Model model) {
        if(CampingAdmin.addUser(user)) {

            return "redirect:/mypage/login";
        }
        else {
            model.addAttribute("warning", "username is already in use");
            return "register";
        }
    }

}
