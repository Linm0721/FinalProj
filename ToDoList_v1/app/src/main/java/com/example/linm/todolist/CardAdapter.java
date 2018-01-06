package com.example.linm.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 2017/12/31.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private OnRecyclerViewItemClickListener mItemClickListener = null;
    private List<ListItem> GihubList = new ArrayList<>();

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
        holder.Ddl.setText(github.getDdl());
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Type;
        TextView Title, Ddl;

        ViewHolder(View view)
        {
            super(view);
            //通过id获取view
            Type = (ImageView)view.findViewById(R.id.type);
            Title = (TextView)view.findViewById(R.id.title);
            Ddl = (TextView)view.findViewById(R.id.ddl);
        }
    }
}
