package com.sap.school;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.PojoClass.AttendancePogoClass;
import com.sap.school.PojoClass.ProfilePojoClass;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener,Validator.ValidationListener{
    Button loginButton,closeButton,closeButton1;
    RelativeLayout relativeLayout;
    PDFView pdfView;
    WebView webView;
    TextView btnTextDownload;
    @NotEmpty
    EditText emailTF;
    @NotEmpty
    @Password(min = 5, scheme = Password.Scheme.ANY)
    EditText passwordTF;
    Validator validator;
    String valueType = "";
    String user_id,roll_id, designation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListner();
        validator = new Validator(this);
        validator.setValidationListener(LoginActivity.this);
    }

    private void setListner() {
        loginButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        closeButton1.setOnClickListener(this);
        btnTextDownload.setOnClickListener(this);
    }

    private void initView() {
        loginButton=(Button)findViewById(R.id.loginButton);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        btnTextDownload=(TextView) findViewById(R.id.btnDownloadManual);
        emailTF=(EditText) findViewById(R.id.email);
        passwordTF = (EditText) findViewById(R.id.password);
        closeButton = (Button)findViewById(R.id.closeButton);
        closeButton1 = (Button)findViewById(R.id.closeButton1);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        valueType=getIntent().getStringExtra("valueType");
       emailTF.setText("300313");
       //emailTF.setText("191701043131611169");
        passwordTF.setText("password");
        btnTextDownload.setPaintFlags(btnTextDownload.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

    }
    private void navigate(){
        if(StringUtils.equalsIgnoreCase(roll_id,"2"))
        {
            startActivity(new Intent(getApplicationContext(),TeacherDashBoardActivity.class));
        }else if(StringUtils.equalsIgnoreCase(roll_id,"4"))

        {
            startActivity(new Intent(getApplicationContext(),StudentDashboardActivity.class));
        }
        else if(StringUtils.equalsIgnoreCase(roll_id,"3"))

        {
            startActivity(new Intent(getApplicationContext(),ParentDashBoardActivity.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginButton:
                validator.validate();
                break;
            case R.id.btnDownloadManual:
                downloadManual();
                break;
            case R.id.closeButton1:
                relativeLayout.setVisibility(View.GONE);
                closeButton1.setVisibility(View.GONE);
                pdfView.setVisibility(View.GONE);
            case R.id.closeButton:
                finish();
                break;
        }

    }
    private void downloadManual(){
        File fileBrochure = new File(Environment.getExternalStorageDirectory() + "/" + "AppManual.pdf");
        if (!fileBrochure.exists())
        {
            CopyAssetsbrochure();
        }
        closeButton1.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        /** PDF reader code */
        pdfView.setVisibility(View.VISIBLE);
        Uri uri =  Uri.parse( "https://www.dropbox.com/s/8yz3zq0y6619n99/AppManual.pdf?dl=0" );

        File file = new File(Environment.getExternalStorageDirectory() + "/" + "AppManual.pdf");
        pdfView.fromAsset("AppManual.pdf").load();

        //ToastUtils.showShort("download btn pressed");
    }
    @Override
    public void onBackPressed() {
        // your code.
        relativeLayout.setVisibility(View.GONE);
        closeButton1.setVisibility(View.GONE);
        pdfView.setVisibility(View.GONE);
    }
    @Override
    public void onValidationSucceeded() {
        String username = emailTF.getText().toString();
        String password = passwordTF.getText().toString();
        loginWS(username,password);
        //ToastUtils.showShort("Validation Successful");

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                ToastUtils.showShort(message);
            }
        }
    }

    //method to write the PDFs file to sd card
    private void CopyAssetsbrochure() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try
        {
            files = assetManager.list("");
        }
        catch (IOException e)
        {
            Log.e("tag", e.getMessage());
        }
        for(int i=0; i<files.length; i++)
        {
            String fStr = files[i];
            if(fStr.equalsIgnoreCase("AppManual.pdf"))
            {
                InputStream in = null;
                OutputStream out = null;
                try
                {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                }
                catch(Exception e)
                {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    private void getProfileData(){
        {
            //showProgressUI(AppConstants.Loading);
            String wsLink = AppConstants.BaseURL+"Profile";
            //web method call
            JSONObject loginJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            String roll_id;
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            roll_id = sharedPref.getString("roll_id", null); // getting String

            String user_id = SPUtils.getInstance().getString("user_id");
            try {
                loginJson.put("role_id",roll_id );
                loginJson.put("user_id", user_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(loginJson);
            JSONObject ws_dataObj = new JSONObject();
            try {
                ws_dataObj.put("WS_DATA", jsonArray);
                ws_dataObj.put("WS_CODE", "110");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json = ws_dataObj.toString();
            ServerComHandler.getInstance().wsCallJsonBy(wsLink,json, new IWSCallHandler() {
                @Override
                public void responseStatus(final int status, final Object data) {
                    if (status == 200) {
                        // List<NotificationItem> arrTmp = new ArrayList<>();
                        try{
                            ResponseStatus responseStatus = new ResponseStatus((String)data);
                            if (responseStatus.isSuccess()) {
                                dismissProgressUI();
                                JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                                JSONObject jsonObjItm = jsonArray.getJSONObject(0);
                                Gson gson = new Gson();
                                String userJson = gson.toJson(jsonObjItm);
                                final ProfilePojoClass profileObject = new ProfilePojoClass();
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("username", responseStatus.parseName());
                                editor.commit();
                                profileObject.setName(responseStatus.parseName());
                                profileObject.setDob(responseStatus.parseDOB());
                                profileObject.setGender(responseStatus.parseGender());

                                Log.d("Json is ","jsonObjItm is"+jsonObjItm);
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissProgressUI();
                                        navigate();

                                    }
                                });
                            }
                            else {
                                ToastUtils.showShort(responseStatus.response_message);
                                dismissProgressUI();
                            }
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                }
                            });
                        }catch (Exception ex){ex.printStackTrace();}


                    }else{
                        Log.d("Webservice","failed");
                        dismissProgressUI();
                    }
                }
            });

        }
    }

    private void loginWS(String username, String password)
    {
        showProgressUI(AppConstants.Loading);
        String wsLink = AppConstants.BaseURL+"Login";
        //web method call
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
        loginJson.put("username", username);
        loginJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(loginJson);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            ws_dataObj.put("WS_CODE", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = ws_dataObj.toString();
        ServerComHandler.getInstance().wsCallJsonBy(wsLink,json, new IWSCallHandler() {
            @Override
            public void responseStatus(final int status, final Object data) {
                if (status == 200) {
                    // List<NotificationItem> arrTmp = new ArrayList<>();
                    try{
                        ResponseStatus responseStatus = new ResponseStatus((String)data);
                        if (responseStatus.isSuccess()) {
                            dismissProgressUI();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                             if (jsonArray != null) {
                                 int count = jsonArray.length();
                                 JSONObject jsonObjItm = jsonArray.getJSONObject(0);
                                 user_id = responseStatus.parseUserId();
                                 roll_id = responseStatus.parseRollId();
                                 designation = responseStatus.parseUserType();
                                 SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                 SharedPreferences.Editor editor = sharedPref.edit();
                                 editor.putString("roll_id", roll_id);
                                 editor.commit();
                                 String roll = sharedPref.getString("roll_id", null); // getting String
                                 Log.d("Json is ", "jsonObjItm is" + jsonObjItm);
                             }
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // dismissProgressUI();

                                    getProfileData();
                                }
                            });
                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgressUI();
                            }
                        });
                    }catch (Exception ex){ex.printStackTrace();}


                }else{

                    Log.d("Webservice","failed");
                    dismissProgressUI();
                }
            }
        });

    }
}
