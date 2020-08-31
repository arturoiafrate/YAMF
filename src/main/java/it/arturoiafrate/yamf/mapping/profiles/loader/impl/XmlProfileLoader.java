package it.arturoiafrate.yamf.mapping.profiles.loader.impl;

import it.arturoiafrate.yamf.exception.GenericException;
import it.arturoiafrate.yamf.exception.XmlDeserializationException;
import it.arturoiafrate.yamf.mapping.profiles.loader.IProfilesLoader;
import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;
import it.arturoiafrate.yamf.mapping.xml.deserializer.XmlProfilesDeserializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class XmlProfileLoader implements IProfilesLoader {
    private String xmlString;
    private XmlProfilesDeserializer xmlDeserializer;


    public XmlProfileLoader(String xml){
        xmlString = xml;
        xmlDeserializer = new XmlProfilesDeserializer(xmlString);
    }

    @Override
    public Map<String, Profile> getProfiles() throws GenericException {
        try {
            return xmlDeserializer.deserialize();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new XmlDeserializationException(e);
        }
    }
}
