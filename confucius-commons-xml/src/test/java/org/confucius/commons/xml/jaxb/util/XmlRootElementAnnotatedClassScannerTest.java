package org.confucius.commons.xml.jaxb.util;

import junit.framework.TestCase;

/**
 * {@link TestCase} for {@link JAXBElementClassScanner}
 */
public class XmlRootElementAnnotatedClassScannerTest extends TestCase {

    private final JAXBElementClassScanner instance = new JAXBElementClassScanner();

    public void testScanOnNPE() {
        NullPointerException ex = null;
        try {
            instance.scan(null, null);
            ex = null;
        } catch (NullPointerException e) {
            ex = e;
        }
        assertNotNull(ex);

        ex = null;
        try {
            instance.scan(null, "");
            ex = null;
        } catch (NullPointerException e) {
            ex = e;
        }
        assertNotNull(ex);

        ex = null;
        try {
            instance.scan(Thread.currentThread().getContextClassLoader(), null);
            ex = null;
        } catch (NullPointerException e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    public void testScan() {


    }
}
