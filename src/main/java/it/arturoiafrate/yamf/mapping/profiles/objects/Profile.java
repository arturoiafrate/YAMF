package it.arturoiafrate.yamf.mapping.profiles.objects;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable {
    private Boolean mapFieldsWithSameName;
    private List<FieldAssociation> associations;

    public Profile(){}

    public Profile(Boolean mapFieldsWithSameName, List<FieldAssociation> associations) {
        this.mapFieldsWithSameName = mapFieldsWithSameName;
        this.associations = associations;
    }

    public Boolean getMapFieldsWithSameName() {
        return mapFieldsWithSameName;
    }

    public void setMapFieldsWithSameName(Boolean mapFieldsWithSameName) {
        this.mapFieldsWithSameName = mapFieldsWithSameName;
    }

    public List<FieldAssociation> getAssociations() {
        return associations;
    }

    public void setAssociations(List<FieldAssociation> associations) {
        this.associations = associations;
    }
}
