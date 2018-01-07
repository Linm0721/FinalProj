package com.example.linm.todolist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ACER on 2017/12/31.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private OnRecyclerViewItemClickListener mItemClickListener = null;
    private List<ListItem> GihubList = new ArrayList<>();

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
        if(day<=0 && hour<=0 && min<=0  ){
            printout = "已结束";
        }
        else{
            printout = "还有"+ day +"天" + hour + "小时" + min + "分钟";
        }


        return  printout;

    }

    public interface OnRecyclerViewItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener){
        this.mItemClickListener = onItemClickListener;
    }
    //添加子项
    public void addItem(ListItem github){
        GihubList.add(github);
        notifyDataSetChanged();
    }
    //得到相应位置的子项
    public ListItem getItem(int position){
        return GihubList.get(position);
    }
    //移除相应位置的子项
    public void removeItem(int position){
        GihubList.remove(position);
        notifyItemRemoved(position);
    }
    //移除所有子项
    public void removeAllItem(){
        GihubList.clear();
        notifyDataSetChanged();
    }
    //获得Item的总数
    @Override
    public int getItemCount() {
        return GihubList.size();
    }
    //创建Item视图
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(mItemClickListener != null)
                    mItemClickListener.onClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v)
            {
                if(mItemClickListener != null)
                    mItemClickListener.onLongClick(holder.getAdapterPosition());
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder holder, int position) {
        //绑定数据到正确的Item视图上
        ListItem github = GihubList.get(position);
        holder.Type.setImageResource(github.getType());
        holder.Title.setText(String.valueOf(github.getTitle()));
        long time = github.getDdl();
        long now = System.currentTimeMillis();
        String setddl="";
        try{
            setddl = longToString(time,"yyyy-MM-dd HH:mm");
        }catch (ParseException p){

        }
        holder.Ddl.setText(setddl);
        holder.Till.setText(getTill(time,now));
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Type;
        TextView Title, Ddl ,Till;

        ViewHolder(View view)
        {
            super(view);
            //通过id获取view
            Type = (ImageView)view.findViewById(R.id.type);
            Title = (TextView)view.findViewById(R.id.title);
            Ddl = (TextView)view.findViewById(R.id.ddl);
            Till = (TextView)view.findViewById(R.id.till);
        }
    }
}
