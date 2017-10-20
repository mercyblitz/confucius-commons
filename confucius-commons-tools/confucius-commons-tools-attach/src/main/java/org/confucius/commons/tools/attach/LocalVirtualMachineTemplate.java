package org.confucius.commons.tools.attach;

import org.confucius.commons.lang.management.ManagementUtils;

/**
 * Local {@link VirtualMachineTemplate}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see LocalVirtualMachineTemplate
 * @since 1.0.0
 */
public class LocalVirtualMachineTemplate extends VirtualMachineTemplate {

    public LocalVirtualMachineTemplate() {
        super(String.valueOf(ManagementUtils.getCurrentProcessId()));
    }

}
