package com.example.restapiproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restapiproject.Models.Data;
import com.example.restapiproject.Models.Model;
import com.example.restapiproject.utils.APIClient;
import com.example.restapiproject.utils.APIInterface;
import com.example.restapiproject.utils.RecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonEmailFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    TextView textView;

    RecyclerView recyclerView;

    APIInterface apiInterface;

    private int currentPage = 1;
    private int totalPage = 2;

    private boolean isLastPage = false;

    private ArrayList<Data> arrayList;


    public PersonEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Person Email");

        recyclerView = view.findViewById(R.id.recyclerView3);
        arrayList = new ArrayList<>();


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        makeApiCall();
    }


    private void makeApiCall() {


        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);

        Call<Model> call = apiInterface.getAllData(String.valueOf(currentPage));
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                Log.e("Dashboard Fragment",""+response.code());

                ArrayList<Model.data> data = response.body().getData();


                for (Model.data data1 : data)
                {

                    Log.e("Person Name",""+data1.getFirst_name());
                    arrayList.add(new Data(getString(R.string.pemail),data1.getEmail(),data1.getAvatar()));

                }

                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList,getContext());
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerAdapter.notifyDataSetChanged();

                if (currentPage<=totalPage)
                {
                    currentPage++;
                    makeApiCall();

                }
                else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Log.e("Dashboard Fragment",""+t.getMessage());


            }
        });
    }
}