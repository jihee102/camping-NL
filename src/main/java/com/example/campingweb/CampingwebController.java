package com.example.campingweb;

import com.example.campingweb.Model.CampingAdmin;
import com.example.campingweb.Model.CampingPark;
import com.example.campingweb.Model.Province;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/camping")
public class CampingwebController {

    @GetMapping("")
    public String getHomePage(Model model){
        model.addAttribute("owner","Jihee");
        model.addAttribute("provinces", CampingAdmin.getProvinces());
        return "province";
    }

    @GetMapping("/{province}")
    public String getParkList(@PathVariable("province")String provinceName, Model model){
        model.addAttribute("provinceName",provinceName);
        ArrayList<CampingPark> parks= new ArrayList<>();
        for (CampingPark c : CampingAdmin.findProvince(provinceName).getCampingParks()){
            parks.add(c);
        }
        model.addAttribute("parkList", parks);
        return "parks";
    }

}
