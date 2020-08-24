package it.arturoiafrate.yamf.obj;

import java.util.Optional;

public interface IGenericObject {
    Optional<Object> getValue();
    Class<?> getClassType();
}
