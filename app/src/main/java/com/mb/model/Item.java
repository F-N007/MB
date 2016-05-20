package com.mb.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String link;
    private int ID;
    private String name, description, category, addresse, image_path;
    private Double rating, location_latitude, location_longtitude;
    private String create_date;
    private String shared,username;
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm");

    public Item() {
        this.create_date = formatter.format(new Date());
        this.shared = "no";
        this.link = "http://www.google.com";



    }


    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }



    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(Double location_latitude) {
        this.location_latitude = location_latitude;
    }

    public Double getLocation_longtitude() {
        return location_longtitude;
    }

    public void setLocation_longtitude(Double location_longtitude) {
        this.location_longtitude = location_longtitude;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    @Override
    public String toString() {
        return "Item{" + "No: " + ID + ", Name: " + name + ", Description: " + description + ", Category: " + category + ", Address: " + addresse + ", Rating: " + rating + ", Latitude" + location_latitude + ", Longtitude: " + location_longtitude + ", Date: " + create_date + ", Shared: " + shared + '}';
    }


}
