package com.w36495.basiclist;

public class Item {

    private String listItem;    // 투두리스트 내용
    private Boolean complete;   // 완료 체크
    private ItemState state;    // 우선순위

    public Item(String listItem, Boolean complete, ItemState state) {
        this.listItem = listItem;
        this.complete = complete;
        this.state = state;
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

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
}
