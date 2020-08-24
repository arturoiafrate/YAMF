package it.arturoiafrate.yamf.field.getter;

import it.arturoiafrate.yamf.obj.IGenericObject;

import java.util.Map;
import java.util.Optional;

public interface IFieldGetter {
    Optional<Map<String, IGenericObject>> getAll();
    Optional<IGenericObject> get(String key);
}
