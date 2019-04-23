package com.sap.handler;


import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.sap.handler.IWSCallHandler;
import com.sap.school.ApplicationContextProvider;
import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

public class ServerComHandler {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String sereverError = "Could not connect to the server";

    public static ServerComHandler getInstance() {
        return new ServerComHandler();
    }

    public void wsCallBy(RequestBody formBody, final IWSCallHandler listener) {
        new OkHttpClient().newCall(new Builder().url(AppConstants.WS_BASE_URL).post(formBody).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("onFailure " + e.getMessage());
                String errMsg = e.getLocalizedMessage();
                if(errMsg == null){
                    errMsg = "" + e;
                }
                listener.responseStatus(202, errMsg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse " + response);
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    System.out.println("Response Data: " + responseStr);
                    listener.responseStatus(response.code(), responseStr);
                    return;
                }
                listener.responseStatus(response.code(), sereverError);
            }
        });
    }

    public void wsCallBy(String webServiceLink, RequestBody formBody, final IWSCallHandler listener) {
        new OkHttpClient().newCall(new Builder().url(webServiceLink).post(formBody).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //System.out.println("onFailure " + e.getMessage());
                String errMsg = e.getLocalizedMessage();
                if(errMsg == null){
                    errMsg = "" + e;
                }
                listener.responseStatus(202, errMsg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("onResponse " + response);
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    //System.out.println("Response Data: " + responseStr);
                    listener.responseStatus(response.code(), responseStr);
                    return;
                }
                listener.responseStatus(response.code(), sereverError);
            }
        });
    }

    public void wsCallByGet(String webServiceLink, final IWSCallHandler listener) {
       // System.out.println("wsCallByGet " + webServiceLink);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(130, TimeUnit.SECONDS)
                .build();

        client.newCall(new Builder().url(webServiceLink).get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //System.out.println("onFailure " + e);
                listener.responseStatus(202, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("onResponse " + response);
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                   // System.out.println("Response Data: " + responseStr);
                    listener.responseStatus(response.code(), responseStr);
                    return;
                }
                listener.responseStatus(response.code(), "Server not found");
            }
        });
    }

    public void wsCallJsonBy(String jsonString, final IWSCallHandler listener) {
        String webServiceLink = AppConstants.WS_BASE_URL;
        OkHttpClient client1 = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(130, TimeUnit.SECONDS)
                .build();


        new OkHttpClient().newCall(new Builder().url(webServiceLink).post(RequestBody.create(JSON, jsonString)).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //System.out.println("onFailure " + e);
                listener.responseStatus(202, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("onResponse " + response);
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    //System.out.println("Response Data: " + responseStr);
                    listener.responseStatus(response.code(), responseStr);
                    return;
                }
                listener.responseStatus(response.code(), "Server not found");
            }
        });
    }


    public void wsCallJsonBy(String webServiceLink, String jsonString, final IWSCallHandler listener) {
        JSONObject jsonObject = new JSONObject();
        String ws_code = "";
        //System.out.println("wsCallJsonBy request " + jsonString);
        String authtoken = SecurePrefManager.with(ApplicationContextProvider.getContext())
                .get("authtoken")
                .defaultValue("unknown")
                .go();
        if (jsonString.contains("\"student\":[")) {
            // authtoken = authtoken+"5";
        }
        String imei = SecurePrefManager.with(ApplicationContextProvider.getContext())
                .get("imei")
                .defaultValue("unknown")
                .go();
        try {
            JSONObject obj = new JSONObject(jsonString);
            ws_code = obj.getString("WS_CODE");
            JSONArray jsonArray = obj.getJSONArray("WS_DATA");
            jsonObject = jsonArray.getJSONObject(0);

            jsonObject.put("authtoken", authtoken);
            jsonObject.put("imei", imei);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JSONArray jsonArray1 = new JSONArray();
        jsonArray1.put(jsonObject);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray1);
            ws_dataObj.put("WS_CODE", ws_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = ws_dataObj.toString();
//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .cipherSuites(
//                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//                .build();

       // OkHttpClient client = new OkHttpClient.Builder()
               // .connectionSpecs(Collections.singletonList(spec))
              //  .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(new Builder().url(webServiceLink).post(RequestBody.create(JSON, json)).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //System.out.println("onFailure " + e.getMessage());
                String errMsg = e.getLocalizedMessage();
                if(errMsg == null){
                    errMsg = "" + e;
                }
                listener.responseStatus(202, errMsg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("onResponse " + response);
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                   // System.out.println("Response Data: " + responseStr);
                    listener.responseStatus(response.code(), responseStr);
                    return;
                }
                listener.responseStatus(response.code(), "Server not found");
            }
        });
    }

    public void wsCallJsonByLogin(String webServiceLink, String jsonString, final IWSCallHandler listener) {

       // System.out.println("wsCallJsonBy request " + jsonString);

        new OkHttpClient().newCall(new Builder().url(webServiceLink).post(RequestBody.create(JSON, jsonString)).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //System.out.println("onFailure " + e.getMessage());
                String errMsg = e.getLocalizedMessage();
                if(errMsg == null){
                    errMsg = "" + e;
                }
                listener.responseStatus(202, errMsg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("onResponse " + response);
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    //System.out.println("Response Data: " + responseStr);
                    listener.responseStatus(response.code(), responseStr);
                    return;
                }
                listener.responseStatus(response.code(), "Server not found");
            }
        });
    }
}
