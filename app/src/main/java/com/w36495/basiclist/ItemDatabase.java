package com.w36495.basiclist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase INSTANCE = null;

    public abstract ItemDAO itemDAO();

    // MainActivity에서 호출하여 db 객체를 반환받는다.
    public static ItemDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, "BasicList.db").build();
        }
        return INSTANCE;
    }

    // db 객체 삭제
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
