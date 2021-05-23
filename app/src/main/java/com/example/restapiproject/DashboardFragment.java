package com.example.restapiproject;

import android.content.Context;
import android.net.Uri;
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

import com.example.restapiproject.Models.Datum;
import com.example.restapiproject.Models.Model;
import com.example.restapiproject.Models.PersonList;
import com.example.restapiproject.Models.UserInfo;
import com.example.restapiproject.utils.APIClient;
import com.example.restapiproject.utils.APIInterface;
import com.example.restapiproject.utils.PaginationListner;
import com.example.restapiproject.utils.PersonRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.restapiproject.utils.PaginationListner.PAGE_START;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PersonRecyclerViewAdapter.OnRecycleClickListner {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    TextView textView;

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;


    private PersonRecyclerViewAdapter adapter;

    private int currentPage = PAGE_START;

    private boolean isLastPage = false;
    private int totalPage = 50;
    private boolean isLoading = false;
    List<Datum> myMovieList;

    APIInterface apiInterface;




    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
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

        recyclerView = view.findViewById(R.id.recyclerView);


        NavigationView navigationView = getActivity().findViewById(R.id.nv);
        View headerView = navigationView.getHeaderView(0);
        textView = headerView.findViewById(R.id.headerTextView);



        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firebaseFirestore.collection("User Profile Information").document(firebaseUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {
                        Log.d("DashboardFragment", doc.getData().toString());

                        textView.setText("Hello " + doc.get("name"));


                    }

                }
            }
        });


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PersonRecyclerViewAdapter(new ArrayList<>(),(PersonRecyclerViewAdapter.OnRecycleClickListner)this);
        recyclerView.setAdapter(adapter);

        //makeApiCall();

        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);

        Call<Model> call = apiInterface.getAllData();
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                Log.e("Dashboard Fragment",""+response.code());

                ArrayList<Model.data> data = response.body().getData();

                for (Model.data data1 : data)
                {

                    Log.e("Dashboard Fragment",""+data1.getEmail());

                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Log.e("Dashboard Fragment",""+t.getMessage());


            }
        });


        recyclerView.addOnScrollListener(new PaginationListner(layoutManager) {
            @Override
            protected void loadMoreItems() {

                isLoading = true;
                currentPage++;
               // makeApiCall();

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

//    public void makeApiCall()
//    {
//        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
//
//        //Call<PersonList> call = apiInterface.getAllData();
//        call.enqueue(new Callback<PersonList>() {
//
//            @Override
//            public void onResponse(Call<PersonList> call, Response<PersonList> response) {
//
//                Log.d("Dashboard Fragment",""+response.body());
//
//                PersonList personList = response.body();
//                //Data data = movieList.data;
//                List<Datum> movies = personList.data;
//                myMovieList = movies;
//
//                Log.d("Dashboard Fragment","Movie Size"+movies.size());
//                Log.d("Dashboard Fragment","First Movie"+movies.get(0).firstName);
//
//
//                if (currentPage != PAGE_START) adapter.removeLoading();
//
//                adapter.addItems(movies);
//                //swipeRefreshLayout.setRefreshing(false);
//
//                if (currentPage<totalPage)
//                {
//                    adapter.addLoading();
//                }
//                else {
//                    isLastPage = true;
//                }
//                isLoading = false;
//
//            }
//
//            @Override
//            public void onFailure(Call<PersonList> call, Throwable t) {
//
//                call.cancel();
//                Log.d("Dashboard Fragment",""+t.getMessage());
//
//            }
//        });
//    }


    @Override
    public void onRefresh() {


        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
       // makeApiCall();
    }

    @Override
    public void onPersonClick(Datum datum) {

        Toast.makeText(getActivity().getApplicationContext(),datum.getFirstName(),Toast.LENGTH_LONG).show();

    }
}
