package com.hqjl.crm.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JsonUtil {

    public static ObjectNode getNoManagerResponse() {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 0);
        ObjectNode response = createObjectNode();
        response.put("actions", createArrayNode());
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode getSizeAndListObjectNode(long count, List<?> list, Integer pageSize) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        ObjectNode response = createObjectNode();
        response.put("size", (count - 1) / pageSize + 1);
        response.put("list", JsonUtil.objectToJsonNode(list));
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode getSizeAndListObjectNode(long count, ArrayNode list, Integer pageSize) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("size", (count - 1) / pageSize + 1);
        ObjectNode response = createObjectNode();
        response.put("list", JsonUtil.objectToJsonNode(list));
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode getSizeAndListObjectNode(long count, List<?> list) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        ObjectNode response = createObjectNode();
        response.put("size", count);
        response.put("list", JsonUtil.objectToJsonNode(list));
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode getListObjectNode(List<?> list) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        ObjectNode response = createObjectNode();
        response.put("list", JsonUtil.objectToJsonNode(list));
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode getArrayObjectNode(Object[] array) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        ObjectNode response = createObjectNode();
        response.put("list", JsonUtil.objectToJsonNode(array));
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode warpStringResponse(String json) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        objectNode.put("response", JsonUtil.StringToJsonNode(json));
        return objectNode;
    }

    public static ObjectNode warpObjectResponse(Object obj) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        objectNode.put("response", obj.toString());
        return objectNode;
    }

    public static ObjectNode warpBooleanResponse(boolean obj) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        objectNode.put("response", obj);
        return objectNode;
    }

    public static ObjectNode warpJsonNodeResponse(JsonNode obj) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        objectNode.put("response", obj);
        return objectNode;
    }

    public static ObjectNode warpReturn(boolean succ) {
        ObjectNode returnV = createObjectNode();
        if (succ)
            returnV.put("code", 1);
        else
            returnV.put("code", 0);
        return returnV;
    }

    public static ObjectNode notLoginResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -1);
        ObjectNode response = createObjectNode();
        response.put("reason", "not login");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode invalidPWResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", 0);
        ObjectNode response = createObjectNode();
        response.put("reason", "invalid password");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode tokenExpiredResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -2);
        ObjectNode response = createObjectNode();
        response.put("reason", "token expired");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode invalidPhone() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -4);
        ObjectNode response = createObjectNode();
        response.put("reason", "invalid phone");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode invalidCode() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -5);
        ObjectNode response = createObjectNode();
        response.put("reason", "invalid code");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode invalidPicCode() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -5);
        ObjectNode response = createObjectNode();
        response.put("reason", "invalid pic code");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode dupEntityResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -6);
        ObjectNode response = createObjectNode();
        response.put("reason", "dup entity");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode noAwardResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -7);
        ObjectNode response = createObjectNode();
        response.put("reason", "not award");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode alreadyPayAwardResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -8);
        ObjectNode response = createObjectNode();
        response.put("reason", "already pay award");
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode getMapObjectNode(Map<String, String> map) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        ObjectNode response = createObjectNode();
        response.put("map", JsonUtil.objectToJsonNode(map));
        objectNode.put("response", response);
        return objectNode;
    }

    public static ObjectNode warpFailJsonNodeResponse(int failCode, JsonNode obj) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", failCode);
        objectNode.put("response", obj);
        return objectNode;
    }

    /*public static ObjectNode getStatusObjectNode(Object obj,Integer status) {
    	ObjectNode objectNode=createObjectNode();
    	objectNode.put("status", status);
    	objectNode.put("object", JsonUtil.objectToJsonNode(obj));
    	return objectNode;
    }*/

    public static <T> List<T> StringToObjectList(String str, TypeReference<?> type) {
        try {
            if (str == null || str.isEmpty() || type == null) {
                //TODO
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(str, type);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T StringToObject(String str, TypeReference<T> type) {
        try {
            if (str == null || str.isEmpty() || type == null) {
                //TODO
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(str, type);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String ObjectToString(Object obj) {
        try {
            if (obj == null) {
                //TODO
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static JsonNode StringToJsonNode(String json) {
        try {
            if (StringUtils.isEmpty(json))
                json = "{}";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode objectToJsonNode(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String objJson = objectMapper.writeValueAsString(obj);
            JsonNode jsonNode = objectMapper.readTree(objJson);
            return jsonNode;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static ObjectNode createObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode ObjectNode = objectMapper.createObjectNode();
        return ObjectNode;
    }

    public static ArrayNode createArrayNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        return arrayNode;
    }

    public static String ObjectToStringByReflect(Object obj, String extra) {
        StringBuilder sbf = new StringBuilder();
        try {

            Class<?> clz = obj.getClass();
            Field[] fields = clz.getDeclaredFields();
            Field.setAccessible(fields, true);
            int size = fields.length;
            sbf.append("{");
            boolean bool = false;
            for (int i = 0; i < size; i++) {
                String fieldName = fields[i].getName();
                if ("serialVersionUID".equals(fieldName)) {
                    continue;
                }
                sbf.append("\"");
                sbf.append(fieldName);
                sbf.append("\"");
                sbf.append(":");
                Object value = fields[i].get(obj);
                if ((fields[i].getType().equals(String.class) || fields[i].getType().equals(Date.class))
                        && value != null && !fieldName.equals("common_cate")) {
                    bool = true;
                    sbf.append("\"");
                } else {
                    bool = false;
                }
                sbf.append(value);
                if (bool) {
                    sbf.append("\"");
                }
                if (i != size - 1) {
                    sbf.append(",");
                }

            }
            if (extra != null || !"".equals(extra)) {
                sbf.append(",");
                sbf.append(extra);
            }
            sbf.append("}");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sbf.toString();
    }

    public static String addChannelAttribute(String json, String childUrl, String url, String parament) {
        ArrayNode arrayNode = (ArrayNode) StringToJsonNode(json);

        if (arrayNode != null) {
            int size = arrayNode.size();
            ObjectNode objectNode = null;
            for (int i = 0; i < size; i++) {
                objectNode = (ObjectNode) arrayNode.get(i);
                objectNode.put("childUrl", childUrl + parament + "=" + objectNode.get(parament));
                objectNode.put("url", url);
            }
            return arrayNode.toString();
        }
        return json;
    }

    public static String optString(ObjectNode node, String key, String defaultValue) {
        JsonNode jsonNode = node.get(key);
        if (jsonNode == null)
            return defaultValue;
        else
            return jsonNode.asText();
    }

    public static ObjectNode errorResponse(int code, String reason) {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", code);
        ObjectNode response = createObjectNode();
        response.put("reason", reason);
        returnV.put("response", response);
        return returnV;
    }

    public static ObjectNode warpDataResponse(Object data) {
        ObjectNode objectNode = createObjectNode();
        objectNode.put("code", 1);
        ObjectNode dataNode = createObjectNode();
        dataNode.put("data", data.toString());
        objectNode.put("response", dataNode);
        return objectNode;
    }

    public static ObjectNode pwNullResponse() {
        ObjectNode returnV = createObjectNode();
        returnV.put("code", -1);
        ObjectNode response = createObjectNode();
        response.put("reason", "new password is null");
        returnV.put("response", response);
        return returnV;
    }

    public static Object nodeToObject(JsonNode node) {
        if (node == null)
            return "";
        if (node instanceof DoubleNode) {
            return node.asDouble();
        } else if (node instanceof TextNode) {
            return node.asText();
        } else if (node instanceof BooleanNode) {
            return node.asBoolean();
        } else if (node instanceof LongNode) {
            return node.asLong();
        } else if (node instanceof IntNode) {
            return node.asInt();
        }
        return node.asText();
    }

    public static JsonNode getByJPath(JsonNode node, String jpath) {
        if (!jpath.contains("."))
            return node.get(jpath);
        String[] paths = jpath.split("\\.");
        JsonNode temp = node;
        for (String path : paths) {
            if (temp != null)
                temp = temp.get(path);
        }
        return temp;
    }

    public static void main(String[] args) {
        String a = "{\"phote\":\"/b/c.png\"}";
        JsonNode node = StringToJsonNode(a);
        System.out.println(node.getNodeType());
    }

    // key1, value1, key2, value2...
    public static String toJsonString(Object... objects) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < objects.length; i = i + 2) {
            Object key = objects[i];
            Object value = objects[i + 1];
            sb.append("\"" + key.toString() + "\"");
            sb.append(": ");
            sb.append("\"" + value.toString() + "\"");
            if (i + 1 < objects.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
