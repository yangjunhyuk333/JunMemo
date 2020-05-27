package com.junhyuk.junmemo.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    //파이어베이스 데이터베이스 회원 정보
    //현재 사용하고 있지는 않는 클래스
    public String userName;
    public String email;
    public String password;

    public String userMemoTitle;
    public String userMemoContent;
    public String userMemoId;

    public User(){

    }

    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "Memo{" +
                "memoId='" + userMemoId + '\'' +
                ",memoTitle='" + userMemoTitle + '\'' +
                ",memoContent='" + userMemoContent + '\'' +
                '}';
    }


}
