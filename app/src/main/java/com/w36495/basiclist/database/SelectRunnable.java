package com.w36495.basiclist.database;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class SelectRunnable implements Runnable {
    private static String TAG = "SelectRunnable";

    private List<Item> mListItems;
    private Context context;

    public SelectRunnable(Context context, List<Item> mListItems) {
        this.context = context;
        this.mListItems = mListItems;
    }

    @Override
    public void run() {
        mListItems = ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().getAll();
        Log.d(TAG, "run() 메소드 호출");
    }
}
