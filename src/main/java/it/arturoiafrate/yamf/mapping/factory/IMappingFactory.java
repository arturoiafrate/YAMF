package it.arturoiafrate.yamf.mapping.factory;

import it.arturoiafrate.yamf.exception.GenericException;

public interface IMappingFactory {
    <S> IMappingFactory fromObject(S object);
    <T> IMappingFactory toClass(Class<T> clazz);
    IMappingFactory mapFieldsWithSameName(boolean value);
    IMappingFactory mapAs(String sourceField, String targetField);
    <T> T doConvert() throws GenericException;
}
