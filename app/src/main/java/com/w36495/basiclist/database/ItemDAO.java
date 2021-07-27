package com.w36495.basiclist.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.w36495.basiclist.ItemState;

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

    // 완료/미완료 변경
    @Query("update items set complete = :itemComplete where id = :itemId")
    void checkedUpdateItem(int itemId, boolean itemComplete);

    @Query("update items set state = :itemState where id = :itemId")
    void stateUpdateItem(int itemId, ItemState itemState);
}
