package com.junhyuk.junmemo.login;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.junhyuk.junmemo.R;

public class RegisterFragment extends Fragment {

    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    private DatabaseReference databaseReference;
    private Context context;

    ImageButton registerButton;

    EditText editName, editEmail, editPassword;

    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        context = container.getContext();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton = (ImageButton) view.findViewById(R.id.register_btn);

        editName = (EditText) view.findViewById(R.id.edit_name);
        editEmail = (EditText) view.findViewById(R.id.edit_email);
        editPassword = (EditText) view.findViewById(R.id.edit_password);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserName = editName.getText().toString().trim();
                String getUserEmail = editEmail.getText().toString().trim();
                String getUserPassword = editPassword.getText().toString().trim();

                if(TextUtils.isEmpty(getUserName)){
                    Toast.makeText(context, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(getUserEmail)){
                    Toast.makeText(context, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(getUserPassword)){
                    Toast.makeText(context, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(getUserEmail, getUserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            ((LoginActivity)getActivity()).replaceFragment(LoginMainFragment.newInstance());
                        }else{
                            Toast.makeText(context, "에러\n - 이미 등록된 이메일 \n -암호 최소 6자리 이상 \n - 서버에러", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }

}
