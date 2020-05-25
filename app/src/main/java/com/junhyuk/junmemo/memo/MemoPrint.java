package com.junhyuk.junmemo.memo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.memo.print.PrintMemoContent;
import com.junhyuk.junmemo.memo.print.PrintMemoTitle;

public class MemoPrint extends AppCompatActivity {

    FragmentManager fragmentManager;

    public static int position;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_memo);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        intent = getIntent();
        position = intent.getExtras().getInt("Position");
        Log.d("Test", "Position: " + position);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_print_title, new PrintMemoTitle());
        fragmentTransaction.add(R.id.container_print_content, new PrintMemoContent());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);
    }
}
