package com.example.campingweb;

import com.example.campingweb.Model.CampingAdmin;
import com.example.campingweb.Model.CampingPark;
import com.example.campingweb.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class LoginController {

   //for login (frontpage) with cookies
   @GetMapping("")
   public String getProfile(HttpSession session, HttpServletRequest req, HttpServletResponse res,
                            Model model,@CookieValue(value = "lastVisitedDate",defaultValue = "0")String lastVisit){
       if (!isLoggedIn(session)) {
           //Redirect user if he is not logged in.
           return "redirect:/mypage/login";
       }else{
           //get user favorite parks.
           List<CampingPark> myList = null;
           for(User u: CampingAdmin.getUsers()){
               if(u.getUserName().equals(session.getAttribute("userName"))){
                   model.addAttribute("user", u);
                   myList = u.getFavoriteList();
               }
           }
           model.addAttribute("favoriteParks", myList);
           

           for(Cookie c: req.getCookies()){
               if(c.getName().equals("lastVisitedDate"+session.getAttribute("userName"))){
                       String decodedCookivalue ="";
               try {
                   decodedCookivalue = URLDecoder.decode(c.getValue(),"UTF-8");
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               }
               model.addAttribute("lastVisit",decodedCookivalue);
               }
           }


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
                           HttpSession session, Model model) {
       //Hashmap for userName and password combination.
       HashMap<String, String> userInfo = new HashMap<>();
       for (User u : CampingAdmin.getUsers()){
           userInfo.put(u.getUserName(),u.getPassword());
       }

       //Check if the userName is matched with registered userName
       if(userInfo.containsKey(name)){
           //Check if the password is matched.
           if(userInfo.get(name).equals(password)) {
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
   public String getLogout(HttpSession session,  HttpServletRequest req, HttpServletResponse res){
       //Send new cookie with visit date & time.
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
       Calendar cal = Calendar.getInstance();
       String date = dateFormat.format(cal.getTime());
       Cookie cookie = null;
       try{
           String encodedCookieValue = URLEncoder.encode(date,"UTF-8");
           cookie = new Cookie("lastVisitedDate"+session.getAttribute("userName"), encodedCookieValue);
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       cookie.setMaxAge(60);
       cookie.setPath("/");
       res.addCookie(cookie);
       session.invalidate();

       return "redirect:/mypage/login";
   }

   @PostMapping(path="/deletefavorite")
   public String deleteFavoritePark(@RequestParam("deletePark")String park,HttpServletRequest req, HttpServletResponse res,
                                            HttpSession session){
       // If an user is already logged in, the user can delete an itme in the favoirte list,
       // otherwise, redirect log-in page.
       if(isLoggedIn(session)){
           CampingAdmin.deleteFavoriteParkInUser(String.valueOf(session.getAttribute("userName")),park);
           return "redirect:/mypage";
       }else{
           return "redirect:/mypage/login";
       }
   }


   // to check is username is set (if you're logged in)

   private boolean isLoggedIn(HttpSession session) {
       return session.getAttribute("userName") != null;
   }

}
