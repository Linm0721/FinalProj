package com.example.linm.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

public class AddTodolist extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup RG;
    private RadioButton RB1;
    private RadioButton RB2;
    private RadioButton RB3;
    private RadioButton RB4;
    private RadioButton RB5;
    private EditText Title;
    private EditText Content;
    private TextView Date;
    private TextView Clock;
    private Button AddButton;
    private int type;

    private Button showdailog;
    private Button time;
    private String dyear="";
    private String dmonth="";
    private String dday="";
    private String dhour="";
    private String dminute="";
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private tpDialog timePickerDialog;

    private int typeE;
    private int mode = 0;//0为添加模式，1为编辑模式

    void init(){
        RG = (RadioGroup)findViewById(R.id.radiogroup);
        RB1 = (RadioButton)findViewById(R.id.ddl_type);
        RB2 = (RadioButton)findViewById(R.id.birthday_type);
        RB3 = (RadioButton)findViewById(R.id.buy_type);
        RB4 = (RadioButton)findViewById(R.id.travel_type);
        RB5 = (RadioButton)findViewById(R.id.others_type);
        Title = (EditText)findViewById(R.id.title);
        Content = (EditText)findViewById(R.id.content);
        Date = (TextView)findViewById(R.id.date);
        Clock = (TextView)findViewById(R.id.clock);
        AddButton = (Button)findViewById(R.id.add);
        type = R.mipmap.ddl;
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        Log.e("debug","datetolong:"+date.getTime()+"");
        return date.getTime();
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todolist);
        init();

        Date.setOnClickListener(this);
        Clock.setOnClickListener(this);

        //判断模式
        mode = 0;
        final int id = getIntent().getIntExtra("id",-1);
        if(id!=-1){
            mode = 1;
        }
        if(mode==1){
            MyDB db = new MyDB(getBaseContext());
            Cursor cursor = db.selectbyid(id);
            if(cursor.moveToFirst()){
                typeE = cursor.getInt(2);
                Title.setText(cursor.getString(1));
                Content.setText(cursor.getString(3));
                String sdate="";
                try{
                    sdate = longToString(cursor.getLong(4),"yyyy-MM-dd");
                }catch (ParseException p){
                    Log.e("debug","很无情，日期转换失败");
                }
                Date.setText(sdate);
                String sclock="";
                try{
                    sclock = longToString(cursor.getLong(4),"HH:mm");
                }catch (ParseException p){
                    Log.e("debug","啊呀，时间转换也失败了TOT");
                }
                Clock.setText(sclock);

            }
            switch (typeE){
                case R.mipmap.ddlfortype:{

                    RB1.setChecked(true);
                    Log.e("debug","原先是ddl");
                    break;
                }
                case R.mipmap.birthday:{

                    RB2.setChecked(true);
                    Log.e("debug","原先是生日");
                    break;
                }
                case R.mipmap.buy:{

                    RB3.setChecked(true);
                    Log.e("debug","原先是购物");
                    break;
                }
                case R.mipmap.travel:{

                    RB4.setChecked(true);
                    Log.e("debug","原先是出行");
                    break;
                }
                case R.mipmap.others:{
                    RB5.setChecked(true);
                    break;
                }
            }
        }




        //类型选择
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){

                if(checkedID == RB1.getId()) {
                    Toast.makeText(AddTodolist.this,"选择了ddl",Toast.LENGTH_SHORT).show();
                    type = R.mipmap.ddlfortype;
                }
                else if(checkedID == RB2.getId()){
                    Toast.makeText(AddTodolist.this,"选择了生日",Toast.LENGTH_SHORT).show();
                    type = R.mipmap.birthday;
                }
                else if(checkedID == RB3.getId()){
                    Toast.makeText(AddTodolist.this,"选择了购物",Toast.LENGTH_SHORT).show();
                    type = R.mipmap.buy;
                }
                else if(checkedID == RB4.getId()){
                    Toast.makeText(AddTodolist.this,"选择了出行",Toast.LENGTH_SHORT).show();
                    type = R.mipmap.travel;
                }
                else if(checkedID == RB5.getId()){
                    Toast.makeText(AddTodolist.this,"选择了其它",Toast.LENGTH_SHORT).show();
                    type = R.mipmap.others;
                }

            };
        }
        );

        //添加
        AddButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String title = Title.getText().toString();
                String content = Content.getText().toString();
                long time = 0;
                String stime = Date.getText().toString() + " " + Clock.getText().toString();
                try{
                    time = stringToLong(stime,"yyyy-MM-dd HH:mm");
                }catch (ParseException p){
                    Log.e("debug","ddl转换错误");
                }
                SimpleDateFormat  formatter  =  new  SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String  str = formatter.format(curDate);
                //名字为空
                if(title.equals("")){
                    Toast.makeText(AddTodolist.this, "备忘不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    MyDB db = new MyDB(getBaseContext());

                        if(mode==1){
                            db.update(id,Title.getText().toString(),type,Content.getText().toString(),time,str);
                            setResult(98, new Intent());
                            finish();
                        }
                        else{
                            db.insert(title,type,content,time,str);
                            setResult(99, new Intent());
                            finish();
                        }


                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                showDailog();
                break;
            case R.id.clock:
                showTime();
                break;
        }
    }



    private void showDailog() {

        Calendar cal = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(this,  null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                dyear = String.valueOf(year);
                dmonth = String.valueOf(month + 1);
                dday = Integer.toString(day);
                Date.setText(dyear+"-"+dmonth+"-"+dday);
                System.out.println(year + "," + month + "," + day);
            }
        });
        //取消按钮，如果不需要直接不设置即可
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("BUTTON_NEGATIVE~~");
            }
        });
        DatePicker datePicker = mDialog.getDatePicker();
        datePicker.setCalendarViewShown(true);
        datePicker.setSpinnersShown(false);
        mDialog.show();


    }

    private int hourtemp ;
    private int minutetemp ;

    private void showTime() {

        final Calendar cal = Calendar.getInstance();
        timePickerDialog = new tpDialog(this, new tpDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.e("debug", Integer.toString(hourOfDay));
                Log.e("debug", Integer.toString(minute));
                dhour = Integer.toString(hourOfDay);
                dminute = Integer.toString(minute);
                Clock.setText(dhour+":"+dminute);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



    }
}

class tpDialog extends TimePickerDialog{

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //super.onStop();
    }
    public tpDialog(Context context, OnTimeSetListener callBack,
                    int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
        // TODO Auto-generated constructor stub

    }
}
