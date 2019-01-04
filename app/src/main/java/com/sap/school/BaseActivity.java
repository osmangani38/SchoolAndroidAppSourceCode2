package com.sap.school;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog progressDialog;
    public View rootView;

    static final String EXTRA_SAMPLE = "sample";
    static final String EXTRA_TYPE = "type";
    static final int TYPE_PROGRAMMATICALLY = 0;
    static final int TYPE_XML = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCancelable(false);

        //this.progressDialog.setCancelable(false);
//        this.progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

//        try {
//            this.progressDialog.setContentView(R.layout.dialog_del_prod);
//            //GifView pGif = progressDialog.findViewById(R.id.progress_bar);
//            //pGif.setImageResource(R.drawable.loading_blue);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        this.progressDialog.getWindow().clearFlags(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
//        dismissKeyboard();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    public Activity getCurrentContext()
    {
        return this;
    }

    public Activity getCurrContext()
    {
        return this;
    }

    //setVisibilityBy
    public void setVisibilityBy(int id, int vstatus)
    {
        View v = findViewById(id);
        v.setVisibility(vstatus);
    }


    //setVisibilityState
    public void setVisibilityState(View v, int vstatus)
    {
        v.setVisibility(vstatus);
    }

    /**
     * Set the click listener for a View.
     *
     * @param id
     *            the id of view
     * @return the view
     */
    public View setClick(int id)
    {
        View v = findViewById(id);
//        if(v != null)
        v.setOnClickListener(this);
        return v;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

    }

    //closeActivity
    public void closeActivity()
    {
        //dismissKeyboard();
        //onBackPressed();
        finish();
    }


    public void showProgressUI(String msgTxt) {
        if (this.progressDialog != null) {
            this.progressDialog.setMessage(msgTxt);
            this.progressDialog.show();
        }
    }

    public void dismissProgressUI() {
        try {
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //getResStringBy
    public String getResStringBy(int resId)
    {
        return getResources().getString(resId);
    }

    private void buildUI() {
    }

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(5);
        }
    }

    //public void dismissKeyboard() {
    //    AppUtility.hideKeybaordWindow(this);
    //}

    public void showSuccessMessage(final String titleStr, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // AppUtility.showMessageDialog(BaseActivity.this, titleStr, msg);
            }
        });
    }

    public void showErrMessage(final String titleStr, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //DialogUtil.showMsgDialogWith(BaseActivity.this, msg, AppConstants.MSG_DIALOG_TYPE_ERROR, null);
                //AppUtility.showMessageDialog(BaseActivity.this, titleStr, msg);
            }
        });
    }

    public void showErrMessage(final int error_type, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //DialogUtil.showMsgDialogWith(BaseActivity.this, msg, error_type, null);
//                AppUtility.showMessageDialog(BaseActivity.this, titleStr, msg);
            }
        });
    }
}
