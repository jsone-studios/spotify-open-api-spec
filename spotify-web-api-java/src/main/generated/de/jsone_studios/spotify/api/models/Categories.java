package de.jsone_studios.spotify.api.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Categories {
    private Paging<Category> categories;
}
