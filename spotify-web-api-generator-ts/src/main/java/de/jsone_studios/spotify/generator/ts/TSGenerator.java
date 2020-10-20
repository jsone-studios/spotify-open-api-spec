package de.jsone_studios.spotify.generator.ts;

import com.samskivert.mustache.Mustache;
import de.jsone_studios.spotify.parser.model.SpotifyApiDocumentation;
import de.jsone_studios.spotify.parser.model.SpotifyObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;

public class TSGenerator {
    private final Mustache.Compiler templateCompiler;

    public TSGenerator() {
        this.templateCompiler = Mustache.compiler()
                .withLoader(this::loadTemplate)
                .escapeHTML(false);
    }

    private Reader loadTemplate(String name) {
        String fileName = String.format("/templates/%s.mustache", name);
        return new InputStreamReader(TSGenerator.class.getResourceAsStream(fileName));
    }

    public void generate(SpotifyApiDocumentation apiDocumentation, Path outputFile) throws IOException {
        //var mainSpotifyObjects = apiDocumentation.getObjects().stream()
        //        .filter(TSGenerator::isMainSpotifyObject)
        //        .collect(Collectors.toList());
        //mainSpotifyObjects.forEach(System.out::println);

        var objectTemplate = new ObjectTemplate().loadTemplate(templateCompiler);
        objectTemplate.generate(apiDocumentation.getObjects(), outputFile);
    }

    private static boolean isMainSpotifyObject(SpotifyObject spotifyObject) {
        return spotifyObject.getProperties().stream().anyMatch(property -> "id".equals(property.getName())) &&
                spotifyObject.getProperties().stream().anyMatch(property -> "type".equals(property.getName())) &&
                spotifyObject.getProperties().stream().anyMatch(property -> "href".equals(property.getName())) &&
                spotifyObject.getProperties().stream().anyMatch(property -> "uri".equals(property.getName()));
    }
}
