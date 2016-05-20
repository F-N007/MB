package com.mb.resource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mb.model.User;

/**
 * Created by N007 on 20-05-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_BNAME = "MBMDatabase.db";
    public static final int DATABASE_VERSION = 1;


    public static final String USERS_TABLE = "USERS";
    private static final String CREATETABLE_USERTABLE = "CREATE TABLE \"Users\" (\"USERNAME\"" +
            " TEXT PRIMARY KEY  NOT NULL ,\"PASS\" TEXT NOT NULL ,\"CREATE_DATE\"" +
            " TEXT ,\"NAME\" TEXT NOT NULL ,\"SURNAME\" TEXT,\"EMAIL\" TEXT,\"TLF\"" +
            " integer DEFAULT (null) ,\"COMMENT\" TEXT,\"PICTURE\" TEXT)";
    private final String USERSTABLE_COL1 = "USERNAME";
    private final String USERSTABLE_COL2 = "PASS";
    private final String USERSTABLE_COL3 = "CREATE_DATE";
    private final String USERSTABLE_COL4 = "NAME";
    private final String USERSTABLE_COL5 = "SURNAME";
    private final String USERSTABLE_COL6 = "EMAIL";
    private final String USERSTABLE_COL7 = "TLF";
    private final String USERSTABLE_COL8 = "COMMENT";
    private final String USERSTABLE_COL9 = "PICTURE";


    public User getUser(String username) {
        String sql = "SELECT USERNAME, PASS, CREATE_DATE, NAME, SURNAME, EMAIL, TLF, COMMENT, " +
                "PICTURE FROM Users WHERE USERNAME = '" + username + "'";
        User user = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToLast()) {
                cursor.moveToFirst();
                user = new User();
                user.setUser_name(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setCreate_date(cursor.getString(2));
                user.setName(cursor.getString(3));
                user.setLast_name(cursor.getString(4));
                user.setEmail(cursor.getString(5));
                user.setTlf(Integer.parseInt(cursor.getString(6)));
                user.setComments(cursor.getString(7));
                user.setProfile_pic(cursor.getString(8));

            }

        }catch (SQLException e){
           log("SQLException: "+e.toString());
        }
        db.close();
        return user;
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERSTABLE_COL1, user.getUser_name());
        contentValues.put(USERSTABLE_COL2, user.getPassword());
        contentValues.put(USERSTABLE_COL3, user.getCreate_date());
        contentValues.put(USERSTABLE_COL4, user.getName());
        contentValues.put(USERSTABLE_COL5, user.getLast_name());
        contentValues.put(USERSTABLE_COL6, user.getEmail());
        contentValues.put(USERSTABLE_COL7, user.getTlf());
        contentValues.put(USERSTABLE_COL8, user.getComments());
        contentValues.put(USERSTABLE_COL9, user.getProfile_pic());
        try {
            long result = db.insert(USERS_TABLE, null, contentValues);
            db.close();
            return result != -1;
        } catch (SQLiteConstraintException e) {
            log("User already exists.");
            db.close();
            return false;
        }
    }



    public DatabaseHelper(Context context) {
        super(context, DATABASE_BNAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE_USERTABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }


    private void log(String msg){
        Log.i("msg","[ DatabaseHelper: "+msg+" ]");
    }
}
