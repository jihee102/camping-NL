package com.example.campingweb;

import com.example.campingweb.Model.CampingAdmin;
import com.example.campingweb.Model.CampingPark;
import com.example.campingweb.Model.Province;
import com.example.campingweb.Model.Review;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    @PostMapping(params = "reviewIndex", path = "/{province}/addreview")
    @ResponseBody
    public ResponseEntity<Review> addReview(@PathVariable("province")String provinceName, Review review,
                                            @RequestParam("reviewIndex")int parkIndex) {
        CampingAdmin.addReview(provinceName,parkIndex,review);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/camping/"+provinceName);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @PostMapping(params="search", path="/findprovince")
    @ResponseBody
    public ResponseEntity goToProvince( @RequestParam("search") String provinceString, Model model) {
        Province p = CampingAdmin.findProvince(provinceString);
        ArrayList<CampingPark> parks= new ArrayList<>();
        for (CampingPark c : p.getCampingParks()){
            parks.add(c);
        }
        model.addAttribute("provinceName",p.getProvinceName());
        model.addAttribute("parkList", parks);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/camping/"+p.getProvinceName());
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @PostMapping(params = "checkboxAmenities" ,path = "/searchparks")
    @ResponseBody
    public String getCustomSearch(@RequestParam("checkboxAmenities")String[] customValues, Model model){
        List<String> customList = Arrays.asList(customValues);

        return null;
    }


}
