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
    private int typeE;
    private boolean tag1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        Log.e("debug","comingTodoList.java");

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardAdapter() ;
        mRecyclerView.setAdapter(mAdapter);
        FloatingButton = (FloatingActionButton) findViewById(R.id.add);
        dataUpdate();

        FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Todolist.this, AddTodolist.class);
                startActivityForResult(intent, 9);

            }
        });

        mAdapter.setOnItemClickListener(new CardAdapter.OnRecyclerViewItemClickListener(){
            @Override
            //备忘列表单击
            public  void onClick(int position){

           /*     LayoutInflater factory = LayoutInflater.from(Todolist.this);
                View newView = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Todolist.this);


                final RadioGroup RG = (RadioGroup)newView.findViewById(R.id.radiogroup);
                final RadioButton RB1 = (RadioButton)newView.findViewById(R.id.ddl_type);
                final RadioButton RB2 = (RadioButton)newView.findViewById(R.id.birthday_type);
                final RadioButton RB3 = (RadioButton)newView.findViewById(R.id.buy_type);
                final RadioButton RB4 = (RadioButton)newView.findViewById(R.id.travel_type);
                final EditText titleET = (EditText) newView.findViewById(R.id.title);
                final EditText contentET = (EditText) newView.findViewById(R.id.content);
                final EditText timeET = (EditText) newView.findViewById(R.id.time);

                final TextView typeTV = (TextView)newView.findViewById(R.id.type_tv);
                final TextView titleTV = (TextView) newView.findViewById(R.id.title_tv);
                final TextView contentTV = (TextView) newView.findViewById(R.id.content_tv);
                final TextView timeTV = (TextView) newView.findViewById(R.id.time_tv);
                final TextView setuptimeTV = (TextView)newView.findViewById(R.id.setuptime);

                final Button button = (Button)newView.findViewById(R.id.button);*/

                final int updateid = mAdapter.getItem(position).getId();
                Intent intent = new Intent(Todolist.this, ToDoDetail.class);
                intent.putExtra("id",updateid);
                startActivityForResult(intent, 9);


         /*      typeE = mAdapter.getItem(position).getType();
                titleET.setText(mAdapter.getItem(position).getTitle());
                contentET.setText(mAdapter.getItem(position).getContent());
                timeET.setText(mAdapter.getItem(position).getDdl());
                titleTV.setText(mAdapter.getItem(position).getTitle());
                contentTV.setText(mAdapter.getItem(position).getContent());
                timeTV.setText(mAdapter.getItem(position).getDdl());
                setuptimeTV.setText(mAdapter.getItem(position).getSetuptime());
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

                builder.setView(newView);
                builder.setTitle("What To Do( • ̀ω•́ )✧");
                builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDB db = new MyDB(getBaseContext());
                        Log.e("debug","保存修改");
                        SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date curDate =  new Date(System.currentTimeMillis());
                        String  str = formatter.format(curDate);
                        db.update(updateid,titleET.getText().toString(),typeE,contentET.getText().toString(),timeET.getText().toString(),str);
                        dataUpdate();
                    }
                });
                builder.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();*/

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
                if(cursor.moveToFirst()){
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String content = cursor.getString(3);
                    String ddl = cursor.getString(4);
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

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    int type = cursor.getInt(2);
                    String content = cursor.getString(3);
                    String ddl = cursor.getString(4);
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
            dataUpdate();
        }
    }
}
