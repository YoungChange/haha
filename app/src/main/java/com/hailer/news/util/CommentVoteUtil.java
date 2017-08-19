package com.hailer.news.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moma on 17-8-19.
 */

public class CommentVoteUtil extends SQLiteOpenHelper{
    private static final String CREAT_COMMENT_VOTE = "create table comment_vote ( id integer primary key )";
    public CommentVoteUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
