package it.arturoiafrate.yamf.mapping.profiles.loader.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.arturoiafrate.yamf.mapping.json.deserializer.ProfilesDeserializer;
import it.arturoiafrate.yamf.mapping.profiles.loader.IProfilesLoader;
import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;

import java.util.Map;

public class JsonProfileLoader implements IProfilesLoader {

    String jsonString;
    Gson gson;

    public JsonProfileLoader(String json){
        jsonString = json;
        gson = new GsonBuilder()
                .registerTypeAdapter(Map.class, new ProfilesDeserializer())
                .create();
    }

    @Override
    public Map<String, Profile> getProfiles() {
        return gson.fromJson(jsonString, Map.class);
    }
}
