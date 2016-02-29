package org.confucius.commons.xml.jaxb.impl;

import org.confucius.commons.xml.jaxb.JAXBSerializer;
import org.confucius.commons.xml.jaxb.JAXBSerializerConfig;
import org.confucius.commons.xml.jaxb.util.JAXBContextFinder;
import org.confucius.commons.xml.jaxb.util.JAXBUtil;
import org.apache.xml.serialize.XMLSerializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.validation.Schema;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map.Entry;

/**
 * Default {@link JAXBSerializer} implementation
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultJAXBSerializer implements JAXBSerializer {

    @Override
    public String serialize(Object object, JAXBSerializerConfig config) throws JAXBException {
        JAXBSerializerConfig actualConfig = config;

        if (actualConfig == null) {
            actualConfig = JAXBSerializerConfig.DEFAULT;
        }

        Marshaller marshaller = createMarshaller(object, actualConfig);
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, getXMLSerializer(writer, actualConfig));
        return writer.toString();
    }

    private XMLSerializer getXMLSerializer(Writer writer, JAXBSerializerConfig config) {
        XMLSerializer serializer = new XMLSerializer(writer, config.getOutputFormat());
        return serializer;
    }

    protected Marshaller createMarshaller(Object object, JAXBSerializerConfig config) throws JAXBException {

        JAXBContext jaxbContext = findJAXBContext(object);

        Marshaller marshaller = jaxbContext.createMarshaller();

        for (Entry<String, Object> entry : config.getProperties().entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            marshaller.setProperty(name, value);
        }

        Schema schema = createSchema(config.getSchemaURL());
        marshaller.setSchema(schema);
        marshaller.setListener(config.getListener());
        config.getAttachmentMarshaller();
        AttachmentMarshaller attachmentMarshaller = marshaller.getAttachmentMarshaller();
        if (attachmentMarshaller != null) {
            marshaller.setAttachmentMarshaller(attachmentMarshaller);
        }
        ValidationEventHandler eventHandler = config.getValidationEventHandler();
        if (eventHandler != null) {
            marshaller.setEventHandler(eventHandler);
        }

        @SuppressWarnings("rawtypes")
        XmlAdapter xmlAdapter = config.getXmlAdapter();

        if (xmlAdapter != null) {
            marshaller.setAdapter(xmlAdapter);
        }

        return marshaller;
    }

    private Schema createSchema(URL schemaURL) {
        if (schemaURL == null)
            return null;
        return JAXBUtil.createSchemaIfAbsent(schemaURL);
    }

    protected JAXBContext findJAXBContext(Object object) throws JAXBException {
        JAXBContext jaxbContent = JAXBContextFinder.find(object.getClass());
        return jaxbContent;
    }

    @Override
    public String serialize(Object object) throws JAXBException {
        return this.serialize(object, JAXBSerializerConfig.DEFAULT);
    }

}
