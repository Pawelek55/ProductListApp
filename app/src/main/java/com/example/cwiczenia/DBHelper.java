package com.example.cwiczenia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cwiczenia.Product;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "productsList.db";
    private static final String TABLE_NAME = "ProductsListDB";


    private static final String COL_ID = "id";
    private static final String COL1 = "name";
    private static final String COL2 = "price";
    private static final String COL3 = "quantity";
    private static final String COL4 = "bought";
    private static final String TABLE_CREATE = " CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + " INTEGER, " + COL3 + " INTEGER, " + COL4 + " BOOLEAN)";
    public DBHelper(Context context){

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //linjka niże była zakomentowana
        String query = "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL1 + " TEXT," + COL2 + " INTEGER," + COL3 + " INTEGER," + COL4 + " BOOLEAN)";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1, product.getName());
        cv.put(COL2, product.getPrice());
        cv.put(COL3, product.getQuantity());
        cv.put(COL4, product.isBought());

        long result = db.insert(TABLE_NAME, null, cv);

    }

    public Cursor getRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        return db.rawQuery(query,null);
    }

    public int deleteProduct(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
    }

    public void updateProduct(Product product, Long id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", product.getName());
        cv.put("price", product.getPrice());
        cv.put("quantity", product.getQuantity());
        cv.put("bought", product.isBought());


        db.update(TABLE_NAME,cv,"id=?",new String[]{String.valueOf(id)});
    }

}
