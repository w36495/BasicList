package com.w36495.basiclist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * 데이터베이스를 다루는 인터페이스
 */
@Dao
public interface ItemDAO {

    @Query("select * from items")
    List<Item> getAll();   // 모든 리스트를 검색

    @Insert
    void insertItem(Item item); // 하나의 리스트 추가

    @Query("delete from items where id = :itemId")
    void deleteItem(int itemId); // 하나의 리스트 삭제


}
