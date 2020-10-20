package de.jsone_studios.spotify.api.models;

import lombok.*;

/**
 * <a href="https://developer.spotify.com/documentation/web-api/reference-beta/#object-contextobject">ContextObject</a>
 */
@Getter
@Setter
@NoArgsConstructor
public class Context {
    /**
     * <p>External URLs for this context.</p>
     */
    private ExternalUrl external_urls;
    /**
     * <p>A link to the Web API endpoint providing full details of the track.</p>
     */
    private String href;
    /**
     * <p>The object type, e.g. &quot;artist&quot;, &quot;playlist&quot;, &quot;album&quot;, &quot;show&quot;.</p>
     */
    private String type;
    /**
     * <p>The <a href="https://developer.spotify.com/documentation/web-api/#spotify-uris-and-ids">Spotify URI</a> for the context.</p>
     */
    private String uri;
}
