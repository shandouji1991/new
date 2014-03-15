package cn.tjpu.eyuan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBNOTICEAdapter implements DbNoticeService {
    static final String KEY_ROWID = "_id";
    static final String KEY_TEXT = "text";
    static final String KEY_VALIDITY = "validity";
    
    static final String TAG = "DBNOTICEAdapter";

    static final String DATABASE_NAME = "eyw_db";
    static final String DATABASE_TABLE = "notice";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
        "create table notice (_id integer primary key autoincrement," + "text text not null, validity text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase dbnotice;
    
    public DBNOTICEAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    @Override
	public DbNoticeService open() throws SQLException 
    {
        dbnotice = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    /* (non-Javadoc)
	 * @see cn.tjpu.eyuan.db.DbNoticeService#close()
	 */
    @Override
	public void close() 
    {
        DBHelper.close();
    }

    //---insert a contact into the database---

	public long insertNotice(String text,String validity) 
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_TEXT,text);
        initialValues.put(KEY_VALIDITY,validity);
      
        return dbnotice.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---

	public boolean deleteNotice(long rowId) 
    {
        return dbnotice.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---

	public Cursor getAllNotice()
    {
        return dbnotice.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_TEXT,KEY_VALIDITY
                }, null, null, null, null, null);
    }

    //---retrieves a particular contact---

	public Cursor getANotice(long rowId) throws SQLException 
    {
        Cursor mCursor =
                dbnotice.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_TEXT,KEY_VALIDITY
                        }, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---

	public boolean updateNotice(long rowId,String author, String image_url ,
    		String name,String remark,String  reviewer,String section,
    		String source,String text,String time)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TEXT,text);
        args.put(KEY_VALIDITY,time);
        return dbnotice.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
	public int countNotice()
    {
		String sql="select * from notice ";
		Cursor c= dbnotice.rawQuery(sql, null);
       c.moveToLast();
       return Integer.parseInt(c.getString(0));
              
    }

	

}
