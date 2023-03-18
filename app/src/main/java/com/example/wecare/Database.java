package com.example.wecare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private Context applicationContext;
    private static final String DATABASE_NAME = "UserInfoDB";
    public Database(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
        applicationContext = context;
    }

    private static final String TABLE_LOGIN_INFO = "LoginInfo";
 
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE "+TABLE_LOGIN_INFO+" ( "+KEY_EMAIL+" TEXT PRIMARY KEY, "+KEY_PASSWORD+" TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN_INFO);

        onCreate(sqLiteDatabase);
    }

    public boolean registerUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkUserInfo(email, password)){
            Toast.makeText(applicationContext, "User already exists! Try signing in", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!checkUniqueUser(email)){
            Toast.makeText(applicationContext, "Username already taken!", Toast.LENGTH_SHORT).show();
            return false;
        }

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);

        db.insert(TABLE_LOGIN_INFO, null, values);
        Toast.makeText(applicationContext, "User registered successfully!", Toast.LENGTH_SHORT).show();
        db.close();
        return true;
    }

    public ArrayList<UserInfoModel> fetchData(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<UserInfoModel> model = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_LOGIN_INFO, null);

        while(cursor.moveToNext()){
            UserInfoModel userInfoModel = new UserInfoModel();

            userInfoModel.email = cursor.getColumnName(0);
            userInfoModel.password = cursor.getColumnName(1);

            model.add(userInfoModel);
        }
        return model;
    }

    public boolean checkUniqueUser(String email){
        ArrayList<UserInfoModel> users = fetchData();

        for(int i=0;i<users.size();i++){
            if(email.equals(users.get(i).email)){
                return false;
            }
        }
        return true;
    }

    public boolean checkUserInfo(String email, String password){
        ArrayList<UserInfoModel> users = fetchData();

        for(int i=0;i<users.size();i++){
            UserInfoModel user = users.get(i);
            if(email.equals(user.email) && password.equals(user.password)){
                return true;
            }
        }
        return false;
    }
}
