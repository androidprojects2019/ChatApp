package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.chatapp.Base.BaseActivity;
import com.example.chatapp.DataBase.Daos.RoomsDao;
import com.example.chatapp.DataBase.Model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class MainActivity extends BaseActivity
        implements OnCompleteListener<QuerySnapshot>, OnFailureListener {

    RoomsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
        setSupportActionBar(toolbar);
        initRecyclerView();
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddRoomActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllRooms();
    }

    public void initRecyclerView(){
        recyclerView =findViewById(R.id.recycler_view);
        adapter =new RoomsAdapter(null);
        adapter.setOnItemClickListener(new RoomsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Room room) {
                ChatThread.currentRoom=room;
                startActivity(new Intent(MainActivity.this,ChatThread.class));
            }
        });
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


    }
    public void getAllRooms(){
        RoomsDao.getAllRooms(this,this);
    }

    List<Room> myRooms ;
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            myRooms =new ArrayList<>();
            for (QueryDocumentSnapshot room : task.getResult()) {
                // Log.d(TAG, document.getId() + " => " + document.getData());
                myRooms.add(room.toObject(Room.class));
            }
            adapter.changeData(myRooms);
            progressBar.setVisibility(View.GONE);

        }

    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }
}

