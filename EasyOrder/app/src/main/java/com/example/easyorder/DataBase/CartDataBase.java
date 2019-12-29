package com.example.easyorder.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CartDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME="EasyorderData.db";
    private static final String TABLE_NAME="CARTORDER";
    private static final int DB_VER=1;
    public CartDataBase(Context context) {
        super(context,DB_NAME,null,DB_VER);
        SQLiteDatabase db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("
                +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"ID_MEAL INTEGER,"
                +"ID_RES_FOOD INTEGER,"
                +"MEAL_NAME TEXT,"+
                "PRICE INTEGER,"+
                "QUANTITY INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addToCart(int ID_MEAL,int ID_RES_FOOD,String MEAL_NAME,int PRICE,int QUANTITY){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID_MEAL",ID_MEAL);
        contentValues.put("ID_RES_FOOD",ID_RES_FOOD);
        contentValues.put("MEAL_NAME",MEAL_NAME);
        contentValues.put("PRICE",PRICE);
        contentValues.put("QUANTITY",QUANTITY);
        String query=String.format("INSERT INTO CARTORDER(ID_MEAL, ID_RES_FOOD, MEAL_NAME, PRICE, QUANTITY) VALUES('%s','%s','%s','%s','%s');",
                ID_MEAL,
                ID_RES_FOOD,
                MEAL_NAME,
                PRICE,
                QUANTITY
        );
        db.execSQL(query);


    }


    public ArrayList<CartModel> GetCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CartModel> order = new ArrayList<CartModel>();
        String query ="SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            int s0=cursor.getInt(cursor.getColumnIndex("ID"));
            int s1= cursor.getInt(cursor.getColumnIndex("ID_MEAL"));
            int s5=cursor.getInt(cursor.getColumnIndex("ID_RES_FOOD"));
            String s2=cursor.getString(cursor.getColumnIndex("MEAL_NAME"));
            int s3= cursor.getInt(cursor.getColumnIndex("PRICE"));
            int s4=    cursor.getInt(cursor.getColumnIndex("QUANTITY"));
            CartModel cc=new CartModel(s0,s5,s1,s2,s3,s4);
            order.add(cc);
        }
        return  order;
    }

    public void removeFromCart(int ID){
        SQLiteDatabase db = getReadableDatabase();
        Log.d("the id for delete is ",ID+"");
        String query = String.format("DELETE FROM CARTORDER WHERE ID='"+ID+"'");
        db.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase db=getReadableDatabase();
        String query =String.format("DELETE FROM CARTORDER");
        db.execSQL(query);
    }
}

