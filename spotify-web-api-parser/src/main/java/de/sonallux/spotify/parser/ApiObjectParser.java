package de.sonallux.spotify.parser;

import de.sonallux.spotify.core.model.SpotifyObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
class ApiObjectParser {

    private String baseUrl;

    SortedMap<String, SpotifyObject> parseSpotifyObjects(List<Elements> sections, String baseUrl) throws ApiParseException{
        this.baseUrl = baseUrl;
        var objects = new TreeMap<String, SpotifyObject>();
        var objectSection = sections.get(sections.size() - 1);//Last section is the Objects Index
        for (var element : objectSection.select("h3")) {
            var object = parseSpotifyObject(element);
            if (objects.containsKey(object.getName())) {
                throw new ApiParseException("Object is defined twice: " + object.getName());
            } else {
                objects.put(object.getName(), object);
            }
        }
        //Apply fixes and add missing objects
        ApiObjectFixes.fixApiObjects(objects);
        for (var object : ApiObjectFixes.getMissingObjects()) {
            if (objects.containsKey(object.getName())) {
                log.warn("Object {} is no longer missing", object.getName());
            }
            else {
                objects.put(object.getName(), object);
            }
        }

        // Sort object properties by name
        for (var object : objects.values()) {
            object.getProperties().sort(Comparator.comparing(SpotifyObject.Property::getName));
        }

        return objects;
    }

    private SpotifyObject parseSpotifyObject(Element objectHeader) throws ApiParseException {
        var id = objectHeader.attributes().get("id");
        var link = this.baseUrl + "/#" + id;
        var objectName = objectHeader.text();
        var spotifyObject = new SpotifyObject(objectName, id, link);
        for (var prop : objectHeader.nextElementSibling().select("tbody > tr")) {
            /*
            Properties have the following structure:
            <tr>
              <td>
                <code>{name}</code>
                <br>
                {description}
              </td>
              <td>{type}</td>
            </tr>
             */
            var nameElement = prop.selectFirst("code");
            var name = nameElement.text();
            var brElement = nameElement.nextElementSibling();
            var descriptionElements = brElement.nextElementSiblings();
            var description = Html2Markdown.convert(descriptionElements);
            var type = prop.child(1).text();
            var property = new SpotifyObject.Property(name, type, description);
            if (spotifyObject.getProperties().contains(property)) {
                throw new ApiParseException(String.format("Property %s of Object %s is defined twice", name, objectName));
            }
            spotifyObject.addProperty(property);
        }
        return spotifyObject;
    }
}
