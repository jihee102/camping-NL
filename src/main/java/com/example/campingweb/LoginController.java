package com.example.campingweb;

import com.example.campingweb.Model.CampingAdmin;
import com.example.campingweb.Model.CampingPark;
import com.example.campingweb.Model.User;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class LoginController {

   //for login (frontpage) with cookies
   @GetMapping("")
   public String getProfile(HttpSession session, HttpServletRequest req, HttpServletResponse res, Model model){
       if (!isLoggedIn(session)) {
           //Redirect user if he is not logged in.
           return "redirect:/mypage/login";
       }else{
           List<CampingPark> myList = null;
           for(User u: CampingAdmin.getUsers()){
               if(u.getUserName().equals(extractCookie(req))){
                   model.addAttribute("user", u);
                   myList = u.getFavoriteList();
               }
           }
           model.addAttribute("favoriteParks", myList);
           return "mypage";
       }
   }

   @GetMapping(path = "/login")
   public String getLogin(HttpSession session) {
       if (isLoggedIn(session)) {
           return "redirect:/mypage";
       }
       return "frontpage";
   }


   @PostMapping(path = "/login")
   public String postLogin(@RequestParam("userName")String name, @RequestParam("password")String password,
                           HttpSession session, Model model , HttpServletRequest req, HttpServletResponse res) {
       HashMap<String, String> userInfo = new HashMap<>();
       for (User u : CampingAdmin.getUsers()){
           userInfo.put(u.getUserName(),u.getPassword());
       }
       if(userInfo.containsKey(name)){
           if(userInfo.get(name).equals(password)) {
                   Cookie cookie = new Cookie("userName", name);
                   cookie.setMaxAge(60);
                   res.addCookie(cookie);
                   session.setAttribute("userName",name);
               return "redirect:/camping";
           }else{
               model.addAttribute("errormessage", "Log in information not valid!");
               return "redirect:/mypage/login";

           }
       }else{
           model.addAttribute("errormessage", "Log in information not valid!");
           return "redirect:/mypage/login";
       }


   }

   @GetMapping("/logout")
   public String getLogout(HttpSession session){
       session.invalidate();

       return "redirect:/mypage/login";
   }

   @PostMapping(path="/deletefavorite")
   @ResponseBody
   public ResponseEntity deleteFavoritePark(@RequestParam("deletePark")String park,HttpServletRequest req, HttpServletResponse res){

       CampingAdmin.deleteFavoriteParkInUser(extractCookie(req),park);
       HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.add("Location", "/mypage");
       return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
   }


   // to check is username is set (if you're logged in)

   private boolean isLoggedIn(HttpSession session) {
       return session.getAttribute("userName") != null;
   }

    private String extractCookie(HttpServletRequest req) {
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals("userName"))
                return c.getValue();
        }
        return null;
    }
}
