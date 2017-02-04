package com.hqjl.crm.utils;

import java.util.HashMap;
import java.util.Map;

public class EnvLib {

    private static Map<String, String> envs_ = new HashMap<String, String>();

    /**
     * Expands the environment variables in the string, and also fixes slashes and colons.
     * @param s the string to expand
     * @return the expanded string
     * @vtexclude
     */
    public static String expandVars(String s) {
        return expandVars(s, true);
    }

    /**
     * Expands the environment variables in the string.  the fixPath flag can be
     * used to control if the resulting string is a path and if it should be adjusted
     * for the local platform.
     *
     * @param s the string to expand
     * @param fixPath for use with path vars, will fix slashes and colons based on OS
     * @return the expanded string
     * @vtexclude
     */
    public static String expandVars(String s, boolean fixPath) {
        if (s == null || s.length() < 1)
            return s;

        // Look for unexpanded DOS style variables
        int start = 0;
        int i;
        while ((i = s.indexOf('%', start)) != -1) {
            // Find the closing '%'
            int j = s.indexOf('%', i + 1);
            if (j == -1)
                // No closing '%'. Stop looking.
                break;
            // Retrieve the value of the environment variable
            String val = getenv(s.substring(i + 1, j));
            if (val == null) {
                // Variable does not expand. Skip it and continue to loop.
                start = j + 1;
                continue;
            }
            // Insert the expanded variable
            s = s.substring(0, i) + val + s.substring(j + 1);
        }

        // Look for unexpanded Unix style variables
        start = 0;
        while ((i = s.indexOf('$', start)) != -1) {
            // Check for braces around the variable name
            if (i + 1 < s.length() && s.charAt(i + 1) == '{') {
                // Look for the closing '}'
                int j = s.indexOf('}', i + 2);
                if (j == -1) {
                    // There is no '}'. Skip over the "${" and try again.
                    start = i + 2;
                    continue;
                }
                // Retrieve the value of the environment variable
                String val = getenv(s.substring(i + 2, j));
                if (val == null) {
                    // Variable does not expand. Skip it and continue to loop.
                    start = j + 1;
                    continue;
                }
                // Insert the expanded variable
                s = s.substring(0, i) + val + s.substring(j + 1);
                continue;
            }

            // Find the variable name terminator
            int j;
            for (j = i + 1; j < s.length(); j++) {
                char c = s.charAt(j);
                if (!((c >= 'a' && c <= 'z') || // lower case letter
                        (c >= 'A' && c <= 'Z') || // upper case letter
                        c == '_' || (j > i + 1 && c >= '0' && c <= '9'))) // digit
                    break;
            }
            // Retrieve the value of the environment variable
            String val = getenv(s.substring(i + 1, j));
            if (val == null) {
                // Variable does not expand. Skip it and continue to loop.
                start = j;
                continue;
            }
            // Insert the expanded variable
            s = s.substring(0, i) + val + s.substring(j);
        }

        if (fixPath) {
            return FileLib.fixSlashesAndPath(s, false);
        } else {
            return s;
        }
    }

    /**
     * A convenience method for getting an environment variable.
     * Returns 'null' if variable not set.
     * @param name Name of environment variable
     * @return Value of environment variable.
     */
    public static String getenv(String name) {
        String value = null;
        if (envs_ != null)
            value = envs_.get(name);
        if (value == null)
            // For JDK 1.5, this is safe.
            value = System.getenv(name);
        return value;

        // keep the implementation of getenv() here for later's investigation.
        /*
        		if (envs_ == null) {
        			envs_ = new HashMap();
        	        Vector osEnv = org.apache.tools.ant.taskdefs.Execute.getProcEnvironment();
        	        for (Enumeration e = osEnv.elements(); e.hasMoreElements();) {
        	            String entry = (String) e.nextElement();
        	            int pos = entry.indexOf('=');
        	            if (pos != -1) {
        	                envs_.put(entry.substring(0, pos), entry.substring(pos + 1));
        	            }
        	        }
        			
        		}
        		return (String)envs_.get(name);
        */
    }

    public static void setenv(String name, String value) {
        envs_.put(name, value);
    }

}
