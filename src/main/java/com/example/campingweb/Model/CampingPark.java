package com.example.campingweb.Model;


import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public class CampingPark  {
    private String parkName;
    private String address;
    private String phone;
    private String webSiteAddress;
    private String parkImage;
    private boolean selected;
    private PropertyAmenity amenities;

    private List<Review> reviews;

    public CampingPark(String parkName, String address, String phone, String webAddress){
        this.parkName = parkName;
        this.address = address;
        this.phone = phone;
        this.webSiteAddress = webAddress;
        reviews = new ArrayList<>();
    }

    public CampingPark(String parkName, String address, String phone, String webAddress, String image){
        this.parkName = parkName;
        this.address = address;
        this.phone = phone;
        this.webSiteAddress = webAddress;
        this.parkImage = image;
        reviews = new ArrayList<>();
    }

    public CampingPark(String parkName, String address, String phone, String webAddress, String image, PropertyAmenity amenities){
        this.parkName = parkName;
        this.address = address;
        this.phone = phone;
        this.webSiteAddress = webAddress;
        this.parkImage = image;
        this.amenities= amenities;
        reviews = new ArrayList<>();
    }




    public void setParkImage(String parkImage) {
        this.parkImage = parkImage;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setAmenities(PropertyAmenity amenities) {
        this.amenities = amenities;
    }

    public boolean isSelected(){
        return selected;
    }

    public int getAverageOfRating() {
        if(reviews.size()>0) {
            int sum = 0;
            for (Review r : reviews) {
                sum += r.getSatisfactionScore();
            }

            double result = sum / reviews.size();
            int output = (int)roundToHalf(result);
            return output;
        }else{
            return 0;
        }
    }

    public double roundToHalf(double result) {
        double diff = result- (int)result;
        if(diff <0.25) return (int)result;
        else if(diff <0.75) return (int)result+0.5;
        else return (int)result+1;
    }

    public String getAddress() {
        return address;
    }

    public String getParkName() {
        return parkName;
    }

    public String getPhone() {
        return phone;
    }

    public String getParkImage() {
        return parkImage;
    }


    public List<Review> getReviews() {
        return reviews;
    }


    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public PropertyAmenity getAmenities() {
        return amenities;
    }

    public ArrayList<String> getParkProperties() {
        if(amenities != null) {
            ArrayList<String> parkProperties = new ArrayList<>();
            if (amenities.isTentSite()) {
                parkProperties.add("tent");
            }
            if (amenities.isCaravanSite()) {
                parkProperties.add("caravan");
            }
            if (amenities.isBungalow()) {
                parkProperties.add("bungalow");
            }
            if (amenities.isRestaurant()) {
                parkProperties.add("restaurant");
            }
            if (amenities.isSwimmingPool()) {
                parkProperties.add("pool");
            }
            if (amenities.isFreeWifi()) {
                parkProperties.add("wifi");
            }
            if (amenities.isAnimalFriendly()) {
                parkProperties.add("dog");
            }
            return parkProperties;
        }
        return null;
    }

    public void addReview(String id, String content, int score){
        Review r = new Review(id,content,score);
        reviews.add(r);
    }

    public void addReview(Review r){
        reviews.add(r);
    }


}
