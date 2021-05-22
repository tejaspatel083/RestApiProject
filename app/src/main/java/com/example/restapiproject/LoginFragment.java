package com.example.restapiproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {


    private Button btnLogin;
    private TextView txtForgotPassword,txtSignup;
    private EditText pwd,emailId;
    private TextView v1,iv1;
    private FirebaseAuth firebaseAuth;


    public LoginFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getActivity().setTitle("Login");

        btnLogin = view.findViewById(R.id.MainLoginBtn);
        txtForgotPassword = view.findViewById(R.id.MainForgotPassword);
        txtSignup = view.findViewById(R.id.MainSignup);
        pwd = view.findViewById(R.id.MainPassword);
        emailId = view.findViewById(R.id.MainEmail);
        v1 = view.findViewById(R.id.visible);
        iv1 = view.findViewById(R.id.notvisible);


        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        }


        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                v1.setVisibility(View.INVISIBLE);
                iv1.setVisibility(View.VISIBLE);
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                iv1.setVisibility(View.INVISIBLE);
                v1.setVisibility(View.VISIBLE);
            }
        });


        View.OnClickListener navigate1 = Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_forgotPasswordFragment);
        View.OnClickListener navigate2 = Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_signupFragment);

        txtForgotPassword.setOnClickListener(navigate1);
        txtSignup.setOnClickListener(navigate2);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailId.getText().toString().trim().length() == 0)
                {
                    emailId.setError("Email Id Required");

                }
                else if (pwd.getText().toString().trim().length() == 0)
                {
                    emailId.setError(null);
                    pwd.setError("Password Required");

                }
                else
                {
                    emailId.setError(null);
                    pwd.setError(null);

                    String email = emailId.getText().toString().trim();
                    String password = pwd.getText().toString().trim();


                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                Toast.makeText(getActivity(), "Login SuccessFull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),HomeActivity.class);
                                startActivity(intent);

                            }
                            else
                            {

                                Toast toast = Toast.makeText(getActivity(),"Enter Valid Email and Password",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }

                        }
                    });

                }
            }
        });
    }

}
