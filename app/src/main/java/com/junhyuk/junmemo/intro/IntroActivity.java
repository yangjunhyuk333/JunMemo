package com.junhyuk.junmemo.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.data.database.AppDatabase;
import com.junhyuk.junmemo.memo.MainActivity;
import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.login.LoginActivity;

import java.util.Arrays;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    AppDatabase db;

    List<String> memoTitle = Arrays.asList();
    List<String> memoContent = Arrays.asList();
    List<Integer> memoId = Arrays.asList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itro);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        db = AppDatabase.getAppDatabase(this);

        db.memoDao().getTitle().observe(this, strings -> {
            Log.d("DataBase", "data1: " + strings.toString());
            memoTitle = strings;
            Log.d("DataBase", "data2: " + memoTitle.toString());
            Memo.MemoData.memoTitleData.addAll(memoTitle);
            Log.d("DataBase", "data3: " + Memo.MemoData.memoTitleData);
        });

        db.memoDao().getContent().observe(this, strings -> {
            Log.d("DataBase", "content_data1: " + strings.toString());
            memoContent = strings;
            Log.d("DataBase", "content_data2: " + memoContent.toString());
            Memo.MemoData.memoContentData.addAll(memoContent);
            Log.d("DataBase", "content_data3: " + Memo.MemoData.memoContentData);
        });

        db.memoDao().getID().observe(this, integers -> {
            Log.d("DataBase", "Id1: " + integers);
            memoId = integers;
            Log.d("DataBase", "Id2: " + memoId.toString());
            Memo.MemoData.memoIdData.addAll(memoId);
            Log.d("DataBase", "Id3: " + Memo.MemoData.memoIdData);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
