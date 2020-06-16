package com.example.campingweb;

import com.example.campingweb.Model.CampingAdmin;
import com.example.campingweb.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class LoginController {

   //for login (frontpage) with cookies
   @GetMapping("")
   public String getProfile(HttpSession session) {
       if (!isLoggedIn(session)) {
           //Redirect user if he is not logged in.
           return "redirect:/mypage/login";
       }

       return "mypage";
   }

   @GetMapping(path = "/login")
   public String getLogin(HttpSession session) {
       if (isLoggedIn(session)) {
           return "redirect:/mypage";
       }
       return "frontpage";
   }


   @PostMapping(path = "/login")
   public String postLogin(@RequestParam("userName")String name,@RequestParam("password")String password,
                           HttpSession session, Model model) {
       HashMap<String, String> userInfo = new HashMap<>();
       for (User u : CampingAdmin.getUsers()){
           userInfo.put(u.getUserName(),u.getPassword());
       }
       if(userInfo.containsKey(name)){
           if(userInfo.get(name).equals(password)) {
               session.setAttribute("username",name);
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

   // to check is username is set (if you're logged in)

   private boolean isLoggedIn(HttpSession session) {
       return session.getAttribute("username") != null;
   }
}
