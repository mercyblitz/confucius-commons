/**
 * Confucius commmons project
 */
package org.confucius.commons.xml.jaxb;

import com.sun.java.xml.ns.j2ee.WebAppType;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.descriptor.web.WebXml;
import org.apache.tomcat.util.descriptor.web.WebXmlParser;
import org.confucius.commons.lang.ClassLoaderUtil;
import org.junit.Test;

import java.net.URL;

/**
 * {@link JAXBManager} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see JAXBManager
 * @since 1.0.0
 */
public class JAXBManagerTest {

    private final static JAXBManager instance = new JAXBManager();

    @Test
    public void test() throws Exception {
        URL resourceURL = ClassLoaderUtil.getResource(Thread.currentThread().getContextClassLoader(), "WEB-INF/web.xml");
        String xmlContent = IOUtils.toString(resourceURL, "UTF-8");
        JAXBDeserializerConfig config = new JAXBDeserializerConfig();
//        config.setSchemaURL(new URL("http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"));
        WebAppType webAppType = instance.deserialize(xmlContent, WebAppType.class, config);

        String content = instance.serialize(webAppType);
        System.out.println(content);
        WebXmlParser webXmlParser = new WebXmlParser(false, false, false);

        WebXml webXml = new WebXml();
        webXmlParser.parseWebXml(resourceURL, webXml, false);
    }

    @Test
    public void testUsingTomcat() throws Exception {
        URL resourceURL = ClassLoaderUtil.getResource(Thread.currentThread().getContextClassLoader(), "WEB-INF/web.xml");
        WebXmlParser webXmlParser = new WebXmlParser(false, false, false);

        WebXml webXml = new WebXml();
        webXmlParser.parseWebXml(resourceURL, webXml, false);

        System.out.println(webXml);
    }
}
