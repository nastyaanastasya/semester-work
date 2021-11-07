package ru.kpfu.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class User {
    private long id;
    private String username;
    private float rating;
    private String email;
    private String passwordHash;
    private Date date;
    private String image;
    private List<User> subscribers;
    private List<Recipe> recipes;
}
