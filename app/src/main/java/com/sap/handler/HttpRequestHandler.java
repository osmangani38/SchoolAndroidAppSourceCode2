package com.sap.handler;


import com.sap.utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import okhttp3.FormBody.Builder;
import okhttp3.RequestBody;

public class HttpRequestHandler {

    public static String getSignInJsonStr(String username, String password, String fcmkey, String imei) {
        String json = "{\"username\":\""+ username +"\", \"password\":\""+password+"\", \"fcmkey\":\"" + fcmkey + "\", \"imei\":\"" + imei + "\"}";

        return json;
    }

    public static String getSignUpJsonStr(String username, String password, String phone_number, String fcmkey, String imei) {
        String json = "{\"username\":\""+ username +"\", \"password\":\""+password+"\", \"phoneno\":\"" + phone_number + "\", \"fcmkey\":\"" + fcmkey + "\", \"imei\":\"" + imei + "\"}";

        return json;
    }


    //getComposeJsonStr
    public static String getComposeJsonStr(String senderId, String receiverid, String message) {
        String json = "{\"senderid\":\""+ senderId +"\", \"receiverid\":\""+receiverid+"\", \"message\":\"" + message + "\"}";

        return json;
    }


    public static String getAllMessageJsonStr(String userid) {
        String json = "{\"userid\":\""+ userid +"\"}";

        return json;
    }

    public static String getAllUserJsonStr(String userid) {
        String json = "{\"userid\":\""+ userid +"\"}";

        return json;
    }


    public static String getDelMsgJsonStr(String ids) {
        String json = "{\"messages\":"+ ids +"}";

        return json;
    }





    public static RequestBody getSignInRequestBody(String email, String password) {
        Builder formBuilder = new Builder();
        try {
            //action=signup&email=abce@a.com&password=1234
            formBuilder.add("action", AppConstants.WS_METHOD_SIGNIN);
            formBuilder.add("email", email);
            formBuilder.add("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getSignUpRequestBody(String email, String password) {
        Builder formBuilder = new Builder();
        try {
            //action=signin&email=abc@a.com&password=1234
            formBuilder.add("action", AppConstants.WS_METHOD_SIGNUP);
            formBuilder.add("email", email);
            formBuilder.add("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getRequestMyListing(String user_id, String prod_type) {
        Builder formBuilder = new Builder();
        try {
            //my_product_list
            formBuilder.add("action", "my_product_list");
            formBuilder.add("user_id", user_id);
            formBuilder.add("product_type", prod_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getRequestFavListing(String user_id, String prod_type) {
        Builder formBuilder = new Builder();
        try {
            //my_product_list
            formBuilder.add("action", "favourite_list");
            formBuilder.add("user_id", user_id);
            formBuilder.add("product_type", prod_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    //getRequestBodyBy
    public static RequestBody getRequestBodyBy(HashMap<String, String> inputData) {
        Builder formBuilder = new Builder();
        try {
            Set<String> allKeys = inputData.keySet();
            for(String key : allKeys){
                formBuilder.add(key, inputData.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }


    public static RequestBody getRequestSearchProd(String user_id) {
        Builder formBuilder = new Builder();
        try {
            //my_product_list
            formBuilder.add("action", "product_details");
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }
















    public static RequestBody getUpdateUserPhoneNumRequestBody(String user_id, String country_code, String ph_no) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "Update_Phone");
            formBuilder.add("user_id", user_id);
            formBuilder.add("country_code", country_code);
            formBuilder.add("ph_no", ph_no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody searchEventCodeRequestBody(String eventCode) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "SEARCH_EVENT");
            formBuilder.add("event_code", eventCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody searchForgotPassRequestBody(String email) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "Forgot_Password");
            formBuilder.add("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getSendVerificationCodeRequestBody(String user_id) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "Resend_Code");
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getVerifyCodeRequestBody(String user_id, String verification_code) {
        System.out.println("getVerifyCodeRequestBody " + user_id + ", " + verification_code);
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "Verify_Account");
            formBuilder.add("user_id", user_id);
            formBuilder.add("verification_code", verification_code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }



    public static RequestBody getForgotPasswordRequestJsonStr(String email) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "Forgot_Password");
            formBuilder.add("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody searchEventListRequestBodyBy(String user_id, String categoryType) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "All_Event");
            formBuilder.add("user_id", user_id);
            formBuilder.add("event_type", categoryType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getEventListRequestBodyBy(String actionName, String user_id) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", actionName);
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static RequestBody getSaveWishListRequestBodyBy(String eventId, String user_id) {
        Builder formBuilder = new Builder();
        try {
            formBuilder.add("action", "Add_Wishlist");
            formBuilder.add("event_id", eventId);
            formBuilder.add("user_id", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formBuilder.build();
    }

    public static String getAttendenceRequestJsonStr(String idUser, String userGiven, String userType) {
        JSONObject temp_json = new JSONObject();
        try {
            temp_json.put("idUser", idUser);
            temp_json.put("userGiven", userGiven);
            temp_json.put("userType", userType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp_json.toString();
    }
}
