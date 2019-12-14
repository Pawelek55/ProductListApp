package com.example.cwiczenia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.util.UUID;

public class AddProduct extends Activity {

    EditText inputName,inputPrice,inputQuantity;
    private FirebaseDatabase fdb;
    private FirebaseAuth fa;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        fa = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference();

        inputName= findViewById(R.id.inputName);
        inputPrice=findViewById(R.id.inputPrice);
        inputQuantity=findViewById(R.id.inputQuantity);
    }

    public void addProduct(View view){

        //zakomentwane przed wdrożeniem firebaseDatabase
//        DBHelper db = new DBHelper(this);
//        Product product = new Product( inputName.getText().toString(), new BigDecimal(inputPrice.getText().toString()).multiply(new BigDecimal(1)), Integer.valueOf(inputQuantity.getText().toString()), false);
//        db.addData(product);
//
//        sendBroadcastMethod(product);
//        startActivity(new Intent(this,ProductsListActivity.class));

        if (!inputName.getText().toString().equals("") && !inputPrice.getText().toString().equals("") && !inputQuantity.getText().toString().equals("")) {
            FirebaseUser user = fa.getCurrentUser();
            String userID = user.getUid();
            String id = UUID.randomUUID().toString();
            dbref.child(userID).child("Products").child(id).setValue(
                    new Product(inputName.getText().toString(),
                            Integer.valueOf(inputPrice.getText().toString()),
                            Integer.valueOf(inputQuantity.getText().toString()),
                                                false));
            Toast.makeText(this, "Product dodany do chmury", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,ProductsListActivity.class));
        }
    }

    private void sendBroadcastMethod(Product product){
        Intent intent = new Intent();
        intent.setAction("com.example.cwiczenia.product");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("name",product.getName());
        intent.putExtra("price",product.getPrice()+"");
        intent.putExtra("quantity",product.getQuantity()+"");
        intent.putExtra("isBought",product.isBought()+"");
        sendBroadcast(intent, "andro.jf.mycustompermision");
    }
}

