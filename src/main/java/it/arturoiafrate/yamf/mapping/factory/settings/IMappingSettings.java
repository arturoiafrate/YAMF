package it.arturoiafrate.yamf.mapping.factory.settings;

import java.util.Map;

public interface IMappingSettings {
    //Setter with fluent interface...
    IMappingSettings mapFieldsWithSameName(boolean value);
    IMappingSettings addSourceToTargetFieldAssociation(String sourceField, String targetField);
    //Getter to retrieve values
    boolean mapFieldsWithSameName();
    Map<String, String> getFieldsAssociation();

}
