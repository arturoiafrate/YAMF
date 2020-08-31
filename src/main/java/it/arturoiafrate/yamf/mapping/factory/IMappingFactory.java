package it.arturoiafrate.yamf.mapping.factory;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.mapping.factory.settings.enumerators.ProfilesEncoding;

public interface IMappingFactory {
    <S> IMappingFactory fromObject(S object);
    <T> IMappingFactory toClass(Class<T> clazz);
    IMappingFactory mapFieldsWithSameName(boolean value);
    IMappingFactory mapAs(String sourceField, String targetField);
    IMappingFactory loadProfiles(String profiles, ProfilesEncoding encoding) throws GenericException;
    IMappingFactory useProfile(String profileName);
    <T> T doConvert() throws GenericException;
}
