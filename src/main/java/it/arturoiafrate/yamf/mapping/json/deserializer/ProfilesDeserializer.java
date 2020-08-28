package it.arturoiafrate.yamf.mapping.json.deserializer;

import com.google.gson.*;
import it.arturoiafrate.yamf.mapping.profiles.objects.FieldAssociation;
import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilesDeserializer implements JsonDeserializer<Map<String, Profile>> {
    @Override
    public Map<String, Profile> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final Map<String, Profile> out = new HashMap<>();
        JsonObject fullJson = jsonElement.getAsJsonObject();
        JsonArray jsonProfiles = fullJson.get("profiles").getAsJsonArray();
        jsonProfiles.forEach(element -> {
            JsonObject objElement = element.getAsJsonObject();
            out.put(objElement.get("profileName").getAsString(), extractProfile(objElement));
        });
        return out;
    }

    private Profile extractProfile(JsonObject obj){
        JsonObject jsonProfile = obj.get("profile").getAsJsonObject();
        return new Profile(jsonProfile.get("mapFieldsWithSameName").getAsBoolean(),
                extractAssociations(jsonProfile.get("associations").getAsJsonArray()));
    }

    private List<FieldAssociation> extractAssociations(JsonArray jsonAssociationsArray){
        final List<FieldAssociation> out = new ArrayList<>();
        jsonAssociationsArray.forEach(jsonElement -> out.add(extractFieldAssociation(jsonElement)));
        return out;
    }

    private FieldAssociation extractFieldAssociation(JsonElement jsonElement){
        JsonObject element = jsonElement.getAsJsonObject();
        return new FieldAssociation(element.get("source").getAsString(), element.get("target").getAsString());
    }

}
