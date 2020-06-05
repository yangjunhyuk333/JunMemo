package com.junhyuk.junmemo.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.ViewModel.MainViewModel;
import com.junhyuk.junmemo.data.Memo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //전역 변수
    FloatingActionButton floatingButton;
    RecyclerView recyclerView;

    //리사이클러 뷰 스와이프 작업을 위한 객체
    SwipeController swipeController = null;

    //arraylist로 변경 시킬때 중간 저장 리스트
    private List<String> memoTitle = Arrays.asList();
    private List<String> memoContent = Arrays.asList();
    private List<Integer> memoId = Arrays.asList();

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

        //ViewModel
        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(MainViewModel.class);

        //DB 정보 받아오기
        //title
        mainViewModel.getMemoTitle().observe(this, strings -> {
            memoTitle.clear();
            memoTitle = strings;
            Memo.MemoData.memoTitleData.clear();
            Memo.MemoData.memoTitleData.addAll(memoTitle);
        });

        //DB 정보 받아오기
        //content
        mainViewModel.getMemoContent().observe(this, strings -> {
            memoContent.clear();
            memoContent = strings;
            Memo.MemoData.memoContentData.clear();
            Memo.MemoData.memoContentData.addAll(memoContent);
        });

        //DB 정보 받아오기
        //ID
        mainViewModel.getMemoId().observe(this, integers -> {
            memoId.clear();
            memoId = integers;
            Memo.MemoData.memoIdData.clear();
            Memo.MemoData.memoIdData.addAll(memoId);
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
                mainViewModel.ThreadCall(position);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        //리사이클러 뷰 아이템 스와이프
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
}
