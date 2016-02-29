package org.confucius.commons.xml.jaxb;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.bind.JAXBException;

/**
 * JAXB Deserializer
 *
 * @author <a href="mailto:taogu.mxx@taobao.com">Mercy</a>
 * @version 1.0.0
 * @since 1.0.0 2012-1-9 下午01:51:35
 */
@ThreadSafe
public interface JAXBDeserializer {


    @Nonnull
    <T> T deserialize(@Nonnull String xmlContent, @Nonnull Class<T> type) throws JAXBException;

    @Nonnull
    <T> T deserialize(@Nonnull String xmlContent, @Nonnull Class<T> type, JAXBDeserializerConfig config) throws JAXBException;
}
