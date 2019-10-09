package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chatapp.DataBase.Model.Message;
import com.example.chatapp.Base.BaseActivity;
import com.example.chatapp.DataBase.Daos.MessagesDao;
import com.example.chatapp.DataBase.Model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

public class ChatThread extends BaseActivity implements View.OnClickListener {

    protected RecyclerView recyclerView;
    protected ImageView send;
    protected EditText message;
    public static Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_chat_thread);
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            String messageContent = message.getText().toString();
            if(messageContent.trim().isEmpty()){
                return;
            }
            Message mMessage=new Message();
            mMessage.setContent(messageContent);
            mMessage.setSenderId(DataUtil.dbUser.getId());
            mMessage.setSenderName(DataUtil.dbUser.getName());
            mMessage.setRoomId(currentRoom.getId());
            MessagesDao.addMessage(mMessage, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    message.setText("");
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChatThread.this, "cannot send message", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        send = (ImageView) findViewById(R.id.send);
        send.setOnClickListener(ChatThread.this);
        message = (EditText) findViewById(R.id.message);
    }
}