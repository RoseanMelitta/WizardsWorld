package com.example.harrypotter;

import java.io.Serializable;

public class Spells implements Serializable {
    private String id;
    private String name;
    private String imageUrl;
    private String incantation;
    private String category;
    private String effect;
    private String creator;

    public Spells( String name,String id, String imageUrl,String incantation,String category,String effect,String creator) {

        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
        this.incantation = incantation;
        this.category = category;
        this.effect = effect;
        this.creator = creator;

    }



    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getIncantation() {
        return incantation;
    }
    public String getCategory() {
        return category;
    }
    public String getEffect() {
        return effect;
    }
    public String getCreator() {
        return creator;
    }

}
