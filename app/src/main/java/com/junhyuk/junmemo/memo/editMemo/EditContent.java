package com.junhyuk.junmemo.memo.editMemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.Memo;

import static com.junhyuk.junmemo.memo.MemoEditActivity.positionEdit;

public class EditContent extends Fragment {

    public static EditText editContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_content, container, false);

        editContent = (EditText) view.findViewById(R.id.content_edit);

        editContent.setText(Memo.MemoData.memoContentData.get(positionEdit));

        return view;
    }
}
