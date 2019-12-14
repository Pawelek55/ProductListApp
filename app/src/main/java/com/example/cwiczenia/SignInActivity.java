package com.example.cwiczenia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends Activity {

    private FirebaseAuth fa;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText et_email, et_pass;
    private Button btn_sing_in, btn_sing_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        fa = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.inputEmail);
        et_pass = findViewById(R.id.inputPassword);
        btn_sing_in = findViewById(R.id.buttonSingIn);
        btn_sing_up = findViewById(R.id.buttonSingUp);

        btn_sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_pass.getText().toString();
                if (!email.equals("") && !password.equals("")) {
                    fa.signInWithEmailAndPassword(email, password);
                    FirebaseUser user = fa.getCurrentUser();
                    updateUI(user);

                } else {
                    Toast.makeText(SignInActivity.this, "Uzupełnij pola", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_pass.getText().toString();
                if (!email.equals("") && !password.equals("")) {
                    fa.createUserWithEmailAndPassword(email, password);
                    FirebaseUser user = fa.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(SignInActivity.this, "Uzupełnij pola", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            fa.removeAuthStateListener(mAuthListener);
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void updateUI(FirebaseUser account) {
        if (account != null) {
            Toast.makeText(this, "Zalogowanie poprawne", Toast.LENGTH_LONG).show();
            openMainActivity();
        } else {
            Toast.makeText(this, "Nieudana próba logowania", Toast.LENGTH_LONG).show();
        }
    }

}
