package com.junhyuk.junmemo.login;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junhyuk.junmemo.memo.MainActivity;
import com.junhyuk.junmemo.R;
import com.junhyuk.junmemo.data.User;

public class LoginFragment extends Fragment {

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    private DatabaseReference databaseReference;

    ImageButton loginbutton;

    private Context context;

    FirebaseAuth firebaseAuth;

    EditText editEmail, editPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        context = container.getContext();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        loginbutton = (ImageButton) view.findViewById(R.id.login_btn);

        editEmail = (EditText) view.findViewById(R.id.edit_em);
        editPassword = (EditText) view.findViewById(R.id.edit_pass);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserEmail = editEmail.getText().toString().trim();
                String getUserPassword = editPassword.getText().toString().trim();

                if(TextUtils.isEmpty(getUserEmail)){
                    Toast.makeText(context, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(getUserPassword)){
                    Toast.makeText(context, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(getUserEmail, getUserPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            getActivity().finish();
                            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(context, "로그인 실패!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }

    private void readUser(){
        databaseReference.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                }else{
                    Toast.makeText(context, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
