package com.junhyuk.junmemo.data.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class MemoData {
    @PrimaryKey(autoGenerate = true)
    public int memoId;

    private String memoTitle;
    private String memoContent;

    public MemoData(String memoTitle, String memoContent) {
        this.memoTitle = memoTitle;
        this.memoContent = memoContent;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
