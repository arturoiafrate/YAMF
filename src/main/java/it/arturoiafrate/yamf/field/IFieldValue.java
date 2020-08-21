package it.arturoiafrate.yamf.field;

import java.util.Optional;

public interface IFieldValue {
    Optional<Object> getValue();
    Class<?> getClassType();
}
