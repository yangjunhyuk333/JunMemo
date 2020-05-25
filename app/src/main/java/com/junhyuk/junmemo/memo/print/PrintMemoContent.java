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

public class PrintMemoContent extends Fragment {

    TextView contentText;
    int positionIndex;

    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.print_memo_content, container,false);

        positionIndex = MemoPrint.position;
        imageView = (ImageView) view.findViewById(R.id.imageView6);

        contentText = (TextView) view.findViewById(R.id.content_print);
        Log.d("Test", "DataClassContent: " + Memo.MemoData.memoContentData.get(0));
        contentText.setText(Memo.MemoData.memoContentData.get(positionIndex));

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), MemoEditActivity.class);
            intent.putExtra("PositionIndex", positionIndex);
            startActivity(intent);
        });

        return view;
    }
}
