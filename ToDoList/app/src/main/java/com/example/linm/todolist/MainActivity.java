package com.example.linm.todolist;



        import android.animation.ObjectAnimator;
        import android.animation.ValueAnimator;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.icu.text.SimpleDateFormat;
        import android.media.MediaPlayer;
        import android.os.Binder;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.Message;
        import android.os.Parcel;
        import android.os.RemoteException;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.media.session.PlaybackStateCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.animation.LinearInterpolator;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import static android.R.attr.cacheColorHint;
        import static android.R.attr.defaultHeight;
        import static android.R.attr.handle;
        import static android.R.attr.progress;
        import static android.R.attr.type;
        import static com.example.linm.todolist.MemoService.mp;


public class MainActivity extends AppCompatActivity {

    /*后台service部分*/
    private ServiceConnection mConnection;//用于连接服务
    private IBinder mBinder;//MainActivity与后台服务互相通信的媒介


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button TdoButton = (Button)findViewById(R.id.todolist);
        TdoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Todolist.class);
                startActivity(intent);

            }
        });
        /*调用控件*/



        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("service","connected");
                mBinder = service;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                //mBinder=null;
                mConnection = null;
            }
        };

        /*在Activity中调用bindService保持与Service的通信，Activity启动时绑定Service*/
        Intent intent = new Intent(this,MemoService.class);
        startService(intent);//开启服务
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);


        /********************多线程*******************************/
        final Handler mhandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch(msg.what){
                    //在此判断消息类型并更新UI
                    case 123:
                        int code = 101;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        try {
                            mBinder.transact(code,data,reply,0);
                        }catch(RemoteException e){
                            e.printStackTrace();
                        }
                        break;

                    case 100:
                        MyDB db = new MyDB(getBaseContext());
                        Cursor cursor = db.selectallbyddl();
                        if(cursor.moveToFirst()){
                            int id = cursor.getInt(0);
                            String title = cursor.getString(1);
                            int type = cursor.getInt(2);
                            String content = cursor.getString(3);
                            long ddl = cursor.getLong(4);
                            String stime = cursor.getString(5);
                            Intent wi = new Intent();
                            wi.setAction("widgetStaticBroadcast");
                            wi.putExtra("id",id);
                            wi.putExtra("title",title);
                            wi.putExtra("type",type);
                            wi.putExtra("ddl",ddl);
                            sendBroadcast(wi);
                        }
                        break;
                }
            }
        };
        /*定义一个新的线程*/
        Thread mThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(30000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(mConnection!=null) mhandler.obtainMessage(123).sendToTarget();
                }
            }
        };
        mThread.start();


       Thread nThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(60000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(mConnection!=null) mhandler.obtainMessage(100).sendToTarget();
                }
            }
        };
        nThread.start();

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(mConnection);
        mConnection = null;
        try {
            MainActivity.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



