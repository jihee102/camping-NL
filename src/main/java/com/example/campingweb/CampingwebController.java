package com.example.campingweb;

import com.example.campingweb.Model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/camping")
public class CampingwebController {

    @GetMapping("")
    public String getHomePage(Model model){
        model.addAttribute("provinces", CampingAdmin.getProvinces());

        return "province";
    }

    @GetMapping("/{province}")
    public String getParkList(@PathVariable("province")String provinceName, Model model){
        model.addAttribute("provinceName",provinceName);
        //Get all parks from the CampingAdmin
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
    public String goToProvince( @RequestParam(value = "search", required = false) String provinceString ) {
        //If the user press the search button without any text or with a text that has no data in the database,
        //it will redirect to camping page.
        if(provinceString==""||CampingAdmin.findProvince(provinceString)==null){
            return "redirect:/camping";
        }
        return "redirect:/camping/"+provinceString;
    }



    @GetMapping(params = "checkboxAmenities" ,path = "/searchparks")
    public String getCustomSearch(@RequestParam("checkboxAmenities")String[] customValues, Model model){

        ArrayList<String> customList = new ArrayList<>();
        //store the values to the arrayList from the array.
        Collections.addAll(customList,customValues);
        model.addAttribute("provinceName","Netherlands");

        //the arrayList default value is 1.
        //So, when the size is 1, it means the user haven't choose any condition.
        if(customList.size()==1){
            model.addAttribute("warning","Please choose at least one option.");
        }else{
            // If there is any outcome with the chosen conditions.
            if(CampingAdmin.getCampingList(customList).isEmpty()){
                model.addAttribute("warning","Sorry, There is no park with the condition you chose.");
            }else{
                model.addAttribute("parkList", CampingAdmin.getCampingList(customList));
            }
        }

        return "parks";
    }

    @PostMapping(params = "favoriteParkName", path = "/addfavorite")
    public String addFavoritePark(@RequestParam("favoriteParkName")String park
                                  ,HttpSession session){
        //If the user haven't done login, it will redirect to login page.
        if(session.getAttribute("userName")!=null){
            CampingAdmin.addFavoriteParkInUser(String.valueOf(session.getAttribute("userName")),park);
            return "redirect:/mypage";
        }else{
         return "redirect:/mypage/login";
        }
    }



}
