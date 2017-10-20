package org.confucius.commons.lang.process;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link Process} Manager
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @version 1.0.0
 * @see ProcessManager
 * @since 1.0.0
 */
public class ProcessManager {

    /**
     * Singleton instance
     */
    public static final ProcessManager INSTANCE = new ProcessManager();
    private final ConcurrentMap<Process, String> unfinishedProcessesCache = Maps.newConcurrentMap();

    protected ProcessManager addUnfinishedProcess(Process process, String arguments) {
        unfinishedProcessesCache.putIfAbsent(process, arguments);
        return this;
    }

    protected ProcessManager removeUnfinishedProcess(Process process, String arguments) {
        unfinishedProcessesCache.remove(process, arguments);
        return this;
    }

    public void destroy(Process process) {
        process.destroy();
    }

    /**
     * Unfinished Processes Map
     *
     * @return non-null
     */
    @Nonnull
    public Map<Process, String> unfinishedProcessesMap() {
        return Collections.unmodifiableMap(unfinishedProcessesCache);
    }
}
