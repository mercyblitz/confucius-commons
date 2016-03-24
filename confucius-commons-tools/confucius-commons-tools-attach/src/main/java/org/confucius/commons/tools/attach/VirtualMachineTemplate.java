package org.confucius.commons.tools.attach;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * {@link VirtualMachine} Template Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see VirtualMachine
 * @since 1.0.0 2016-03-23
 */
public class VirtualMachineTemplate {

    /**
     * Process ID
     */
    private final String processId;

    /**
     * Constructor with {@link Process} ID
     *
     * @param processId
     *         {@link Process} ID
     */
    public VirtualMachineTemplate(String processId) {
        this.processId = processId;
    }

    /**
     * Execute {@link VirtualMachineCallback}
     *
     * @param callback
     *         {@link VirtualMachineCallback}
     * @param <T>
     * @return
     * @throws AttachNotSupportedException
     * @throws IOException
     */
    public final <V extends VirtualMachine, T> T execute(VirtualMachineCallback<V, T> callback) throws AttachNotSupportedException, IOException {
        VirtualMachine virtualMachine = null;
        T result = null;
        try {
            virtualMachine = VirtualMachine.attach(processId);
            result = callback.doInVirtualMachine((V) virtualMachine);
        } finally {
            virtualMachine.detach();
        }

        return result;
    }


    /**
     * Get {@link #processId}
     *
     * @return processId
     * @version 1.0.0
     * @since 1.0.0
     **/
    public String getProcessId() {
        return processId;
    }
}
