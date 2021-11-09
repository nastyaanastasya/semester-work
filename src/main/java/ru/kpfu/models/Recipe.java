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
    private long saveAmount;
    private float rating;
    private int timeOfCooking;
    private long likesNumber;
    private String title;
    private String description;
    private List<String> images;
    private Date date;
    private User user;
    List<Comment> comments;
    List<Ingredient> ingredients;
}
