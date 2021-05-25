package com.example.restapiproject.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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

import com.example.restapiproject.Models.UserInfo;
import com.example.restapiproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignupFragment extends Fragment {


    private Button btnSignup;
    private TextView txtLogin;

    private EditText user_pwd1,user_pwd2,user_name,user_email;
    private TextView v1,v2,iv1,iv2;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private NavController navController;



    public SignupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signup,container,false);
        getActivity().setTitle("Signup");

        btnSignup = view.findViewById(R.id.CreateBtn);
        txtLogin = view.findViewById(R.id.LoginText);

        user_email = view.findViewById(R.id.CreateEmail);
        user_name = view.findViewById(R.id.CreateName);
        user_pwd1 = view.findViewById(R.id.CreatePassword);
        user_pwd2 = view.findViewById(R.id.CreateRePassword);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        navController = Navigation.findNavController(getActivity(),R.id.Host_Fragment);


        v1 = view.findViewById(R.id.visible1);
        iv1 = view.findViewById(R.id.notvisible1);
        v2 = view.findViewById(R.id.visible2);
        iv2 = view.findViewById(R.id.notvisible2);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_pwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                v1.setVisibility(View.INVISIBLE);
                iv1.setVisibility(View.VISIBLE);
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_pwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                iv1.setVisibility(View.INVISIBLE);
                v1.setVisibility(View.VISIBLE);
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_pwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                v2.setVisibility(View.INVISIBLE);
                iv2.setVisibility(View.VISIBLE);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_pwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                iv2.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.VISIBLE);
            }
        });

        View.OnClickListener navigate1 = Navigation.createNavigateOnClickListener(R.id.action_signupFragment_to_loginFragment);



        txtLogin.setOnClickListener(navigate1);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_name.getText().toString().trim().length()==0)
                {
                    user_name.setError("User Name Required");
                }
                else if(user_email.getText().toString().trim().length()==0)
                {
                    user_email.setError("Email ID Required");
                }
                else if (user_pwd1.getText().toString().trim().length()==0)
                {
                    user_pwd1.setError("Password Required");
                }
                else if (user_pwd2.getText().toString().trim().length()==0)
                {
                    user_pwd2.setError("Confirm Password Required");
                }
                else if (user_pwd1.getText().toString().trim().equals(user_pwd2.getText().toString().trim()))
                {


                    String email=user_email.getText().toString().trim();
                    String password = user_pwd1.getText().toString().trim();




                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                sendData();
                            }

                            else
                            {

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                    Toast toast = Toast.makeText(getActivity(),"User with this email already exist.",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                                else
                                {
                                    Toast toast = Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }

                            }
                        }
                    });



                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(),"Password not matched",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }

            }
        });





        return view;


    }

    private void sendData()
    {
        String email1= user_email.getText().toString().trim();
        String name = user_name.getText().toString().trim();

        UserInfo obj1 = new UserInfo(name,email1);



        db.collection("User Profile Information")
                .document(firebaseAuth.getUid())
                .set(obj1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"Registration Successful!",Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            navController.navigate(R.id.action_signupFragment_to_loginFragment);

                        }else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"FireStore Error!",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
