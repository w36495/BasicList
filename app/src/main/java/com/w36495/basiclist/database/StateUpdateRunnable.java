package com.w36495.basiclist.database;

import android.content.Context;
import android.util.Log;

import com.w36495.basiclist.ItemState;

public class StateUpdateRunnable implements Runnable {
    private static String TAG = "StateUpdateRunnable";

    private Context context;
    private int itemId;
    private ItemState state;

    public StateUpdateRunnable(Context context, int itemId, ItemState state) {
        this.context = context;
        this.itemId = itemId;
        this.state = state;
    }

    @Override
    public void run() {
        ItemDatabase.getInstance(context.getApplicationContext()).itemDAO().stateUpdateItem(itemId, state);
        Log.d(TAG, "run() 메소드 호출");
    }
}
