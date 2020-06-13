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

//    @PostMapping(params = "reviewIndex", path = "/{province}/addreview")
//    @ResponseBody
//    public ResponseEntity<Review> addReview(@PathVariable("province")String provinceName, Review review,
//                                            @RequestParam("reviewIndex")int parkIndex) {
////        CampingAdmin.addReview(provinceName,parkIndex,review);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Location", "/camping/"+provinceName);
//        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//    }

//
//    @PostMapping("/addreview")
//    @ResponseBody
//    public ResponseEntity<Review> addRe(Review r){
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Location", "/camping/Drenthe");
//        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//    }
    @PostMapping("/addreview")
    @ResponseBody
    public String addReview(Review r){
        return "Review is added"+r;
    }

}
