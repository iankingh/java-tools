package io.github.iankingh.javatools.jakarta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.junit.jupiter.api.Test;

class XmlBindingsTest {
    @Test
    void roundTripsXmlWithADeclaration() throws Exception {
        Person original = new Person("Ian");

        String xml = XmlBindings.toXml(original);
        Person restored = XmlBindings.fromXml(xml, Person.class);

        assertTrue(xml.startsWith("<?xml"));
        assertEquals("Ian", restored.name);
    }

    @Test
    void rejectsDocumentTypesAndInvalidArguments() {
        String externalEntity =
                """
        <!DOCTYPE person [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
        <person><name>&xxe;</name></person>
        """;

        assertThrows(JAXBException.class, () -> XmlBindings.fromXml(externalEntity, Person.class));
        assertThrows(NullPointerException.class, () -> XmlBindings.fromXml(null, Person.class));
        assertThrows(NullPointerException.class, () -> XmlBindings.toXml(null));
    }

    @Test
    void supportsDeclaredTypesWithoutXmlRootElement() throws Exception {
        NoRootPerson person =
                XmlBindings.fromXml("<person><name>Ian</name></person>", NoRootPerson.class);

        assertEquals("Ian", person.name);
    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class Person {
        private String name;

        public Person() {}

        Person(String name) {
            this.name = name;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class NoRootPerson {
        private String name;

        public NoRootPerson() {}
    }
}
