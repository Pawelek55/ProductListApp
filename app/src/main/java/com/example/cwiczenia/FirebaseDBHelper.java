package com.example.cwiczenia;

import androidx.annotation.NonNull;

import com.example.cwiczenia.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseDBHelper {
    private FirebaseDatabase fdb;
    private FirebaseAuth fa;
    private DatabaseReference dbref;
    private List<Product> productList;
    private String userID;

    public FirebaseDBHelper(FirebaseDatabase fdb, FirebaseAuth fa, DatabaseReference dbref, List<Product> productList, String userID) {
        this.fdb = FirebaseDatabase.getInstance();
        this.fa = FirebaseAuth.getInstance();
        this.dbref = fdb.getReference();
        this.productList = productList;
    }

    public void setListener() {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Product product = new Product(); // Tu do połączenia z produktami.
        }
    }

}
