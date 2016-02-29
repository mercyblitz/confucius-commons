/**
 * Project   : commons-xml
 * File      : JAXBContextFinderTest.java
 * Date      : 2012-1-9
 * Time      : обнГ12:01:01
 * Copyright : taobao.com Ltd.
 */
package org.confucius.commons.xml.jaxb.util;

import junit.framework.TestCase;
import org.confucius.commons.xml.jaxb.util.mock.A;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * {@link TestCase} for {@link JAXBContextFinder}
 * 
 * @author <a href="mailto:taogu.mxx@taobao.com">Mercy</a>
 * @since 1.0.0 2012-1-9 обнГ12:01:01
 * @version 1.0.0
 * @see JAXBContextFinder
 */
public class JAXBContextFinderTest extends TestCase {

	public void testFind() throws Exception {
		JAXBContext jaxbContext = JAXBContextFinder.find(Thread.currentThread().getContextClassLoader(), "org.confucius.commons.xml.jaxb.util.mock");
		assertNotNull(jaxbContext);
		
		jaxbContext = JAXBContextFinder.find(A.class);
		assertNotNull(jaxbContext);

		JAXBException ex = null;
		try {
			jaxbContext = JAXBContextFinder.find(JAXBContextFinderTest.class);
		} catch (JAXBException e) {
			ex=e;
		}
		assertNotNull(ex);
	}
}
