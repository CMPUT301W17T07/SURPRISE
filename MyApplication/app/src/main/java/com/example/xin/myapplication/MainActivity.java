package com.example.xin.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
    public final static int NOTIFICATION_ID = "NotificationActivityDemo".hashCode();
    Button button;
    //以dialog形式展现
    AlertDialog TimeDialog;
    AlertDialog DateDialog;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinte = calendar.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn);

        innit();
    }
    private void innit() {
        final View dateView = View.inflate(getApplicationContext(),R.layout.datepicker,null);
        final View timeView = View.inflate(getApplicationContext(),R.layout.timepicker,null);
        TimePicker timePicker = (TimePicker) timeView.findViewById(R.id.time);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                currentHour = hourOfDay;
                currentMinte = minute;
            }
        });
        DatePicker datePicker = (DatePicker) dateView.findViewById(R.id.pick);
        datePicker.setDrawingCacheBackgroundColor(getResources().getColor(R.color.colorAccent));
        datePicker.init(currentYear, currentMonth, currentDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentYear = year;
                currentMonth = monthOfYear;
                currentDay = dayOfMonth;
            }
        });
        DateDialog = new AlertDialog.Builder(MainActivity.this)
                .setView(dateView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,currentYear+"年"+currentMonth+"月"+currentDay+"日"+currentHour+"时"+currentMinte+"分",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateDialog.dismiss();
                    }
                })
                .create();
        TimeDialog = new AlertDialog.Builder(MainActivity.this)
                .setView(timeView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateDialog.show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TimeDialog.dismiss();
                    }
                })
                .create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog.show();
            }
        });
    }
}