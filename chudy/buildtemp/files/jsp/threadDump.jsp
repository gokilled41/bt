<%@ page language="java" %>
<%@ page contentType="text/html; charset=GB2312"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.lang.*" %>
<%@ page language="java" import="java.util.Map.Entry" %>

<html>
<%
        out.println("<br>");
        out.println("JBoss Thread Count: " + java.lang.Thread.getAllStackTraces().size() + "<br>");
        out.println("<br>");

        Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
        List<Long> list = new ArrayList<Long>();
        Map<Long, Thread> map = new HashMap<Long, Thread>();
        for (Thread t : traces.keySet()) {
            Long s = t.getId();
            list.add(s);
            map.put(s, t);
        }
        Long[] arr = list.toArray(new Long[list.size()]);
        Arrays.sort(arr);
        
        for (int i = 0; i < arr.length; i++) {
            Long s = arr[i];
            Thread t = map.get(s);
            StackTraceElement[] elements = traces.get(t);
            
            StringBuilder sb = new StringBuilder();
            sb.append("\"" + t.getName() + "\"");
            sb.append(" ");
            sb.append("prio=");
            sb.append(t.getPriority());
            sb.append(" ");
            sb.append("tid=");
            sb.append(t.getId());
            sb.append(" ");
            sb.append(t.getState().name());
            out.println(sb.toString() + "<br>");

            for (int j = 0; j < elements.length; j++) {
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;" + elements[j] + "<br>");
            }
            out.println("<br>");
        }
%>
</html> 