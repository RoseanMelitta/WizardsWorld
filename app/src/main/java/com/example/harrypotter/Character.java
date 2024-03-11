package com.example.harrypotter;

import java.io.Serializable;

public class Character implements Serializable {
    private String id;
    private String name;
    private String imageUrl;
    private String died;
    private String born;
    private String nationality;
    private String house;
    private String gender;
    private String species;
    // Constructor care primește informații despre personaj și le setează

    public Character(String id,
                     String name, String imageUrl, String died, String born, String nationality, String house, String gender, String species) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.died= died;
        this.born= born;
        this.nationality= nationality;
        this.house= house;
        this.gender= gender;
        this.species= species;
    }
    // Metodele Getter pentru a accesa informațiile despre personaj

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getDied() {return died;}

    public String getImageUrl() {
        return imageUrl;
    }
    public String getBorn() {
        return born;
    }

    public String getNationality() {
        return nationality;
    }

    public String getHouse() {
        return house;
    }

    public String getGender() {
        return gender;
    }

    public String getSpecies() {
        return species;
    }


}
