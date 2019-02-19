package com.sap.school;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.sap.school.Fragment.RoutinFragment;
import com.sap.utils.AppConstants;

public class RoutinScreenActivity extends BaseActivity implements View.OnClickListener{
    ViewPager viewPager;
    TextView classTextView,sectionTextView;
    TabLayout tabLayout;
    AlertDialog ad;
    String classId = "6";
    String sectionId = "2";
    public String selectedDay;
    ViewPagerAdapter viewPagerAdapter;
    FragmentManager manager;
    RelativeLayout backButton;
    LinearLayout selectClassButton,selectSectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routin_screen);
        initView();
        selectedDay = "1";
        setListner();
        SPUtils.getInstance().put("day",selectedDay);
    }
    private void SelectSection() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoutinScreenActivity.this);
        LayoutInflater inflater = LayoutInflater.from(RoutinScreenActivity.this);
        final View convertView = (View) inflater.inflate(R.layout.custom_popup_select_class_page_item, null);
        //Button goToDashBoardButton,makeOtherPlanButton;

        alertDialog.setView(convertView);
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (ad.isShowing()) {
                    ad.dismiss();
                }
            }
        };
        final RadioButton sectionA = (RadioButton) convertView
                .findViewById(R.id.radioButtonSection1);
        sectionA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sectionId = "1";
                SPUtils.getInstance().put("section",sectionId);
                sectionTextView.setText("Sec-"+"A");
                handler.postDelayed(runnable, 1000);
            }
        });        alertDialog.setView(convertView);
        final RadioButton sectionB = (RadioButton) convertView
                .findViewById(R.id.radioButtonSection2);
        sectionB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sectionId = "2";
                SPUtils.getInstance().put("section",sectionId);
                sectionTextView.setText("Sec-"+"B");
                handler.postDelayed(runnable, 1000);
            }
        });        alertDialog.setView(convertView);

        final RadioButton sectionC = (RadioButton) convertView
                .findViewById(R.id.radioButtonSection3);
        sectionC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sectionId = "3";
                SPUtils.getInstance().put("section",sectionId);
                sectionTextView.setText("Sec-"+"C");
                handler.postDelayed(runnable, 1000);
            }
        });        alertDialog.setView(convertView);

        final RadioButton sectionD = (RadioButton) convertView
                .findViewById(R.id.radioButtonSection4);
        sectionD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sectionId = "4";
                SPUtils.getInstance().put("section",sectionId);
                sectionTextView.setText("Sec-"+"D");
                handler.postDelayed(runnable, 1000);
            }
        });

        // create alert dialog

         ad = alertDialog.show();
    }
    private void SelectClass() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoutinScreenActivity.this);
        LayoutInflater inflater = LayoutInflater.from(RoutinScreenActivity.this);
        final View convertView = (View) inflater.inflate(R.layout.custom_popup_select_section_page_item, null);
        //Button goToDashBoardButton,makeOtherPlanButton;

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (ad.isShowing()) {
                    ad.dismiss();
                }
            }
        };
        final RadioButton classV = (RadioButton) convertView
                .findViewById(R.id.classV);
        classV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                classId = "6";
                SPUtils.getInstance().put("class",classId);
                classTextView.setText("Class-"+"V");
                refressDataFragment();
                handler.postDelayed(runnable, 1000);
            }
        });        alertDialog.setView(convertView);
        final RadioButton classVI = (RadioButton) convertView
                .findViewById(R.id.classV1);
        classVI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                classId = "7";
                SPUtils.getInstance().put("class",classId);
                classTextView.setText("Class-"+"VI");
                refressDataFragment();
                handler.postDelayed(runnable, 1000);
            }
        });        alertDialog.setView(convertView);

        final RadioButton classVII = (RadioButton) convertView
                .findViewById(R.id.classVII);
        classVII.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                classId = "8";
                SPUtils.getInstance().put("class",classId);
                classTextView.setText("Class-"+"VII");
                refressDataFragment();
                handler.postDelayed(runnable, 1000);
            }
        });        alertDialog.setView(convertView);

        final RadioButton classVIII = (RadioButton) convertView
                .findViewById(R.id.classVIII);
        classVIII.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                classId = "9";
                SPUtils.getInstance().put("class",classId);
                classTextView.setText("Class-"+"VIII");
                refressDataFragment();
                handler.postDelayed(runnable, 1000);
            }
        });
        final RadioButton classIX = (RadioButton) convertView
                .findViewById(R.id.classIX);
        classIX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                classId = "10";
                SPUtils.getInstance().put("class",classId);
                classTextView.setText("Class-"+"IX");
                refressDataFragment();
                handler.postDelayed(runnable, 1000);
            }
        });
        final RadioButton classX = (RadioButton) convertView
                .findViewById(R.id.classX);
        classX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                classId = "11";
                SPUtils.getInstance().put("class",classId);
                classTextView.setText("Class-"+"X");
                handler.postDelayed(runnable, 1000);
            }
        });
        ad = alertDialog.show();
    }
    private void refressDataFragment(){
        Fragment frg = null;
        frg = getSupportFragmentManager().getFragments().get(0);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();    }

    private void setListner() {
        backButton.setOnClickListener(this);
        selectClassButton.setOnClickListener(this);
        selectSectionButton.setOnClickListener(this);
    }

    private void initView() {
        backButton=(RelativeLayout)findViewById(R.id.backButton);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        manager=getSupportFragmentManager();
        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        viewPagerAdapter=new ViewPagerAdapter(manager);
        //This code is used to visible title
        //set Adapter to view pager
        viewPager.setAdapter(viewPagerAdapter);
        //set tablayout with viewpager
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager( viewPager);
        selectClassButton=(LinearLayout)findViewById(R.id.selectClassButton);
        selectSectionButton=(LinearLayout)findViewById(R.id.selectSectionButton);
        classTextView = (TextView) findViewById(R.id.classTextView);
        sectionTextView = (TextView) findViewById(R.id.sectionTextV);
        String roll_id;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( RoutinScreenActivity.this);
        roll_id = sharedPref.getString("roll_id", null); // getting String
        if (roll_id.equals("4")) {
            selectClassButton.setVisibility(View.GONE);
            selectSectionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
                break;
            case R.id.selectClassButton:
                SelectClass();
                break;
            case R.id.selectSectionButton:
                SelectSection();
                break;
        }

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag=null;
            Bundle bundle;
            switch (position){
                case 0:
                    selectedDay = "1";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    bundle = new Bundle();
                    bundle.putInt("day", Integer.parseInt(selectedDay));
                    frag.setArguments(bundle);
                    break;
                case 1:
                    selectedDay = "2";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    bundle = new Bundle();
                    bundle.putInt("day", Integer.parseInt(selectedDay));
                    frag.setArguments(bundle);
                    break;
                case 2:
                    selectedDay = "3";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    bundle = new Bundle();
                    bundle.putInt("day", Integer.parseInt(selectedDay));
                    frag.setArguments(bundle);
                    break;
                case 3:
                    selectedDay = "4";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    bundle = new Bundle();
                    bundle.putInt("day", Integer.parseInt(selectedDay));
                    frag.setArguments(bundle);
                    break;
                case 4:
                    selectedDay = "5";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    bundle = new Bundle();
                    bundle.putInt("day", Integer.parseInt(selectedDay));
                    frag.setArguments(bundle);
                    break;
                case 5:
                    selectedDay = "6";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    bundle = new Bundle();
                    bundle.putInt("day", Integer.parseInt(selectedDay));
                    frag.setArguments(bundle);
                    break;
                /*case 6:
                    selectedDay = "6";
                    SPUtils.getInstance().put("day",selectedDay);
                    frag=new RoutinFragment();
                    break;*/
            }

            return frag;
        }

        @Override
        public int getCount() {
            return 6;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            String title=" ";
            switch (position){
                case 0:
                    selectedDay = "1";
                    SPUtils.getInstance().put("day",selectedDay);
                    title="Monday";
                    break;
                case 1:
                    selectedDay = "2";
                    SPUtils.getInstance().put("day",selectedDay);
                    title="Tuesday";
                    break;
                case 2:
                    selectedDay = "3";
                    SPUtils.getInstance().put("day",selectedDay);
                    title="Wednesday";
                    break;
                case 3:
                    selectedDay = "4";
                    SPUtils.getInstance().put("day",selectedDay);
                    title="Thursday";
                    break;
                case 4:
                    selectedDay = "5";
                    SPUtils.getInstance().put("day",selectedDay);
                    title="Friday";
                    break;
                case 5:
                    selectedDay = "6";
                    SPUtils.getInstance().put("day",selectedDay);
                    title="Saturday";
                    break;
            }
            return title;
        }
    }
}
