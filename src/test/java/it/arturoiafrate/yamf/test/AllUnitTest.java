package it.arturoiafrate.yamf.test;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.mapping.factory.impl.MappingFactory;
import it.arturoiafrate.yamf.obj.IGenericObject;
import it.arturoiafrate.yamf.field.getter.impl.FieldGetter;
import it.arturoiafrate.yamf.obj.impl.GenericObject;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;
import it.arturoiafrate.yamf.field.setter.impl.FieldSetter;
import it.arturoiafrate.yamf.test.classes.TesterClassA;
import it.arturoiafrate.yamf.test.classes.TesterClassB;
import it.arturoiafrate.yamf.test.classes.TesterClassC;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AllUnitTest {

    private TesterClassA testerClassA;

    @BeforeAll
    public void init(){
        testerClassA =
                new TesterClassA(32, true, "This is the class A", 64);

        assertNotNull(testerClassA);
    }

    @Test
    public void gettingValues() throws GenericException {
        FieldGetter<TesterClassA> getter = new FieldGetter<>(testerClassA);
        AtomicReference<String> retValue = new AtomicReference<>();
        AtomicReference<Class<?>> retType = new AtomicReference<>();
        final Map<String, IGenericObject> valueMap = new HashMap<>();

        getter.get("string").ifPresent(x ->
                {
                    retType.set(x.getClassType());
                    x.getValue().ifPresent(
                            y -> retValue.set((String) y));
                }
        );

        assertEquals(retType.get(), String.class);
        assertEquals(retValue.get(), "This is the class A");

        getter.getAll().ifPresent(valueMap::putAll);
        
        assertEquals(valueMap.size(), 4);
        if (valueMap.get("integer").getValue().isPresent() && valueMap.get("aBoolean").getValue().isPresent()){
            assertEquals(valueMap.get("integer").getValue().get(), 32);
            assertEquals(valueMap.get("aBoolean").getValue().get(), true);
        } else {
            assertEquals(1, 0); //Test failed
        }
    }

    @Test
    public void settingValues() throws GenericException {
        IFieldSetter fieldSetter = new FieldSetter();
        TesterClassA copy = fieldSetter.setAll(new TesterClassA(), new FieldGetter<>(testerClassA).getAll().get());
        assertNotNull(copy);
        assertEquals(copy.getaBoolean(), testerClassA.getaBoolean());
        assertEquals(copy.getString(), testerClassA.getString());
        copy = fieldSetter.set(copy, "primitiveInteger", new GenericObject<>(56));
        assertEquals(copy.getPrimitiveInteger(), 56);
    }

    @Test
    public void standardMapping() throws GenericException{
        TesterClassB classB = new MappingFactory()
                .fromObject(testerClassA)
                .toClass(TesterClassB.class)
                .doConvert();

        assertEquals(testerClassA.getaBoolean(), classB.getaBoolean());
        assertEquals(testerClassA.getString(), classB.getString());

    }

    @Test
    public void specificFieldsMapping() throws GenericException{
        TesterClassC classC = new MappingFactory()
                .fromObject(testerClassA)
                .toClass(TesterClassC.class)
                .mapAs("integer", "itg")
                .mapAs("aBoolean", "bln")
                .mapAs("string", "str")
                .doConvert();

        assertEquals(testerClassA.getaBoolean(), classC.getBln());
        assertEquals(testerClassA.getInteger(), classC.getItg());
        assertEquals(testerClassA.getString(), classC.getStr());
        assertEquals(testerClassA.getPrimitiveInteger(), classC.getPrimitiveInteger());
    }

}
