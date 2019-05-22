package com.example.app_choferes.models;

import java.util.Map;

public class QueryResponse {

    private Map<String, String> result;

    public QueryResponse(Map<String, String> respone) {
        result = respone;
    }

    public boolean isSuccess() {
        return Integer.parseInt(this.result.get("result")) == 1;
    }

    public String getValue(String key) {
        return result.get(key);
    }
}
