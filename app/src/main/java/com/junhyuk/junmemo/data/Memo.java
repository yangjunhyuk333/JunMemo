package com.junhyuk.junmemo.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Memo {
    public static class MemoData implements Serializable {

        public static ArrayList<Integer> memoIdData = new ArrayList<>();
        public static ArrayList<String> memoTitleData = new ArrayList<>();
        public static ArrayList<String> memoContentData = new ArrayList<>();



        public MemoData() {
        }
    }
}
