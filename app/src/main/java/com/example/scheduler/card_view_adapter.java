package com.example.scheduler;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class card_view_adapter extends RecyclerView.Adapter<card_view_adapter.MyviewHolder> {


    protected ArrayList<schedule> arraylist;
    Context mCtx;
    int day;

    public class MyviewHolder extends RecyclerView.ViewHolder{
        public TextView subject,teacher,room,time,duration,type;
        public ImageButton button;
        public MyviewHolder(View itemView, Context context) {
            super(itemView);
            mCtx=context;
            subject= itemView.findViewById(R.id.subject);
            teacher= itemView.findViewById(R.id.teacher);
            room= itemView.findViewById(R.id.room);
            time= itemView.findViewById(R.id.time1);
            duration= itemView.findViewById(R.id.duration);
            button= itemView.findViewById(R.id.imagebutton);
            type= itemView.findViewById(R.id.type);
        }
    }
    public card_view_adapter(ArrayList<schedule> arraylist,int position)
    {
        this.arraylist=arraylist;
        this.day=position;
    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.card_design,parent,false);
        return new MyviewHolder(v,v.getContext());
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, int position) {
        schedule s= arraylist.get(position);
        holder.subject.setText(s.subject);
        holder.teacher.setText(s.teacher);
        holder.room.setText(s.room);
        holder.time.setText(s.time);
        holder.type.setText(s.type);
        holder.duration.setText(s.getDuration());
        final String teacher=s.teacher;
        final String subject=s.subject;
        final String room=s.room;
        int i=getIndex(s.time);
        final String type=s.type;
       final String from=s.time.substring(0,i);
       final String to=s.time.substring(i+1);
        holder.button.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(mCtx,holder.button);
            popup.inflate(R.menu.options_menu);
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if(id == R.id.menu2){
                    delete(teacher,subject,room,from,to,day,position);
                }
                return false;
            });
            popup.show();
        });
    }

    private int getIndex(String s) {
        int i;
        for(i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='-')
            {
                break;
            }
        }
        return i;
    }
    public void delete(String teacher,String subject,String room,String from,String to,int day,int position)
    {
           String where=Data.TEACHER+" = ? AND " +
              Data.SUBJECT+" = ? AND "+
                Data.ROOM+" = ? AND "+
                Data.FROM+" = ? AND "+
                Data.TO+" = ? AND "+
                Data.DAY+" = ? ";
        String[] s={teacher,subject,room,from,to,""+day};
        Log.d("utkarsh",teacher+" "+subject+" "+room+" "+from+" "+to+" "+day);
        Cursor c= mCtx.getContentResolver().query(Data.CONTENT_URI,null,where,s,null);
        if(c!=null&&c.getCount()!=0)
        {
            mCtx.getContentResolver().delete(Data.CONTENT_URI,where,s);
            arraylist.remove(position);
            notifyDataSetChanged();
        }
        else
            Toast.makeText(mCtx,"no delete",Toast.LENGTH_SHORT).show();
        c.close();
    }
    @Override
    public int getItemCount() {
        return arraylist.size() ;
    }


}





