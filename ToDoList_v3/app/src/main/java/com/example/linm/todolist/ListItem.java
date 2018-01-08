package com.example.linm.todolist;

/**
 * Created by ACER on 2017/12/31.
 */

public class ListItem {

    private int id;
    private int type;
    private String title;
    private String content;
    private long ddl;
    private String setuptime;

    public ListItem(int id,int type, String title, String content, long ddl,String setuptime){
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.ddl = ddl;
        this.setuptime = setuptime;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getType(){
        return type;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public long getDdl(){
        return ddl;
    }
    public String getSetuptime(){
        return setuptime;
    }
}
