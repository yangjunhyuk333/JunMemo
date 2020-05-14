package com.junhyuk.junmemo.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junhyuk.junmemo.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observer;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingButton;
    RecyclerView recyclerView;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        floatingButton = findViewById(R.id.floating_button);
        recyclerView = findViewById(R.id.recycler_view);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });

        if(Memo.memoSize != 0 && Memo.memoContentData != null && Memo.memoTitleData != null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new MyAdapter(Memo.memoTitleData, Memo.memoContentData));
        }

    }

    public static class Memo implements Serializable {
        public static ArrayList<String> memoTitleData = new ArrayList<String>();
        public static ArrayList<String> memoContentData = new ArrayList<String>();
        public static int memoSize = 0;

        public Memo() {
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        Memo memo;

        public MyAdapter(ArrayList<String> TitleList, ArrayList<String> ContentList) {
            this.memo.memoTitleData = TitleList;
            this.memo.memoContentData = ContentList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.cardview, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.title.setText(memo.memoTitleData.get(position));
            viewHolder.content.setText(memo.memoContentData.get(position));
        }

        @Override
        public int getItemCount() {
            return memo.memoSize;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView content;

            public ViewHolder(@NonNull View itemView){
                super(itemView);
                title = itemView.findViewById(R.id.title_text);
                content = itemView.findViewById(R.id.content_text);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MemoPrint.class);
                        startActivity(intent);
                    }
                });
            }

        }
    }

}
