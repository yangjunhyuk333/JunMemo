package com.junhyuk.junmemo.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.memo.fragment.MemoContent;
import com.junhyuk.junmemo.memo.fragment.MemoTitle;

public class Main2Activity extends AppCompatActivity {

    FragmentManager fragmentManager;

    ImageButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_title, new MemoTitle());
        fragmentTransaction.add(R.id.container_content, new MemoContent());
        fragmentTransaction.commit();

        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MemoTitle.editTitle.length() == 0 || MemoContent.editContent.length() == 0){
                    Toast.makeText(getApplicationContext(), "제목이나 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.Memo.memoTitleData.add(MemoTitle.editTitle.getText().toString());
                    MainActivity.Memo.memoContentData.add(MemoContent.editContent.getText().toString());
                    MainActivity.Memo.memoSize++;

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
