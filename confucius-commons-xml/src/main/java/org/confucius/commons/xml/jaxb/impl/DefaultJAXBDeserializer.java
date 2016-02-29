/**
 * Project   : commons-xml File      : DefaultJAXBDeserializer.java Date      : 2012-1-10 Time      : ����03:08:48
 * Copyright : taobao.com Ltd.
 */
package org.confucius.commons.xml.jaxb.impl;

import org.confucius.commons.xml.jaxb.JAXBDeserializer;
import org.confucius.commons.xml.jaxb.JAXBDeserializerConfig;
import org.confucius.commons.xml.jaxb.util.JAXBContextFinder;
import org.confucius.commons.xml.jaxb.util.JAXBUtil;

import javax.xml.bind.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.validation.Schema;
import java.io.StringReader;
import java.net.URL;
import java.util.Map.Entry;

/**
 * Default {@link JAXBDeserializer}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultJAXBDeserializer implements JAXBDeserializer {

    @Override
    public <T> T deserialize(String xmlContent, Class<T> type) throws JAXBException {
        return this.deserialize(xmlContent, type, JAXBDeserializerConfig.DEFAULT);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(String xmlContent, Class<T> type, JAXBDeserializerConfig config) throws JAXBException {
        JAXBContext jaxbContext = JAXBContextFinder.find(type);
        Unmarshaller unmarshaller = createUnmarshaller(jaxbContext, config);
        StringReader reader = new StringReader(xmlContent);
        Object object = unmarshaller.unmarshal(reader);
        if (object instanceof JAXBElement) {
            JAXBElement<T> element = (JAXBElement<T>) object;
            object = element.getValue();
        }

        if (!type.isInstance(object)) {
            throw new JAXBException("deserialize objects' type can't be matched");
        }
        return (T) object;
    }

    protected Unmarshaller createUnmarshaller(JAXBContext jaxbConterxt, JAXBDeserializerConfig config) throws JAXBException {
        Unmarshaller unmarshaller = jaxbConterxt.createUnmarshaller();

        for (Entry<String, Object> entry : config.getProperties().entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            unmarshaller.setProperty(name, value);
        }

        Schema schema = createSchema(config.getSchemaURL());
        unmarshaller.setSchema(schema);
        unmarshaller.setListener(config.getListener());
        AttachmentUnmarshaller attachmentUnmarshaller = unmarshaller.getAttachmentUnmarshaller();
        if (attachmentUnmarshaller != null) {
            unmarshaller.setAttachmentUnmarshaller(attachmentUnmarshaller);
        }
        ValidationEventHandler eventHandler = config.getValidationEventHandler();
        if (eventHandler != null) {
            unmarshaller.setEventHandler(eventHandler);
        }

        @SuppressWarnings("rawtypes")
        XmlAdapter xmlAdapter = config.getXmlAdapter();

        if (xmlAdapter != null) {
            unmarshaller.setAdapter(xmlAdapter);
        }

        return unmarshaller;
    }

    private Schema createSchema(URL schemaURL) {
        if (schemaURL == null)
            return null;
        return JAXBUtil.createSchemaIfAbsent(schemaURL);
    }

}
