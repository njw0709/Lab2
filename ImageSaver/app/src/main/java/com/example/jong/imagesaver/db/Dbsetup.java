package com.example.jong.imagesaver.db;

import android.provider.BaseColumns;

/**
 * Created by jong on 9/30/15.
 */
public final class Dbsetup {
    public Dbsetup(){
    }
    public static abstract class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME="SavedImgUrl";
        public static final String COLUMN_NAME_ENTRY_ID="ImgUrlColid";
        public static final String COLUMN_NAME_TITLE="ImgUrl";
    }
}
