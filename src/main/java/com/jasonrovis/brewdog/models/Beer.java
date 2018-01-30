package com.jasonrovis.brewdog.models;

/**
 * Created by jasonrovis on 29/01/2018.
 */

public class Beer {

    private String name;
    private String tagline;
    private String description;
    private Double abv;
    private String imageUrlString;

    public Beer(String name, String tagline, String description, Double abv, String imageUrlString) {
        this.name = name;
        this.tagline = tagline;
        this.description = description;
        this.abv = abv;
        this.imageUrlString = imageUrlString;

    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getDescription() {
        return description;
    }

    public Double getAbv() {
        return abv;
    }

    public String getImageUrlString() {
        return imageUrlString;
    }
}
