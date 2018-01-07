package com.example.linm.todolist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        views.setTextViewText(R.id.title, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       /* for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
        //实例化RemoteView，与相应的布局对应起来
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.m_widget);
        //点击跳转到MainActivity页面
        Intent i = new Intent(context,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.widget,pi);

        ComponentName me = new ComponentName(context,mWidget.class);
        //更新widget
        appWidgetManager.updateAppWidget(me,updateViews);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context,Intent intent){
        super.onReceive(context,intent);
        if(intent.getAction().equals("widgetStaticBroadcast")){
            Log.e("debug","收到广播");
            int id = intent.getIntExtra("id",0);
            int type = intent.getIntExtra("type",0);
            String title = intent.getStringExtra("title");
            String ddl = intent.getStringExtra("ddl");
            RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.m_widget);
            updateViews.setTextViewText(R.id.title,title);
            updateViews.setImageViewResource(R.id.type,type);
            updateViews.setTextViewText(R.id.time,ddl);
            Log.e("debug",title);
            //点击跳转
            Intent newintent = new Intent(context, ToDoDetail.class);
            newintent.addCategory(Intent.CATEGORY_ALTERNATIVE);
            newintent.putExtra("id",id);
            PendingIntent pi = PendingIntent.getActivity(context, 0, newintent,PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.widget,pi);


            ComponentName me = new ComponentName(context,mWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(me,updateViews);
        }
    }
}

