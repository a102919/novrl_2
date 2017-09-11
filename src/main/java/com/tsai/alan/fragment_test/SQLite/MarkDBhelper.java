package com.tsai.alan.fragment_test.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alan on 2017/7/22.
 */

public class MarkDBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MySQL";
    private static final int DATABASE_VERSION = 1;
    private static final String mySQL_NAME = "MARK";
    public MarkDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE "+ mySQL_NAME +
                "(_id INTEGER PRIMARY KEY  NOT NULL , " +
                "novel_id INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
