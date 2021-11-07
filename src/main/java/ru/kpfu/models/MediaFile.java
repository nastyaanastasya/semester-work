package ru.kpfu.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MediaFile {
    private long id;
    private String name;
    private String mimeType;
}
