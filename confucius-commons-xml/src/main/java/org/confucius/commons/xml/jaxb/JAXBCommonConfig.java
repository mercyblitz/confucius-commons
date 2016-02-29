package org.confucius.commons.xml.jaxb;

import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.net.URL;
import java.util.Map;

/**
 * JAXB Coommon Config
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
class JAXBCommonConfig {

    final private Map<String, Object> properties = Maps.newHashMap();
    private String encoding;
    private URL schemaURL;
    private ValidationEventHandler validationEventHandler;
    @SuppressWarnings("rawtypes")
    private XmlAdapter xmlAdapter;

    /**
     * ��ȡ XmlAdapter
     *
     * @return the xmlAdapter
     * @version 1.0.0
     * @see
     * @since 1.0.0 2012-1-10 ����10:01:04
     */
    @SuppressWarnings("rawtypes")
    public XmlAdapter getXmlAdapter() {
        return xmlAdapter;
    }

    /**
     * ����XmlAdapter
     *
     * @param xmlAdapter
     *         the xmlAdapter to set
     * @version 1.0.0
     * @see XmlAdapter
     * @since 1.0.0 2012-1-10 ����10:01:04
     */
    public JAXBCommonConfig setXmlAdapter(@SuppressWarnings("rawtypes") XmlAdapter xmlAdapter) {
        this.xmlAdapter = xmlAdapter;
        return this;
    }

    /**
     * ��ȡValidationEventHandler
     *
     * @return the validationEventHandler
     * @version 1.0.0
     * @see ValidationEventHandler
     * @since 1.0.0 2012-1-9 ����04:30:50
     */
    public ValidationEventHandler getValidationEventHandler() {
        return validationEventHandler;
    }

    /**
     * ����ValidationEventHandler
     *
     * @param validationEventHandler
     *         the validationEventHandler to set
     * @version 1.0.0
     * @see JAXBCommonConfig
     * @since 1.0.0 2012-1-9 ����04:30:50
     */
    public JAXBCommonConfig setValidationEventHandler(ValidationEventHandler validationEventHandler) {
        this.validationEventHandler = validationEventHandler;
        return this;
    }

    /**
     * ��������
     *
     * @param propertyName
     *         ��������
     * @param propertyValue
     *         ����ֵ
     * @return JAXBCommonConfig
     * @version 1.0.0
     * @since 1.0.0 2012-1-9 ����03:06:33
     */
    public JAXBCommonConfig setProperty(String propertyName, Object propertyValue) {
        this.properties.put(propertyName, propertyValue);
        return this;
    }

    /**
     * ��ȡ����
     *
     * @return ����Map
     * @version 1.0.0
     * @since 1.0.0 2012-1-9 ����03:06:14
     */
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    /**
     * �ַ�����
     *
     * @return the encoding
     * @version 1.0.0
     * @since 1.0.0 2012-1-9 ����03:05:15
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * �����ַ�����
     *
     * @param encoding
     *         the encoding to set
     * @version 1.0.0
     * @since 1.0.0 2012-1-9 ����03:05:15
     */
    public JAXBCommonConfig setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    /**
     * ��֤schema URL
     *
     * @return the schemaURL
     * @version 1.0.0
     * @since 1.0.0 2012-1-9 ����03:05:15
     */
    @Nullable
    public URL getSchemaURL() {
        return schemaURL;
    }

    /**
     * ������֤schema URL
     *
     * @param schemaURL
     *         the schemaURL to set
     * @version 1.0.0
     * @since 1.0.0 2012-1-9 ����03:05:15
     */
    public JAXBCommonConfig setSchemaURL(URL schemaURL) {
        this.schemaURL = schemaURL;
        return this;
    }

}
