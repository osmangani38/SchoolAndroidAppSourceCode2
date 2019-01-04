package com.sap.handler;

import com.sap.utils.AppConstants;
import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class FavUnFavHandler {

    public static void doFavOrUnFavProd(String product_id, String user_id, boolean isFav)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, getRequestForFav(product_id, user_id), new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
//                dismissProgressUI();
                if (status == 200) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            handleWSResponse((String)data);
//                        }
//                    });
                } else {
//                    showErrMessage("Error", (String)data);
                }
            }
        });
    }


    public static RequestBody getRequestForFav(String product_id, String user_id) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "add_favourite");
            formBuilder.add("product_id", product_id);
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getRequestForUnFav(String product_id, String user_id) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "add_favourite");
            formBuilder.add("product_id", product_id);
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }
}
