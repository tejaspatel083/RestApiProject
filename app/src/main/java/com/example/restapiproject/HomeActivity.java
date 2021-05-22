package com.example.restapiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView navi;
    private NavController navController;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStore;
    private FirebaseUser currentUser;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        navi = findViewById(R.id.nv);
        dl = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this,dl,R.string.nav_drawer_open,R.string.nav_drawer_close);

        navController = Navigation.findNavController(HomeActivity.this,R.id.Host_Fragment2);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();


        View headerView = navi.getHeaderView(0);
        textView = headerView.findViewById(R.id.headerTextView);

        getUserName();

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setCheckable(true);

                switch (item.getItemId())
                {

                    case R.id.Dashboard :

                        navController.navigate(R.id.dashboardFragment);
                        break;


                    case R.id.Logout :

                        Toast toast = Toast.makeText(HomeActivity.this,"Logged out",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;



                    default:
                        navController.navigate(R.id.dashboardFragment);
                        break;


                }

                dl.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    private void getUserName() {

        currentUser = firebaseAuth.getCurrentUser();

        DocumentReference docRef = fireStore.collection("User Profile Information").document(currentUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {
                        Log.d("DashboardFragment", doc.getData().toString());

                        textView.setText("Welcome " + doc.get("name"));


                    }

                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (t.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
