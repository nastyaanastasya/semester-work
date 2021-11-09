package ru.kpfu.models;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Ingredient {
    private long id;
    private String name;
    private int amount;
    private String unit;
}
