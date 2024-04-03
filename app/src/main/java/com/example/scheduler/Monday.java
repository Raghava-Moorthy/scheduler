package com.example.scheduler;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class Monday extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<schedule> scheduleArrayList;
    private card_view_adapter adapter;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.monday, container, false);
        context=getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        scheduleArrayList = new ArrayList<>();
        adapter = new card_view_adapter(scheduleArrayList,0);
        recyclerView.setAdapter(adapter);
        prepareSchedule(context);
        return recyclerView;

    }

    @Override
    public void onResume() {
        super.onResume();
        scheduleArrayList.clear();
        prepareSchedule(context);
        adapter.notifyDataSetChanged();
    }

    private void prepareSchedule(Context co) {
        final String CONDI =" day = '0' ";
        Cursor cc=co.getContentResolver().query(Data.CONTENT_URI,null,CONDI,null,null);
        if(cc!=null && cc.getCount()!=0)
        {
            schedule s=null;
            while(cc.moveToNext()) {
                String teacher = cc.getString(cc.getColumnIndex(Data.TEACHER));
                String type=cc.getString(cc.getColumnIndex(Data.TYPE));
                String subject = cc.getString(cc.getColumnIndex(Data.SUBJECT));
                String room = cc.getString(cc.getColumnIndex(Data.ROOM));
                String from = cc.getString(cc.getColumnIndex(Data.FROM));
                String to = cc.getString(cc.getColumnIndex(Data.TO));
                String time = from + "-" + to;
                s = new schedule(subject, teacher, room, time,type);
                scheduleArrayList.add(s);
            }
        }
    }
}



