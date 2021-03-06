package com.smieciolapp.autentication.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.smieciolapp.ViewModel.WelcomePage;
import com.smieciolapp.autentication.FirebaseAuthClass;
import com.smieciolapp.ViewModel.MenuMainPage;
import com.smieciolapp.R;

import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton ;
    ProgressBar loadingProgressBar ;

    FirebaseAuthClass auth = new FirebaseAuthClass();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(LoginActivity.this, MenuMainPage.class);
            intent.putExtra("collection","users");
            intent.putExtra("document",auth.getCurrentUser().getEmail());
            startActivity(intent);
        }



        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.register);
        loadingProgressBar = findViewById(R.id.loading);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pobranie uzytkownika wpisanego w formie i walidacja danych jesli ok to nowe activity
                try{
                   auth.logIn(emailEditText.getText().toString().trim(),passwordEditText.getText().toString().trim()).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                       }
                   }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                               Intent intent = new Intent(LoginActivity.this, MenuMainPage.class);
                               intent.putExtra("collection","users");
                               intent.putExtra("document",emailEditText.getText().toString().trim());
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(intent);
                       }
                   });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        });
    }




}
