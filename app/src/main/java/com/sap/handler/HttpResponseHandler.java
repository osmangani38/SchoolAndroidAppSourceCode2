package com.sap.handler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HttpResponseHandler {

    public static ArrayList<HashMap<String, String>> parseCustomerLedgerSearchData(String jsonArrayStr) {
        ArrayList<HashMap<String, String>> itemList = new ArrayList();
        try {
            JSONArray jsonArray = new JSONObject(jsonArrayStr).getJSONArray("statement");
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> item = new HashMap();
                JSONObject jsonTemp = jsonArray.optJSONObject(i);
                if (jsonTemp != null) {
                    parseJsonItemBy(item, jsonTemp, "doc_no");
                    parseJsonItemBy(item, jsonTemp, "posting_dt");
                    parseJsonItemBy(item, jsonTemp, "qty");
                    parseJsonItemBy(item, jsonTemp, "debit");
                    parseJsonItemBy(item, jsonTemp, "credit");
                    itemList.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public static void parseJsonItemBy(HashMap<String, String> item, JSONObject jsonTemp, String key) throws Exception {
        String val = jsonTemp.optString(key);
        if (val != null) {
            item.put(key, val);
        }
    }

    public static void parseJsonItemObjBy(HashMap<String, Object> item, JSONObject jsonTemp, String key) throws Exception {
        String val = jsonTemp.optString(key);
        if (val != null) {
            item.put(key, val);
        }
    }
}
