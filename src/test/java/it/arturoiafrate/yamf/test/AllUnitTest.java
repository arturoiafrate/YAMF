package it.arturoiafrate.yamf.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.mapping.factory.impl.MappingFactory;
import it.arturoiafrate.yamf.mapping.factory.settings.enumerators.ProfilesEncoding;
import it.arturoiafrate.yamf.mapping.json.deserializer.ProfilesDeserializer;
import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;
import it.arturoiafrate.yamf.obj.IGenericObject;
import it.arturoiafrate.yamf.field.getter.impl.FieldGetter;
import it.arturoiafrate.yamf.obj.impl.GenericObject;
import it.arturoiafrate.yamf.field.setter.IFieldSetter;
import it.arturoiafrate.yamf.field.setter.impl.FieldSetter;
import it.arturoiafrate.yamf.test.classes.*;
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

        InternalClassA internal = new InternalClassA();
        internal.setString("This is the internal string.");
        internal.setaDouble(76.098);
        testerClassA.setInternal(internal);

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
        
        assertEquals(valueMap.size(), 5);
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

    @Test
    public void subclassFieldsMapping() throws GenericException{
        TesterClassC classC = new MappingFactory()
                .fromObject(testerClassA)
                .toClass(TesterClassC.class)
                .mapFieldsWithSameName(false)
                .mapAs("internal.string", "str")
                .doConvert();
        assertEquals(testerClassA.getInternal().getString(), classC.getStr());

        TesterClassC classC2 = new MappingFactory()
                .fromObject(testerClassA)
                .toClass(TesterClassC.class)
                .mapFieldsWithSameName(false)
                .mapAs("internal.internal2.string", "str")
                .doConvert();

        assertEquals(testerClassA.getInternal().getInternal2String(), classC2.getStr());
    }

    @Test
    public void setFieldToSubclass() throws GenericException{
        TesterClassD classD = new MappingFactory()
                .fromObject(testerClassA)
                .toClass(TesterClassD.class)
                .mapFieldsWithSameName(false)
                .mapAs("string", "internal.string")
                .doConvert();

        assertEquals(testerClassA.getString(), classD.getInternal().getString());
    }

    @Test
    public void jsonDeserialization(){
        String jsonStr = """
                  {
                  	"profiles": [{
                  		"profileName": "testProfile",
                  		"profile": {
                  			"mapFieldsWithSameName": true,
                  			"associations": [{
                  					"source": "string",
                  					"target": "str"
                  				},
                  				{
                  					"source": "Integer",
                  					"target": "itg"
                  				}
                  			]
                  		}
                  	}]
                  }
                """;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Map.class, new ProfilesDeserializer())
                .create();

        Map<String, Profile> profileMap = gson.fromJson(jsonStr, Map.class);

        assertEquals(profileMap.size(), 1);
        assertEquals(profileMap.get("testProfile").getMapFieldsWithSameName(), true);
        assertEquals(profileMap.get("testProfile").getAssociations().get(0).getSource(), "string");
        assertEquals(profileMap.get("testProfile").getAssociations().get(0).getTarget(), "str");

    }

    @Test
    public void jsonMapping() throws GenericException{
        String jsonStr = """
                  {
                  	"profiles": [{
                  		"profileName": "toUse",
                  		"profile": {
                  			"mapFieldsWithSameName": false,
                  			"associations": [{
                  					"source": "integer",
                  					"target": "itg"
                  				},
                  				{
                  					"source": "aBoolean",
                  					"target": "bln"
                  				},
                  				{
                  					"source": "string",
                  					"target": "str"
                  				}
                  			]
                  		}
                  	}, {
                  		"profileName": "profile2",
                  		"profile": {
                  			"mapFieldsWithSameName": true,
                  			"associations": [{
                  					"source": "thisDoesNotExists",
                  					"target": "thisToo"
                  				}
                  			]
                  		}
                  	}]
                  }
                """;

        TesterClassC classC = new MappingFactory()
                .loadProfiles(jsonStr, ProfilesEncoding.JSON)
                .fromObject(testerClassA)
                .toClass(TesterClassC.class)
                .useProfile("toUse")
                .doConvert();

        assertEquals(testerClassA.getaBoolean(), classC.getBln());
        assertEquals(testerClassA.getInteger(), classC.getItg());
        assertEquals(testerClassA.getString(), classC.getStr());
        assertNotEquals(testerClassA.getPrimitiveInteger(), classC.getPrimitiveInteger());
    }

}
