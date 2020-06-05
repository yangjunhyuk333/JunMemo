package com.junhyuk.junmemo.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.data.database.AppDatabase;
import com.junhyuk.junmemo.data.database.MemoData;

import java.util.Arrays;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    AppDatabase db;

    public MainViewModel(@NonNull Application application) {
        super(application);

        //데이터베이스 인스턴스 얻기
        db = AppDatabase.getAppDatabase(application);

    }

    //데이터 베이스 정보 얻어오는 메소드
    public LiveData<List<String>> getMemoTitle(){
        return db.memoDao().getTitle();
    }

    public LiveData<List<String>> getMemoContent(){
        return db.memoDao().getContent();
    }

    public LiveData<List<Integer>> getMemoId(){
        return db.memoDao().getID();
    }

    public void ThreadCall(int position){
        DeleteThread deleteThread = new DeleteThread(position);
        deleteThread.start();
    }

    //삭제 쓰레드
    private class DeleteThread extends Thread{
        private int position;

        public DeleteThread(int position) {
            this.position = position;
        }

        public void run(){
            db.memoDao().delete(Memo.MemoData.memoIdData.get(position));
            Log.d("DataBase",  "position: " + position);
            Log.d("DataBase",  "position: " + Memo.MemoData.memoIdData.get(position));
        }
    }


}
