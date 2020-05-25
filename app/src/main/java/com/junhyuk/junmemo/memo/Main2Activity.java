package com.junhyuk.junmemo.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.data.database.AppDatabase;
import com.junhyuk.junmemo.data.database.MemoData;
import com.junhyuk.junmemo.memo.fragment.MemoContent;
import com.junhyuk.junmemo.memo.fragment.MemoTitle;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    FragmentManager fragmentManager;

    ImageButton saveButton;

    private static String inputTitle;
    private static String inputContent;

    AppDatabase db;

    String getTitle, getContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        db = AppDatabase.getAppDatabase(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_title, new MemoTitle());
        fragmentTransaction.add(R.id.container_content, new MemoContent());
        fragmentTransaction.commit();

        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {
            if (MemoTitle.editTitle.length() == 0 || MemoContent.editContent.length() == 0) {
                Toast.makeText(getApplicationContext(), "제목이나 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                Memo.MemoData.memoTitleData.add(MemoTitle.editTitle.getText().toString());
                Memo.MemoData.memoContentData.add(MemoContent.editContent.getText().toString());

                inputTitle = MemoTitle.editTitle.getText().toString();
                inputContent = MemoContent.editContent.getText().toString();

                DataBaseInsertThread dataBaseInsertThread = new DataBaseInsertThread(inputTitle, inputContent);
                dataBaseInsertThread.start();
                try {
                    dataBaseInsertThread.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                db.memoDao().getTitle().observe(this, strings -> {
                    Log.d("DataBase", "data: " + strings.toString());
                });

                db.memoDao().getContent().observe(this, strings -> {
                    Log.d("DataBase", "data: " + strings.toString());
                });

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    private class DataBaseInsertThread extends Thread{
        private static final String TAG = "MemoThread";

        private String inputTitle;
        private String inputContent;

        public DataBaseInsertThread(String inputTitle, String inputContent){
            this.inputTitle = inputTitle;
            this.inputContent = inputContent;
        }

        public void run(){
            db.memoDao().insert(new MemoData(inputTitle, inputContent));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);
    }
}
