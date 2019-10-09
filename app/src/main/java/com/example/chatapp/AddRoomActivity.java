package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.Base.BaseActivity;
import com.example.chatapp.DataBase.Daos.RoomsDao;
import com.example.chatapp.DataBase.Model.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class AddRoomActivity extends
        BaseActivity implements View.OnClickListener,
        OnSuccessListener, OnFailureListener {

    protected EditText name;
    protected EditText description;
    protected Button addRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_room);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_room) {
            String roomTitle = name.getText().toString();
            String descriptionText = description.getText().toString();
            Room room = new Room();
            room.setName(roomTitle);
            room.setDescription(descriptionText);
            showProgressDialog(R.string.loading);
            RoomsDao.addRoom(room, this, this);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        hideProgressDialog();
        showMessage("cannot add room, " + e.getLocalizedMessage(),
                "ok");
    }

    @Override
    public void onSuccess(Object o) {
        hideProgressDialog();
        showMessage("Room addedd successfully", "ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }, false);
    }

    private void initView() {
        name = findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        addRoom = (Button) findViewById(R.id.add_room);
        addRoom.setOnClickListener(AddRoomActivity.this);
    }
}

