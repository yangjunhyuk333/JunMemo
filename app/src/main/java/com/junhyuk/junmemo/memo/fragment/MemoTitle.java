package com.junhyuk.junmemo.memo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.memo.MainActivity;

public class MemoTitle extends Fragment {

    public static EditText editTitle;
    public MainActivity.Memo memo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        memo = new MainActivity.Memo();

        editTitle = (EditText) view.findViewById(R.id.title_input);

        return view;
    }
}
