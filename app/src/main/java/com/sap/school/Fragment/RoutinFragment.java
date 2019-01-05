package com.sap.school.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sap.school.PojoClass.ClassRoutinePojoClass;
import com.sap.school.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinFragment extends Fragment {
    View view;
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
        initView();
        croutinInfo();


        return view;
    }

    private void croutinInfo() {
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("Mathematics","Class - V | Section - B","11am - 11:45am"));
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("Hindi","Class - V | Section - B","11am - 11:45am"));
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("English","Class - V | Section - B","11am - 11:45am"));
        classRoutinePojoClassArrayList.add(new ClassRoutinePojoClass("Life Science","Class - V | Section - B","11am - 11:45am"));
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
