package org.confucius.commons.xml.jaxb;

import com.google.common.collect.Lists;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import java.util.List;

/**
 * Chainable implementation for {@link Unmarshaller.Listener}
 *
 * @author <a href="mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @since 1.0.0
 */
class ChainableUnmarshallerListener extends Listener {

    final private List<Listener> listenersChain = Lists.newLinkedList();

    public void beforeUnmarshal(Object target, Object parent) {
        for (Listener instance : listenersChain) {
            instance.beforeUnmarshal(target, parent);
        }
    }

    public void afterUnmarshal(Object target, Object parent) {
        for (Listener instance : listenersChain) {
            instance.afterUnmarshal(target, parent);
        }
    }

    public ChainableUnmarshallerListener addListener(Listener listener) {
        listenersChain.add(listener);
        return this;
    }

    public ChainableUnmarshallerListener removeListener(Listener listener) {
        listenersChain.remove(listener);
        return this;
    }
}
