package com.w36495.basiclist.database;

import android.content.Context;
import android.util.Log;

public class CompleteUpdateRunnable implements Runnable {
    private static String TAG = "UpdateRunnable";

    private Context context;
    private boolean isChecked;
    private int itemId;

    public CompleteUpdateRunnable(Context context, int itemId, boolean isChecked) {
        this.context = context;
        this.itemId = itemId;
        this.isChecked = isChecked;
    }

    @Override
    public void run() {
        ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().checkedUpdateItem(itemId, isChecked);
        Log.d(TAG, "run() 메소드 호출");
    }
}
