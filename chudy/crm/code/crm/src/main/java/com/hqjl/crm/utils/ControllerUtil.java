package com.hqjl.crm.utils;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class ControllerUtil {

    public static void responseList(HttpServletResponse response, String callback, List<?> list, long count,
            Integer size) {
        WebUtil.response(response,
                WebUtil.packJsonp(callback, JsonUtil.getSizeAndListObjectNode(count, list, size).toString()));
    }

    public static void responseList(HttpServletResponse response, String callback, List<?> list) {
        WebUtil.response(response, WebUtil.packJsonp(callback, JsonUtil.getListObjectNode(list).toString()));
    }

    public static void responseInt(HttpServletResponse response, String callback, int count) {
        WebUtil.response(response,
                WebUtil.packJsonp(callback, JsonUtil.warpReturn((count > 0) ? true : false).toString()));
    }

    public static void responseObject(HttpServletResponse response, String callback, Object r) {
        WebUtil.response(response,
                WebUtil.packJsonp(callback, JsonUtil.warpJsonNodeResponse(JsonUtil.objectToJsonNode(r)).toString()));
    }

    public static void responseJson(HttpServletResponse response, String callback, Object... objects) {
        String json = JsonUtil.toJsonString(objects);
        WebUtil.response(response, WebUtil.packJsonp(callback, JsonUtil.warpStringResponse(json).toString()));
    }

    public static void responseBoolean(HttpServletResponse response, String callback, boolean b) {
        WebUtil.response(response, WebUtil.packJsonp(callback, JsonUtil.warpReturn(b).toString()));
    }

}
