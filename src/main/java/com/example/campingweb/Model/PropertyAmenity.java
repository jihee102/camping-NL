package com.example.campingweb.Model;


public class PropertyAmenity  {
    private boolean isTentSite;
    private boolean isCaravanSite;
    private boolean isBungalow;
    private boolean isFreeWifi;
    private boolean isRestaurant;
    private boolean isAnimalFriendly;
    private boolean isSwimmingPool;

    public PropertyAmenity(boolean isTentSite, boolean isCaravanSite, boolean isBungalow,
                           boolean isFreeWifi, boolean isRestaurant, boolean isAnimalFriendly, boolean isSwimmingPool){
        this.isTentSite= isTentSite;
        this.isCaravanSite = isCaravanSite;
        this.isBungalow = isBungalow;
        this.isFreeWifi =isFreeWifi;
        this.isRestaurant = isRestaurant;
        this.isAnimalFriendly = isAnimalFriendly;
        this.isSwimmingPool= isSwimmingPool;

    }


    public boolean isAnimalFriendly() {
        return isAnimalFriendly;
    }

    public boolean isBungalow() {
        return isBungalow;
    }

    public boolean isCaravanSite() {
        return isCaravanSite;
    }

    public boolean isFreeWifi() {
        return isFreeWifi;
    }

    public boolean isTentSite() {
        return isTentSite;
    }

    public boolean isRestaurant() {
        return isRestaurant;
    }

    public boolean isSwimmingPool() {
        return isSwimmingPool;
    }


}
