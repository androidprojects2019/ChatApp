package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatapp.Base.BaseActivity;
import com.example.chatapp.DataBase.Daos.UsersDao;
import com.example.chatapp.DataBase.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;

public class LoginActivity extends BaseActivity implements
        View.OnClickListener, OnCompleteListener<AuthResult>, OnFailureListener {

    protected EditText email;
    protected EditText password;
    protected Button login;
    protected TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        initView();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            DataUtil.user =
                    FirebaseAuth.getInstance().getCurrentUser();
            openMainActivity();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            User user = new User();
            user.setEmail(emailText);
            user.setPassword(passwordText);
            if(emailText.trim().isEmpty())
            {
                email.setError("required");
            }

            if (passwordText.trim().isEmpty()) {
                email.setError("required");
            }
            showProgressDialog(R.string.loading);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this).addOnFailureListener(this);

        } else if (view.getId() == R.id.register) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }

    private void initView() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(LoginActivity.this);
        register = findViewById(R.id.register);
        register.setOnClickListener(LoginActivity.this);
    }

    public void openMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        hideProgressDialog();
        if (task.isSuccessful()) {
            DataUtil.user =
                    FirebaseAuth.getInstance().getCurrentUser();
            UsersDao.getUser(DataUtil.user.getUid(), new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful())
                    {
                        for(QueryDocumentSnapshot userDocument : task.getResult())
                        {
                            if (userDocument !=null){
                              User currentUser = userDocument.toObject(User.class);
                              DataUtil.dbUser = currentUser;
                                openMainActivity();

                            }
                            break;
                        }

                    }
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                showMessage("Invalid username or password","Ok");
                }
            });
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        hideProgressDialog();
        showMessage("Invalid email or password", "Ok");
    }
}
