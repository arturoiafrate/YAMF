package it.arturoiafrate.yamf.test;

import it.arturoiafrate.yamf.field.IFieldValue;
import it.arturoiafrate.yamf.field.getter.impl.FieldGetter;
import it.arturoiafrate.yamf.test.field.getter.TesterClassA;
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
    public void gettingValues(){
        FieldGetter<TesterClassA> getter = new FieldGetter<>(testerClassA);
        AtomicReference<String> retValue = new AtomicReference<>();
        AtomicReference<Class<?>> retType = new AtomicReference<>();
        final Map<String, IFieldValue> valueMap = new HashMap<>();

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

}
