package com.example.cwiczenia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup rb_group;
    private RadioButton rb_button;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv, textFont;
    private Button  btn_sing_out;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        tv = findViewById(R.id.textChangeColor);
        textFont = (TextView) findViewById(R.id.textShowFontSize);
        sp = getPreferences(Context.MODE_PRIVATE);
        editor = sp.edit();
        fa = FirebaseAuth.getInstance();

       addListenerOnCheckBox();

        btn_sing_out = findViewById(R.id.buttonSingOut);

        btn_sing_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        textFont.setTextSize(Float.parseFloat(sp.getString("fontSize", "45")));

    }

    private void openLoginActivity() {
        fa.signOut();
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void addListenerOnCheckBox(){
        rb_group = (RadioGroup) findViewById(R.id.fontsColor);
        Button bt1 = (Button) findViewById(R.id.btnChangeColorFont);
        final  EditText edTxt = (EditText) findViewById(R.id.inputFontSize);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int selectedId = rb_group.getCheckedRadioButtonId();
                rb_button = (RadioButton) findViewById(selectedId);

//                setTheme(R.style.greenColorFont);
                Toast.makeText(getBaseContext(), rb_button.getText(), Toast.LENGTH_SHORT).show();

                if(rb_button.getText().toString().matches("Czarny")){
                    TextView textHeader = (TextView) findViewById(R.id.textChangeColor);
                    textHeader.setTextColor(Color.BLACK);
                    editor.putString("fontColor", textHeader.getTextColors().toString());
                    editor.apply();
                } else {
                    TextView textHeader = (TextView) findViewById(R.id.textChangeColor);
                    textHeader.setTextColor(Color.GREEN);

                    editor.putString("fontColor", textHeader.getTextColors().toString());

                    editor.apply();
                };

            }
        });


        edTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){

                    TextView textView = (TextView) findViewById(R.id.textShowFontSize);
                    textView.setTextSize(Float.parseFloat(edTxt.getText().toString())) ;
                    editor.putString("fontSize", edTxt.getText().toString());

                    editor.apply();
                } else {
                    TextView textView = (TextView) findViewById(R.id.textShowFontSize);
                    textView.setTextSize(20) ;
                }
            }
        });
    };

}

