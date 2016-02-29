package org.confucius.commons.xml.jaxb;

import org.apache.commons.lang3.SystemUtils;
import org.apache.xml.serialize.EncodingInfo;
import org.apache.xml.serialize.OutputFormat;

import javax.annotation.Nonnull;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Marshaller.Listener;
import javax.xml.bind.attachment.AttachmentMarshaller;
import java.io.UnsupportedEncodingException;

/**
 * {@link JAXBSerializer} Config
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see JAXBSerializer
 * @see JAXBCommonConfig
 * @since 1.0.0
 */
public class JAXBSerializerConfig extends JAXBCommonConfig {

    final static public JAXBSerializerConfig DEFAULT = new JAXBSerializerConfig();

    static {
        DEFAULT.setEncoding(SystemUtils.FILE_ENCODING).setLineSeparator(SystemUtils.LINE_SEPARATOR).setIndenting(true).setIndent(4).setLineWidth(Integer.MAX_VALUE).setPreserveSpace(false);
    }

    private ChainableMarshallerListener listener = new ChainableMarshallerListener();

    private AttachmentMarshaller attachmentMarshaller;
    private OutputFormat outputFormat = new OutputFormat();


    public AttachmentMarshaller getAttachmentMarshaller() {
        return attachmentMarshaller;
    }

    public JAXBCommonConfig setAttachmentMarshaller(AttachmentMarshaller attachmentMarshaller) {
        this.attachmentMarshaller = attachmentMarshaller;
        return this;
    }

    @Nonnull
    public Listener getListener() {
        return this.listener;
    }

    public JAXBSerializerConfig addListener(Listener listener) {
        this.listener.addListener(listener);
        return this;
    }

    public JAXBSerializerConfig removeListener(Listener listener) {
        this.listener.removeListener(listener);
        return this;
    }

    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    public String getMethod() {
        return outputFormat.getMethod();
    }

    public JAXBSerializerConfig setMethod(String method) {
        outputFormat.setMethod(method);
        return this;
    }

    public String getVersion() {
        return outputFormat.getVersion();
    }

    public JAXBSerializerConfig setVersion(String version) {
        outputFormat.setVersion(version);
        return this;
    }

    public int getIndent() {
        return outputFormat.getIndent();
    }

    public JAXBSerializerConfig setIndent(int indent) {
        outputFormat.setIndent(indent);
        return this;
    }

    public boolean getIndenting() {
        return outputFormat.getIndenting();
    }

    public JAXBSerializerConfig setIndenting(boolean on) {
        outputFormat.setIndenting(on);
        return this;
    }

    public String getEncoding() {
        return outputFormat.getEncoding();
    }

    public JAXBSerializerConfig setEncoding(String encoding) {
        outputFormat.setEncoding(encoding);
        this.setProperty(Marshaller.JAXB_ENCODING, encoding);
        return this;
    }

    public JAXBSerializerConfig setEncoding(EncodingInfo encInfo) {
        outputFormat.setEncoding(encInfo);
        return this;
    }

    /**
     * �Ƿ��ʽ�����
     *
     * @return �Ƿ��ʽ�����
     * @version 1.0.0
     * @since 1.0.0 2012-1-10 ����11:16:17
     */
    public boolean isFormattedOutput() {
        Object value = this.getProperties().get(Marshaller.JAXB_FORMATTED_OUTPUT);
        return value == null ? false : Boolean.valueOf(value.toString());
    }

    public JAXBSerializerConfig setFormattedOutput(boolean formattedOutput) {
        this.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
        return this;
    }


    public JAXBSerializerConfig setSchemaLocation(String schemaLocation) {
        this.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
        return this;
    }


    public String getNoNamespaceSchemaLocation() {
        return (String) this.getProperties().get(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION);
    }

    public JAXBSerializerConfig setNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, noNamespaceSchemaLocation);
        return this;
    }

    public JAXBSerializerConfig setFragment(boolean fragment) {
        this.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
        return this;
    }

    public EncodingInfo getEncodingInfo() throws UnsupportedEncodingException {
        return outputFormat.getEncodingInfo();
    }

    public JAXBSerializerConfig setAllowJavaNames(boolean allow) {
        outputFormat.setAllowJavaNames(allow);
        return this;
    }

    public boolean setAllowJavaNames() {
        return outputFormat.setAllowJavaNames();
    }

    public String getMediaType() {
        return outputFormat.getMediaType();
    }

    public JAXBSerializerConfig setMediaType(String mediaType) {
        outputFormat.setMediaType(mediaType);
        return this;
    }

    public JAXBSerializerConfig setDoctype(String publicId, String systemId) {
        outputFormat.setDoctype(publicId, systemId);
        return this;
    }

    public String getDoctypePublic() {
        return outputFormat.getDoctypePublic();

    }

    public String getDoctypeSystem() {
        return outputFormat.getDoctypeSystem();
    }

    public boolean getOmitComments() {
        return outputFormat.getOmitComments();
    }

    public JAXBSerializerConfig setOmitComments(boolean omit) {
        outputFormat.setOmitComments(omit);
        return this;
    }

    public boolean getOmitDocumentType() {
        return outputFormat.getOmitDocumentType();
    }

    public JAXBSerializerConfig setOmitDocumentType(boolean omit) {
        outputFormat.setOmitDocumentType(omit);
        return this;
    }

    public boolean getOmitXMLDeclaration() {
        return outputFormat.getOmitXMLDeclaration();
    }

    public JAXBSerializerConfig setOmitXMLDeclaration(boolean omit) {
        outputFormat.setOmitXMLDeclaration(omit);
        return this;
    }

    public boolean getStandalone() {
        return outputFormat.getStandalone();
    }

    public JAXBSerializerConfig setStandalone(boolean standalone) {
        outputFormat.setStandalone(standalone);
        return this;
    }

    public String[] getCDataElements() {
        return outputFormat.getCDataElements();
    }

    public JAXBSerializerConfig setCDataElements(String[] cdataElements) {
        outputFormat.setCDataElements(cdataElements);
        return this;
    }

    public boolean isCDataElement(String tagName) {
        return outputFormat.isCDataElement(tagName);
    }

    public String[] getNonEscapingElements() {
        return outputFormat.getNonEscapingElements();
    }

    public JAXBSerializerConfig setNonEscapingElements(String[] nonEscapingElements) {
        outputFormat.setNonEscapingElements(nonEscapingElements);
        return this;
    }

    public boolean isNonEscapingElement(String tagName) {
        return outputFormat.isNonEscapingElement(tagName);
    }

    public String getLineSeparator() {
        return outputFormat.getLineSeparator();
    }

    public JAXBSerializerConfig setLineSeparator(String lineSeparator) {
        outputFormat.setLineSeparator(lineSeparator);
        return this;
    }

    public boolean getPreserveSpace() {
        return outputFormat.getPreserveSpace();
    }

    public JAXBSerializerConfig setPreserveSpace(boolean preserve) {
        outputFormat.setPreserveSpace(preserve);
        return this;
    }

    public int getLineWidth() {
        return outputFormat.getLineWidth();
    }

    public JAXBSerializerConfig setLineWidth(int lineWidth) {
        outputFormat.setLineWidth(lineWidth);
        return this;
    }

    public boolean getPreserveEmptyAttributes() {
        return outputFormat.getPreserveEmptyAttributes();
    }

    public JAXBSerializerConfig setPreserveEmptyAttributes(boolean preserve) {
        outputFormat.setPreserveEmptyAttributes(preserve);
        return this;
    }

    public char getLastPrintable() {
        return outputFormat.getLastPrintable();
    }

}
