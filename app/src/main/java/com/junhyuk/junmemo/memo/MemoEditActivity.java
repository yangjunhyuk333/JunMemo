package com.junhyuk.junmemo.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.data.database.AppDatabase;
import com.junhyuk.junmemo.memo.editMemo.EditContent;
import com.junhyuk.junmemo.memo.editMemo.EditTitle;

import android.os.Handler;

public class MemoEditActivity extends AppCompatActivity {

    //데이터 수정 엑티비티

    FragmentManager fragmentManager;

    ImageButton editButton;

    AppDatabase db;

    public static int positionEdit;

    Intent intent;

    //핸들러 생성

    Handler handler = new Handler();

    //runnable 객체 생성
    Runnable runnable = () -> {
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        // 엑션바 디자인
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //프래그먼트
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.edit_title, new EditTitle());
        fragmentTransaction.add(R.id.edit_content, new EditContent());
        fragmentTransaction.commit();

        db = AppDatabase.getAppDatabase(this);

        intent = getIntent();
        positionEdit = intent.getExtras().getInt("Position");

        editButton = findViewById(R.id.edit_button);

        Log.d("DataBase", "data4: " + Memo.MemoData.memoTitleData.get(positionEdit));

        editButton.setOnClickListener(v -> {
            EditThread editThread = new EditThread();
            editThread.start();

            final ProgressDialog progressDialog = new ProgressDialog(MemoEditActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("잠시만 기다려 주세요");
            progressDialog.show();

            handler.postDelayed(runnable, 1500);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private class EditThread extends Thread{

        public EditThread() {

        }

        public void run(){
            Log.d("DataBase", "Edit1: " + EditTitle.editTitle.getText().toString());
            Log.d("DataBase", "Edit2: " + EditContent.editContent.getText().toString());
            Log.d("DataBase", "Edit3: " + Memo.MemoData.memoIdData.get(positionEdit));
            db.memoDao().update(EditTitle.editTitle.getText().toString(),
                    EditContent.editContent.getText().toString(),
                    Memo.MemoData.memoIdData.get(positionEdit));
        }
    }
}
