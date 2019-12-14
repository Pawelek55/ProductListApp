package com.example.cwiczenia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cwiczenia.ProductAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductsListActivity extends AppCompatActivity {

    private RecyclerView rv;
    DBHelper db;
    private FirebaseDatabase fdb;
    private FirebaseAuth fa;
    private DatabaseReference dbref;
    private String userID;
    private List<Product> products;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list);

        fa = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference();
        FirebaseUser user = fa.getCurrentUser();
        userID = user.getUid();

        products = new ArrayList<>();

        rv = findViewById(R.id.rv1);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Przed wdrożeniem fireaseDatabase
/*       db = new DBHelper(this);
        Cursor cursor = db.getRecords();

        List<Product> lp = new ArrayList<>();


       if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"Nie masz żadnych produktów",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                boolean bought = cursor.getInt(4) > 0;
                lp.add(new Product(cursor.getString(0),cursor.getString(1), cursor.getInt(2) ,cursor.getInt(3),bought));
            }
        }

        ProductAdapter pa = new ProductAdapter(lp, this);

        rv.setAdapter(pa);*/

//      Na zajęciach stworzona statyczna lista
        /* List<Products> lp = new ArrayList<>();
        lp.add(new Products("mleko", 4, 2, false));
        lp.add(new Products("ciastka", 5, 1, false));
        lp.add(new Products("mleko", 2, 4, false));

        ProductAdapter pa = new ProductAdapter(lp);
        rv.setAdapter(pa); */

    }

    private void showData(DataSnapshot dataSnapshot) {
        List<Product> productList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child(userID).child("Products").getChildren()) {
            Product product = new Product();

            String productId = snapshot.getKey();
            product.setId(productId);
            product.setName(snapshot.getValue(Product.class).getName());
            product.setPrice(snapshot.getValue(Product.class).getPrice());
            product.setQuantity(snapshot.getValue(Product.class).getQuantity());
            productList.add(product);
        }
        ProductAdapter pa = new ProductAdapter(productList, this);
        rv.setAdapter(pa);
    }

    public void goAddProduct  (View view){
        db = new DBHelper(this);
        Intent intent = new Intent (this, AddProduct.class);
        startActivity(intent);
    }

    public void goBack  (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}