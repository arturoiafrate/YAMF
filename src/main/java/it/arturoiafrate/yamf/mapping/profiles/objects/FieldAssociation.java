package it.arturoiafrate.yamf.mapping.profiles.objects;

import java.io.Serializable;

public class FieldAssociation implements Serializable {
    private String source;
    private String target;

    public FieldAssociation(){}

    public FieldAssociation(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
