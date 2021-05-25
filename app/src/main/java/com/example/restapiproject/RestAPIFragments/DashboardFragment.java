package com.example.restapiproject.RestAPIFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapiproject.Models.Data;
import com.example.restapiproject.Models.Model;
import com.example.restapiproject.R;
import com.example.restapiproject.utils.APIClient;
import com.example.restapiproject.utils.APIInterface;
import com.example.restapiproject.utils.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    APIInterface apiInterface;
    RecyclerAdapter recyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private int currentPage = 1;
    private int totalPage = 2;
    private boolean isLastPage = false;
    private ArrayList<Data> arrayList;



    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getActivity().setTitle("Dashboard");


        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView1);
        arrayList = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);


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
                    arrayList.add(new Data(getString(R.string.pname),data1.getFirst_name(),data1.getAvatar()));

                }

                recyclerAdapter = new RecyclerAdapter(arrayList, getContext(), new RecyclerAdapter.ItemClickListner() {
                    @Override
                    public void onItemClick(Data data) {

                        showToast(data.getMessage());

                    }

                });
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerAdapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);

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

    public void showToast(String message)
    {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {

        currentPage = 1;
        isLastPage = false;
        arrayList.clear();
        recyclerAdapter.notifyDataSetChanged();
        makeApiCall();

    }
}
