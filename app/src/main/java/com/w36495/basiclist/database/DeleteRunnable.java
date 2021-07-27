package com.w36495.basiclist.database;

import android.content.Context;
import android.util.Log;

public class DeleteRunnable implements Runnable {
    private static String TAG = "DeleteRunnable";

    private Context context;
    private int itemId;

    public DeleteRunnable(Context context, int itemId) {
        this.context = context;
        this.itemId = itemId;
    }

    @Override
    public void run() {
        ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().deleteItem(itemId);
        Log.d(TAG, "run() 메소드 호출");
    }
}
