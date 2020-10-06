package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user_table")
public class UserTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "age")
    private String age;

    @ColumnInfo(name = "weight")
    private String weight;

    @ColumnInfo(name = "height")
    private String height;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "imagePath")
    private String imagePath;


    public UserTable(@NonNull String id, String name, String gender, String age, String weight,
                     String height, String country, String city, String imagePath){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.country = country;
        this.city = city;
        this.imagePath = imagePath;
    }

    // =================================   SETTERS

    public void setName(String Name){
        this.name = name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setAge(String age){
        this.age = age;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public void setHeight(String height){
        this.height = height;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }


    // =================================   GETTERS

    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public String getWeight(){
        return weight;
    }
    public String getHeight(){
        return height;
    }
    public String getCountry(){
        return country;
    }
    public String getCity(){
        return city;
    }
    public String getImagePath(){
        return imagePath;
    }



}
