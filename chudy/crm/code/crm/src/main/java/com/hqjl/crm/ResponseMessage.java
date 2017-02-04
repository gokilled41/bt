package com.hqjl.crm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hqjl.crm.utils.JsonUtil;

public class ResponseMessage {
    private int status;
    private String result;
    @JsonInclude(Include.NON_NULL)
    private String error;

    public ResponseMessage() {
        super();
    }

    public ResponseMessage(int status) {
        super();
        this.status = status;
    }

    public ResponseMessage(int status, String result) {
        super();
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static void main(String[] args) {
        ResponseMessage m = new ResponseMessage();
        m.setStatus(1);
        m.setResult("test");
        System.out.println(JsonUtil.objectToJsonNode(m));
    }
}
