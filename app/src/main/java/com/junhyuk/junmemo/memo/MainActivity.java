package com.junhyuk.junmemo.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.data.database.AppDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //전역 변수
    FloatingActionButton floatingButton;
    RecyclerView recyclerView;

    AppDatabase db;

    //데이터베이스의 값을 받아와서 array리스트로 전달 해주기 위한 변수들
    private List<String> memoTitle = Arrays.asList();
    private List<String> memoContent = Arrays.asList();
    private List<Integer> memoId = Arrays.asList();

    //리사이클러 뷰 스와이프 작업을 위한 객체
    SwipeController swipeController = null;

    //파이어베이스 데이터베이스
//    private FirebaseDatabase firebaseDatabase;
//    private FirebaseUser firebaseUser;
//    private FirebaseAuth firebaseAuth;

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //커스텀 액션바 적용
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //플로팅 버튼 아이디 적용
        floatingButton = findViewById(R.id.floating_button);

        //리사이클러 뷰 아이디 적용
        recyclerView = findViewById(R.id.recycler_view);

        //데이터베이스 인스턴스 얻기
        db = AppDatabase.getAppDatabase(this);

        //파이어베이스 인스턴스 얻어오기
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();

//        Log.d("FireBaseUser", "UserData: " + firebaseUser);

        //데이터 베이스 정보 로그 출력
        db.memoDao().getAll().observe(this, memoData -> {
            Log.d("DataBase", "AllData: " + memoData.toString());
        });

        //리사이클러 뷰 출력
        if(Memo.MemoData.memoContentData != null && Memo.MemoData.memoTitleData != null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new MyAdapter(Memo.MemoData.memoTitleData, Memo.MemoData.memoContentData));
        }

        //플로팅 버튼 작업 처리
        //메모 추가 엑티비티로 이동
        floatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        });

        //memo 정보를 realDataBase 에 저장
        //memoSaveRealTimeDataBase();

        //데이터베이스 정보 받아서 arraylist로 처리

        //제목 DB 저장
        db.memoDao().getTitle().observe(this, strings -> {
            Log.d("DataBase", "data1: " + strings.toString());
            memoTitle.clear();
            memoTitle = strings;
            Log.d("DataBase", "data2: " + memoTitle.toString());
            Memo.MemoData.memoTitleData.clear();
            Memo.MemoData.memoTitleData.addAll(memoTitle);
            Log.d("DataBase", "data3: " + Memo.MemoData.memoTitleData);
        });

        //내용 DB 저장
        db.memoDao().getContent().observe(this, strings -> {
            Log.d("DataBase", "content_data1: " + strings.toString());
            memoContent.clear();
            memoContent = strings;
            Log.d("DataBase", "content_data2: " + memoContent.toString());
            Memo.MemoData.memoContentData.clear();
            Memo.MemoData.memoContentData.addAll(memoContent);
            Log.d("DataBase", "content_data3: " + Memo.MemoData.memoContentData);
        });

        //아이디 DB 저장
        db.memoDao().getID().observe(this, integers -> {
            Log.d("DataBase", "Id1: " + integers);
            memoId.clear();
            memoId = integers;
            Log.d("DataBase", "Id2: " + memoId.toString());
            Memo.MemoData.memoIdData.clear();
            Memo.MemoData.memoIdData.addAll(memoId);
            Log.d("DataBase", "Id3: " + Memo.MemoData.memoIdData);
        });

        //스와이프 작업 처리
        swipeController = new SwipeController(new SwipeControllerActions() {
            //왼쪽 스와이프 버튼 클릭시 수정
            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(getApplicationContext(), MemoEditActivity.class);
                intent.putExtra("PositionIndex", position);
                startActivity(intent);
            }

            //오른쪽 스와이프 버튼 클릭시 삭제
            @Override
            public void onRightClicked(int position) {
                DeleteThread deleteThread = new DeleteThread(position);
                deleteThread.start();
            }
        });

        //리사이클러 뷰 아이템 스와이프 기능 처리 코드
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }




    //리사이클러 뷰, 어뎁터
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
                Intent intent = new Intent(getApplicationContext(), MemoEditActivity.class);
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
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

//    private void memoSaveRealTimeDataBase(){
//
//    }


}
