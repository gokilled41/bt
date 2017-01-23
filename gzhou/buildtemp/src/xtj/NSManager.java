package xtj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class NSManager {
    private List<String> retainNS;
    private List<String> retainPrefix;

    private Stack<Map<String, String>> contextes = new Stack<Map<String, String>>();
    boolean retainAll = false;

    public NSManager(String namespaces, String prefixes) {
        if (namespaces.trim().equals("*") || prefixes.trim().equals("*")) {
            retainAll = true;
            return;
        }

        String nses[] = namespaces.split("\\s+");
        retainNS = Arrays.asList(nses);

        String pres[] = prefixes.split("\\s+");
        retainPrefix = Arrays.asList(pres);
    }

    public void pushContext() {
        Map<String, String> context = new HashMap<String, String>();
        contextes.push(context);
    }

    public Map<String, String> popContext() {
        return contextes.pop();
    }

    public Map<String, String> currentContext() {
        return contextes.peek();
    }

    public void addMapping(String prefix, String namespace) {
        if (prefix == null) {
            prefix = "";
        }
        Map<String, String> current = currentContext();
        current.put(prefix, namespace);
    }

    public String getPrefix(String prefix) {
        return getPrefix(prefix, true);
    }

    public String getPrefix(String prefix, boolean deep) {
        int lowerBound = deep ? 0 : contextes.size();
        for (int i = contextes.size() - 1; i >= lowerBound; i--) {
            Map<String, String> context = contextes.get(i);
            if (context.containsKey(prefix)) {
                return context.get(prefix);
            }
        }
        return null;
    }

    public boolean isRetained(String prefix, String namespace) {
        if (retainAll)
            return true;

        boolean retain = false;
        if (prefix != null) {
            retain = retainPrefix.contains(prefix);
        }

        if (!retain && namespace != null) {
            retain = retainNS.contains(namespace);
        }

        return retain;
    }
}
