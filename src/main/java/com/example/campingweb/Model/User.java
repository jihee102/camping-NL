package com.example.campingweb.Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private List<CampingPark> favoriteList;

    public User(String firstName, String lastName, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        favoriteList = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<CampingPark> getFavoriteList() {
        return favoriteList;
    }

    public void addCampingPark(CampingPark campingPark){
        favoriteList.add(campingPark);
    }

    public void removeCampingPark(CampingPark campingPark){
        for(CampingPark park : favoriteList){
            if(park==campingPark){
                favoriteList.remove(campingPark);
            }
        }
    }
}