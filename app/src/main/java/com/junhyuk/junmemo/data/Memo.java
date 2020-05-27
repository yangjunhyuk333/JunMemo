package com.junhyuk.junmemo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Memo {
    public static class MemoData implements Serializable {

        //메모장 어플이 실행되고 있을 때 데이터베이스 정보를 받아서 데이터베이스 값을 처리 할때 사용할 클래스

        //아이디 데이터
        public static ArrayList<Integer> memoIdData = new ArrayList<>();
        //제목 데이터
        public static ArrayList<String> memoTitleData = new ArrayList<>();
        //내용 데이터
        public static ArrayList<String> memoContentData = new ArrayList<>();



        public MemoData() {
        }
    }
}
