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
    private float amount;
    private String unit;
}
