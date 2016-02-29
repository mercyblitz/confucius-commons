package org.confucius.commons.xml.jaxb;

import com.google.common.collect.Lists;

import javax.xml.bind.Marshaller.Listener;
import java.util.List;

/**
 * Chainable implementation for {@link Listener}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
final class ChainableMarshallerListener extends Listener {

	final private List<Listener> listenersChain = Lists.newLinkedList();

	public void beforeMarshal(Object source) {
		for (Listener instance : listenersChain) {
			instance.beforeMarshal(source);
		}
	}

	public void afterMarshal(Object source) {
		for (Listener instance : listenersChain) {
			instance.afterMarshal(source);
		}
	}

	public ChainableMarshallerListener addListener(Listener listener) {
		listenersChain.add(listener);
		return this;
	}

	public ChainableMarshallerListener removeListener(Listener listener) {
		listenersChain.remove(listener);
		return this;
	}
}