package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.Base.BaseActivity;
import com.example.chatapp.DataBase.Daos.UsersDao;
import com.example.chatapp.DataBase.Model.User;
import com.example.chatapp.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends BaseActivity
        implements View.OnClickListener, OnFailureListener, OnCompleteListener<AuthResult> {

    protected EditText username;
    protected EditText phoneNumber;
    protected EditText email;
    protected EditText password;
    protected Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register);
        initView();
    }
    User user;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            String usernameText = username.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            String phoneText = phoneNumber.getText().toString();

            if (usernameText.trim().isEmpty()) {
                username.setError("required");
            }
            if (emailText.trim().isEmpty()) {
                email.setError("required");
            }
            if (passwordText.trim().isEmpty()) {
                password.setError("required");
            }
            if (phoneText.trim().isEmpty()) {
                phoneNumber.setError("required");
            }
             user = new User();

            user.setName(usernameText);
            user.setEmail(emailText);
            user.setPhone(phoneText);

            showProgressDialog(R.string.loading);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this).addOnFailureListener(this);


        }
    }

    private void initView() {
        username = findViewById(R.id.username);
        phoneNumber = findViewById(R.id.phone_number);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        register.setOnClickListener(RegisterActivity.this);
    }

    public void openMainActivity() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            DataUtil.user =
                    FirebaseAuth.getInstance().getCurrentUser();


            user.setId(DataUtil.user.getUid());
            UsersDao.addUser(user, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    DataUtil.dbUser = user;
                    hideProgressDialog();
                    openMainActivity();
                    finish();
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hideProgressDialog();
                    showMessage(e.getLocalizedMessage(),"Ok");
                }
            });

         }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        hideProgressDialog();
        showMessage(e.getLocalizedMessage(), "Ok");
    }
}
