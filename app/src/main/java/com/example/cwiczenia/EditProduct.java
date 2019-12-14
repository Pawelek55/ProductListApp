package com.example.cwiczenia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.util.Objects;

public class EditProduct extends Activity {

    private EditText editText1, editText2, editText3;
    private FirebaseDatabase fdb;
    private FirebaseAuth fa;
    private DatabaseReference dbref;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_paroduct);


        fa = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference();
        FirebaseUser user = fa.getCurrentUser();
        userID = user.getUid();

        getIncomingIntent();

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("productName") && getIntent().hasExtra("productPrice") && getIntent().hasExtra("productQuantity")&& getIntent().hasExtra("productId")&&getIntent().hasExtra("productBought")) {
            String productName = getIntent().getStringExtra("productName");
            String productPrice = getIntent().getStringExtra("productPrice");
            String productQuantity = getIntent().getStringExtra("productQuantity");



            setValues(productName,productPrice,productQuantity);
        }
    }

    private void setValues(String productName, String productPrice, String productQuantity) {
        EditText name = findViewById(R.id.editInputName);
        name.setText(productName);
        EditText price = findViewById(R.id.editInputPrice);
        price.setText(productPrice);
        EditText quantity = findViewById(R.id.editInputQuantity);
        quantity.setText(productQuantity);


    }

    public void saveValues(View view){
        EditText name = findViewById(R.id.editInputName);
        EditText price = findViewById(R.id.editInputPrice);
        EditText quantity = findViewById(R.id.editInputQuantity);

        if (!name.getText().toString().equals("") && !price.getText().toString().equals("") && !quantity.getText().toString().equals("")) {
            FirebaseUser user = fa.getCurrentUser();
            String userID = user.getUid();
            String id = getIntent().getStringExtra("productId");
            dbref.child(userID).child("Products").child(id).setValue(new Product(name.getText().toString(),
                    Integer.valueOf(price.getText().toString()),
                    Integer.valueOf(quantity.getText().toString()),
                    false));
            Toast.makeText(this, "Edycja udana", Toast.LENGTH_LONG).show();
            startListActivity();
        }
    }


    // przed firebaseDatabase
//    public void saveValues(View view){
//        DBHelper db = new DBHelper(this);
//
//        boolean isBought = Objects.equals(getIntent().getStringExtra("productBought"), "true");
//            EditText name = findViewById(R.id.editInputName);
//            EditText price = findViewById(R.id.editInputPrice);
//            EditText quantity = findViewById(R.id.editInputQuantity);
//            Product product = new Product(name.getText().toString(),Integer.valueOf(price.getText().toString()),Integer.valueOf(quantity.getText().toString()),isBought);
////            Product product = new Product("rzodkiew" ,new BigDecimal(1),Integer.valueOf(2),true);
//            db.updateProduct(product,Long.valueOf(getIntent().getStringExtra("productId")));
//
//            startActivity(new Intent(this,ProductsListActivity.class));
//
//    }

    public void deleteItem  (View view){
        DBHelper db = new DBHelper(this);
        db.deleteProduct(Long.valueOf(getIntent().getStringExtra("productId")));
        startActivity(new Intent(this,ProductsListActivity.class));
    }

    private void startListActivity(){
        startActivity(new Intent(this, ProductsListActivity.class));
    }
}
