package com.w36495.basiclist.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String contents;    // 투두리스트 내용
    private Boolean complete;   // 완료 체크
    private ItemState state;    // 우선순위

    public Item(int id, String contents, Boolean complete, ItemState state) {
        this.id = id;
        this.contents = contents;
        this.complete = complete;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
}
