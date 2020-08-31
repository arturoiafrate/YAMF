package it.arturoiafrate.yamf.mapping.xml.deserializer;

import it.arturoiafrate.yamf.mapping.profiles.objects.FieldAssociation;
import it.arturoiafrate.yamf.mapping.profiles.objects.Profile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlProfilesDeserializer {

    private String xmlString;

    public XmlProfilesDeserializer(String xml){
        xmlString = xml;
    }

    public Map<String, Profile> deserialize() throws SAXException, IOException, ParserConfigurationException{
        return getProfileMap(getNormalizedDocument());
    }

    private Document getNormalizedDocument() throws SAXException, IOException, ParserConfigurationException{
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
        document.normalize();
        return document;
    }

    private Map<String, Profile> getProfileMap(Document doc){
        final Map<String, Profile> out = new HashMap<>();
        NodeList profiles = doc.getElementsByTagName("profile");
        for(int i=0; i<profiles.getLength(); ++i){
            Node profile = profiles.item(i);
            if(profile.getNodeType() == Node.ELEMENT_NODE){
                Element eProfile = (Element) profile;
                out.put(eProfile.getAttribute("name"), extractProfile(eProfile));
            }
        }
        return out;
    }

    private Profile extractProfile(Element xmlProfile){
        boolean mapFieldsWithSameName = xmlProfile.getElementsByTagName("mapFieldsWithSameName")
                .item(0).getTextContent().equalsIgnoreCase("true");
        return new Profile(mapFieldsWithSameName, extractAssociations(xmlProfile.getElementsByTagName("association")));
    }

    private List<FieldAssociation> extractAssociations(NodeList xmlAssociations){
        final List<FieldAssociation> out = new ArrayList<>();
        for(int i=0; i<xmlAssociations.getLength(); ++i){
            if(xmlAssociations.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element xmlAssociation = (Element) xmlAssociations.item(i);
                String source = xmlAssociation.getElementsByTagName("source").item(0).getTextContent();
                String target = xmlAssociation.getElementsByTagName("target").item(0).getTextContent();
                out.add(new FieldAssociation(source, target));
            }
        }
        return out;
    }


}
