package com.w36495.basiclist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase itemDatabase;

    public abstract ItemDAO itemDAO();

    // MainActivity에서 호출하여 db 객체를 반환받는다.
    public static ItemDatabase getInstance(Context context) {
        if (itemDatabase == null) {
            itemDatabase = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, "BasicList.db")
                    .allowMainThreadQueries()   // 메인스레드에서도 db 실행 가능하도록 함
                    .build();
        }
        return itemDatabase;
    }

    // db 객체 삭제
    public static void destroyInstance() {
        itemDatabase = null;
    }

}
