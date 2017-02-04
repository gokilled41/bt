package com.hqjl.crm.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class BeanUtil {
    public static void mergeBean(Class clz, Object exist, Object latest) {
        Method[] ms = clz.getMethods();
        for (Method m : ms) {
            if (m.getName().startsWith("set")) {
                String getM = m.getName().replaceFirst("set", "get");
                try {
                    Method getMethod = clz.getMethod(getM);
                    Object latestV = getMethod.invoke(latest);
                    if (latestV != null) {
                        m.invoke(exist, latestV);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String[] stringToArray(String input) {
        if (!StringUtils.isEmpty(input))
            return input.split(HQJLUtil.SPLITTER);
        return new String[0];
    }

    public static int[] stringArrayToIntArray(String[] ss) {
        int[] is = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            is[i] = Integer.valueOf(ss[i]);
        }
        return is;
    }

    public static long[] stringArrayToLongArray(String[] ss) {
        long[] is = new long[ss.length];
        for (int i = 0; i < ss.length; i++) {
            is[i] = Long.valueOf(ss[i]);
        }
        return is;
    }

    public static String arrayToString(String[] input) {
        if (input == null)
            return "";
        StringBuffer sb = new StringBuffer();
        String delimiter = "";
        for (String s : input) {
            if (!StringUtils.isEmpty(s)) {
                sb.append(delimiter).append(s);
                delimiter = HQJLUtil.SPLITTER;
            }
        }
        return sb.toString();
    }

    public static Object initBean(String xml, String beanId) {
        Resource r = new ByteArrayResource(xml.getBytes());
        BeanFactory f = new XmlBeanFactory(r);
        Object bean = f.getBean(beanId);
        return bean;
    }

    public static boolean isChineseChar(String str) {
        if (str == null)
            return true;
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    public static String toChinese(String str) {
        try {
            return new String(str.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static <T> List<T> toBeanList(Map kvMap, String prefix, Class<T> clz)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<T> beans = new ArrayList<T>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            boolean found = false;
            Field[] fields = clz.getDeclaredFields();
            T t = clz.newInstance();
            for (Field field : fields) {
                String key = prefix + "[" + i + "][" + field.getName() + "]";
                Object v = kvMap.get(key);
                if (v != null) {
                    Method[] ms = clz.getMethods();
                    for (Method m : ms) {
                        if (m.getName().equalsIgnoreCase("set" + field.getName())) {
                            set(m, t, v);
                            break;
                        }
                    }
                    found = true;
                }
            }
            if (!found) {
                break;
            }
            beans.add(t);
        }
        return beans;
    }

    private static void set(Method method, Object obj, Object value)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        method.invoke(obj, value);
    }

    //only for multipart request
    public static <T> T toBean(Map kvMap, Class<T> clz) throws Exception {
        T b = clz.newInstance();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            Object v = kvMap.get(field.getName());
            if (v != null) {
                Method[] ms = clz.getMethods();
                for (Method m : ms) {
                    if (m.getName().equalsIgnoreCase("set" + field.getName())) {
                        setFromArray(m, b, v);
                        break;
                    }
                }
            }
        }
        return b;
    }

    private static void setFromArray(Method method, Object obj, Object value) throws Exception {
        Object o = null;
        if (((String[]) value).length == 1) {
            o = ((String[]) value)[0];
        } else {
            o = ((String[]) value);
        }
        Class<?>[] ps = method.getParameterTypes();
        if (ps.length > 1) {
            return;
        }
        if (o instanceof String) {
            String v = (String) o;
            if (v.length() == 0)
                return;
            Class<?> pType = ps[0];
            if (pType.equals(String.class)) {
                method.invoke(obj, v);
                return;
            } else if (pType.equals(Integer.class) || pType.equals(int.class)) {
                method.invoke(obj, Integer.valueOf(v));
                return;
            } else if (pType.equals(Long.class) || pType.equals(long.class)) {
                method.invoke(obj, Long.valueOf(v));
                return;
            } else if (pType.equals(Double.class) || pType.equals(double.class)) {
                method.invoke(obj, Double.valueOf(v));
                return;
            } else if (pType.equals(Timestamp.class)) {
                Timestamp dt = DatetimeUtil.parseDate(v, DatetimeUtil.STANDARD_DATETIME_PATTERN);
                method.invoke(obj, dt);
                return;
            } else if (pType.equals(Date.class)) {
                Date dt = null;
                try {
                    dt = DatetimeUtil.parseDate(v, DatetimeUtil.STANDARD_DATETIME_PATTERN);
                } catch (Exception e) {
                    dt = DatetimeUtil.parse(v, DatetimeUtil.STANDARD_DATE_PATTERN);
                }
                method.invoke(obj, dt);
                return;
            } else if (pType.equals(String[].class)) {
                String[] s = new String[] { v };
                method.invoke(obj, new Object[] { s });
                return;
            }
        } else if (o instanceof String[]) {
            method.invoke(obj, o);
            return;
        }
    }
}
