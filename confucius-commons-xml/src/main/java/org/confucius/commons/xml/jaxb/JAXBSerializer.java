package org.confucius.commons.xml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * JAXB Serializer
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see Marshaller
 * @since 1.0.0
 */
@ThreadSafe
public interface JAXBSerializer {

    /**
     * Serialize object to XML content
     *
     * @param object
     *         object to be serialized
     * @param config
     *         {@link JAXBSerializerConfig}
     * @return XML content
     * @throws JAXBException
     */
    @Nonnull
    String serialize(@Nonnull Object object, @Nullable JAXBSerializerConfig config) throws JAXBException;

    /**
     * Serialize object to XML content
     *
     * @param object
     *         Object
     * @return XML content
     * @throws JAXBException
     * @version 1.0.0
     * @see
     * @since 1.0.0
     */
    @Nonnull
    String serialize(@Nonnull Object object) throws JAXBException;
}
