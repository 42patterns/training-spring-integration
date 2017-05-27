package com.example.integration.orchestration.services.events;

import com.example.integration.orchestration.services.accommodation.Accommodation;

import java.util.List;

public class Event {

    private String venueName;
    private String city;
    private String country;
    private String website;
    private String startDate;
    private List<Accommodation> hotels;


    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<Accommodation> getHotels() {
        return hotels;
    }

    public void setHotels(List<Accommodation> hotels) {
        this.hotels = hotels;
    }

    @Override
    public String toString() {
        return "Event{" +
                "venueName='" + venueName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", website='" + website + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
