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
    public String getHomePage(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session){
        if(extractCookie(req)==null){
            if(!(session.getAttribute("userName") ==null)){
                Cookie cookie= new Cookie("userName",(String) session.getAttribute("userName"));
                res.addCookie(cookie);
            }
        }
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

        //add in the parks html mapping
        //model.addAttribute("myList",CampingAdmin.findUser(user-name).getFavoriteList())

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
        if(provinceString==""||CampingAdmin.findProvince(provinceString)==null){
            return "redirect:/camping";
        }
        return "redirect:/camping/"+provinceString;
    }



    @GetMapping(params = "checkboxAmenities" ,path = "/searchparks")
    public String getCustomSearch(@RequestParam("checkboxAmenities")String[] customValues, Model model){
        ArrayList<String> customList = new ArrayList<>();
        Collections.addAll(customList,customValues);
        model.addAttribute("provinceName","Netherlands");
        if(customList.size()==1){
            model.addAttribute("warning","Please choose at least one option.");
        }else{
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
                                  ,HttpServletRequest req, HttpServletResponse res){
        if(extractCookie(req)!=null){
            CampingAdmin.addFavoriteParkInUser(extractCookie(req),park);
        }
        return "redirect:/mypage";
    }

    private String extractCookie(HttpServletRequest req) {
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals("userName"))
                return c.getValue();
        }
        return null;
    }

}
