/**
 * Project   : commons-xml
 * File      : JAXBContextInitializer.java
 * Date      : 2012-1-9
 * Time      : 上午10:39:33
 * Copyright : taobao.com Ltd.
 */
package org.confucius.commons.xml.jaxb.util;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Set;

/**
 * {@link JAXBContext} 初始化
 * 
 * @author <a href="mailto:taogu.mxx@taobao.com">Mercy</a>
 * @since 1.0.0 2012-1-9 上午10:39:33
 * @version 1.0.0
 * @see JAXBContext
 */
class JAXBContextInitializer {

	final private JAXBElementClassScanner classScanner = new JAXBElementClassScanner();

	/**
	 * 创建{@link JAXBContext}
	 * 
	 * @since 1.0.0 2012-1-9 上午11:30:20
	 * @version 1.0.0
	 * @see
	 * @param classLoader
	 *            ClassLoader
	 * @param packageName
	 *            包名
	 * @return JAXBContext
	 * @throws JAXBException
	 *             创建{@link JAXBContext}失败时
	 */
	public JAXBContext createJAXBContext(@Nonnull ClassLoader classLoader, @Nonnull String packageName) throws JAXBException {
		final Set<Class<?>> classesSet = classScanner.scan(classLoader, packageName);
		if (classesSet.isEmpty()) {
			// TODO:Doc ME
			throw new JAXBException("");
		}
		return JAXBContext.newInstance(classesSet.toArray(new Class<?>[0]));
	}

}
