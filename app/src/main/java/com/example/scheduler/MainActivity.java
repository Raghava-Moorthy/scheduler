package com.example.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    TabLayout tabLayout;
    static String[] titles;
    public int pos;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        titles = res.getStringArray(R.array.days);  //Fetching the days name from the strings.xml

        onNotification();           //Method to set the notification

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);

        FragmentManager f = getSupportFragmentManager();
        myAdapter = new MyAdapter(this,titles);
        viewPager.setAdapter(myAdapter);
        new TabLayoutMediator(tabLayout,viewPager, (tab,position) -> tab.setText(titles[position])).attach();
//        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(7);         //It sets how much pages should be remained saved offscreen

        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        switch (dayLongName) {
            case "Monday":
                viewPager.setCurrentItem(0);
                break;
            case "Tuesday":
                viewPager.setCurrentItem(1);         //This switch checks which day of the week
                break;                                          // is today and which page is to show when open
            case "Wednesday":
                viewPager.setCurrentItem(2);       // the app
                break;
            case "Thursday":
                viewPager.setCurrentItem(3);
                break;
            case "Friday":
                viewPager.setCurrentItem(4);
                break;
            case "Saturday":
                viewPager.setCurrentItem(5);
                break;
            case "Sunday":
                viewPager.setCurrentItem(6);
                break;
            default:
                viewPager.setCurrentItem(0);
        }


    }

    public void Addtime(View view) {
        Intent i = new Intent(this, AddSchedule.class);
        Bundle b = new Bundle();                        //  This is the method which is called when the Floating
        b.putInt("ID", viewPager.getCurrentItem());      //  button is clicked
        i.putExtras(b);
        startActivity(i);
    }
    private void onNotification() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Notify", null);
        if (name == null) {
            name = "1";
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Notify", name);
            editor.commit();                                   //Here when for the first time
            //this method will be called then
            AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE); //an alarm will be set for
            Date dat = new Date();                              // 7:00 which will repeat after 24 hours
            Calendar cal_alarm = Calendar.getInstance();        //The alarm will not be created again once created
            Calendar cal_now = Calendar.getInstance();
            cal_now.setTime(dat);
            cal_alarm.setTime(dat);
            cal_alarm.set(Calendar.HOUR_OF_DAY, 7);
            cal_alarm.set(Calendar.MINUTE, 0);
            cal_alarm.set(Calendar.SECOND, 0);
            if (cal_alarm.before(cal_now)) {
                cal_alarm.add(Calendar.DATE, 1);
            }
            Intent myIntent = new Intent(this, MyBroadcastReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        }
    }

}

class MyAdapter extends FragmentStateAdapter {
    private final String[] title;
    public MyAdapter(FragmentActivity fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment f = null;
        if (position == 0) {
            f = new Monday();

        } else if (position == 1) {
            f = new Tuesday();
        } else if (position == 2) {
            f = new Wednesday();
        } else if (position == 3) {
            f = new Thrusday();
        } else if (position == 4) {
            f = new Friday();
        } else if (position == 5) {
            f = new Saturday();
        } else {
            f = new Sunday();
        }

        return f;
    }
    @Override
    public int getItemCount() {
        return MainActivity.titles.length;
    }

}
