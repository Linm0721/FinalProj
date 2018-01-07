package com.example.linm.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTodolist extends AppCompatActivity {

    private RadioGroup RG;
    private RadioButton RB1;
    private RadioButton RB2;
    private RadioButton RB3;
    private RadioButton RB4;
    private EditText Title;
    private EditText Content;
    private EditText Time;
    private Button AddButton;
    private int type;

    void init(){
        RG = (RadioGroup)findViewById(R.id.radiogroup);
        RB1 = (RadioButton)findViewById(R.id.ddl_type);
        RB2 = (RadioButton)findViewById(R.id.birthday_type);
        RB3 = (RadioButton)findViewById(R.id.buy_type);
        RB4 = (RadioButton)findViewById(R.id.travel_type);
        Title = (EditText)findViewById(R.id.title);
        Content = (EditText)findViewById(R.id.content);
        Time = (EditText)findViewById(R.id.time);
        AddButton = (Button)findViewById(R.id.add);
        type = R.mipmap.ddl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todolist);
        init();

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

            };
        }
        );

        //添加
        AddButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String title = Title.getText().toString();
                String content = Content.getText().toString();
                String time = Time.getText().toString();
                SimpleDateFormat  formatter  =  new  SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String  str = formatter.format(curDate);
                //名字为空
                if(title.equals("")){
                    Toast.makeText(AddTodolist.this, "备忘不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    MyDB db = new MyDB(getBaseContext());
                    Cursor cursor = db.ifexit(title);
                    if (cursor.moveToFirst() == true) {
                        Toast.makeText(AddTodolist.this, "备忘不能重复", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        db.insert(title,type,content,time,str);
                        setResult(99, new Intent());
                        finish();
                    }
                }

            }
        });

    }
}
