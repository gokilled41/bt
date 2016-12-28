package gzhou;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

public class WorkflowCache {

    private static WorkflowCache cache;
    private static JCS workflowCache;

    private WorkflowCache() {
        try {
            workflowCache = JCS.getInstance("workflowCache");
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public static WorkflowCache getInstance() {
        if (cache == null)
            cache = new WorkflowCache();
        return cache;
    }

    public Object get(String key) throws CacheException {
        long start = System.currentTimeMillis();
        String logmsg = "WorkflowCache.get";
        Object value = workflowCache.get(key);
        System.out.println(logmsg + " key = " + key + ", value = " + value + " cost "
                + (System.currentTimeMillis() - start) + " ms.");
        return value;
    }

    public void put(String key, Object value) throws CacheException {
        workflowCache.put(key, value);
        System.out.println("Put: key = " + key + ", value = " + value);
    }

    public void clean() throws CacheException {
        workflowCache.clear();
    }
}
