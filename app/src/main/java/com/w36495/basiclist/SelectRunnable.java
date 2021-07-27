package com.w36495.basiclist;

import android.content.Context;
import android.util.Log;

import com.w36495.basiclist.database.Item;
import com.w36495.basiclist.database.ItemDatabase;

import java.util.ArrayList;
import java.util.List;

public class SelectRunnable implements Runnable {
    private static String TAG = "SelectRunnable";

    private Context context;
    private ArrayList<Item> mListItems = new ArrayList<>();

    public SelectRunnable(Context context, List<Item> list) {
        this.context = context;
    }

    @Override
    public void run() {

        mListItems = (ArrayList<Item>) ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().getAll();
        Log.d(TAG, "run() 메소드 호출");
        return;
    }
}
