package org.confucius.commons.lang.process;

/**
 * {@link ProcessExecutor} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ProcessExecutorTest
 * @since 1.0.0
 */
public class ProcessExecutorTest {

    public void testExecute2() throws Exception {
        ProcessExecutor executor = new ProcessExecutor("java","-version");
        executor.execute(System.out, 2000);
    }

}
