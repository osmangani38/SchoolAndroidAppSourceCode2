package com.sap.handler;


import com.sap.handler.IWSCallHandler;
import com.sap.utils.AppConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public void wsCallByGet(String webServiceLink, final IWSCallHandler listener) {
        System.out.println("wsCallByGet " + webServiceLink);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(130, TimeUnit.SECONDS)
                .build();

        client.newCall(new Builder().url(webServiceLink).get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("onFailure " + e);
                listener.responseStatus(202, e);
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
                System.out.println("onFailure " + e);
                listener.responseStatus(202, e);
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
                listener.responseStatus(response.code(), "Server not found");
            }
        });
    }


    public void wsCallJsonBy(String webServiceLink, String jsonString, final IWSCallHandler listener) {

        System.out.println("wsCallJsonBy request " + jsonString);

        new OkHttpClient().newCall(new Builder().url(webServiceLink).post(RequestBody.create(JSON, jsonString)).build()).enqueue(new Callback() {
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
                listener.responseStatus(response.code(), "Server not found");
            }
        });
    }
}
