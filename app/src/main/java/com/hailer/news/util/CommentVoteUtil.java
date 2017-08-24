package com.hailer.news.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.twitter.sdk.android.core.models.TwitterCollection;

/**
 * Created by moma on 17-8-19.
 */

public class CommentVoteUtil extends SQLiteOpenHelper{
    private static final String CREAT_COMMENT_VOTE = "create table if not exists comment_vote ( id integer primary key )";
    private static final String SELECT_COMMENT_VOTED = "select id from comment_vote where id = ";
    private static final String INSERT_COMMENT_VOTED = "insert into comment_vote ( id ) values ( ? )";
    private static CommentVoteUtil mCommentVote;
    private static final String DB_NAME = "news_app_vote.db";
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
                    mCommentVote = new CommentVoteUtil(context, DB_NAME, null, 1);
                }
            }
        }
        return mCommentVote;
    }

    public boolean isVoted(int commentId) {
        Cursor cousor = this.getReadableDatabase().rawQuery(SELECT_COMMENT_VOTED + commentId, null);
        boolean isVoted = false;
        if (cousor != null && cousor.moveToNext()) {
            isVoted = true;
        }
        return isVoted;
    }

    public void setVoted(int commentId, boolean isVote) {
        if (isVote) {
            //getWritableDatabase().execSQL(INSERT_COMMENT_VOTED , new String[]{Integer.toString(commentId)});
            SQLiteDatabase database = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", commentId);
            database.insert("comment_vote", null, values);
        } else {
            getWritableDatabase().execSQL("delete * from comment_vote where id = " + Integer.toString(commentId));
//            SQLiteDatabase database = getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put("id", commentId);
//            database.insert("comment_vote", null, values);
        }
    }
}
