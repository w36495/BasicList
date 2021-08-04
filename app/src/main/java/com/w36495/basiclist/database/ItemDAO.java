package com.w36495.basiclist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 데이터베이스를 다루는 인터페이스
 */
@Dao
public interface ItemDAO {

    @Query("select * from items")
    LiveData<List<Item>> getAll();   // 모든 리스트를 검색

    @Query("select id from items order by id desc limit 1")
    int lastGetId();   // 마지막 행 id 가져오기

    @Query("select * from items where id = :itemId")
    Item findOneItems(int itemId);  // 아이디로 하나의 리스트 검색

    @Insert
    void insertItem(Item item); // 하나의 리스트 추가

    @Query("delete from items where id = :itemId")
    void deleteItem(int itemId); // 하나의 리스트 삭제

    // 완료/미완료 변경
    @Query("update items set complete = :isChecked where id = :itemId")
    void checkedUpdateItem(int itemId, boolean isChecked);


    // 우선순위 변경
    @Query("update items set state = :itemState where id = :itemId")
    void stateUpdateItem(int itemId, ItemState itemState);
}
