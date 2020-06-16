package com.example.campingweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class LoginController {

   //for login (frontpage) with cookies
   @GetMapping(path = "")
   public String getProfile(HttpSession session) {
       if (!isLoggedIn(session)) {
           //Redirect user if he is not logged in.
           return "redirect:/login";
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
/**
   @PostMapping(path = "/login")
   public String postLogin(HttpSession session, Model model) {
       if (users.contains(user.getUserName())) {
           //Create session and add username and password
           session.setAttribute("username", user.getUserName());
           session.setAttribute("password", user.getPassword());
           //And redirect user to profile page
           return "redirect:/mypage";
       } else {
           //Add a message if it's not valid username to show in the login form
           model.addAttribute("errormessage", "Log in information not valid!");
           return "frontpage";    //Redirect to the login getter
       }
   }
*/
   // to check is username is set (if you're logged in)

   private boolean isLoggedIn(HttpSession session) {
       return session.getAttribute("username") != null;
   }
}
