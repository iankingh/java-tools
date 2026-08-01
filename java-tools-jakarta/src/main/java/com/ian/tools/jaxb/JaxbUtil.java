package com.ian.tools.jaxb;

import io.github.iankingh.javatools.jakarta.XmlBindings;
import jakarta.xml.bind.JAXBException;

/**
 * @deprecated Use {@link XmlBindings}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class JaxbUtil {
    private JaxbUtil() {}

    /**
     * @deprecated Use {@link XmlBindings#fromXml(String, Class, Class[])}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static <T> T XmlToObj(Class<T> type, String xml, Class<?>... additionalTypes) {
        try {
            return XmlBindings.fromXml(xml, type, additionalTypes);
        } catch (JAXBException exception) {
            throw new IllegalArgumentException("Invalid XML", exception);
        }
    }

    /**
     * @deprecated Use {@link XmlBindings#toXml(Object, Class[])}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String ObjToXml(Object value, Class<?>... additionalTypes) {
        try {
            return XmlBindings.toXml(value, additionalTypes);
        } catch (JAXBException exception) {
            throw new IllegalArgumentException("Cannot serialize XML", exception);
        }
    }
}
