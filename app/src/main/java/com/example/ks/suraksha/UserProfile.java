package com.example.ks.suraksha;

import android.content.Context;


public class UserProfile {
    private String name;
    private String phone;
    private static UserProfile user=null;
    private static Address userAddress=null;


    UserProfile(String name, String phone){
        this.name=name;
        this.phone=phone;
    }

    public static UserProfile getUser(Context context) {
        if (user!=null)
            return user;
        if (AppData.isUserProfileSetup(context))
            user=AppData.getUserProfile(context);
        if (AppData.iPermanentAddressProvided(context))
            userAddress=AppData.getUserAddress(context);
        return user;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public static Address getUserAddress() {
        return userAddress;
    }

    public static class Address{
        private String streetNo;
        private String landmark;
        private String city;
        private String zipCode;
        private String state;
        private String country;

        public Address(String streetNo, String landmark, String city, String zipCode, String state, String country) {
            this.streetNo = streetNo;
            this.landmark = landmark;
            this.city = city;
            this.zipCode = zipCode;
            this.state = state;
            this.country = country;
        }

        public String getStreetNo() {
            return streetNo;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getCity() {
            return city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public static Address getUserAddress() {
            return userAddress;
        }
    }
}
