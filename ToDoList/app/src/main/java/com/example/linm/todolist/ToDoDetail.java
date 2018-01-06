package com.example.linm.todolist;

import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoDetail extends AppCompatActivity {


    private int typeE;
    private boolean tag1 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);

        final RadioGroup RG = (RadioGroup)findViewById(R.id.radiogroup);
        final RadioButton RB1 = (RadioButton)findViewById(R.id.ddl_type);
        final RadioButton RB2 = (RadioButton)findViewById(R.id.birthday_type);
        final RadioButton RB3 = (RadioButton)findViewById(R.id.buy_type);
        final RadioButton RB4 = (RadioButton)findViewById(R.id.travel_type);
        final EditText titleET = (EditText)findViewById(R.id.title);
        final EditText contentET = (EditText)findViewById(R.id.content);
        final EditText timeET = (EditText)findViewById(R.id.time);

        final TextView typeTV = (TextView)findViewById(R.id.type_tv);
        final TextView titleTV = (TextView)findViewById(R.id.title_tv);
        final TextView contentTV = (TextView) findViewById(R.id.content_tv);
        final TextView timeTV = (TextView)findViewById(R.id.time_tv);
        final TextView setuptimeTV = (TextView)findViewById(R.id.setuptime);

        final Button button = (Button)findViewById(R.id.button);

        int id = getIntent().getIntExtra("id",0);
        MyDB db = new MyDB(getBaseContext());
        Cursor cursor = db.selectbyid(id);


        if(cursor.moveToFirst()){
            typeE = cursor.getInt(2);
            titleET.setText(cursor.getString(1));
            contentET.setText(cursor.getString(3));
            timeET.setText(cursor.getString(4));
            titleTV.setText(cursor.getString(1));
            contentTV.setText(cursor.getString(3));
            timeTV.setText(cursor.getString(4));
            setuptimeTV.setText(cursor.getString(5));
        }

        final int updateid = id;


        switch (typeE){
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

        //编辑或保存按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑模式
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
                    db.update(updateid,titleET.getText().toString(),typeE,contentET.getText().toString(),timeET.getText().toString(),str);
                    setResult(98, new Intent());

                }
            }
        });

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){
                if(checkedID == RB1.getId()) {
                    typeE = R.mipmap.ddlfortype;
                }
                else if(checkedID == RB2.getId()){
                    typeE = R.mipmap.birthday;
                }
                else if(checkedID == RB3.getId()){
                    typeE = R.mipmap.buy;
                }
                else if(checkedID == RB4.getId()){
                    typeE = R.mipmap.travel;
                }

            };
        });

    }
}
