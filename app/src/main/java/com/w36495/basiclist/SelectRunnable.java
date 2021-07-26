package com.w36495.basiclist;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class SelectRunnable implements Runnable {
    private static String TAG = "SelectRunnable";

    private Context context;
    private ArrayList<Item> mListItems = new ArrayList<>();

    public SelectRunnable(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        mListItems = (ArrayList<Item>) ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().getAll();
        Log.d(TAG, "run() 메소드 호출");
        return;
    }
}
