package com.example.linm.todolist;


        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.media.MediaPlayer;
        import android.os.Binder;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.IBinder;
        import android.os.Parcel;
        import android.os.RemoteException;
        import android.util.Log;
        import android.widget.Toast;

        import java.io.IOException;

public class MemoService extends Service {

    private IBinder mBinder;
    public static MediaPlayer mp = new MediaPlayer();//用于管理音乐播放相关的MediaPlayer

    public String getTill(long time, long now){
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        diff = time-now;
        day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff % nd % nh / nm;// 计算差多少分钟
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
        String printout="";
        if(day==0 && hour==0 && min==0  ){
            printout = "时间到了！";
        }
        else if(day==0 && hour==0 && min==30){
            printout = "还有"+  min + "分钟";
        }

        return  printout;

    }

    public MemoService() {
        try{
            Log.e("debug","进入初始化");
            mp.setDataSource(Environment.getExternalStorageDirectory() + "/musicbox/melt.mp3");
            Log.e("debug","成功初始化");
            //mp.setDataSource("@drawable/raw/melt.mp3");
            mp.prepare();
            mp.setLooping(true);
        }catch(Exception e){
            Log.e("debug", "初始化音乐失败");
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    /*通过Binder来保持Activity和Service的通信*/
    public class MyBinder extends Binder {

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            switch (code){
                case 101:{

                    int id = 0;
                    String title = "";
                    int type = R.mipmap.alarm;
                    long ddl = 0;
                    MyDB db = new MyDB(getBaseContext());
                    Cursor cursor = db.selectallbyddl();

                    while(cursor.moveToNext()){


                        int remind = 0;
                        int remindprev = 0;
                        remind = cursor.getInt(7);
                        remindprev = cursor.getInt(6);
                        id = cursor.getInt(0);
                        title = cursor.getString(1);
                        type = cursor.getInt(2);
                        ddl = cursor.getLong(4);

                        if(remindprev==0 && getTill(ddl,System.currentTimeMillis()).equals("还有30分钟")){

                            db.updateremindprev(id);
                            Intent intent = new Intent();
                            intent.setAction("StaticBroadcast");
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id);
                            bundle.putString("title",title);
                            bundle.putInt("type",type);
                            bundle.putString("time","还有30分钟");
                            intent.putExtras(bundle);
                            sendBroadcast(intent);

                        }
                        if(remind==0 && getTill(ddl,System.currentTimeMillis()).equals("时间到了！")){

                            Log.e("debug","提醒,"+id+"");

                            db.updateremind(id);

                            Intent intent = new Intent();
                            intent.setAction("StaticBroadcast");
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",id);
                            bundle.putString("title",title);
                            bundle.putInt("type",type);
                            bundle.putString("time","时间到了");
                            intent.putExtras(bundle);
                            sendBroadcast(intent);

                        }

                    }

                    break;}

            }
            return super.onTransact(code, data, reply, flags);
        }
    }


}
