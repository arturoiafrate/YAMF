package it.arturoiafrate.yamf.field.getter;

import it.arturoiafrate.yamf.field.IFieldValue;

import java.util.Map;
import java.util.Optional;

public interface IFieldGetter {
    Optional<Map<String, IFieldValue>> getAll();
    Optional<IFieldValue> get(String key);
}
