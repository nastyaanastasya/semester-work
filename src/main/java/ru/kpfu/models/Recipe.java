package ru.kpfu.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Recipe {
    private long id;
    private int saveAmount;
    private float rating;
    private int timeOfCooking;
    private int likesNumber;
    private String title;
    private String description;
    private String image;
    private Date date;
    private User user;
    List<Comment> comments;
    List<Ingredient> ingredients;
}
