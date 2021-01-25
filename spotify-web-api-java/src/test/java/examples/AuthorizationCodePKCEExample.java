package examples;

import de.sonallux.spotify.api.SpotifyApi;
import de.sonallux.spotify.api.SpotifyApiException;
import de.sonallux.spotify.api.authorization.InMemoryTokenStore;
import de.sonallux.spotify.api.authorization.Scope;
import de.sonallux.spotify.api.authorization.SpotifyAuthorizationException;
import de.sonallux.spotify.api.authorization.authorization_code.AuthorizationCodePKCEFlow;

public class AuthorizationCodePKCEExample {
    private final SpotifyApi spotifyApi;
    private final AuthorizationCodePKCEFlow authCodePKCEFlow;

    public AuthorizationCodePKCEExample() {
        var tokenStore = new InMemoryTokenStore();
        this.authCodePKCEFlow = new AuthorizationCodePKCEFlow("<client id>", "<redirect uri>", tokenStore);
        this.spotifyApi = new SpotifyApi(authCodePKCEFlow);
    }

    public static void main(String[] args) {
        var example = new AuthorizationCodePKCEExample();

        if (example.authorize()) {
            // Once authorized the access token will be automatically renewed, if is has expired
            example.useTheSpotifyWebApi();
        }
    }

    private boolean authorize() {
        // Navigate the user to this authorizationUrl, so he can grant access
        var authorizationUrl = authCodePKCEFlow.createAuthorizationUrl("<code challenge>")
            .state("<state>")
            .scopes(Scope.USER_LIBRARY_READ)
            //.showDialog(true)
            .build();

        // After successful authorization the user will be redirected
        var responseUrl = "<redirect response url>";
        var redirectResponse = authCodePKCEFlow.parseAuthorizationRedirectResponse(responseUrl);

        //Validate redirectResponse.getState() with the state parameter you choose for the authorization url

        if (!redirectResponse.isSuccess()) {
            System.err.println("Authorization failed: " + redirectResponse.getError());
            return false;
        }

        try {
            authCodePKCEFlow.exchangeAuthorizationCode(redirectResponse, "<code verifier>");
            return true;
        } catch (SpotifyAuthorizationException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void useTheSpotifyWebApi() {
        var usersSavedTracksCall = spotifyApi.getLibraryApi().getUsersSavedTracks();
        try {
            var usersSavedTracks = spotifyApi.callApiAndReturnBody(usersSavedTracksCall);
            usersSavedTracks.getItems().forEach(System.out::println);
        } catch (SpotifyApiException e) {
            e.printStackTrace();
        }
    }
}
