package com.example.restapiproject.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.restapiproject.R;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordFragment extends Fragment {


    private EditText forgotpwd;
    private Button submitbtn;
    private NavController navController;
    private FirebaseAuth firebaseAuth;


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setTitle("Forgot Password?");
        forgotpwd = view.findViewById(R.id.ForgotEmail);
        submitbtn = view.findViewById(R.id.ForgotSubmitBtn);


        navController = Navigation.findNavController(getActivity(),R.id.Host_Fragment);

        firebaseAuth = FirebaseAuth.getInstance();

    }
}
