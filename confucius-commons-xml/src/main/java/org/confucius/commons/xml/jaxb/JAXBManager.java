package org.confucius.commons.xml.jaxb;

import org.confucius.commons.lang.util.ServiceLoaderUtil;
import org.confucius.commons.xml.jaxb.util.JAXBUtil;

import javax.xml.bind.JAXBException;

/**
 * JAXB Manager
 * <p/>
 * <p/>
 * <p/>
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see JAXBSerializer
 * @see JAXBDeserializer
 * @see ServiceLoaderUtil#loadFirstService(ClassLoader, Class)
 * @since 1.0.0
 */
public class JAXBManager implements JAXBSerializer, JAXBDeserializer {

    final private JAXBSerializer jaxbSerializer = this.loadJAXBSerializer();

    final private JAXBDeserializer jaxbDeserializer = this.loadJAXBDeserializer();

    protected JAXBDeserializer loadJAXBDeserializer() {
        return JAXBUtil.loadJAXBDeserializer();
    }

    protected JAXBSerializer loadJAXBSerializer() {
        return JAXBUtil.loadJAXBSerializer();
    }

    public String serialize(Object object, JAXBSerializerConfig config) throws JAXBException {
        return jaxbSerializer.serialize(object, config);
    }

    public String serialize(Object object) throws JAXBException {
        return jaxbSerializer.serialize(object);
    }

    public <T> T deserialize(String xmlContent, Class<T> type) throws JAXBException {
        return jaxbDeserializer.deserialize(xmlContent, type);
    }

    public <T> T deserialize(String xmlContent, Class<T> type, JAXBDeserializerConfig config) throws JAXBException {
        return jaxbDeserializer.deserialize(xmlContent, type, config);
    }

}
