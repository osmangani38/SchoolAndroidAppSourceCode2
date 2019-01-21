package com.sap.handler;

import com.blankj.utilcode.util.SPUtils;

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
            if(jsonObject.has("status")) {
                message_code = jsonObject.getBoolean("status");
            }

            if(jsonObject.has("message")) {
                response_message = jsonObject.getString("message");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public JSONObject getJsonObject() throws Exception
    {
        return jsonObject.getJSONObject("result");
    }

    public JSONObject getFirstJsonObject() throws Exception
    {
        JSONArray jsonArray = jsonObject.getJSONArray("result");
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
            JSONObject jsonResult = getFirstJsonObject();
            if(jsonResult.has("user_id")) {
                SPUtils.getInstance().put("user_id",jsonResult.getString("user_id"));
                return jsonResult.getString("user_id");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }
    //parseRollId
    public String parseRollId()
    {
        try {
            JSONObject jsonResult = getFirstJsonObject();

            if(jsonResult.has("role_id")) {
                SPUtils.getInstance().put("role_id",jsonResult.getString("role_id"));
                return jsonResult.getString("role_id");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }
    public String parseUserType()
    {
        try {
            JSONObject jsonResult = getFirstJsonObject();

            if(jsonResult.has("designation")) {
                SPUtils.getInstance().put("designation",jsonResult.getString("designation"));
                return jsonResult.getString("designation");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }
    public String parseName()
    {
        try {
            JSONObject jsonResult = getFirstJsonObject();

            if(jsonResult.has("name")) {
                SPUtils.getInstance().put("name",jsonResult.getString("name"));
                return jsonResult.getString("name");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }
    public String parseDOB()
    {
        try {
            JSONObject jsonResult = getFirstJsonObject();

            if(jsonResult.has("dob")) {
                SPUtils.getInstance().put("dob",jsonResult.getString("dob"));
                return jsonResult.getString("dob");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }
    public String parseGender()
    {
        try {
            JSONObject jsonResult = getFirstJsonObject();

            if(jsonResult.has("gender")) {
                SPUtils.getInstance().put("gender",jsonResult.getString("gender"));
                return jsonResult.getString("gender");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }

}
