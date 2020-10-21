package de.jsone_studios.spotify.api.models;

import lombok.*;

/**
 * <a href="https://developer.spotify.com/documentation/web-api/reference-beta/#object-privateuserobject">PrivateUserObject</a>
 */
@Getter
@Setter
@NoArgsConstructor
public class PrivateUser {
    /**
     * <p>The country of the user, as set in the user's account profile. An <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 alpha-2 country code</a>. <em>This field is only available when the current user has granted access to the <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#list-of-scopes">user-read-private</a> scope.</em></p>
     */
    private String country;
    /**
     * <p>The name displayed on the user's profile. <code>null</code> if not available.</p>
     */
    private String display_name;
    /**
     * <p>The user's email address, as entered by the user when creating their account. <em><strong>Important!</strong> This email address is unverified; there is no proof that it actually belongs to the user.</em> <em>This field is only available when the current user has granted access to the <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#list-of-scopes">user-read-email</a> scope.</em></p>
     */
    private String email;
    /**
     * <p>The user's explicit content settings. <em>This field is only available when the current user has granted access to the <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#list-of-scopes">user-read-private</a> scope.</em></p>
     */
    private ExplicitContentSettings explicit_content;
    /**
     * <p>Known external URLs for this user.</p>
     */
    private ExternalUrl external_urls;
    /**
     * <p>Information about the followers of the user.</p>
     */
    private Followers followers;
    /**
     * <p>A link to the Web API endpoint for this user.</p>
     */
    private String href;
    /**
     * <p>The <a href="https://developer.spotify.com/documentation/web-api/#spotify-uris-and-ids">Spotify user ID</a> for the user.</p>
     */
    private String id;
    /**
     * <p>The user's profile image.</p>
     */
    private java.util.List<Image> images;
    /**
     * <p>The user's Spotify subscription level: &quot;premium&quot;, &quot;free&quot;, etc. (The subscription level &quot;open&quot; can be considered the same as &quot;free&quot;.) <em>This field is only available when the current user has granted access to the <a href="https://developer.spotify.com/documentation/general/guides/authorization-guide/#list-of-scopes">user-read-private</a> scope.</em></p>
     */
    private String product;
    /**
     * <p>The object type: &quot;user&quot;</p>
     */
    private String type;
    /**
     * <p>The <a href="https://developer.spotify.com/documentation/web-api/#spotify-uris-and-ids">Spotify URI</a> for the user.</p>
     */
    private String uri;
}
