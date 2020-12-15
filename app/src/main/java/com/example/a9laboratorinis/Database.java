package com.example.a9laboratorinis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Db.db";
    public static final String TABLE_NAME = "markers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "length";

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_NAME +
                " (" + COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_LATITUDE + " float," +
                COLUMN_LONGITUDE + " float)"
        );
    }

    public boolean insertMarker(double lat, double lng)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LATITUDE, lat);
        contentValues.put(COLUMN_LONGITUDE, lng);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<LatLng> getMarkers()
    {
        ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            LatLng location = new LatLng(res.getFloat(res.getColumnIndex(COLUMN_LATITUDE)),
                    res.getFloat(res.getColumnIndex(COLUMN_LONGITUDE)));
            arrayList.add(location);
            res.moveToNext();
        }
        return arrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}