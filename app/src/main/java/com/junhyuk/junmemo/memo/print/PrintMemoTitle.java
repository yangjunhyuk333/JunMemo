package com.junhyuk.junmemo.memo.print;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.Memo;
import com.junhyuk.junmemo.memo.MemoEditActivity;
import com.junhyuk.junmemo.memo.MemoPrint;

public class PrintMemoTitle extends Fragment {


    TextView titleText;
    int positionIndex;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.print_memo_titile, container,false);

        positionIndex = MemoPrint.position;

        titleText = (TextView) view.findViewById(R.id.title_print);
//        Log.d("Test", "DataClassTitle: " + Memo.MemoData.memoContentData.get(positionIndex));
        imageView = (ImageView) view.findViewById(R.id.imageView7);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), MemoEditActivity.class);
            intent.putExtra("PositionIndex", positionIndex);
            startActivity(intent);
        });

        titleText.setText(Memo.MemoData.memoTitleData.get(positionIndex));
        return view;
    }
}
