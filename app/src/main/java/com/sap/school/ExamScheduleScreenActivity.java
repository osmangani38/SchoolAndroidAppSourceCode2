package com.sap.school;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.sap.school.Fragment.ExamScheduleFragment;

public class ExamScheduleScreenActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    FragmentManager manager;
    RelativeLayout backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examschedule_screen);
        initView();
        setListner();
    }

    private void setListner() {
        backButton.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButton:
                finish();
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
            switch (position){
                case 0:
                    frag=new ExamScheduleFragment();
                    break;
                case 1:
                    frag=new ExamScheduleFragment();
                    break;
                case 2:
                    frag=new ExamScheduleFragment();
                    break;
                case 3:
                    frag=new ExamScheduleFragment();
                    break;
                case 4:
                    frag=new ExamScheduleFragment();
                    break;
                case 5:
                    frag=new ExamScheduleFragment();
                    break;
                case 6:
                    frag=new ExamScheduleFragment();
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 7;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            String title=" ";
            switch (position){
                case 0:
                    title="Today";
                    break;
                case 1:
                    title="Tueday";
                    break;
                case 2:
                    title="Wednesday";
                    break;
                case 3:
                    title="Thursday";
                    break;
                case 4:
                    title="Friday";
                    break;
                case 5:
                    title="Saturday";
                    break;
            }

            return title;
        }
    }
}
