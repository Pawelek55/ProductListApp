package com.example.cwiczenia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cwiczenia.DBHelper;
import com.example.cwiczenia.R;
import com.example.cwiczenia.EditProduct;
import com.example.cwiczenia.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    List<Product> lp;
    Context context;

    private FirebaseDatabase fdb;
    private FirebaseAuth fa;
    private DatabaseReference dbref;
//    private List<Product> productList;
    private String userID;


    public ProductAdapter(List<Product> lp, Context context) {
        this.lp = lp;
        this.context = context;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tworzy się gdy cokolwiek dodajemy do listy

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_element, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);

        return pvh;
    }



    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        // positon zwraca, o którego itema dokładnie nam chodzi
        // jak cała treść przygotowana, to wówczas wyświetlane. Co należy do danego textView

        Product p = lp.get(position);
        holder.name.setText(p.getName());
        holder.price.setText(p.getPrice() + "");
        holder.quantity.setText(p.getQuantity()+"");
        holder.bought.setChecked(p.isBought());

        fa = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference();
        FirebaseUser user = fa.getCurrentUser();
        userID = user.getUid();

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProduct.class);
                intent.putExtra("productName", lp.get(position).getName());
                intent.putExtra("productPrice", lp.get(position).getPrice() + "");
                intent.putExtra("productQuantity", lp.get(position).getQuantity() + "");
                intent.putExtra("productId", lp.get(position).getId() + "");
                intent.putExtra("productBought", holder.bought.isChecked()+"");
                context.startActivity(intent);
            }
        });

//        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                DBHelper db = new DBHelper(context);
//                builder.setMessage("Skasować product?");
//                builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    int id = lp.get(position).getId();
//                    DBHelper db = new DBHelper(context);
//
//                    int result = db.deleteProduct(id);
//                    if (result > 0) {
//                        lp.remove(position);
//                    }
//                }
//                });
//                builder.setNegativeButton("Nie", null);
//                builder.show();
//
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return lp.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name; //Można zrobić to prywatne, ale trzeba zrobić wówczas settery i gettery
        TextView price;
        TextView quantity;
        CheckBox bought;
        LinearLayout parentLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView4);
            price = itemView.findViewById(R.id.textView5);
            quantity = itemView.findViewById(R.id.textView6);
            bought = itemView.findViewById(R.id.checkBox);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(this); // Odpowiada za nacisniecie na dowolny element aby odpalić klasę rodzica

        }

        @Override
        public void onClick(View v) {
            // TODO przejście do EditActivity

            Toast.makeText(v.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
