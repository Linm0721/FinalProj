package com.example.linm.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Todolist extends AppCompatActivity {


    private CardAdapter mAdapter; //适配器
    private RecyclerView mRecyclerView; //RecyclerView列表
    private FloatingActionButton FloatingButton;
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
        FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Todolist.this, AddTodolist.class);
                startActivityForResult(intent, 9);

            }
        });
        dataUpdate();
    }

    public void dataUpdate() {
        try {
            //select *
            MyDB db = new MyDB(getBaseContext());
            Cursor cursor = db.selectall();

            if (cursor == null) {

            }
            else {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(0);
                    int type = cursor.getInt(1);
                    String content = cursor.getString(2);
                    String ddl = cursor.getString(3);
                    ListItem newitem = new ListItem(type,title,content,ddl);
                    mAdapter.addItem(newitem);
                }

            }
        } catch (SQLException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && resultCode == 99) {
            dataUpdate();
        }
    }
}
