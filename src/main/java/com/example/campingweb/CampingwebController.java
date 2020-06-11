package com.example.campingweb;

import com.example.campingweb.Model.CampingAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camping")
public class CampingwebController {

    @GetMapping("")
    public String getHomePage(Model model){
        model.addAttribute("owner","Jihee");
        model.addAttribute("provinces", CampingAdmin.provinces);
        return "province";
    }

    @GetMapping("/{province}")
    public String getParkList(@PathVariable("province")String provinceName, Model model){
        model.addAttribute("provinceName",provinceName);
        return "parks";
    }

}
