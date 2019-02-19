package com.sap.school.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sap.handler.IWSCallHandler;
import com.sap.handler.ResponseStatus;
import com.sap.handler.ServerComHandler;
import com.sap.school.AttendenceScreenActivity;
import com.sap.school.LoginActivity;
import com.sap.school.PojoClass.AttendancePogoClass;
import com.sap.school.PojoClass.ClassRoutinePojoClass;
import com.sap.school.PojoClass.StudentInfoPojoClass;
import com.sap.school.R;
import com.sap.school.RoutinScreenActivity;
import com.sap.school.StudentScreenActivity;
import com.sap.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinFragment extends Fragment {
    View view;
    private ProgressDialog progressDialog;
    int selectedDays;
    RecyclerView routinRecyclerView;
    RoutineRecyclerViewAdapter routineRecyclerViewAdapter;
    ArrayList<ClassRoutinePojoClass> classRoutinePojoClassArrayList;
    public RoutinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_routin, container, false);
        this.progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.setCancelable(false);
        initView();
        croutinInfo();
        selectedDays = getArguments().getInt("day", 0);
        String user_id = SPUtils.getInstance().getString("user_id");
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( getActivity());
        roll_id = sharedPref.getString("roll_id", null); // getting String
        getClassRoutine(user_id,roll_id);
        return view;
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

    private void getClassRoutine(String user_id, String role_id)
    {
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( getActivity());
        roll_id = sharedPref.getString("roll_id", null); // getting String
        String wsLink = AppConstants.BaseURL+"TeacherRoutine";
        //web method call
        if (role_id.equals("4")) {
             wsLink = AppConstants.BaseURL+"ClassRoutine";
        }
        showProgressUI("Loading...");
        JSONObject loginJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String classId = SPUtils.getInstance().getString("class");
        String sectionId = SPUtils.getInstance().getString("section");
        if (StringUtils.isEmpty(classId)) {
            classId = "6";
        }
        if (StringUtils.isEmpty(sectionId)) {
            sectionId = "2";
        }
        try {
            loginJson.put("user_id", user_id);
            loginJson.put("role_id", role_id);
            if (role_id.equals("2")) {
                loginJson.put("class_id", classId);
                loginJson.put("section_id", sectionId);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(loginJson);
        JSONObject ws_dataObj = new JSONObject();
        try {
            ws_dataObj.put("WS_DATA", jsonArray);
            if (role_id.equals("4")) {
                ws_dataObj.put("WS_CODE", "230");
            }
            else {
                ws_dataObj.put("WS_CODE", "130");
            }
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
                           // dismissProgressUI();
                            final ArrayList mArrayList=new ArrayList<>();
                            JSONArray jsonArray = responseStatus.jsonObject.getJSONArray("result");
                            int count = jsonArray.length();
                            if (jsonArray !=null) {
                                for (int i = 0; i < count; i++) {
                                    JSONObject jsonObjItm = jsonArray.getJSONObject(i);
                                    Log.d("Json is ", "jsonObjItm is" + jsonObjItm);
                                    String classSection = "Class - " + jsonObjItm.getString("class") + " | " + "Section - " + jsonObjItm.getString("section");
                                    String day = String.valueOf(selectedDays);//SPUtils.getInstance().getString("day");
                                    Log.d("day is ", "day is " + day);
                                    if (day.equals(jsonObjItm.getString("day_id"))) {
                                        mArrayList.add(new ClassRoutinePojoClass(jsonObjItm.getString("subject"), classSection, jsonObjItm.getString("period_time")));
                                    }
                                }
                            }
                            else {
                                ToastUtils.showShort("Data not found");
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                    update(mArrayList);

                                }
                            });
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressUI();
                                }
                            });
                        }
                        else {
                            ToastUtils.showShort(responseStatus.response_message);
                            dismissProgressUI();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgressUI();
                            }
                        });
                    }catch (Exception ex){ex.printStackTrace();}


                }else{
                    Log.d("Webservice","failed");
                   // dismissProgressUI();
                }
            }
        });

    }
    public void update(ArrayList<ClassRoutinePojoClass> data) {
        classRoutinePojoClassArrayList.clear();
        classRoutinePojoClassArrayList.addAll(data);
        routinRecyclerView.removeAllViews();
        routinRecyclerView.invalidate();
    }

    private void croutinInfo() {
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("Mathematics","Class - V | Section - B","11am - 11:45am"));
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("Hindi","Class - V | Section - B","11am - 11:45am"));
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("English","Class - V | Section - B","11am - 11:45am"));
//        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("Life Science","Class - V | Section - B","11am - 11:45am"));
        routineRecyclerViewAdapter=new RoutineRecyclerViewAdapter(getActivity(),classRoutinePojoClassArrayList);
        routinRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        routinRecyclerView.setItemAnimator(new DefaultItemAnimator());
        routinRecyclerView.setAdapter(routineRecyclerViewAdapter);
    }

    private void initView() {
        routinRecyclerView=(RecyclerView)view.findViewById(R.id.routinRecyclerView);
        classRoutinePojoClassArrayList=new ArrayList<>();

    }

    private class RoutineRecyclerViewAdapter extends RecyclerView.Adapter<RoutineRecyclerViewAdapter.MyViewHolderClass>{
        Context context;ArrayList<ClassRoutinePojoClass> classRoutinePojoClassArrayList;
        public RoutineRecyclerViewAdapter(Context context, ArrayList<ClassRoutinePojoClass> classRoutinePojoClassArrayList) {
        this.context=context;this.classRoutinePojoClassArrayList=classRoutinePojoClassArrayList;
        }

        @NonNull
        @Override
        public RoutineRecyclerViewAdapter.MyViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_routin_page, parent, false);
            return new RoutineRecyclerViewAdapter.MyViewHolderClass(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RoutineRecyclerViewAdapter.MyViewHolderClass holder, int position) {
            holder.subjectTextView.setText(classRoutinePojoClassArrayList.get(position).getSubject());
            holder.sectionTextView.setText(classRoutinePojoClassArrayList.get(position).getClassInfo());
            holder.timingTextView.setText(classRoutinePojoClassArrayList.get(position).getTiming());
        }

        @Override
        public int getItemCount() {
            return classRoutinePojoClassArrayList.size();
        }

        public class MyViewHolderClass extends RecyclerView.ViewHolder {
            TextView subjectTextView,sectionTextView,timingTextView;
            LinearLayout linearLayout;
            public MyViewHolderClass(@NonNull View itemView) {
                super(itemView);
                subjectTextView=(TextView)itemView.findViewById(R.id.subjectTextView);
                sectionTextView=(TextView)itemView.findViewById(R.id.sectionTextView);
                timingTextView=(TextView)itemView.findViewById(R.id.timingTextView);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            }
        }
    }
}
