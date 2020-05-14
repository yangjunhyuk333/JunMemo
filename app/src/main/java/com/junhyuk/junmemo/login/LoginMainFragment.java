package com.junhyuk.junmemo.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.junhyuk.junmemo.R;

public class LoginMainFragment extends Fragment {

    public static LoginMainFragment newInstance(){
        return new LoginMainFragment();
    }

    ImageButton registerButton, loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_main, container, false);

        registerButton = (ImageButton) view.findViewById(R.id.register_button);
        loginButton = (ImageButton) view.findViewById(R.id.login_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).replaceFragment(RegisterFragment.newInstance());
            }
        });

        loginButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).replaceFragment(LoginFragment.newInstance());
            }
        }));

        return view;
    }
}
