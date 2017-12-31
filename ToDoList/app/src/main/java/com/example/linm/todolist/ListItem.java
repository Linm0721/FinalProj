package com.example.linm.todolist;

/**
 * Created by ACER on 2017/12/31.
 */

public class ListItem {

    private int type;
    private String title;
    private String content;
    private String ddl;

    public ListItem(int type, String title, String content, String ddl){
        this.type = type;
        this.title = title;
        this.content = content;
        this.ddl = ddl;
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
    public String getDdl(){
        return ddl;
    }
}
