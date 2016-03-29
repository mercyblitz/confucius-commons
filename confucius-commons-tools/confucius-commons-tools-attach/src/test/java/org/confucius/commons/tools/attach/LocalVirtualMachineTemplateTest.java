/**
 * Confucius commmons project
 */
package org.confucius.commons.tools.attach;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.spi.AttachProvider;
import junit.framework.Assert;
import org.junit.Test;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.IOException;
import java.util.List;

/**
 * {@link LocalVirtualMachineTemplate} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see LocalVirtualMachineTemplate
 * @since 1.0.0
 */
public class LocalVirtualMachineTemplateTest {

    @Test
    public void testNew() {
        LocalVirtualMachineTemplate localVirtualMachineTemplate = new LocalVirtualMachineTemplate();
        String processId = localVirtualMachineTemplate.getProcessId();
        Assert.assertNotNull(processId);
        Assert.assertTrue(Integer.parseInt(processId) > -1);
    }

    @Test
    public void testExecute() throws IOException, AttachNotSupportedException {
        LocalVirtualMachineTemplate localVirtualMachineTemplate = new LocalVirtualMachineTemplate();

        AttachProvider result = localVirtualMachineTemplate.execute(new HotSpotVirtualMachineCallback<AttachProvider>() {
            @Override
            public AttachProvider doInVirtualMachine(HotSpotVirtualMachine virtualMachine) throws IOException {
                AttachProvider attachProvider = virtualMachine.provider();
                return attachProvider;
            }
        });

        Assert.assertNotNull(result);
    }

    @Test
    public void test() {
        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        List<Connector> allConnectors = virtualMachineManager.allConnectors();
        List<VirtualMachine> connectedVirtualMachines = virtualMachineManager.connectedVirtualMachines();
        System.out.println(allConnectors);
        System.out.println(connectedVirtualMachines);


    }

}
