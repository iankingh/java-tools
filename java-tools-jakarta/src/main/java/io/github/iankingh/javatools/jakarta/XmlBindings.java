package io.github.iankingh.javatools.jakarta;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

/** Secure JAXB marshalling and unmarshalling with no shared mutable JAXB objects. */
public final class XmlBindings {
    private XmlBindings() {}

    /** Deserializes XML while disabling document types and external entities. */
    public static <T> T fromXml(String xml, Class<T> rootType, Class<?>... additionalTypes)
            throws JAXBException {
        Objects.requireNonNull(xml, "xml");
        Objects.requireNonNull(rootType, "rootType");
        JAXBContext context = JAXBContext.newInstance(boundTypes(rootType, additionalTypes));
        return context.createUnmarshaller().unmarshal(secureSource(xml), rootType).getValue();
    }

    /** Serializes an object as formatted UTF-8 XML with a declaration. */
    public static String toXml(Object value, Class<?>... additionalTypes) throws JAXBException {
        Objects.requireNonNull(value, "value");
        JAXBContext context =
                JAXBContext.newInstance(boundTypes(value.getClass(), additionalTypes));
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter output = new StringWriter();
        marshaller.marshal(value, output);
        return output.toString();
    }

    private static Class<?>[] boundTypes(Class<?> rootType, Class<?>[] additionalTypes) {
        Objects.requireNonNull(additionalTypes, "additionalTypes");
        Class<?>[] types = new Class<?>[additionalTypes.length + 1];
        types[0] = rootType;
        System.arraycopy(additionalTypes, 0, types, 1, additionalTypes.length);
        return types;
    }

    private static SAXSource secureSource(String xml) throws JAXBException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            XMLReader reader = factory.newSAXParser().getXMLReader();
            return new SAXSource(new XMLFilterImpl(reader), new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException | SAXException exception) {
            throw new JAXBException("Cannot configure secure XML parser", exception);
        }
    }
}
