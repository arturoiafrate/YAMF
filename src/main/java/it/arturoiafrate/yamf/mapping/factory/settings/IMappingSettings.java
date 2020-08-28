package it.arturoiafrate.yamf.mapping.factory.settings;

import it.arturoiafrate.yamf.mapping.factory.settings.enumerators.ProfilesEncoding;

import java.util.Map;

public interface IMappingSettings {
    //Setter with fluent interface...
    IMappingSettings mapFieldsWithSameName(boolean value);
    IMappingSettings addSourceToTargetFieldAssociation(String sourceField, String targetField);
    IMappingSettings decodeProfilesFrom(ProfilesEncoding encoding);
    //Getter to retrieve values
    boolean mapFieldsWithSameName();
    Map<String, String> getFieldsAssociation();
    ProfilesEncoding getProfilesEncoding();

}
