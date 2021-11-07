package ru.kpfu.models;

import lombok.*;

import java.util.Date;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Comment {
    private long id;
    private int rating;
    private int likesNumber;
    private String review;
    private Date date;
    private User user;
    private Recipe recipe;
}
