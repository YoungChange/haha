package com.hailer.news.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moma on 17-8-19.
 */

public class CommentVoteUtil extends SQLiteOpenHelper{
    private static final String CREAT_COMMENT_VOTE = "create table is not exists comment_vote ( id integer primary key )";
    private static CommentVoteUtil mCommentVote;
    private static final String DB_NAME = "MEWS_APP.db";
    private CommentVoteUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAT_COMMENT_VOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static CommentVoteUtil getInstances(Context context) {
        if (context == null) return null;
        if (mCommentVote == null) {
            synchronized (CommentVoteUtil.class) {
                if (mCommentVote == null) {
                    mCommentVote = new CommentVoteUtil(context, DB_NAME, null, 0);
                }
            }
        }
        return mCommentVote;
    }

}
