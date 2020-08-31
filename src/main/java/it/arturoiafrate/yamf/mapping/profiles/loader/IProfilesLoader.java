package it.arturoiafrate.yamf.mapping.profiles.loader;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;

import java.util.Map;

public interface IProfilesLoader {
    Map<String, Profile> getProfiles() throws GenericException;
}
