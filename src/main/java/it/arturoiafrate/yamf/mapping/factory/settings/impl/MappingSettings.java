package it.arturoiafrate.yamf.mapping.factory.settings.impl;

import it.arturoiafrate.yamf.mapping.factory.settings.IMappingSettings;

import java.util.HashMap;
import java.util.Map;

public class MappingSettings implements IMappingSettings {

    private boolean mapSameNameFields;
    private Map<String, String> associationMap;

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
    public boolean mapFieldsWithSameName() {
        return mapSameNameFields;
    }

    @Override
    public Map<String, String> getFieldsAssociation() {
        return associationMap;
    }
}
