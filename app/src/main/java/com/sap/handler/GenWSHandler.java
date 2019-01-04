package com.sap.handler;

import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class GenWSHandler {

    public static void wsCallBy(RequestBody requestBody, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, requestBody, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if(listener != null){
                    listener.responseStatus(status, data);
                }
            }
        });
    }

    public static void delProdBy(String product_id, String user_id)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, getRequestDelProd(product_id, user_id), new IWSCallHandler() {
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

    //loadReviewsBy
    public static void loadReviewsBy(String product_id, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, getRequestReviews(product_id), new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {

                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("data");
                            int count = jsonArray.length();
                            for (int ii = 0; ii < count; ii++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(ii);
                                //arrTmp.add(new CommentItem(jsonObjItm));
                            }
                        }
                    }catch (Exception ex){ex.printStackTrace();}

                    if(listener != null){
                       // listener.responseStatus(status, arrTmp);
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }

    //loadReservationBy
    public static void loadReservationBy(String user_id, int booking_list_type, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, getRequestMyBooking(user_id, booking_list_type), new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                    //List<ReservationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("data");
                            int count = jsonArray.length();
                            for (int ii = 0; ii < count; ii++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(ii);
                                //arrTmp.add(new ReservationItem(jsonObjItm));
                            }
                        }
                    }catch (Exception ex){ex.printStackTrace();}

                    if(listener != null){
                       // listener.responseStatus(status, arrTmp);
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }


    //loadReservationDtlsBy
    public static void loadReservationDtlsBy(String booking_id, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, getRequestMyBookingDtls(booking_id), new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
//                    List<ReservationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("data");
                            int count = jsonArray.length();
                            for (int ii = 0; ii < count; ii++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(ii);
                               // arrTmp.add(new ReservationItem(jsonObjItm));
                            }
                        }
                    }catch (Exception ex){ex.printStackTrace();}

                    if(listener != null){
                        //listener.responseStatus(status, arrTmp);
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }


    //loadReservationBy
    public static void loadNotificationsBy(String user_id, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, getRequestNotifications(user_id), new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                   // List<NotificationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("data");
                            int count = jsonArray.length();
                            for (int ii = 0; ii < count; ii++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(ii);
                               // arrTmp.add(new NotificationItem(jsonObjItm));
                            }
                        }
                    }catch (Exception ex){ex.printStackTrace();}

                    if(listener != null){
                        //listener.responseStatus(status, arrTmp);
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }

    public static void getAllStudents(final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_StudentsDetails;
        //web method call
        ServerComHandler.getInstance().wsCallByGet(wsLink, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                    // List<NotificationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("data");
                            int count = jsonArray.length();
                            for (int ii = 0; ii < count; ii++) {
                                JSONObject jsonObjItm = jsonArray.getJSONObject(ii);
                                // arrTmp.add(new NotificationItem(jsonObjItm));
                            }
                        }
                    }catch (Exception ex){ex.printStackTrace();}

                    if(listener != null){
                        //listener.responseStatus(status, arrTmp);
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }
    //updateQuickbloxID
    public static void updateQuickbloxID(RequestBody requestBody, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, requestBody, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                   // List<NotificationItem> arrTmp = new ArrayList<>();
                    ResponseStatus responseStatus = new ResponseStatus((String)data);
                    if (responseStatus.isSuccess()) {
                        listener.responseStatus(status, data);
                    } else {
                        listener.responseStatus(201, responseStatus.getErrMessage());
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }

    //updateToken
    public static void updateToken(RequestBody requestBody, final IWSCallHandler listener)
    {
        String wsLink = AppConstants.WS_BASE_URL;
        //web method call
        ServerComHandler.getInstance().wsCallBy(wsLink, requestBody, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                   // List<NotificationItem> arrTmp = new ArrayList<>();
                    ResponseStatus responseStatus = new ResponseStatus((String)data);
                    if (responseStatus.isSuccess()) {
                        listener.responseStatus(status, data);
                    } else {
                        listener.responseStatus(201, responseStatus.getErrMessage());
                    }
                }else{
                    if(listener != null){
                        listener.responseStatus(status, data);
                    }
                }
            }
        });
    }


    //getRequestDelProd
    private static RequestBody getRequestDelProd(String product_id, String user_id) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "delete_product");
            formBuilder.add("product_id", product_id);
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }


    //getRequestDelProd
    private static RequestBody getRequestReviews(String product_id) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "review_list");
            formBuilder.add("product_id", product_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    //getRequestMyBooking
    private static RequestBody getRequestMyBooking(String user_id, int booking_list_type) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "my_booking");
            formBuilder.add("user_id", user_id);
            formBuilder.add("booking_list_type", "" + booking_list_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    //getRequestMyBookingDtls
    private static RequestBody getRequestMyBookingDtls(String booking_id) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "booking_details");
            formBuilder.add("booking_id", booking_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    //getRequestMyBooking
    private static RequestBody getRequestNotifications(String user_id) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        try {
            formBuilder.add("action", "notification");
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }
}
