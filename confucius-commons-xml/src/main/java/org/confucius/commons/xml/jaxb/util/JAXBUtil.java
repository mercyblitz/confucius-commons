/**
 * Project   : commons-xml File      : JAXBUtil.java Date      : 2012-1-10 Time      : ����03:36:21 Copyright :
 * taobao.com Ltd.
 */
package org.confucius.commons.xml.jaxb.util;

import com.google.common.collect.Maps;
import org.confucius.commons.lang.util.ServiceLoaderUtil;
import org.confucius.commons.xml.jaxb.JAXBDeserializer;
import org.confucius.commons.xml.jaxb.JAXBSerializer;
import org.confucius.commons.xml.jaxb.impl.DefaultJAXBSerializer;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URL;
import java.util.Map;

/**
 * JAXB Utility class
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
abstract public class JAXBUtil {

    private final static Map<URL, Schema> schemaCache = Maps.newConcurrentMap();
    private final static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private final static JAXBSerializer jaxbSerializer = ServiceLoaderUtil.loadFirstService(classLoader, JAXBSerializer.class);
    private final static JAXBDeserializer jaxbDeserializer = ServiceLoaderUtil.loadFirstService(classLoader, JAXBDeserializer.class);

    private JAXBUtil() {

    }


    /**
     * Load {@link JAXBSerializer} instance
     *
     * @return {@link JAXBSerializer} instance
     */
    public static JAXBSerializer loadJAXBSerializer() {
        return jaxbSerializer;
    }

    /**
     * Load {@link JAXBDeserializer} instance
     *
     * @return {@link JAXBDeserializer} instance
     */
    public static JAXBDeserializer loadJAXBDeserializer() {
        return jaxbDeserializer;
    }

    /**
     * Create {@link Schema} if absent
     *
     * @param schemaURL
     *         The URL of {@link Schema}
     * @return {@link Schema} instance
     */
    @Nonnull
    public static Schema createSchemaIfAbsent(URL schemaURL) {
        Schema schema = schemaCache.get(schemaURL);
        if (schema == null) {
            schema = createSchema(schemaURL);
            schemaCache.put(schemaURL, schema);
        }
        return schema;
    }


    @Nonnull
    protected static Schema createSchema(URL schemaURL) throws NullPointerException, IllegalArgumentException {
        if (schemaURL == null)
            throw new NullPointerException();
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            return schemaFactory.newSchema(schemaURL);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Is JAXB element object
     *
     * @param object
     *         element object
     * @return
     */
    public static boolean isJAXBElement(Object object) {
        JAXBElementClassFilter filter = new JAXBElementClassFilter();
        return filter.accept(object.getClass());
    }

    public static String toString(Object object) {
        DefaultJAXBSerializer instance = new DefaultJAXBSerializer();
        String content = null;
        try {
            content = instance.serialize(object);
        } catch (JAXBException e) {
            content = object.toString();
        }
        return content;
    }

}
