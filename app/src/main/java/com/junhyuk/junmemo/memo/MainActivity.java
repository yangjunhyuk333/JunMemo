package com.junhyuk.junmemo.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.SwipeControllerActions;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.data.database.AppDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingButton;
    RecyclerView recyclerView;

    AppDatabase db;

    List<String> memoTitle = Arrays.asList();
    List<String> memoContent = Arrays.asList();
    List<Integer> memoId = Arrays.asList();

    SwipeController swipeController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        floatingButton = findViewById(R.id.floating_button);
        recyclerView = findViewById(R.id.recycler_view);

        db = AppDatabase.getAppDatabase(this);

        db.memoDao().getAll().observe(this, memoData -> {
            Log.d("DataBase", "AllData: " + memoData.toString());
        });

        if(Memo.MemoData.memoContentData != null && Memo.MemoData.memoTitleData != null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new MyAdapter(Memo.MemoData.memoTitleData, Memo.MemoData.memoContentData));
        }

        floatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        });

        db.memoDao().getTitle().observe(this, strings -> {
            Log.d("DataBase", "data1: " + strings.toString());
            memoTitle.clear();
            memoTitle = strings;
            Log.d("DataBase", "data2: " + memoTitle.toString());
            Memo.MemoData.memoTitleData.clear();
            Memo.MemoData.memoTitleData.addAll(memoTitle);
            Log.d("DataBase", "data3: " + Memo.MemoData.memoTitleData);
        });

        db.memoDao().getContent().observe(this, strings -> {
            Log.d("DataBase", "content_data1: " + strings.toString());
            memoContent.clear();
            memoContent = strings;
            Log.d("DataBase", "content_data2: " + memoContent.toString());
            Memo.MemoData.memoContentData.clear();
            Memo.MemoData.memoContentData.addAll(memoContent);
            Log.d("DataBase", "content_data3: " + Memo.MemoData.memoContentData);
        });

        db.memoDao().getID().observe(this, integers -> {
            Log.d("DataBase", "Id1: " + integers);
            memoId.clear();
            memoId = integers;
            Log.d("DataBase", "Id2: " + memoId.toString());
            Memo.MemoData.memoIdData.clear();
            Memo.MemoData.memoIdData.addAll(memoId);
            Log.d("DataBase", "Id3: " + Memo.MemoData.memoIdData);
        });

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(getApplicationContext(), MemoEditActivity.class);
                intent.putExtra("PositionIndex", position);
                startActivity(intent);
            }

            @Override
            public void onRightClicked(int position) {
                DeleteThread deleteThread = new DeleteThread(position);
                deleteThread.start();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private ArrayList<String> memoTitleData;
        private ArrayList<String> memoContentData;

        public MyAdapter(ArrayList<String> TitleList, ArrayList<String> ContentList) {
            this.memoTitleData = TitleList;
            this.memoContentData = ContentList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.cardview, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
            viewHolder.title.setText(memoTitleData.get(position));
            viewHolder.content.setText(memoContentData.get(position));

            viewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MemoPrint.class);
                intent.putExtra("Position", viewHolder.getAdapterPosition());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return memoTitleData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView content;

            public ViewHolder(@NonNull final View itemView){
                super(itemView);
                title = itemView.findViewById(R.id.title_text);
                content = itemView.findViewById(R.id.content_text);
            }

        }
    }

    private class DeleteThread extends Thread{
        private int position;

        public DeleteThread(int position) {
            this.position = position;
        }

        public void run(){
            db.memoDao().delete(db.memoDao().getIdDelete(position + 1));
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
