package org.confucius.commons.xml.jaxb;

import javax.annotation.Nonnull;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.bind.attachment.AttachmentUnmarshaller;

/**
 * {@link JAXBDeserializer} Config
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see JAXBDeserializer
 * @see JAXBCommonConfig
 * @since 1.0.0
 */
public class JAXBDeserializerConfig extends JAXBCommonConfig {

    public final static JAXBDeserializerConfig DEFAULT = new JAXBDeserializerConfig();

    private ChainableUnmarshallerListener listener = new ChainableUnmarshallerListener();

    private AttachmentUnmarshaller attachmentUnmarshaller;

    /**
     * ��ȡAttachmentUnmarshaller
     *
     * @return the attachmentUnmarshaller
     * @version 1.0.0
     * @see AttachmentUnmarshaller
     * @since 1.0.0 2012-1-10 ����03:22:35
     */
    public AttachmentUnmarshaller getAttachmentUnmarshaller() {
        return attachmentUnmarshaller;
    }

    /**
     * ����AttachmentUnmarshaller
     *
     * @param attachmentUnmarshaller
     *         the attachmentUnmarshaller to set
     * @version 1.0.0
     * @see
     * @since 1.0.0 2012-1-10 ����03:22:35
     */
    public JAXBDeserializerConfig setAttachmentUnmarshaller(AttachmentUnmarshaller attachmentUnmarshaller) {
        this.attachmentUnmarshaller = attachmentUnmarshaller;
        return this;
    }

    /**
     * ���{@link Listener}
     *
     * @param listener
     *         Listener
     * @return JAXBDeserializerConfig
     * @version 1.0.0
     * @since 1.0.0 2012-2-27 ����02:45:51
     */
    public JAXBDeserializerConfig addListener(Listener listener) {
        this.listener.addListener(listener);
        return this;
    }

    /**
     * �Ƴ�{@link Listener}
     *
     * @param listener
     *         Listener
     * @return JAXBDeserializerConfig
     * @version 1.0.0
     * @since 1.0.0 2012-2-27 ����02:45:51
     */
    public JAXBDeserializerConfig removeListener(Listener listener) {
        this.listener.removeListener(listener);
        return this;
    }

    /**
     * ��ȡ Listener
     *
     * @return
     * @version 1.0.0
     * @see Listener
     * @since 1.0.0 2012-1-10 ����12:47:39
     */
    @Nonnull
    public Listener getListener() {
        return this.listener;
    }

}
