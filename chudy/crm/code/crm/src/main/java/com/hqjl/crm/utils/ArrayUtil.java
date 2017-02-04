package com.hqjl.crm.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {

    private ArrayUtil() {
        super();
    }

    // 判断数组是否为空
    public static <T> boolean isEmpty(T[][] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static <T> boolean isEmpty(T[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static boolean isEmpty(char[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static boolean isEmpty(byte[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static boolean isEmpty(int[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static boolean isEmpty(double[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static boolean isEmpty(float[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 判断数组是否为空
    public static boolean isEmpty(long[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        return false;
    }

    // 交换数据
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // 将数组转化为集合
    public static <T> List<T> asList(T[] array) {
        if (isEmpty(array)) {
            return null;
        }

        List<T> result = new ArrayList<T>(array.length);

        for (T e : array) {
            result.add(e);
        }

        return result;
    }

    // 判断数组中是否包含某�?个元�?
    public static <T> boolean contains(T[] array, T e) {
        if (isEmpty(array)) {
            return false;
        }

        for (T t : array) {
            if (t.equals(e)) {
                return true;
            }
        }

        return false;
    }

}
