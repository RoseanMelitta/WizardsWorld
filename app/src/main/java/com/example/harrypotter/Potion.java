package com.example.harrypotter;

import java.io.Serializable;

public class Potion implements Serializable {
    private String id;
    private String name;
    private String imageUrl;
    private String effect;
    private String side_effects;
    private String time;
    private String difficulty;

    public Potion( String name,String id, String imageUrl, String effect, String side_effects, String time, String difficulty) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.effect=effect;
        this.side_effects=side_effects;
        this.time=time;
        this.difficulty=difficulty;
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
    public String getEffect() {
        return effect;
    }
    public String getSide_effects() {
        return side_effects;
    }
    public String getTime() {
        return time;
    }
    public String getDifficulty() {
        return difficulty;
    }

}
