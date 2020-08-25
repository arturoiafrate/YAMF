package it.arturoiafrate.yamf.mapping.factory.impl;

import it.arturoiafrate.yamf.exception.*;
import it.arturoiafrate.yamf.exception.IllegalAccessException;
import it.arturoiafrate.yamf.field.getter.impl.FieldGetter;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;
import it.arturoiafrate.yamf.field.setter.impl.FieldSetter;
import it.arturoiafrate.yamf.mapping.factory.settings.IMappingSettings;
import it.arturoiafrate.yamf.mapping.factory.settings.impl.MappingSettings;
import it.arturoiafrate.yamf.obj.IGenericObject;
import it.arturoiafrate.yamf.obj.impl.GenericObject;
import it.arturoiafrate.yamf.mapping.factory.IMappingFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MappingFactory implements IMappingFactory{

    private IGenericObject source;
    private IGenericObject target;
    private Class<?> targetClass;
    private IMappingSettings settings;
    private List<String> alreadyProcessedSubClasses;

    public MappingFactory(){
        settings = new MappingSettings();
        alreadyProcessedSubClasses = new ArrayList<>();
    }

    @Override
    public <S> IMappingFactory fromObject(S object) {
        source = new GenericObject<>(object);
        return this;
    }

    @Override
    public <T> IMappingFactory toClass(Class<T> clazz) {
        targetClass = clazz;
        return this;
    }

    @Override
    public IMappingFactory mapFieldsWithSameName(boolean value) {
        settings.mapFieldsWithSameName(value);
        return this;
    }

    @Override
    public IMappingFactory mapAs(String sourceField, String targetField) {
        settings.addSourceToTargetFieldAssociation(sourceField, targetField);
        return this;
    }

    @Override
    public <T> T doConvert() throws GenericException {
        //1) Checking for mandatory fields
        checkForMandatoryFields();
        //2) Extracting field from soruce
        Map<String, IGenericObject> valueMap = extractFieldsFrom(source);
        //3) Setting extracted fields from source to target
        T tempTarget = settings.mapFieldsWithSameName() ?
                setSTDValuesToTarget(valueMap) : getEmptyTarget();
        //4) Mapping extra fields from source to target
        if(!settings.getFieldsAssociation().isEmpty()){
            tempTarget = setSpecificFieldsToTarget(tempTarget, valueMap, settings.getFieldsAssociation());
        }
        target = new GenericObject<>(tempTarget);
        return (T) target.getValue().get();
    }

    private <T> T setSpecificFieldsToTarget(T tempTarget, Map<String, IGenericObject> valueMap,
                                            Map<String, String> associationMap) throws GenericException{
        IFieldSetter setter = new FieldSetter();
        AtomicReference<T> temp = new AtomicReference<>(tempTarget);
        AtomicReference<GenericException> ex = new AtomicReference<>(null);
        associationMap.forEach((fieldFrom, fieldTo) ->{
            try {
                //Looking for sub-class fields...
                if(fieldFrom.contains(".")){
                    mapSubclassFields(valueMap, fieldFrom);
                }
                temp.set(setter.set(temp.get(), fieldTo, valueMap.get(fieldFrom)));
            } catch (GenericException e) {
                ex.set(e);
            }
        });
        if(Optional.ofNullable(ex.get()).isPresent())
            throw ex.get();
        return temp.get();
    }

    private Map<String, IGenericObject> mapSubclassFields(Map<String, IGenericObject> valueMap, String fieldDefinition)
            throws GenericException{
        List<String> levels = Arrays.stream(fieldDefinition.split("\\.")).collect(Collectors.toList());
        //Last level is the field, so we don't need to extract fields
        levels.remove(levels.size()-1);
        AtomicReference<String> current = new AtomicReference<>("");
        AtomicReference<GenericException> ex = new AtomicReference<>(null);
        levels.forEach( level -> {
            try{
                current.set(current.get().concat(level));
                if(!alreadyProcessedSubClasses.contains(level)){
                    IGenericObject currentSource = valueMap.get(current.get());
                    current.set(current.get().concat("."));
                    Map<String, IGenericObject> currentFields = addPrefix(extractFieldsFrom(currentSource), current.get());
                    valueMap.putAll(currentFields);
                    alreadyProcessedSubClasses.add(level);
                }
            } catch (GenericException e){
                ex.set(e);
            }
        });
        if(Optional.ofNullable(ex.get()).isPresent())
            throw ex.get();
        return valueMap;
    }

    private Map<String, IGenericObject> extractFieldsFrom(IGenericObject source) throws IllegalAccessException {
        Map<String, IGenericObject> valueMap = new HashMap<>();
        if(source.getValue().isPresent()){
            Optional<Map<String, IGenericObject>> fields = new FieldGetter<>(source.getValue().get())
                    .getAll();
            if(fields.isPresent()) valueMap.putAll(fields.get());
        }
        return valueMap;
    }

    private Map<String, IGenericObject> addPrefix(Map<String, IGenericObject> source, String prefix){
        Map<String, IGenericObject> out = new HashMap<>();
        source.forEach((key, value) -> out.put(prefix.concat(key), value));
        return out;
    }

    private <T> T setSTDValuesToTarget(Map<String, IGenericObject> valueMap) throws GenericException{
        IFieldSetter setter = new FieldSetter();
        try {
            var d = targetClass.getDeclaredConstructor().newInstance();
            return (T) setter.setAll(d, valueMap);
        } catch (InstantiationException e) {
            throw new CanNotInitializeClassException(e);
        } catch (java.lang.IllegalAccessException | InvocationTargetException e) {
            throw new IllegalAccessException(e);
        }  catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(e);
        }
    }

    private <T> T getEmptyTarget() throws GenericException{
        try {
            return (T) targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new CanNotInitializeClassException(e);
        } catch (java.lang.IllegalAccessException | InvocationTargetException e) {
            throw new IllegalAccessException(e);
        }  catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(e);
        }
    }

    private void checkForMandatoryFields() throws GenericException{
        //1 - Checking for source and target
        if(Optional.ofNullable(source).isEmpty()) throw new MandatoryPropertyNotFoundException("source");
        if(Optional.ofNullable(targetClass).isEmpty()) throw new MandatoryPropertyNotFoundException("target");
    }
}
