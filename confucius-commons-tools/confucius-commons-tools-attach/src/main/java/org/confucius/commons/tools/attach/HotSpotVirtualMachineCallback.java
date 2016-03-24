/**
 * Confucius commmons project
 */
package org.confucius.commons.tools.attach;

import sun.tools.attach.HotSpotVirtualMachine;

/**
 * {@link HotSpotVirtualMachine} {@link VirtualMachineCallback}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see HotSpotVirtualMachine
 * @since 1.0.0
 */
public interface HotSpotVirtualMachineCallback<T> extends VirtualMachineCallback<HotSpotVirtualMachine, T> {

}
