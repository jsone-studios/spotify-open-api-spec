package de.sonallux.spotify.api.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private Error error;
}
