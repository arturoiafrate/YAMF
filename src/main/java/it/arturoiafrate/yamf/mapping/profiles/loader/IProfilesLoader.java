package it.arturoiafrate.yamf.mapping.profiles.loader;

import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;

import java.util.Map;

public interface IProfilesLoader {
    Map<String, Profile> getProfiles();
}
