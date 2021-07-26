package com.w36495.basiclist;

import android.content.Context;
import android.util.Log;

/**
 * Room 데이터베이스 - 삽입
 */
public class InsertRunnable implements Runnable {

    private static String TAG = "InsertRunnable";

    private Context context;
    private Item item;

    public InsertRunnable(Context context,Item item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public void run() {
        ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().insertItem(item);
        Log.d(TAG, "run() 메소드 호출");
    }
}
