package it.arturoiafrate.yamf.mapping.factory.settings.impl;

import it.arturoiafrate.yamf.mapping.factory.settings.IMappingSettings;
import it.arturoiafrate.yamf.mapping.factory.settings.enumerators.ProfilesEncoding;

import java.util.HashMap;
import java.util.Map;

public class MappingSettings implements IMappingSettings {

    private boolean mapSameNameFields;
    private Map<String, String> associationMap;
    private ProfilesEncoding profilesEncodingFormat;

    public MappingSettings(){
        mapSameNameFields = true; //Default value...
        associationMap = new HashMap<>();
    }

    @Override
    public IMappingSettings mapFieldsWithSameName(boolean value){
        mapSameNameFields = value;
        return this;
    }

    @Override
    public IMappingSettings addSourceToTargetFieldAssociation(String sourceField, String targetField){
        associationMap.put(sourceField, targetField);
        return this;
    }

    @Override
    public IMappingSettings decodeProfilesFrom(ProfilesEncoding encoding) {
        profilesEncodingFormat = encoding;
        return this;
    }

    @Override
    public boolean mapFieldsWithSameName() {
        return mapSameNameFields;
    }

    @Override
    public Map<String, String> getFieldsAssociation() {
        return associationMap;
    }

    @Override
    public ProfilesEncoding getProfilesEncoding() {
        return profilesEncodingFormat;
    }
}
