package it.arturoiafrate.yamf.mapping.factory;

import it.arturoiafrate.yamf.exception.GenericException;

public interface IMappingFactory {
    <S> IMappingFactory fromObject(S object);
    <T> IMappingFactory toClass(Class<T> clazz);
    //IMappingFactory convertAs(String fieldFrom, String fieldTo);
    <T> T doConvert() throws GenericException;
}
