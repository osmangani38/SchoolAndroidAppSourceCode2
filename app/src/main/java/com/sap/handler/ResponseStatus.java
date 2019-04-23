package com.sap.handler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.school.ApplicationContextProvider;
import com.sap.school.LoginActivity;

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

            if(jsonObject.has("status") && jsonObject.has("result") && !jsonObject.isNull("result")) {
                message_code = jsonObject.getBoolean("status");
            }

            if(jsonObject.has("message")) {
                response_message = jsonObject.getString("message");
            }
            if (response_message.equals("Session expired.")){
                ToastUtils.showShort("Your Login Session Expired, Please Login Again");

                Intent i = ApplicationContextProvider.getContext().getPackageManager()
                        .getLaunchIntentForPackage( ApplicationContextProvider.getContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ApplicationContextProvider.getContext().startActivity(i);

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
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            if(jsonResult.has("user_id")) {
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
            JSONObject jsonResult = jsonObject.getJSONObject("result");

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
            JSONObject jsonResult = jsonObject.getJSONObject("result");

            if(jsonResult.has("designation")) {
                SecurePrefManager.with(ApplicationContextProvider.getContext())
                        .set("designation")
                        .value(jsonResult.getString("designation"))
                        .go();

                return jsonResult.getString("designation");
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }

    public String parseAuthToken()
    {
        try {
            JSONObject jsonResult = jsonObject.getJSONObject("result");

            if(jsonResult.has("authtoken")) {
                SecurePrefManager.with(ApplicationContextProvider.getContext())
                        .set("authtoken")
                        .value(jsonResult.getString("authtoken"))
                        .go();

                return jsonResult.getString("authtoken");
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
                SecurePrefManager.with(ApplicationContextProvider.getContext())
                        .set("name")
                        .value(jsonResult.getString("name"))
                        .go();

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
                SecurePrefManager.with(ApplicationContextProvider.getContext())
                        .set("dob")
                        .value(jsonResult.getString("dob"))
                        .go();

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
