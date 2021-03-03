package de.sonallux.spotify.api.models;

import lombok.*;

/**
 * <a href="https://developer.spotify.com/documentation/web-api/reference/#object-simplifiedtrackobject">SimplifiedTrackObject</a>
 */
@Getter
@Setter
@NoArgsConstructor
public class SimplifiedTrack {
    /**
     * <p>The artists who performed the track. Each artist object includes a link in <code>href</code> to more detailed information about the artist.</p>
     */
    private java.util.List<SimplifiedArtist> artists;
    /**
     * <p>A list of the countries in which the track can be played, identified by their <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 alpha-2</a> code.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("available_markets")
    private java.util.List<String> availableMarkets;
    /**
     * <p>The disc number (usually <code>1</code> unless the album consists of more than one disc).</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("disc_number")
    private Integer discNumber;
    /**
     * <p>The track length in milliseconds.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("duration_ms")
    private Integer durationMs;
    /**
     * <p>Whether or not the track has explicit lyrics ( <code>true</code> = yes it does; <code>false</code> = no it does not OR unknown).</p>
     */
    private Boolean explicit;
    /**
     * <p>External URLs for this track.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("external_urls")
    private ExternalUrl externalUrls;
    /**
     * <p>A link to the Web API endpoint providing full details of the track.</p>
     */
    private String href;
    /**
     * <p>The <a href="https://developer.spotify.com/documentation/web-api/#spotify-uris-and-ids">Spotify ID</a> for the track.</p>
     */
    private String id;
    /**
     * <p>Whether or not the track is from a local file.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("is_local")
    private Boolean isLocal;
    /**
     * <p>Part of the response when <a href="https://developer.spotify.com/documentation/general/guides/track-relinking-guide/">Track Relinking</a> is applied. If <code>true</code> , the track is playable in the given market. Otherwise <code>false</code>.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("is_playable")
    private Boolean isPlayable;
    /**
     * <p>Part of the response when <a href="https://developer.spotify.com/documentation/general/guides/track-relinking-guide/">Track Relinking</a> is applied and is only part of the response if the track linking, in fact, exists. The requested track has been replaced with a different track. The track in the <code>linked_from</code> object contains information about the originally requested track.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("linked_from")
    private LinkedTrack linkedFrom;
    /**
     * <p>The name of the track.</p>
     */
    private String name;
    /**
     * <p>A URL to a 30 second preview (MP3 format) of the track.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("preview_url")
    private String previewUrl;
    /**
     * <p>Included in the response when a content restriction is applied. See <a href="https://developer.spotify.com/documentation/web-api/reference/#object-trackrestrictionobject">Restriction Object</a> for more details.</p>
     */
    private TrackRestriction restrictions;
    /**
     * <p>The number of the track. If an album has several discs, the track number is the number on the specified disc.</p>
     */
    @com.fasterxml.jackson.annotation.JsonProperty("track_number")
    private Integer trackNumber;
    /**
     * <p>The object type: &quot;track&quot;.</p>
     */
    private String type;
    /**
     * <p>The <a href="https://developer.spotify.com/documentation/web-api/#spotify-uris-and-ids">Spotify URI</a> for the track.</p>
     */
    private String uri;
}
