package com.example.cwiczenia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    private EditText et;
    private Intent in;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        sp = getPreferences(Context.MODE_PRIVATE);
        editor = sp.edit();
    }


    public void click(View view){
//        Toast.makeText( this, et.getText(), Toast.LENGTH_LONG).show();
//        in.putExtra("et", et.getText().toString());
//        startActivity(in);
        editor.putString("str1", et.getText().toString());
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        et.setText(sp.getString("str1", "Domyślna wartość"));

    }

    @Override
    public void onBackPressed() {

    }


    public void goSettings  (View view){
//        Intent intent = new Intent (this, SettingsActivity.class);
        Intent intent = new Intent (this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goProductsList  (View view){
//        Intent intent = new Intent (this, SettingsActivity.class);
        Intent intent = new Intent (this, ProductsListActivity.class);
        startActivity(intent);
    }


}
