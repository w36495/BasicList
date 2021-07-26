package com.w36495.basiclist;

import android.util.Log;

import java.util.ArrayList;

public class ItemRepository {
    private static String TAG = "ItemRepository";

    private ItemDatabase itemDatabase;
    private ItemDAO itemDAO;

    public ArrayList<Item> getAll() {
        Log.d(TAG, "getAll() 메소드 호출");
        if (itemDatabase.itemDAO().getAll() == null) {
            return null;
        }
        else {
            ArrayList<Item> mListItems = (ArrayList<Item>) itemDatabase.itemDAO().getAll();
            return mListItems;
        }

    }

    public void insertItem(Item item) {
        Log.d(TAG, "insertItem() 메소드 호출");
        if (item != null) itemDatabase.itemDAO().insertItem(item);
    }


}
