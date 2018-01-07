package com.example.linm.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoDetail extends AppCompatActivity {


    private int typeE;
    private boolean tag1 = false;
    private int id=0;



    private TextView typeTV ;
    private TextView titleTV;
    private TextView contentTV ;
    private TextView timeTV;
    private TextView setuptimeTV ;

    private Button Editbutton;
    private Button Backbutton;
    private Button Deletbutton;


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

    public void acceptvalue(){
        MyDB db = new MyDB(getBaseContext());
        Cursor cursor = db.selectbyid(id);

        long time = 0;
        String stime="";

        if(cursor.moveToFirst()){
            typeE = cursor.getInt(2);

            time = cursor.getLong(4);
            try{
                stime = longToString(time,"yyyy-MM-dd HH:mm");
            }catch (ParseException p){

            }

            titleTV.setText(cursor.getString(1));
            contentTV.setText(cursor.getString(3));
            timeTV.setText(stime);
            setuptimeTV.setText(cursor.getString(5));
        }
        switch (typeE){
            case R.mipmap.ddlfortype:{
                typeTV.setText("ddl");

                Log.e("debug","原先是ddl");
                break;
            }
            case R.mipmap.birthday:{
                typeTV.setText("生日");

                Log.e("debug","原先是生日");
                break;
            }
            case R.mipmap.buy:{
                typeTV.setText("购物");

                Log.e("debug","原先是购物");
                break;
            }
            case R.mipmap.travel:{
                typeTV.setText("出行");

                Log.e("debug","原先是出行");
                break;
            }
            case R.mipmap.others:{
                typeTV.setText("其它");
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);



        typeTV = (TextView)findViewById(R.id.type_tv);
        titleTV = (TextView)findViewById(R.id.title_tv);
        contentTV = (TextView) findViewById(R.id.content_tv);
        timeTV = (TextView)findViewById(R.id.time_tv);
        setuptimeTV = (TextView)findViewById(R.id.setuptime);

        Editbutton = (Button)findViewById(R.id.edit);
        Backbutton = (Button)findViewById(R.id.back);
        Deletbutton = (Button)findViewById(R.id.delete);

        id = getIntent().getIntExtra("id",0);
        acceptvalue();
  /*      MyDB db = new MyDB(getBaseContext());
        Cursor cursor = db.selectbyid(id);

        long time = 0;
        String stime="";

        if(cursor.moveToFirst()){
            typeE = cursor.getInt(2);

            time = cursor.getLong(4);
            try{
                stime = longToString(time,"yyyy-MM-dd HH:mm");
            }catch (ParseException p){

            }

            titleTV.setText(cursor.getString(1));
            contentTV.setText(cursor.getString(3));
            timeTV.setText(stime);
            setuptimeTV.setText(cursor.getString(5));
        }
*/
        final int updateid = id;


    /*    switch (typeE){
            case R.mipmap.ddlfortype:{
                typeTV.setText("ddl");
                RB1.setChecked(true);
                Log.e("debug","原先是ddl");
                break;
            }
            case R.mipmap.birthday:{
                typeTV.setText("生日");
                RB2.setChecked(true);
                Log.e("debug","原先是生日");
                break;
            }
            case R.mipmap.buy:{
                typeTV.setText("购物");
                RB3.setChecked(true);
                Log.e("debug","原先是购物");
                break;
            }
            case R.mipmap.travel:{
                typeTV.setText("出行");
                RB4.setChecked(true);
                Log.e("debug","原先是出行");
                break;
            }
        }
*/


        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Deletbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder message = new AlertDialog.Builder(ToDoDetail.this);

                message.setMessage("是否删除该备忘");
                message.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除
                        MyDB db = new MyDB(getBaseContext());
                        db.delete(id);
                        setResult(98, new Intent());
                        finish();
                    }
                });
                message.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                message.create().show();
            }
        });

        //编辑或保存按钮
        Editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ToDoDetail.this, AddTodolist.class);
                intent.putExtra("id",updateid);
                startActivityForResult(intent, 9);




          /*      //编辑模式
                if (!tag1) {
                    button.setBackgroundResource(R.mipmap.correct);
                    tag1 = true;
                    RG.setVisibility(View.VISIBLE);
                    titleET.setVisibility(View.VISIBLE);
                    contentET.setVisibility(View.VISIBLE);
                    timeET.setVisibility(View.VISIBLE);
                    typeTV.setVisibility(View.GONE);
                    titleTV.setVisibility(View.GONE);
                    contentTV.setVisibility(View.GONE);
                    timeTV.setVisibility(View.GONE);

                }
                //显示
                else {
                    button.setBackgroundResource(R.mipmap.edit);
                    tag1 = false;
                    RG.setVisibility(View.GONE);
                    titleET.setVisibility(View.GONE);
                    contentET.setVisibility(View.GONE);
                    timeET.setVisibility(View.GONE);
                    switch (typeE){
                        case R.mipmap.ddlfortype:{
                            typeTV.setText("ddl");
                            break;
                        }
                        case R.mipmap.birthday:{
                            typeTV.setText("生日");
                            break;
                        }
                        case R.mipmap.buy:{
                            typeTV.setText("购物");
                            break;
                        }
                        case R.mipmap.travel:{
                            typeTV.setText("出行");
                            break;
                        }
                    }

                    typeTV.setVisibility(View.VISIBLE);
                    titleTV.setText(titleET.getText());
                    titleTV.setVisibility(View.VISIBLE);
                    contentTV.setText(contentET.getText());
                    contentTV.setVisibility(View.VISIBLE);
                    timeTV.setText(timeET.getText());
                    timeTV.setVisibility(View.VISIBLE);

                    MyDB db = new MyDB(getBaseContext());
                    Log.e("debug","保存修改");
                    SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    String  str = formatter.format(curDate);

                    long time = 0;
                    try{
                        time = stringToLong(timeET.getText().toString(),"yyyy-MM-dd HH:mm");
                    }catch (ParseException p){

                    }
                    db.update(updateid,titleET.getText().toString(),typeE,contentET.getText().toString(),time,str);
                    setResult(98, new Intent());

                }*/

            }



        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && (resultCode == 99 || resultCode==98)) {

            acceptvalue();
            setResult(98, new Intent());
        }
    }
}
