package com.example.linm.todolist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        String title = bundle.getString("title");
        String time = bundle.getString("time");
        int type = bundle.getInt("type");
        Log.e("debug","收到 "+id+" " +title + " " + time + " " + type+"");

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title) //标题
                .setContentText(time) //内容
                .setAutoCancel(true)
                .setTicker("您有一个代办项") //提示消息
                .setSmallIcon(type) //小图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), type)); //大图标

        //跳转到商品详情介绍页面
        Intent x= new Intent(context, ToDoDetail.class);
        x.putExtra("id",id);
        Log.e("debug",id+"");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                x, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Notification notify = builder.build();
        //manager负责将状态显示出来
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int uniqueId = (int) System.currentTimeMillis(); //设置随机数，不会覆盖之前的通知
        manager.notify(uniqueId,notify);

    }
}
