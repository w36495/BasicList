package com.w36495.basiclist;

public class Item {

    private String listItem;    // 투두리스트 내용
    private Boolean complete;   // 완료 체크

    public Item(String listItem, Boolean complete) {
        this.listItem = listItem;
        this.complete = complete;
    }

    public String getListItem() {
        return listItem;
    }

    public void setListItem(String listItem) {
        this.listItem = listItem;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
