package com.sap.handler;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sarfarajbiswas on 18/08/16.
 */
public class ResponseStatus {

    public boolean message_code;
    public String response_message;

    public JSONObject jsonObject;
//    public JSONArray jsonArr;

    public ResponseStatus(){}

    public ResponseStatus(String jsonResponse)
    {
        try {
            jsonObject = new JSONObject(jsonResponse);
            if(jsonObject.has("success")) {
                message_code = jsonObject.getBoolean("success");
            }

            if(jsonObject.has("msg")) {
                response_message = jsonObject.getString("msg");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public JSONObject getJsonObject() throws Exception
    {
        return jsonObject.getJSONObject("student_list");
    }

    public JSONObject getFirstJsonObject() throws Exception
    {
        JSONArray jsonArray = jsonObject.getJSONArray("student_list");
        if(jsonArray.length() > 0){
            return jsonArray.getJSONObject(0);
        }

        return null;
    }

    //responseStatus
    public boolean isSuccess()
    {
        if(this.message_code) return true;

        return false;
    }

    //getErrMessage
    public String getErrMessage()
    {
        if(this.response_message != null && this.response_message.length() > 0) return this.response_message;

        return "Server error.";
    }


    //parseUserId
    public String parseUserId()
    {
        try {
            if(jsonObject.has("userid")) {
                return jsonObject.getString("userid");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }

}
