package de.sonallux.spotify.parser;

import de.sonallux.spotify.core.model.SpotifyObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Slf4j
class ApiObjectFixes {

    static void fixApiObjects(SortedMap<String, SpotifyObject> objects) {
        fixLinkedTrackObjectReferenceInTrackObject(objects);
        fixTracksTypeInPlaylistObject(objects);
        fixTracksTypeInAlbumObject(objects);
        fixEpisodesTypeInShowObject(objects);
    }

    private static void fixLinkedTrackObjectReferenceInTrackObject(SortedMap<String, SpotifyObject> objects) {
        var linkedFromProperty = objects.get("TrackObject")
                .getProperties().stream()
                .filter(p -> "linked_from".equals(p.getName()) && "".equals(p.getType()))
                .findFirst().orElse(null);
        if (linkedFromProperty == null) {
            log.warn("TrackObject: wrong type of property linked_from has been fixed");
        } else {
            linkedFromProperty.setType("LinkedTrackObject");
        }
    }

    private static void fixTracksTypeInPlaylistObject(SortedMap<String, SpotifyObject> objects) {
        var tracksProperty = objects.get("PlaylistObject")
            .getProperties().stream()
            .filter(p -> "tracks".equals(p.getName()) && "Array[PlaylistTrackObject]".equals(p.getType()))
            .findFirst().orElse(null);
        if (tracksProperty == null) {
            log.warn("PlaylistObject: wrong type of property tracks has been fixed");
        } else {
            tracksProperty.setType("PagingObject[PlaylistTrackObject]");
        }
    }

    private static void fixTracksTypeInAlbumObject(SortedMap<String, SpotifyObject> objects) {
        var tracksProperty = objects.get("AlbumObject")
            .getProperties().stream()
            .filter(p -> "tracks".equals(p.getName()) && "Array[SimplifiedTrackObject]".equals(p.getType()))
            .findFirst().orElse(null);
        if (tracksProperty == null) {
            log.warn("AlbumObject: wrong type of property tracks has been fixed");
        } else {
            tracksProperty.setType("PagingObject[SimplifiedTrackObject]");
        }
    }

    private static void fixEpisodesTypeInShowObject(SortedMap<String, SpotifyObject> objects) {
        var tracksProperty = objects.get("ShowObject")
            .getProperties().stream()
            .filter(p -> "episodes".equals(p.getName()) && "Array[SimplifiedEpisodeObject]".equals(p.getType()))
            .findFirst().orElse(null);
        if (tracksProperty == null) {
            log.warn("ShowObject: wrong type of property episodes has been fixed");
        } else {
            tracksProperty.setType("PagingObject[SimplifiedEpisodeObject]");
        }
    }

    static List<SpotifyObject> getMissingObjects() {
        var objects = new ArrayList<SpotifyObject>();

        //AlbumsObject
        objects.add(new SpotifyObject("AlbumsObject")
            .addProperty(new SpotifyObject.Property("albums", "Array[AlbumObject]")));

        //ArtistsObject
        objects.add(new SpotifyObject("ArtistsObject")
            .addProperty(new SpotifyObject.Property("artists", "Array[ArtistObject]")));

        //AudioAnalysisObject
        objects.add(new SpotifyObject("SectionObject", "https://developer.spotify.com/documentation/web-api/reference/tracks/get-audio-analysis/#section-object")
            .addProperty(new SpotifyObject.Property("start", "Float", "The starting point (in seconds) of the section."))
            .addProperty(new SpotifyObject.Property("duration", "Float", "The duration (in seconds) of the section."))
            .addProperty(new SpotifyObject.Property("confidence", "Float", "The confidence, from 0.0 to 1.0, of the reliability of the section’s \"designation\"."))
            .addProperty(new SpotifyObject.Property("loudness", "Float", "The overall loudness of the section in decibels (dB). Loudness values are useful for comparing relative loudness of sections within tracks."))
            .addProperty(new SpotifyObject.Property("tempo", "Float", "The overall estimated tempo of the section in beats per minute (BPM). In musical terminology, tempo is the speed or pace of a given piece and derives directly from the average beat duration."))
            .addProperty(new SpotifyObject.Property("tempo_confidence", "Float"))
            .addProperty(new SpotifyObject.Property("key", "Integer"))
            .addProperty(new SpotifyObject.Property("key_confidence", "Float"))
            .addProperty(new SpotifyObject.Property("mode", "Integer"))
            .addProperty(new SpotifyObject.Property("mode_confidence", "Float"))
            .addProperty(new SpotifyObject.Property("time_signature", "Integer"))
            .addProperty(new SpotifyObject.Property("time_signature_confidence", "Float"))
        );
        objects.add(new SpotifyObject("SegmentObject", "https://developer.spotify.com/documentation/web-api/reference/tracks/get-audio-analysis/#segment-object")
            .addProperty(new SpotifyObject.Property("start", "Float"))
            .addProperty(new SpotifyObject.Property("duration", "Float"))
            .addProperty(new SpotifyObject.Property("confidence", "Float"))
            .addProperty(new SpotifyObject.Property("loudness_start", "Float"))
            .addProperty(new SpotifyObject.Property("loudness_max", "Float"))
            .addProperty(new SpotifyObject.Property("loudness_max_time", "Float"))
            .addProperty(new SpotifyObject.Property("loudness_end", "Float"))
            .addProperty(new SpotifyObject.Property("pitches", "Array[Float]"))
            .addProperty(new SpotifyObject.Property("timbre", "Array[Float]"))
        );
        objects.add(new SpotifyObject("TimeIntervalObject", "https://developer.spotify.com/documentation/web-api/reference/tracks/get-audio-analysis/#time-interval-object")
            .addProperty(new SpotifyObject.Property("start", "Float", "The starting point (in seconds) of the time interval."))
            .addProperty(new SpotifyObject.Property("duration", "Float", "The duration (in seconds) of the time interval."))
            .addProperty(new SpotifyObject.Property("confidence", "Float", "The confidence, from 0.0 to 1.0, of the reliability of the interval."))
        );
        objects.add(new SpotifyObject("AudioAnalysisObject", "https://developer.spotify.com/documentation/web-api/reference/tracks/get-audio-analysis/#audio-analysis-object")
            .addProperty(new SpotifyObject.Property("bars", "Array[TimeIntervalObject]", "The time intervals of the bars throughout the track. A bar (or measure) is a segment of time defined as a given number of beats. Bar offsets also indicate downbeats, the first beat of the measure."))
            .addProperty(new SpotifyObject.Property("beats", "Array[TimeIntervalObject]", "The time intervals of beats throughout the track. A beat is the basic time unit of a piece of music; for example, each tick of a metronome. Beats are typically multiples of tatums."))
            .addProperty(new SpotifyObject.Property("sections", "Array[SectionObject]", "Sections are defined by large variations in rhythm or timbre, e.g. chorus, verse, bridge, guitar solo, etc. Each section contains its own descriptions of tempo, key, mode, time_signature, and loudness."))
            .addProperty(new SpotifyObject.Property("segments", "Array[SegmentObject]", "Audio segments attempts to subdivide a song into many segments, with each segment containing a roughly consistent sound throughout its duration."))
            .addProperty(new SpotifyObject.Property("tatums", "Array[TimeIntervalObject]", "A tatum represents the lowest regular pulse train that a listener intuitively infers from the timing of perceived musical events (segments)."))
        );

        //AudioFeaturesArrayObject
        objects.add(new SpotifyObject("AudioFeaturesArrayObject")
            .addProperty(new SpotifyObject.Property("audio_features", "Array[AudioFeaturesObject]")));

        //CategoriesObject
        objects.add(new SpotifyObject("CategoriesObject")
            .addProperty(new SpotifyObject.Property("categories", "PagingObject[CategoryObject]")));

        //EpisodesObject
        objects.add(new SpotifyObject("EpisodesObject")
            .addProperty(new SpotifyObject.Property("episodes", "Array[EpisodeObject]")));

        //ErrorResponseObject
        objects.add(new SpotifyObject("ErrorResponseObject")
            .addProperty(new SpotifyObject.Property("error", "ErrorObject")));

        //FeaturedPlaylistObject
        objects.add(new SpotifyObject("FeaturedPlaylistObject")
            .addProperty(new SpotifyObject.Property("message", "String"))
            .addProperty(new SpotifyObject.Property("playlists", "PagingObject[SimplifiedPlaylistObject]"))
        );

        //FollowingArtistsObject
        objects.add(new SpotifyObject("FollowingArtistsObject")
            .addProperty(new SpotifyObject.Property("artists", "CursorPagingObject[ArtistObject]")));

        //GenreSeedsObject
        objects.add(new SpotifyObject("GenreSeedsObject")
            .addProperty(new SpotifyObject.Property("genres", "Array[String]")));

        //NewReleasesObject
        objects.add(new SpotifyObject("NewReleasesObject")
            //.addProperty(new SpotifyObject.Property("message", "String"))//Note: property is specified, but it is not returned
            .addProperty(new SpotifyObject.Property("albums", "PagingObject[SimplifiedAlbumObject]"))
        );

        //PlaylistPagingObject
        objects.add(new SpotifyObject("PlaylistPagingObject")
            .addProperty(new SpotifyObject.Property("playlists", "PagingObject[SimplifiedPlaylistObject]")));

        //SearchResponseObject
        objects.add(new SpotifyObject("SearchResponseObject")
            .addProperty(new SpotifyObject.Property("album", "PagingObject[SimplifiedAlbumObject]"))
            .addProperty(new SpotifyObject.Property("artist", "PagingObject[ArtistObject]"))
            .addProperty(new SpotifyObject.Property("playlist", "PagingObject[SimplifiedPlaylistObject]"))
            .addProperty(new SpotifyObject.Property("track", "PagingObject[TrackObject]"))
            .addProperty(new SpotifyObject.Property("show", "PagingObject[SimplifiedShowObject]"))
            .addProperty(new SpotifyObject.Property("episode", "PagingObject[SimplifiedEpisodeObject]"))
        );

        //ShowsObject
        objects.add(new SpotifyObject("ShowsObject")
            .addProperty(new SpotifyObject.Property("shows", "Array[SimplifiedShowObject]")));

        //SnapshotIdObject
        objects.add(new SpotifyObject("SnapshotIdObject")
            .addProperty(new SpotifyObject.Property("snapshot_id", "String", "The snapshot_id can be used to identify your playlist version in future requests.")));

        //TracksObject
        objects.add(new SpotifyObject("TracksObject")
            .addProperty(new SpotifyObject.Property("tracks", "Array[TrackObject]")));

        return objects;
    }
}
