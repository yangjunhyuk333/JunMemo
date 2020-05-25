package com.junhyuk.junmemo.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemoDao {
    @Query("SELECT * FROM MemoData")
    LiveData<List<MemoData>> getAll();

    @Query("SELECT memoTitle FROM 'MemoData'")
    LiveData<List<String>> getTitle();

    @Query("SELECT memoContent FROM 'MemoData'")
    LiveData<List<String>> getContent();

    @Query("SELECT memoId FROM 'MemoData'")
    LiveData<List<Integer>> getID();

    @Query("UPDATE 'MemoData' SET memoTitle = :titleEdit, memoContent = :contentEdit WHERE memoId = :id")
    void update(String titleEdit, String contentEdit, int id);

    @Query("DELETE FROM 'MemoData' WHERE memoId = :id")
    void delete(int id);

    @Query("SELECT memoId FROM 'MemoData' WHERE :id")
    int getIdDelete(int id);

    @Insert
    void insert(MemoData memo);

    @Delete
    void delete(MemoData memo);
}
