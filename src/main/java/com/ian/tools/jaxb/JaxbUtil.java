package com.ian.tools.jaxb;


import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * JAXB工具類
 * 
 * @author inahuang 2020年1月6日
 */
@Slf4j
public class JaxbUtil {


    /**
     * Convert xml to object. Supports polymorphism. Note that implement classes
     * must be submitted to JAXBContext.newInstance one way or the other.
     * 
     * @param clazz primary class
     * @param xml   xml string
     * @param clz   implement classes
     * @return object of primary
     */
    public static <T> T XmlToObj(Class<T> clazz, String xml, Class<?>... clz) {
        try {
            log.debug("Xml :" + xml);
            JAXBContext context = JAXBContext.newInstance(clz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            return clazz.cast(unmarshaller.unmarshal(reader));
        } catch (JAXBException e) {
            log.error("Exception " + e);
        }
        return null;
    }

    /**
     * 
     * java物件轉<br>
     * xml Convert object to xml. Supports polymorphism. Note that implement classes
     * must be submitted to JAXBContext.newInstance one way or the other.
     * 
     * @param obj   object to convert to xml
     * @param clazz implement classes
     * @return xml
     */
    public static String ObjToXml(Object obj, Class<?>... clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            // format the XML output
            // Marshaller.JAXB_FORMATTED_OUTPUT:決定是否在轉換成xml時同時進行格式化（即按標籤自動換行，否則即是一行的xml）
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Marshaller.JAXB_ENCODING:xml的編碼方式 指定輸出 XML 編碼
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // Marshaller.JAXB_FRAGMENT:是否省略xml頭資訊,true省略，false不省略
            // 不產生 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // Print XML String to Console
            StringWriter stringWriter = new StringWriter();
            // Write XML to StringWriter
            marshaller.marshal(obj, stringWriter);
            // Verify XML Content
            String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + stringWriter.toString();
            log.debug("xmlContent : " + xmlContent);
            return xmlContent;
        } catch (JAXBException e) {
            log.error("JAXBException " + e);
        }
        return null;
    }

}
