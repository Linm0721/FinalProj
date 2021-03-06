package com.example.linm.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Todolist extends AppCompatActivity {


    private CardAdapter mAdapter; //适配器
    private RecyclerView mRecyclerView; //RecyclerView列表
    private FloatingActionButton FloatingButton;

    private Button Backbutton;
    private TextView SortByddl;
    private TextView SortBySetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        Log.e("debug","comingTodoList.java");

        Backbutton = (Button)findViewById(R.id.back);
        SortBySetup = (TextView)findViewById(R.id.forsetup) ;
        SortByddl = (TextView)findViewById(R.id.forddl);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardAdapter() ;
        mRecyclerView.setAdapter(mAdapter);
        FloatingButton = (FloatingActionButton) findViewById(R.id.add);

        dataUpdate();
        SortBySetup.setTextColor(0xffd81e06);


        FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Todolist.this, AddTodolist.class);
                startActivityForResult(intent, 9);

            }
        });

        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        SortByddl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortByddl.setTextColor(0xffd81e06);
                SortBySetup.setTextColor(0xff515151);
                dataUpdateByddl();
            }
        });

        SortBySetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortByddl.setTextColor(0xff515151);
                SortBySetup.setTextColor(0xffd81e06);
                dataUpdate();
            }
        });

        mAdapter.setOnItemClickListener(new CardAdapter.OnRecyclerViewItemClickListener(){
            @Override
            //备忘列表单击
            public  void onClick(int position){

                final int updateid = mAdapter.getItem(position).getId();
                Intent intent = new Intent(Todolist.this, ToDoDetail.class);
                intent.putExtra("id",updateid);
                startActivityForResult(intent, 9);



            }
            @Override
            //备忘列表长按
            public  void onLongClick(final int position){
                final int removeid = mAdapter.getItem(position).getId();
                AlertDialog.Builder message = new AlertDialog.Builder(Todolist.this);

                message.setMessage("是否删除");
                message.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除
                        MyDB db = new MyDB(getBaseContext());
                        db.delete(removeid);
                        // 删除listview中的对应数据
                        mAdapter.removeItem(position);
                    }
                });
                message.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                message.create().show();


            }

        });


    }

    public void dataUpdate() {
        try {
            //select *
            MyDB db = new MyDB(getBaseContext());
            Cursor cursor = db.selectall();
            mAdapter.removeAllItem();
            if (cursor == null) {

            }
            else {
                //发送广播
          /*      if(cursor.moveToFirst()){
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String content = cursor.getString(3);
                    long ddl = cursor.getLong(4);
                    String stime = cursor.getString(5);
                    ListItem newitem = new ListItem(id,type,title,content,ddl,stime);
                    mAdapter.addItem(newitem);
                    Intent wi = new Intent();
                    wi.setAction("widgetStaticBroadcast");
                    wi.putExtra("id",id);
                    wi.putExtra("title",title);
                    wi.putExtra("type",type);
                    wi.putExtra("ddl",ddl);
                    sendBroadcast(wi);
                }
*/
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String content = cursor.getString(3);
                    long ddl = cursor.getLong(4);
                    String stime = cursor.getString(5);
                    ListItem newitem = new ListItem(id,type,title,content,ddl,stime);
                    mAdapter.addItem(newitem);
                }


            }
        } catch (SQLException e) {

        }
    }


    public void dataUpdateByddl() {
        try {
            //select *
            MyDB db = new MyDB(getBaseContext());
            Cursor cursor = db.selectallbyddl();
            mAdapter.removeAllItem();
            if (cursor == null) {

            }
            else {
                //发送广播
         /*       if(cursor.moveToFirst()){
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String content = cursor.getString(3);
                    long ddl = cursor.getLong(4);
                    String stime = cursor.getString(5);
                    ListItem newitem = new ListItem(id,type,title,content,ddl,stime);
                    mAdapter.addItem(newitem);
                    Intent wi = new Intent();
                    wi.setAction("widgetStaticBroadcast");
                    wi.putExtra("id",id);
                    wi.putExtra("title",title);
                    wi.putExtra("type",type);
                    wi.putExtra("ddl",ddl);
                    sendBroadcast(wi);
                }
*/
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String content = cursor.getString(3);
                    long ddl = cursor.getLong(4);
                    String stime = cursor.getString(5);
                    ListItem newitem = new ListItem(id,type,title,content,ddl,stime);
                    mAdapter.addItem(newitem);
                }


            }
        } catch (SQLException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && (resultCode == 99 || resultCode==98)) {
            SortByddl.setTextColor(0xff515151);
            SortBySetup.setTextColor(0xffd81e06);
            dataUpdate();
        }
    }
}
